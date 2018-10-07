package cn.studio.cc.net.http;

public class HttpServerTools {
	public static void main(String[] args) {
		HttpServer httpServer = new HttpServer(8888);
		
		httpServer.start();
	}
}
