package com.priorityTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * ClassName: PriorityTest 
 * @Description: 关于线程优先级的一个测试
 * @date 2016年6月23日
 */
public class PriorityTest {
	private static volatile boolean notStart = true;
	private static volatile boolean notEnd = true;
	
	public static void main(String[] args) throws Exception{
		List<Job> jobs = new ArrayList<Job>();
		for(int i=0; i<10; i++){
			int priority = i<5?Thread.MIN_PRIORITY : Thread.MAX_PRIORITY;
			Job job = new Job(priority);
			jobs.add(job);
			Thread thread = new Thread(job, "Thread:" + i);
			thread.setPriority(priority);
			thread.start();
		}
		notStart = false;
		TimeUnit.SECONDS.sleep(10);
		notEnd = false;
		long minAvg = 0;
		long maxAvg = 0;
		for(Job job : jobs){
			System.out.println("Job Priority:" + job.priority + "-" + job.jobCount);
			if(job.priority < 5){
				minAvg += job.jobCount;
			}else{
				maxAvg += job.jobCount;
			}
		}
		System.out.println("最小平均：" + minAvg/5);
		System.out.println("最大平均：" + maxAvg/5);
	}
	static class Job implements Runnable{
		private int priority;
		private long jobCount;
		
		private Job(int priority){
			this.priority = priority;
		}
		
		public void run() {
			while(notStart){
				Thread.yield();
			}
			while(notEnd){
				Thread.yield(); //不应该所有的线程都是暂停么？为什么有的线程往下面执行了？
				jobCount++;
				System.out.println(Thread.currentThread().getName() + "=" + jobCount);
			}
		}
	}
}