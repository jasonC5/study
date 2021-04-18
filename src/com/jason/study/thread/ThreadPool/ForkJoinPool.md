比较难【恶心】的点：

​	1. 位运算

​	2. 在if里面做临时变量赋值

​	3. CAS

 4. Volatile

    ```java
        volatile long ctl;  
    一个Long64位，拆成4个16位使用
    高32位的高16位：活跃线程数---第一个16位	AC	= 16个1-设置的工作线程数【只要第一位为0说明溢出】
    高32位的低16位：总线程数---第二个16位		TC=  16个1-设置的最大线程数【只要第一位为0说明溢出】
    低32位的高16位：版本计数器 version count  --第三个16位置	VC
    低32位的低16位：INACTIVE线程[活跃线程]在wqs的下标	
        
各种掩码：
        //取高三十二位和低三十微微
        private static final long SP_MASK    = 0xffffffffL;     //32个0~32个1
        private static final long UC_MASK    = ~SP_MASK;		//32个1~32个0
    	private static final int  AC_SHIFT   = 48;//
        private static final long AC_UNIT    = 0x0001L << AC_SHIFT;//左移48位，第49位为1，高32位的高16位的初始位置，活跃线程数+-单位
        private static final long AC_MASK    = 0xffffL << AC_SHIFT;//16个1~48个0，高16位取数掩码，取活跃线数
        private static final int  TC_SHIFT   = 32;
        private static final long TC_UNIT    = 0x0001L << TC_SHIFT;//第33位为1，高32位的低16位的+-单位=全部线程数加减单位
        private static final long TC_MASK    = 0xffffL << TC_SHIFT;//16个0~16个1~32个0，取高32位的低16位，全部线程数掩码
        private static final long ADD_WORKER = 0x0001L << (TC_SHIFT + 15); // sign 可以看做高32位的低16位的符号位，为1代表没有溢出，还有位置可以新增线程，为0代表已经达到最大线程数【溢出】，不能新增工作线程
    
    
    ```
    
    

submit()

```java
//
    public ForkJoinTask<?> submit(Runnable task) {
        if (task == null)	//日常判空
            throw new NullPointerException();
        ForkJoinTask<?> job;
        if (task instanceof ForkJoinTask<?>) // avoid re-wrap
            job = (ForkJoinTask<?>) task;
        else
            job = new ForkJoinTask.AdaptedRunnableAction(task);//包装
        externalPush(job);//
        return job;
    }

```

externalPush()

```java
    final void externalPush(ForkJoinTask<?> task) {
        WorkQueue[] ws; WorkQueue q; int m;
        int r = ThreadLocalRandom.getProbe();	//随机数种子
        int rs = runState;						// lockable status
        if (
            (ws = workQueues) != null &&	
            (m = (ws.length - 1)) >= 0 &&//m = ws.length - 1 	2^n-1  0+(n-1)1
            (q = ws[m & r & SQMASK]) != null &&//随机数种子对应的下标的queue不是空  【SQMASK=1111110，0保证偶数-外部提交队列，整体大小保证边界】
            r != 0 &&//不是下标为0的
            rs > 0 &&//说明不是shutdown
            U.compareAndSwapInt(q, QLOCK, 0, 1)//上锁
           ) {
            ForkJoinTask<?>[] a; int am, n, s;
            if ((a = q.array) != null &&//外部提交队列不为空
                (am = a.length - 1) > (n = (s = q.top) - q.base)) {//而且队列未满
                int j = ((am & s) << ASHIFT) + ABASE;//找到外部提交队列下一个空位的偏移量
                U.putOrderedObject(a, j, task);//将task放入外部提交队列
                U.putOrderedInt(q, QTOP, s + 1);//top指针+1
                U.putIntVolatile(q, QLOCK, 0);//QLOCK重置锁？，volatile保证上面两个写操作也线程可见的
                if (n <= 1) //添加成功，但是没有工作线程，创建线程并执行
                    signalWork(ws, q);//创建或者唤醒一个工作线程
                return;
            }
            U.compareAndSwapInt(q, QLOCK, 1, 0);//放锁
        }
        externalSubmit(task);
    }
```

