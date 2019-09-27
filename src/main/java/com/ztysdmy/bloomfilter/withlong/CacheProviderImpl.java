package com.ztysdmy.bloomfilter.withlong;

import java.util.Arrays;

public enum CacheProviderImpl implements CacheProvider {

	Cache_64(new Bucket[1]), Cache_128(new Bucket[2]), Cache_192(new Bucket[3]);

	CacheProviderImpl(Bucket[] buckets) {

		this.buckets = buckets;
		Arrays.fill(this.buckets, new Bucket());
	}

	private Bucket[] buckets;

	@Override
	public Bucket bucket(int hash) {
		try {
			return buckets[bucketNumber(hash)];
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new RuntimeException("Hash exceeds cache capacity", e);
		}
	}

	private int bucketNumber(int hash) {
		return (int) Math.ceil(hash / 64);
	}

}
