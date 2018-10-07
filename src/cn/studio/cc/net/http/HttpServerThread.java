package cn.studio.cc.net.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

import cn.studio.cc.net.http.request.Request;
import cn.studio.cc.net.http.request.RequestLine;
import cn.studio.cc.net.http.response.Response;

public class HttpServerThread implements Runnable {
	
	@Override
	public void run() {
		ServerSocket serverSocket;
		try {
			serverSocket = new ServerSocket(8888);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		while (true) {
			try {
				System.out.println("wait...");
				
				Socket socket = serverSocket.accept();
				HttpServerIThread it = new HttpServerIThread(socket);
				Thread t = new Thread(it);
				t.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