signalWork()//创建或者唤醒一个工作线程

```java
final void signalWork(WorkQueue[] ws, WorkQueue q) {
        long c; int sp, i; WorkQueue v; Thread p;
        while ((c = ctl) < 0L) {                       // ctl初始化的时候，前16位：1111……留了最大线程数的空，只要活跃线程数达到了最大线程数，就会溢出，变为正数，符号位没有溢出，说明没有达到最大值
            if ((sp = (int)c) == 0) {                  // ctl的低32位代表了INACTIVE数 =0代表  没有空闲线程，那么就添加一个工作线程
                if ((c & ADD_WORKER) != 0L)            // add_worker=0x0001L << (TC_SHIFT【32】 + 15),可以看做高32位的低16位的符号位，和上面一样，不等于0说明还有空，没溢出，工作线程数没有达到最大值
                    tryAddWorker(c);//那么就添加工作线程线程
                break;
            }
            if (ws == null)                            //workQ为空，没有start或者已经terminated
                break;
            if (ws.length <= (i = sp & SMASK))         // SMASK = 0xffff  取CTL的低三十二位的低16位，存放了INACTIVE线程在wqs的下标，放到了i里面
                break;
            if ((v = ws[i]) == null)                   // INACTIVE线程的WS为空了，正在terminating
                break;
            int vs = (sp + SS_SEQ) & ~INACTIVE;        // SS_SEQ = 1 << 16; 低32位的高16位+1=版本数+1【version count】，与上一个 INACTIVE = 1 << 31 取反，0~31个1，符号位置为0【为什么符号位要置0？？？防止符号位溢出？】 是下一个scanState  【versioned, <0: inactive; odd:scanning】
            int d = sp - v.scanState;                  //=0表示版本号和活跃线程数未发生变化
            long nc = (UC_MASK & (c + AC_UNIT)) | (SP_MASK & v.stackPred);//next ctl 活跃线线程数+1【为什么活跃线程数+1，TC不+1？】
            if (d == 0 && U.compareAndSwapLong(this, CTL, c, nc)) {//cas替换
                v.scanState = vs;                      // 版本号更新
                if ((p = v.parker) != null)//唤醒工作线程
                    U.unpark(p);
                break;
            }
            if (q != null && q.base == q.top)          // 队列为空
                break;
        }
    }
```

tryAddWorker()// 尝试添加工作线程

```java
    private void tryAddWorker(long c) {
        boolean add = false;
        do {
            long nc = ((AC_MASK & (c + AC_UNIT)) |
                       (TC_MASK & (c + TC_UNIT)));//下一个ctl，活跃线程数+1，总线程数+1
            if (ctl == c) {//ctl未发生变化
                int rs, stop;                 // check if terminating
                //CAS上锁并先查线程池状态是否为STOP
                if ((stop = (rs = lockRunState()) & STOP) == 0)
                    //修改ctl为nextctl
                    add = U.compareAndSwapLong(this, CTL, c, nc);//个人理解：这里体现出来了使用一个Long来控制4个信息的好处，原子性好控制
                unlockRunState(rs, rs & ~RSLOCK);//放锁
                if (stop != 0)//如果线程都stop了，那么就不新增工作线程了
                    break;
                if (add) {//上面修改ctl成功了，两个线程数都+1了，
                    createWorker();//添加工作线程
                    break;
                }
            }
        } while (((c = ctl) & ADD_WORKER) != 0L && (int)c == 0);//添加工作线程未成功，校验是否能添加线程【& ADD_WORKER) != 0L=高三十位的低16位首位!=0，没有溢出，Total Count未达到最大线程数，可以添加线程】，【 (int)c == 0，低32位为0，说明没有被别的线程初始化】
    }
```

createWorker() 创建工作线程

