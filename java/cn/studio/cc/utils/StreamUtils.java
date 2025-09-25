package cn.studio.cc.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class StreamUtils {
	public static byte[] inputToByte(InputStream is) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] isByteTemp = new byte[1024];
		int readNum;
		while ((readNum = is.read(isByteTemp)) >= 0) {
			baos.write(isByteTemp, 0, readNum);
		}
		byte[] inb = baos.toByteArray();
		return inb;
	}
	public static byte[] inputToByteByChunked(InputStream is) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		while (true) {
			byte[] chunkedLB = inputToByte(is, new byte[] { '\r', '\n' });
			baos.write(chunkedLB);
			String chunkedL = new String(chunkedLB).trim();
			int length = Integer.parseInt(chunkedL, 16);
			byte[] b = inputToByte(is, length);
			baos.write(b);
			byte[] chunkedLBT = inputToByte(is, new byte[] { '\r', '\n' });
			baos.write(chunkedLBT);
			if (length == 0) {
				break;
			}
		}
		byte[] inb = baos.toByteArray();
		return inb;
	}
	public static byte[] inputToByte(InputStream is, byte[] ends) throws IOException {
		if (ends.length == 0) {
			return inputToByte(is);
		}
		byte endsL = ends[ends.length - 1];
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int bT = -1;
		while ((bT = is.read()) != -1) {
			baos.write(bT);
			byte bbT = (byte) bT;
			if (bbT == endsL) {
				byte[] b = baos.toByteArray();
				if (ArrayUtils.endsWith(b, ends)) {
					return b;
				}
			}
		}
		return baos.toByteArray();
	}
	public static byte[] inputToByte(InputStream is, int length) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int already = 0;
		byte[] isByteTemp = new byte[length - already];
		int readNum;
		while ((readNum = is.read(isByteTemp)) >= 0) {
			baos.write(isByteTemp, 0, readNum);
			already += readNum;
			if (already >= length) {
				break;
			}
			isByteTemp = new byte[length - already];
		}
		return baos.toByteArray();
	}
}
