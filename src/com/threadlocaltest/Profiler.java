package com.threadlocaltest;

import java.util.concurrent.TimeUnit;

public class Profiler {
	private static final ThreadLocal<Long> TIME_THREADLOCAL = new ThreadLocal<Long>(){
		@Override
		protected Long initialValue() {
			return Thread.currentThread().getId();
		}
	};
	public static final void begin(){
		TIME_THREADLOCAL.set(System.currentTimeMillis());
	}
	public static final long end(){
		return System.currentTimeMillis() - TIME_THREADLOCAL.get();
	}
	
	public static void main(String[] args) {
		Profiler.begin();
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(Profiler.end());
	}
}