```java
 private boolean createWorker() {
        ForkJoinWorkerThreadFactory fac = factory;
        Throwable ex = null;
        ForkJoinWorkerThread wt = null;
        try {
            if (fac != null && (wt = fac.newThread(this)) != null) {//创建线程成功
                wt.start();//启动
                return true;
            }
        } catch (Throwable rex) {
            ex = rex;
        }
        deregisterWorker(wt, ex);//线程创建成功走不到这， 如果创建出现异常，将ctl前面加的1回滚
        return false;
    }
```

ForkJoinWorkerThreadFactory 线程工厂--是一个接口，两个实现：DefaultForkJoinWorkerThreadFactory、InnocuousForkJoinWorkerThreadFactory

默认线程工厂：DefaultForkJoinWorkerThreadFactory

```java
static final class DefaultForkJoinWorkerThreadFactory
    implements ForkJoinWorkerThreadFactory {
    public final ForkJoinWorkerThread newThread(ForkJoinPool pool) {
        return new ForkJoinWorkerThread(pool); // 直接new ForkJoinWorkerThread
    }
}
//
protected ForkJoinWorkerThread(ForkJoinPool pool) {
    // Use a placeholder until a useful name can be set in registerWorker
    super("aForkJoinWorkerThread");
    this.pool = pool;//持有外部的ForkJoinPool的引用
    this.workQueue = pool.registerWorker(this);//将自己注册到FJP中，保存到数组奇数位中
}
```

//registerWorker(this)  线程注册到FJP中

```java
    final WorkQueue registerWorker(ForkJoinWorkerThread wt) {
        UncaughtExceptionHandler handler;
        wt.setDaemon(true);                           // ForkJoinWorkerThread为守护线程
        if ((handler = ueh) != null)
            wt.setUncaughtExceptionHandler(handler);//塞入异常捕获器
        WorkQueue w = new WorkQueue(this, wt);//创建一个内部窃取队列，外部提交队列的创建形式为： new WorkQueue(this, null)
        int i = 0;                                    //索引下标
        int mode = config & MODE_MASK;//this.config = (parallelism & SMASK) | mode; 并行度+mode；从config中取得mode  【FIFO、LIFO】
        int rs = lockRunState();//上锁，拿runState
        try {
            WorkQueue[] ws; int n;                    // skip if no array
            if ((ws = workQueues) != null && (n = ws.length) > 0) {//日常判空，wqs是不是空的，并给临时变量赋值
                int s = indexSeed += SEED_INCREMENT;  // indexSeed = 0 SEED_INCREMENT = 0x9e3779b9 --大质数，减少hash碰撞 
                int m = n - 1;//n是2的倍数，-1，后面都变成1，作为掩码使用，位操作代替取余操作
                i = ((s << 1) | 1) & m;               // 取余并置换为奇数，找存放的下标
                if (ws[i] != null) {                  // 发生了碰撞
                    int probes = 0;                   // step by approx half n
                    int step = (n <= 4) ? 2 : ((n >>> 1) & EVENMASK) + 2;//发生碰撞二次寻址EVENMASK = 0xfffe ，获取一个偶数
                    while (ws[i = (i + step) & m] != null) {//死循环次寻址
                        //记录寻址次数，并判断如果寻址次数都大于数组长度了，达到了极限，扩容
                        if (++probes >= n) {
                            workQueues = ws = Arrays.copyOf(ws, n <<= 1);//扩容，拷贝
                            m = n - 1;//重新获取掩码
                            probes = 0;//重置寻址次数
                        }
                    }
                }
                w.hint = s;                           // s作为随机数保存在wq的hint中
                w.config = i | mode;				  //索引下标+模式
                w.scanState = i;                      //版本号为0，直接保存索引下标，并且scanState为volatile，这里写入可见，也保证了上面两个写操作也可见【有序性，内存屏障】，且不会和下面的ws[i]赋值发生重排序，这里scanState为奇数，所以要开始扫描获取任务并执行了
                ws[i] = w;//放入wqs中
            }
        } finally {
            unlockRunState(rs, rs & ~RSLOCK);//放锁
        }
        wt.setName(workerNamePrefix.concat(Integer.toString(i >>> 1)));//命名
        return w;
    }
```

