package com.ztysdmy.bloomfilter.hash;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Optional;
import java.util.zip.CRC32;

import com.ztysdmy.bloomfilter.BloomFilter.HashFunction;

/**
 * Utility class which provides set of hash functions
 * @author dmytro.tyshchenko
 *
 */
public class HashFunctionsCollections {

	private HashFunctionsCollections() {
	}

	/**
	 * Returns {@link HashFunction} based on crc32 calculation in range between 1 and capacity
	 * @param capacity
	 * @return
	 */
	public static <T> HashFunction<T> crc32(CapacityProvider capacity) {
		return (t) -> {
			var crcValue = crcValue(t);
			return calculateHash(crcValue, capacity.capacity());
		};
	}

	private static <T> long crcValue(T t) {
		var objectBytes = convertToBytes(t).orElseThrow(() -> new RuntimeException("Can't convert Object to bytes"));
		var crc32 = new CRC32();
		crc32.update(objectBytes);
		return crc32.getValue();
	}

	private static int calculateHash(long crcValue, int n) {
		var result = (int) (crcValue % n);
		if (result == 0) {
			return 1;
		}
		return result;
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

	public static interface CapacityProvider {
		
		int capacity();
	}
	
	public static enum CapacityProviderImpl implements CapacityProvider {
		
		CapacityProvider_64(64), CapacityProvider_128(128), CapacityProvider_192(192);
		
		CapacityProviderImpl(int capacity) {
			this.capacity = capacity;
		}
		
		private int capacity;

		@Override
		public int capacity() {
			return capacity;
		}	
	}
}
