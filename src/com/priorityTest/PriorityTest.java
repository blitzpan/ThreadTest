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
		/*
		Job Priority:1-2305868
		Job Priority:1-1063145
		Job Priority:1-3303310
		Job Priority:1-6943207
		Job Priority:1-5033111
		Job Priority:10-5216364
		Job Priority:10-5472412
		Job Priority:10-4568884
		Job Priority:10-4714181
		Job Priority:10-9458990
		最小平均：3729728
		最大平均：5886166
		*/
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
				Thread.yield();
				jobCount++;
			}
		}
	}
}