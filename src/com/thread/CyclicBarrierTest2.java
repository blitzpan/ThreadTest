package com.thread;

import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierTest2 {
	static CyclicBarrier c = new CyclicBarrier(2);
	
	public static void main(String[] args) {
		Thread t1 = new Thread(new Runnable(){
			@Override
			public void run() {
				try {
					c.await();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}		
		});
		t1.start();
		t1.interrupt();
		try {
			c.await();
		} catch (Exception e) {
			System.out.println(c.isBroken());
			e.printStackTrace();
		}
	}
}