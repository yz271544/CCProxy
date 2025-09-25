package cn.studio.cc.net.http.request;

public class RequestLine {
	
	public final String method;
	public final RequestURL requestURL;
	public final String protocol;
	
	public RequestLine(String method, String requestURI, String protocol) {
		this.method = method;
		this.requestURL = new RequestURL(requestURI);
		this.protocol = protocol;
	}
	
	public RequestLine(String requestLine) {
		String requestLineT = requestLine.trim();
		String[] arguments = requestLineT.split(" ", 4);
		if (arguments.length != 3) {
			throw new IllegalArgumentException("invalid RequestLine format: " + requestLine);
		}
		this.method = arguments[0].trim();
		this.requestURL = new RequestURL(arguments[1].trim());
		this.protocol = arguments[2].trim();
	}
	
	public RequestLine(byte[] requestLine) {
		this(new String(requestLine));
	}
	
	@Override
	public String toString() {
		return method + " " + requestURL + " " + protocol;
	}
}
