package cn.studio.cc.net.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import cn.studio.cc.utils.LogUtils;

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
		try (
				InputStream is = this.is;
				OutputStream os = this.os;
				) {
			while ((bT = is.read()) != -1) {
				os.write(bT);
			}
		} catch (IOException e) {
			LogUtils.error(e);
		}
	}
}
