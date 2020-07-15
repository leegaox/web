package com.example.learnnetty.daemon;

import java.util.concurrent.TimeUnit;

/**
 * <p>
 *
 * </p>
 *
 * @author gaox
 * @since 2020/4/1
 */
public class DaemonTest {

    public static void main(String[] args) throws IllegalArgumentException, InterruptedException {
        long startTime = System.nanoTime();
        Thread t = new Thread(() -> {
            try {
                TimeUnit.DAYS.sleep(Long.MAX_VALUE);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "Daemon-T");

//        t.setDaemon(true);//启动Daemon线程，无其他线程，JVM进程正常退出
        t.setDaemon(false);//启动非Daemon线程，即使main线程执行完成，JVM进程也不会退出
        t.start();
        TimeUnit.SECONDS.sleep(10);
        System.out.println("系统退出，程序执行" + (System.nanoTime() - startTime) / 1000 / 1000 / 1000 + "s");

    }
}
