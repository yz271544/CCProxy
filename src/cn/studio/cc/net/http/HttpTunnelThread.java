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

public class HttpTunnelThread implements Runnable {

	private InputStream is;
	private OutputStream os;
	
	public HttpTunnelThread(InputStream is, OutputStream os) {
		this.is = is;
		this.os = os;
	}

	@Override
	public void run() {
		int bT = -1;
		try {
			while ((bT = is.read()) != -1) {
				os.write(bT);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
