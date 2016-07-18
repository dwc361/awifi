package org.roof.safety.service;

import java.util.Timer;
import java.util.TimerTask;

public class CoreTimer{
	
	public void showTimer() throws Exception {
		
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				try {
					new PreventTamperTask().run();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};

		Timer timer = new Timer(false);
		timer.schedule(task, 1000, 5000);

	}

	public static void main(String[] args) throws Exception {
		PreventTamperUtil preventTamper = new PreventTamperUtil();
//		preventTamper.initFilesToDB();
//		new CoreTimer().showTimer();// 轮循进行比对
		
//		ThreadPool threadPool = new ThreadPool();
//		PreventTamperTask task = new PreventTamperTask();
//		threadPool.runTask(task);
		
	}

}
