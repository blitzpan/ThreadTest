package com.thread;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExchangerTest {
	private static final Exchanger<String> exgr = new Exchanger<String>();
	private static ExecutorService threadPool = Executors.newFixedThreadPool(2);
	
	public static void main(String[] args) {
		threadPool.execute(new Runnable(){
			@Override
			public void run() {
				String a = "银行流水A";
				try {
					String b = exgr.exchange(a);
					System.out.println("a中数据交换完毕.a=" + a+";b="+b);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		threadPool.execute(new Runnable(){
			@Override
			public void run() {
				String b = "银行流水B";
				try {
					Thread.sleep(3000);
					String a = exgr.exchange(b);//传递b数据并获得a的数据
					System.out.println("b中数据交换完毕.a=" + a+";b="+b);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
	}
}