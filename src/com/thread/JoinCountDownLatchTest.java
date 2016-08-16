package com.thread;

public class JoinCountDownLatchTest {
	public static void main(String[] args) {
		Thread parser1 = new Thread(new Runnable(){
			@Override
			public void run() {
				
			}
		});
		Thread parser2 = new Thread(new Runnable(){
			@Override
			public void run() {
				try {
					Thread.sleep(1000*2);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("parser2 finished!");
			}
		});
		parser1.start();
		parser2.start();
		try {
			parser1.join();
			parser2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("All parser finished!");
	}
}