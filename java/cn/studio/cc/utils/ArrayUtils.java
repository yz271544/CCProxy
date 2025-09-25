package cn.studio.cc.utils;

public class ArrayUtils {
	public static int indexOf(byte[] b, byte[] subB, int indexFrom) {
		if (subB.length == 0) {
			return indexFrom;
		}
		for (int i = indexFrom; i <= b.length - subB.length; i++) {
			boolean equals = true;
			for (int j = 0; j < subB.length; j++) {
				if (b[i + j] != subB[j]) {
					equals = false;
					break;
				}
			}
			if (equals) {
				return i;
			}
		}
		return -1;
	}
	public static boolean endsWith(byte[] b, byte[] subB) {
		if (subB.length == 0) {
			return true;
		}
		if (subB.length > b.length) {
			return false;
		}
		int bStartIndex = b.length - subB.length;
		for (int i = 0; i < subB.length; i++) {
			if (subB[i] != b[bStartIndex + i]) {
				return false;
			}
		}
		return true;
	}
}
