package cn.studio.cc.net.http;

/**
 * 
 * @author CC
 *
 */
public class HttpProxy {
	private int port;

	public HttpProxy(int port) {
		this.port = port;
	}
	
	public void start() {
		Thread t = new Thread(new HttpServerThread());
		t.start();
	}
}
