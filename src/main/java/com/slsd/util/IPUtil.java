package com.slsd.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.util.concurrent.locks.ReentrantLock;

public class IPUtil {

	private static int offset;
	private static int[] index = new int[256];
	private static ByteBuffer dataBuffer;
	private static ByteBuffer indexBuffer;
	private static File ipFile = new File("ip_data.dat");
	private static ReentrantLock lock = new ReentrantLock();

	public static void ipDataLoad() {
		InputStream in = IPUtil.class.getResourceAsStream("/ip_data.dat");
		inputstreamtofile(in, ipFile);
		// ipFile = new File(filename);
		load();
	}

	public static void inputstreamtofile(InputStream ins, File file) {
		try {
			OutputStream os = new FileOutputStream(file);
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
				os.write(buffer, 0, bytesRead);
			}
			os.close();
			ins.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String find(String ip) {
		int ip_prefix_value = new Integer(ip.substring(0, ip.indexOf(".")));
		long ip2long_value = ip2long(ip);
		int start = index[ip_prefix_value];
		int max_comp_len = offset - 1028;
		long index_offset = -1;
		int index_length = -1;
		byte b = 0;
		for (start = start * 8 + 1024; start < max_comp_len; start += 8) {
			if (int2long(indexBuffer.getInt(start)) >= ip2long_value) {
				index_offset = bytesToLong(b, indexBuffer.get(start + 6),
						indexBuffer.get(start + 5), indexBuffer.get(start + 4));
				index_length = 0xFF & indexBuffer.get(start + 7);
				break;
			}
		}

		byte[] areaBytes;

		lock.lock();
		try {
			dataBuffer.position(offset + (int) index_offset - 1024);
			areaBytes = new byte[index_length];
			dataBuffer.get(areaBytes, 0, index_length);
		} finally {
			lock.unlock();
		}
		return new String(areaBytes, Charset.forName("UTF-8")).split("\t", -1)[1];
	}

	private static void load() {
		FileInputStream fin = null;
		lock.lock();
		try {
			dataBuffer = ByteBuffer.allocate(Long.valueOf(ipFile.length()).intValue());
			fin = new FileInputStream(ipFile);
			int readBytesLength;
			byte[] chunk = new byte[4096];
			while (fin.available() > 0) {
				readBytesLength = fin.read(chunk);
				dataBuffer.put(chunk, 0, readBytesLength);
			}
			dataBuffer.position(0);
			int indexLength = dataBuffer.getInt();
			byte[] indexBytes = new byte[indexLength];
			dataBuffer.get(indexBytes, 0, indexLength - 4);
			indexBuffer = ByteBuffer.wrap(indexBytes);
			indexBuffer.order(ByteOrder.LITTLE_ENDIAN);
			offset = indexLength;

			int loop = 0;
			while (loop++ < 256) {
				index[loop - 1] = indexBuffer.getInt();
			}
			indexBuffer.order(ByteOrder.BIG_ENDIAN);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			try {
				if (fin != null) {
					fin.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			lock.unlock();
		}
	}

	private static long bytesToLong(byte a, byte b, byte c, byte d) {
		return int2long((((a & 0xff) << 24) | ((b & 0xff) << 16) | ((c & 0xff) << 8) | (d & 0xff)));
	}

	private static int str2Ip(String ip) {
		String[] ss = ip.split("\\.");
		int a, b, c, d;
		a = Integer.parseInt(ss[0]);
		b = Integer.parseInt(ss[1]);
		c = Integer.parseInt(ss[2]);
		d = Integer.parseInt(ss[3]);
		return (a << 24) | (b << 16) | (c << 8) | d;
	}

	private static long ip2long(String ip) {
		return int2long(str2Ip(ip));
	}

	private static long int2long(int i) {
		long l = i & 0x7fffffffL;
		if (i < 0) {
			l |= 0x080000000L;
		}
		return l;
	}

	public static void main(String[] args) throws Exception {
		IPUtil.ipDataLoad();
		System.out.println(IPUtil.find("163.19.9.247"));
	}
}
