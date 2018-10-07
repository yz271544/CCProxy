package cn.studio.cc.net.http;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {
	private int port;

	public HttpServer(int port) {
		this.port = port;
	}
	
	public void start() {
		Thread t = new Thread(new HttpServerThread());
		t.start();
	}
}
