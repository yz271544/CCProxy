package cn.studio.cc.net.http;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Header {
	
	public Map<String, List<String>> headerMap;
	public Header(String headerStr) {
		headerMap = new HashMap<>();
		String[] headers = headerStr.split("\r\n");
		for (String header: headers) {
			String[] headerT = header.split(":", 2);
			if (headerT.length != 2) {
				throw new IllegalArgumentException("Header格式不正确: " + header);
			}
			List<String> headerValue = headerMap.get(headerT[0].trim());
			if (headerValue == null) {
				headerValue = new ArrayList<>();
				headerMap.put(headerT[0].trim(), headerValue);
			}
			headerValue.add(headerT[1].trim());
		}
	}
	public Header(byte[] header) {
		this(new String(header));
	}
	
	public int getContentLength() {
		String contentLengthStr = getValue("Content-Length");
		if (contentLengthStr == null) {
			return -1;
		}
		return Integer.parseInt(contentLengthStr);
	}
	public String getProxyConnection() {
		return getValue("Proxy-Connection");
	}
	public String getContentEncoding() {
		return getValue("Content-Encoding");
	}
	public String getTransferEncoding() {
		return getValue("Transfer-Encoding");
	}
	public String getConnection() {
		return getValue("Connection");
	}
	private String getValue(String key) {
		List<String> valueList = headerMap.get(key);
		if (valueList == null || valueList.size() == 0) {
			return null;
		}
		return valueList.get(0);
	}
}
