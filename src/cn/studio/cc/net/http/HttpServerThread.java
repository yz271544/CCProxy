package cn.studio.cc.net.http;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import cn.studio.cc.utils.LogUtils;

public class HttpServerThread implements Runnable {

	private int port;
	private int answerThreadNum;

	public HttpServerThread(int port, int answerThreadNum) {
		this.port = port;
		this.answerThreadNum = answerThreadNum;
	}

	private ExecutorService httpThreadPool;
	@Override
	public void run() {
		ThreadFactory threadFactory = new HttpAnswerThreadFactory();
		httpThreadPool = Executors.newFixedThreadPool(answerThreadNum, threadFactory);

		try (
				ServerSocket serverSocket = new ServerSocket(port);
				) {
			while (true) {
				try {
					LogUtils.debug("µÈ´ýÇëÇó");
					Socket socket = serverSocket.accept();
					HttpServerIThread it = new HttpServerIThread(socket);
					httpThreadPool.execute(it);
				} catch (IOException e) {
					LogUtils.error(e);
				}
			}
		} catch (IOException e) {
			LogUtils.error(e);
		}
	}
}
