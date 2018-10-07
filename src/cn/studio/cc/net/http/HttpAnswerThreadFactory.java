package cn.studio.cc.net.http;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import cn.studio.cc.utils.LogUtils;

public class HttpAnswerThreadFactory implements ThreadFactory {
	private static final AtomicInteger POOL_NUMBER = new AtomicInteger(1);
	private final ThreadGroup group;
	private final AtomicInteger threadNumber = new AtomicInteger(1);
	private final String namePrefix;

	public HttpAnswerThreadFactory() {
		group = new ThreadGroup(Thread.currentThread().getThreadGroup(), "Http响应线程组") {
			@Override
			public void uncaughtException(Thread t, Throwable e) {
				super.uncaughtException(t, e);
				LogUtils.error(e, t.getName() + "发生异常");
			}
		};
		namePrefix = "Http响应线程(" + POOL_NUMBER.getAndIncrement() + ")";
	}
	
	@Override
	public Thread newThread(Runnable r) {
		Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
		if (t.isDaemon()) {
			t.setDaemon(false);
		}
		if (t.getPriority() != Thread.NORM_PRIORITY) {
			t.setPriority(Thread.NORM_PRIORITY);
		}
		return t;
	}
}
