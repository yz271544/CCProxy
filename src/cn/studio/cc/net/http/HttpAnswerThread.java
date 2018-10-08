package cn.studio.cc.net.http;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import cn.studio.cc.net.http.request.Request;
import cn.studio.cc.net.http.response.Response;
import cn.studio.cc.utils.StreamUtils;

public class HttpAnswerThread implements Runnable {

	private Socket socket;
	
	public HttpAnswerThread(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		Socket socketN = null;
		boolean connect = false;
		try {
			while (true) {
				if (connect) {
					InputStream is = socket.getInputStream();
					OutputStream os = socket.getOutputStream();
					OutputStream osN = socketN.getOutputStream();
					InputStream isN = socketN.getInputStream();
					
					HttpServerIPThread t1 = new HttpServerIPThread(is, osN);
					HttpServerIPThread t2 = new HttpServerIPThread(isN, os);
					Thread tt1 = new Thread(t1);
					tt1.start();
					Thread tt2 = new Thread(t2);
					tt2.start();
					return;
				} else {
					InputStream is = socket.getInputStream();
					Request request = new Request(is);
					if (request.requestLine.requestURL.host.equals("im.qq.com")) {
						System.out.println(1);
					}
					System.out.println(new String(request.request));
					
					byte[] rB = null;
					if (request.requestLine.method.equals("CONNECT")) {
						rB = "HTTP/1.1 200 Connection Established\r\n\r\n".getBytes();
						if (socketN == null) {
							socketN = new Socket(request.requestLine.requestURL.host, request.requestLine.requestURL.port);
						}
						connect = true;
					} else {
						if (socketN == null) {
							socketN = new Socket(request.requestLine.requestURL.host, request.requestLine.requestURL.port);
						}
						OutputStream osN = socketN.getOutputStream();
						osN.write(request.request);
						osN.flush();
						
						InputStream isN = socketN.getInputStream();
						Response response = new Response(isN);
						System.out.println(request.requestLine + ":::::");
						System.out.println(new String(response.response));
						if ("gzip".equals(response.header.getContentEncoding()) && !"chunked".equals(response.header.getTransferEncoding())) {
							GZIPInputStream gzipis = new GZIPInputStream(new ByteArrayInputStream(response.body));
							byte[] bb = StreamUtils.inputToByte(gzipis);
							System.out.println(new String(bb));
							gzipis.close();
						}
						rB = response.response;
					}
					
					OutputStream os = socket.getOutputStream();
					os.write(rB);
					os.flush();
					System.out.println("========");
				}

				//if (!"keep-alive".equals(response.header.getProxyConnection())) {
					//break;
				//}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (!connect) {
				if (socketN != null) {
					try {
						socketN.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				try {
					socket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
