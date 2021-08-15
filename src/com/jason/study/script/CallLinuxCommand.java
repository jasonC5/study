package com.jason.study.script;

import java.io.IOException;
public class CallLinuxCommand {
    private static final String COMMAND = "/app/support/ffmpeg-4.4/bin/ffmpeg  -f rtsp -rtsp_transport tcp -i \"rtsp://admin:DXH2021d@39.96.113.162:20001/Streaming/tracks/101?starttime=20210812T105000z&endtime=20210812T115000z\" -codec copy -f flv -an rtmp://39.104.175.3:1935/vod/test";

    public static void main(String[] args) {
        String scrept = "/app/script/putStream.sh";   //程序路径
        String source = "admin:DXH2021d@39.96.113.162:20001/Streaming/tracks/101";
        String startTime = "20210812T105000z";
        String endTime = "20210812T115000z";
        String name = "streamTest";
        StringBuilder param = new StringBuilder();
        param.append(" ")
                .append(source).append(" ")
                .append(startTime).append(" ")
                .append(endTime).append(" ")
                .append(name);
        String command = "/bin/sh " + scrept + param.toString();
        System.out.println(command);
        try {
            Runtime.getRuntime().exec(command).waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
