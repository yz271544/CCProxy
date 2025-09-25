package cn.studio.cc.net.http.response;

public class StatusLine {

	public final String protocol;
	public final String statusCode;
	public final String reasonPhrase;
	
	public StatusLine(String protocol, String statusCode, String reasonPhrase) {
		this.protocol = protocol;
		this.statusCode = statusCode;
		this.reasonPhrase = reasonPhrase;
	}
	
	public StatusLine(String statusLine) {
		String statusLineT = statusLine.trim();
		String[] arguments = statusLineT.split(" ", 3);
		if (arguments.length != 3) {
			throw new IllegalArgumentException("invalid RequestLine format: " + statusLine);
		}
		this.protocol = arguments[0].trim();
		this.statusCode = arguments[1].trim();
		this.reasonPhrase = arguments[2].trim();
	}
	
	public StatusLine(byte[] statusLine) {
		this(new String(statusLine));
	}
	
	@Override
	public String toString() {
		return protocol + " " + statusCode + " " + reasonPhrase;
	}
}
