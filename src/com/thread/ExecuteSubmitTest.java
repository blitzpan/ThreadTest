package com.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExecuteSubmitTest {

	public static void main(String[] args) {
		ExecutorService threadPool = Executors.newFixedThreadPool(1);
		threadPool.execute(new Runnable(){
			@Override
			public void run() {
			}			
		});
		
		Future<String> future = threadPool.submit(new CallableTest());
		try {
			future.get();
		} catch (InterruptedException e) {//处理中断
			e.printStackTrace();
		} catch (ExecutionException e) {//处理无法执行任务异常
			e.printStackTrace();
		}finally{
			threadPool.shutdown();
		}
	}
}

class CallableTest implements Callable<String>{
	@Override
	public String call() throws Exception {
		//your operation
		return "";
	}
}