package cn.studio.cc.net.http.request;

public class RequestURL {
	public static final String PROTOCOL_DEFAULT = "http";
	public final String protocol;
	
	public final String host;
	
	public static final int PORT_HTTP_DEFAULT = 80;
	public static final int PORT_HTTPS_DEFAULT = 443;
	public final int port;
	
	public final String part;
	
	public RequestURL(String uri) {
		String uriTemp = uri.trim();
		
		int protocolEndIndex = uriTemp.indexOf("://");
		if (protocolEndIndex > 0) {
			protocol = uriTemp.substring(0, protocolEndIndex);
		} else {
			protocol = PROTOCOL_DEFAULT;
		}
		
		int hostBeginIndex = protocolEndIndex >= 0 ? protocolEndIndex + 3 : 0;
		int partBeginIndex = uriTemp.indexOf('/', hostBeginIndex);
		if (partBeginIndex < 0) {
			partBeginIndex = uriTemp.length();
		}
		String hostPort = uriTemp.substring(hostBeginIndex, partBeginIndex);
		int hostEndIndexT = hostPort.indexOf(':');
		if (hostEndIndexT < 0) {
			host = hostPort;
			if ("http".equals(protocol)) {
				port = PORT_HTTP_DEFAULT;
			} else if ("https".equals(protocol)) {
				port = PORT_HTTPS_DEFAULT;
			} else {
				throw new IllegalArgumentException("未知的协议: " + protocol);
			}
		} else {
			host = hostPort.substring(0, hostEndIndexT);
			port = Integer.parseInt(hostPort.substring(hostEndIndexT + 1));
		}
		
		part = uriTemp.substring(partBeginIndex);
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return protocol + "://" + host + ":" + port + part;
	}
}
