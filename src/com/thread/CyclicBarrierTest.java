package com.thread;

import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierTest {
	static CyclicBarrier c = new CyclicBarrier(2, new A());
	
	public static void main(String[] args) throws Exception {
		new Thread(new Runnable(){
			@Override
			public void run() {
				try {
					Thread.sleep(3000);
					System.out.println(2);
					c.await();
					System.out.println(2.1);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
		System.out.println(1);
		c.await();
		System.out.println(1.1);
	}
	
	static class A implements Runnable{
		@Override
		public void run() {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(3);
		}
	}
}