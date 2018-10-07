package cn.studio.cc.net.http;

/**
 * 
 * @author CC
 *
 */
public class HttpProxy {
	
	private int port;
	private int answerThreadNum;

	public HttpProxy(int port, int answerThreadNum) {
		this.port = port;
		this.answerThreadNum = answerThreadNum;
	}
	
	public void start() {
		Thread t = new Thread(new HttpServerThread(port, answerThreadNum), "Http代理监听线程");
		t.start();
	}
}
