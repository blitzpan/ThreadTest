package com.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class SemaphoreTest {
	private static final int THREAD_COUNT = 30;
	private static ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_COUNT);
	private static Semaphore s = new Semaphore(10);
	
	public static void main(String[] args) {
		for(int i=0; i<THREAD_COUNT; i++){
			threadPool.execute(new MyThread(i, s));
		}
		threadPool.shutdown();
	}
	
}
class MyThread implements Runnable{
	int c = 0;
	Semaphore s;
	public MyThread(int c, Semaphore s) {
		this.c = c;
		this.s = s;
	}
	@Override
	public void run() {
		try {
			System.out.println(c + " begin:");
			s.acquire();
			System.out.println("saveDate=" + c);
			Thread.sleep(3000);
			s.release();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}