package com.jason.study.test;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CalendarTest {

    public static void main(String[] args) {
//        System.out.println("thisMonth"+getDayOfMonth(0));
//        System.out.println("nextMonth"+getDayOfMonth(-1));
//        System.out.println("lastMonth"+getDayOfMonth(1));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("this Month start " + formatter.format(getBeginDayOfMonth(0)));
        System.out.println("this Month end  " + formatter.format(getEndDayOfWeek(0)));
        System.out.println("last Month start " + formatter.format(getBeginDayOfMonth(1)));
        System.out.println("last Month end  " + formatter.format(getEndDayOfWeek(1)));
    }

    private static int getDayOfMonth(int last) {
        Calendar aCalendar = Calendar.getInstance(Locale.CHINA);
        if (last != 0) {
            aCalendar.add(Calendar.MONTH, 0 - last);
        }
        return aCalendar.getActualMaximum(Calendar.DATE);
    }

    // 获取本月的开始时间
    public static Date getBeginDayOfMonth(int last) {
        Date date = new Date();
        if (date == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        //加上前推的月份
        if (last != 0) {
            cal.add(Calendar.WEEK_OF_YEAR, 0 - last);
        }
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 1, 0, 0, 0);//设置当月的起始时间
        return getDayStartTime(cal.getTime());
    }

    // 获取本月的结束时间
    public static Date getEndDayOfWeek(int last) {
        Date now = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        //加上前推的月份
        if (last != 0) {
            cal.add(Calendar.WEEK_OF_YEAR, 0 - last);
        }
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        return getDayEndTime(cal.getTime());
    }


    // 获取某个日期的开始时间
    public static Timestamp getDayStartTime(Date d) {
        Calendar calendar = Calendar.getInstance();
        if (null != d)
            calendar.setTime(d);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return new Timestamp(calendar.getTimeInMillis());
    }

    // 获取某个日期的结束时间
    public static Timestamp getDayEndTime(Date d) {
        Calendar calendar = Calendar.getInstance();
        if (null != d)
            calendar.setTime(d);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return new Timestamp(calendar.getTimeInMillis());
    }

}
