package cn.studio.cc.net.http.request;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import cn.studio.cc.net.http.Header;
import cn.studio.cc.utils.ArrayUtils;
import cn.studio.cc.utils.StreamUtils;

/**
 * 
 * @author CC
 *
 */
public class Request {

	public byte[] request;
	public RequestLine requestLine;
	public Header header;
	public byte[] body;
	
	public Request(InputStream is) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] b = StreamUtils.inputToByte(is, new byte[] { '\r', '\n', '\r', '\n' });
		baos.write(b);
		int requestLineEndIndex = ArrayUtils.indexOf(b, new byte[] { '\r', '\n' }, 0);
		if (requestLineEndIndex == -1) {
			throw new IllegalArgumentException("无法从输入流中解析RequestLine");
		}
		byte[] requestLineB = Arrays.copyOfRange(b, 0, requestLineEndIndex);
		byte[] headerB = Arrays.copyOfRange(b, requestLineEndIndex + 2, b.length - 4);
		requestLine = new RequestLine(requestLineB);
		header = new Header(headerB);
		
		if (requestLine.method.equals("GET") || requestLine.method.equals("CONNECT")) {
			body = new byte[0];
		} else {
			int contentLength = header.getContentLength();
			if (contentLength >= 0) {
				body = StreamUtils.inputToByte(is, contentLength);
			} else {
				body = StreamUtils.inputToByte(is);
			}
			baos.write(body);
		}
		
		request = baos.toByteArray();
	}
}
