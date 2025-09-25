package cn.studio.cc.net.http.response;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import cn.studio.cc.net.http.Header;
import cn.studio.cc.net.http.request.RequestLine;
import cn.studio.cc.utils.ArrayUtils;
import cn.studio.cc.utils.StreamUtils;

public class Response {

	public byte[] response;
	public StatusLine statusLine;
	public Header header;
	public byte[] body;
	
	public Response(InputStream is) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] b = StreamUtils.inputToByte(is, new byte[] { '\r', '\n', '\r', '\n' });
		baos.write(b);
		int statusLineEndIndex = ArrayUtils.indexOf(b, new byte[] { '\r', '\n' }, 0);
		if (statusLineEndIndex == -1) {
			throw new IllegalArgumentException("can not parser StatusLine from input stream");
		}
		byte[] statusLineB = Arrays.copyOfRange(b, 0, statusLineEndIndex);
		byte[] headerB = Arrays.copyOfRange(b, statusLineEndIndex + 2, b.length - 4);
		statusLine = new StatusLine(statusLineB);
		header = new Header(headerB);
		
		int contentLength = header.getContentLength();
		if ("chunked".equals(header.getTransferEncoding())) {
			body = StreamUtils.inputToByteByChunked(is);
		} else if (contentLength >= 0) {
			body = StreamUtils.inputToByte(is, contentLength);
		} else {
			body = StreamUtils.inputToByte(is);
		}
		//System.out.println("body.length=" + body.length + " contentLength=" + contentLength);
		baos.write(body);
		
		response = baos.toByteArray();
	}
}
