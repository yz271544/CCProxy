package cn.studio.cc.net.http;

public class HttpServerTools {
	public static void main(String[] args) {
		HttpProxy httpServer = new HttpProxy(8888);
		
		httpServer.start();
	}
}