lockRunState() CAS上锁

```java
    private int lockRunState() {
        int rs;
        return ((((rs = runState) & RSLOCK) != 0 ||		//runState 第一位=1【说明是本线程上的锁，重入？】
                 !U.compareAndSwapInt(this, RUNSTATE, rs, rs |= RSLOCK)) ? //或者CAS把第一位赋成1
                awaitRunStateLock() : rs);//上面两步有一步成功就返回rs，失败的话走awaitRunStateLock()等待获取锁 此时 runState的lock位【第一位】为0且尝试改为1失败说明被别的线程从0改为了1，别的线程上的锁
    }
```

awaitRunStateLock() 等待获取锁

```java
private int awaitRunStateLock() {
        Object lock;
        boolean wasInterrupted = false;
    	//关在死循环里面竞争所，什么时候获取了什么时候出去
        for (int spins = SPINS, r = 0, rs, ns;;) {//初始化spins=1，r=0,rs=0,ns=0
            if (((rs = runState) & RSLOCK) == 0) {//没有锁了，可以去竞争锁了
                if (U.compareAndSwapInt(this, RUNSTATE, rs, ns = rs | RSLOCK)) {//rs第一位改成1，CAS竞争锁
                    if (wasInterrupted) {//拿到锁了，但是有中断
                        try {
                            Thread.currentThread().interrupt();//线程中断
                        } catch (SecurityException ignore) {
                        }
                    }
                    return ns;//拿到锁了，没有中断，返回next state，=runState+1
                }
            }
            else if (r == 0)//第一次进小黑屋，初始化随机数种子
                r = ThreadLocalRandom.nextSecondarySeed();
            else if (spins > 0) {//不是第一次进来这里了，之前初始化过随机种子
                r ^= r << 6; r ^= r >>> 21; r ^= r << 7; // xorshift  // 异或随机数
                if (r >= 0)
                    --spins;
            }
            else if ((rs & STARTED) == 0 || (lock = stealCounter) == null)
                //runState运行位为0，没有在运行，或者stealCounter=null,说明在初始化，但是这个状态时间特别短暂，没有不要睡眠，只是让出CPU，等待初始化
                Thread.yield();   // initialization race
            else if (U.compareAndSwapInt(this, RUNSTATE, rs, rs | RSIGNAL)) {//第二位改为1，提醒其他线程需要唤醒它
                synchronized (lock) {//同步锁，
                    if ((runState & RSIGNAL) != 0) {//如果没有其他线程改过RSIGNAL位，那就取睡眠了
                        try {
                            lock.wait();
                        } catch (InterruptedException ie) {//终端信号
                            if (!(Thread.currentThread() instanceof
                                  ForkJoinWorkerThread))
                                wasInterrupted = true;//被中断了
                        }
                    }
                    else
                        lock.notifyAll();//被其他线程改了，唤醒所有线程
                }
            }
        }
    }
```





externalSubmit  //【提交任务】

