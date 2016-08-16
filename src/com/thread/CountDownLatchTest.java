package com.thread;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchTest {
	public static CountDownLatch cdl = new CountDownLatch(2);
	
	public static void main(String[] args) {
		new Thread(new Runnable(){
			@Override
			public void run() {
				System.out.println(1);
				cdl.countDown();
				System.out.println(2);
				cdl.countDown();
			}
		}).start();
		try {
			cdl.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(3);
	}
}