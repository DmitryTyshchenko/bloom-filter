package com.ztysdmy.bloomfilter.hash;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Optional;
import java.util.zip.CRC32;

import com.ztysdmy.bloomfilter.BloomFilter.HashFunction;

public class HashFunctionsProvider {

	private HashFunctionsProvider() {
	}

	/**
	 * Returns HashFunction based on crc32 calculation restricted by n parameter
	 * @param <T>
	 * @param n
	 * @return
	 */
	public static <T> HashFunction<T> crc32(int n) {
		return (t)->{
			byte[] objectBytes = convertToBytes(t).orElseThrow(()->new RuntimeException("Can't convert Object to bytes"));
			CRC32 crc32 = new CRC32();
			crc32.update(objectBytes);
			return (int) (crc32.getValue()%n);
		};
	}

	static <T> Optional<byte[]> convertToBytes(T t) {

		byte[] result = null;

		try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
				ObjectOutputStream out = new ObjectOutputStream(bos)) {
			out.writeObject(t);
			result = bos.toByteArray();
		} catch (IOException e) {
		}

		return Optional.ofNullable(result);
	}

}
