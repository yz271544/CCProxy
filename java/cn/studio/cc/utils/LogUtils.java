package cn.studio.cc.utils;

public class LogUtils {
	public static void debug(String msg) {
		System.out.println(msg);
	}
	public static void error(String msg) {
		System.err.println(msg);
	}
	public static void error(Throwable t) {
		t.printStackTrace();
	}
	public static void error(Throwable t, String msg) {
		System.err.println(msg);
		t.printStackTrace();
	}
}