```java
private void externalSubmit(ForkJoinTask<?> task) {
        int r;                                    // initialize caller's probe
        if ((r = ThreadLocalRandom.getProbe()) == 0) {//获取随机数种子
            ThreadLocalRandom.localInit();
            r = ThreadLocalRandom.getProbe();
        }
        for (;;) {
            WorkQueue[] ws; WorkQueue q; int rs, m, k;
            boolean move = false;
            if ((rs = runState) < 0) {//SHUTDOWN了，中断
                tryTerminate(false, false);     // help terminate
                throw new RejectedExecutionException();//拒绝执行
            }
            else if ((rs & STARTED) == 0 ||     // initialize
                     ((ws = workQueues) == null || (m = ws.length - 1) < 0)) {//还没初始化，那么执行初始化
                // 注意：如果该分支不执行，但是由于||运算符的存在，这里ws和m变量已经初始化
                int ns = 0;
                rs = lockRunState();//初始化，先上锁
                try {
                    if ((rs & STARTED) == 0) {//在判断一遍，没有初始化
                        U.compareAndSwapObject(this, STEALCOUNTER, null,
                                               new AtomicLong());//STEALCOUNTER 初始化
                        // create workQueues array with size a power of two
                        int p = config & SMASK; // 并行度，工作线程的数量
                        int n = (p > 1) ? p - 1 : 1;//初始化wqs大小
                        n |= n >>> 1; n |= n >>> 2;  n |= n >>> 4;
                        n |= n >>> 8; n |= n >>> 16; n = (n + 1) << 1;//>p且离p最近的一个2的次方
                        workQueues = new WorkQueue[n];
                        ns = STARTED;//已启动
                    }
                } finally {
                    unlockRunState(rs, (rs & ~RSLOCK) | ns);//放锁并改为已启动
                }
            }
              // 在上一个分支执行完毕后，第二次循环将会到达这里。由于咱们的算法是用了一个全局队列:workQueues来存储两个队列：外部提交队列、内部工作队列（任务窃取队列），那么这时，应该去找到我这个任务放在哪个外部提交队列里面。就是通过上面获取的随机种子r，来找到应该放在哪里？SQMASK = 1111110，所以由SQMASK的前面的1来限定长度，末尾的0来表明，外部提交队列一定在偶数位
            else if ((q = ws[k = r & m & SQMASK]) != null) {//SQMASK 111 1110 获取范围内的偶数下标
                //随机到一个外部队列，没上锁，尝试上把锁
                if (q.qlock == 0 && U.compareAndSwapInt(q, QLOCK, 0, 1)) {
                    ForkJoinTask<?>[] a = q.array;//拿到wq中的任务数组
                    int s = q.top;//top指针
                    boolean submitted = false; // initial submission or resizing
                    try {                      // locked version of push
                        //数组不为空，且没满，或者能扩容growArray()跑去扩容了
                        if ((a != null && a.length > s + 1 - q.base) ||
                            (a = q.growArray()) != null) {
                            //放数据
                            int j = (((a.length - 1) & s) << ASHIFT) + ABASE;//拿到栈顶的绝对地址
                            U.putOrderedObject(a, j, task);//CAS指定位置放入task
                            U.putOrderedInt(q, QTOP, s + 1);//栈顶指针+1
                            submitted = true;//已经提交了
                        }
                    } finally {
                        U.compareAndSwapInt(q, QLOCK, 1, 0);//放锁，QLOCK是volatile的，这个操作CAS出去，那么qlock线程可见，上面的task和q必定线程可见工作线程执行
                    if (submitted) {
                        //放入外部队列成功，唤醒或者创建一个工作线程来执行
                        signalWork(ws, q);
                        return;
                    }
                }
                move = true;                   //这个线程很忙，标记一下下次循环的时候重新寻找别的队列
            }
            else if (((rs = runState) & RSLOCK) == 0) {  // 如果找到的这个wq没有被创建，而且别的线程没有持有锁，那么创建外部队列
                q = new WorkQueue(this, null);//new一个外部提交队列，第二个参数是线程，由于是外部提交队列没有绑定的线程，所以是null
                q.hint = r;//保存随机数
                q.config = k | SHARED_QUEUE;//下标+mode。这里mode=SHARED_QUEUE=1 << 31，为符号位，所以是负数，表名当前队列是共享队列，（外部提交队列）
                q.scanState = INACTIVE;//1 << 31   由于当前wq并没有进行扫描任务，所以扫描状态位无效状态INACTIVE
                rs = lockRunState();           //上锁wq放入wqs
                //线程池在运行&&队列不是空&&下标没溢出&&要放的位置是空的【没有别的线程抢占】
                if (rs > 0 &&  (ws = workQueues) != null &&
                    k < ws.length && ws[k] == null)
                    ws[k] = q;                 // 放入
                unlockRunState(rs, rs & ~RSLOCK);
            }
            else// // 发生竞争时，让当前线程选取其他的wq来重试
                move = true;                   // move if busy
            if (move)
                r = ThreadLocalRandom.advanceProbe(r);//重新计算随机数
        }
    }
```

