package cn.studio.cc.net.http;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import cn.studio.cc.utils.LogUtils;

/**
 * http response thread factory
 * @author CC
 *
 */
public class HttpAnswerThreadFactory implements ThreadFactory {
	/** thread number */
	private static final AtomicInteger POOL_NUMBER = new AtomicInteger(1);
	/** thread group */
	private final ThreadGroup group;
	/** thread number */
	private final AtomicInteger threadNumber = new AtomicInteger(1);
	/** thread name prefix */
	private final String namePrefix;

	public HttpAnswerThreadFactory() {
		group = new ThreadGroup(Thread.currentThread().getThreadGroup(), "Http reponse thread group") {
			@Override
			public void uncaughtException(Thread t, Throwable e) {
				super.uncaughtException(t, e);
				/* unhandled exceptions of threads in a thread group are recorded */
				LogUtils.error(e, t.getName() + "occurred an uncaught exception");
			}
		};
		namePrefix = "Http reponse thread(" + POOL_NUMBER.getAndIncrement() + ")";
	}
	
	@Override
	public Thread newThread(Runnable r) {
		Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
		/* creating daemon threads is prohibited */
		if (t.isDaemon()) {
			t.setDaemon(false);
		}
		/* custom priorities are prohibited */
		if (t.getPriority() != Thread.NORM_PRIORITY) {
			t.setPriority(Thread.NORM_PRIORITY);
		}
		return t;
	}
}
