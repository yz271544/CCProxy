package cn.studio.cc.net.http;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import cn.studio.cc.utils.LogUtils;

/**
 * http响应线程工厂
 * @author CC
 *
 */
public class HttpAnswerThreadFactory implements ThreadFactory {
	/** 线程池编号 */
	private static final AtomicInteger POOL_NUMBER = new AtomicInteger(1);
	/** 线程所属线程组 */
	private final ThreadGroup group;
	/** 线程编号 */
	private final AtomicInteger threadNumber = new AtomicInteger(1);
	/** 线程名称前缀 */
	private final String namePrefix;

	public HttpAnswerThreadFactory() {
		group = new ThreadGroup(Thread.currentThread().getThreadGroup(), "Http响应线程组") {
			@Override
			public void uncaughtException(Thread t, Throwable e) {
				super.uncaughtException(t, e);
				/* 线程组中线程的未处理异常均进行记录 */
				LogUtils.error(e, t.getName() + "发生异常");
			}
		};
		namePrefix = "Http响应线程(" + POOL_NUMBER.getAndIncrement() + ")";
	}
	
	@Override
	public Thread newThread(Runnable r) {
		Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
		/* 禁止创建守护线程 */
		if (t.isDaemon()) {
			t.setDaemon(false);
		}
		/* 禁止自定义优先级 */
		if (t.getPriority() != Thread.NORM_PRIORITY) {
			t.setPriority(Thread.NORM_PRIORITY);
		}
		return t;
	}
}
