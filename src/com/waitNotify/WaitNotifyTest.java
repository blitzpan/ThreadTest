package com.waitNotify;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

//wait、notify测试
public class WaitNotifyTest {
	static boolean flag = true;
	static Object lock = new Object();
	
	public static void main(String[] args) {
		Thread waitThread = new Thread(new Wait(), "waitThread");
		waitThread.start();
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Thread notifyThread = new Thread(new Notify(), "notifyThread");
		notifyThread.start();
	}
	
	static class Wait implements Runnable{
		public void run(){
			//加锁
			synchronized(lock){
				//当条件不满足的时候，继续wait，同时释放lock锁
				while(flag){
					try {
						System.out.println(Thread.currentThread()+" flag is true.wait@" + new SimpleDateFormat("HH:mm:ss").format(new Date()));
						lock.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				//条件满足的时候，完成工作
				System.out.println(Thread.currentThread()+" flag is false.running@" + new SimpleDateFormat("HH:mm:ss").format(new Date()));
			}
		}
	}
	
	static class Notify implements Runnable{
		public void run(){
			//加锁
			synchronized(lock){
				//拥有了lock锁，进行通知，通知时不会释放lock锁
				//只有当前线程释放了lock锁之后，wait线程才能从wait方法中返回
				System.out.println(Thread.currentThread()+" hold lock.notify@" + new SimpleDateFormat("HH:mm:ss").format(new Date()));
				lock.notifyAll();
				flag = false;
				try {
					TimeUnit.SECONDS.sleep(5);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			//再次加锁
			synchronized(lock){
				System.out.println(Thread.currentThread()+" hold lock again.sleep@" + new SimpleDateFormat("HH:mm:ss").format(new Date()));
				try {
					TimeUnit.SECONDS.sleep(5);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}