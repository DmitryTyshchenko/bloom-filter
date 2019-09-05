package com.ztysdmy.bloomfilter;

public interface BloomFilter<T> {

	boolean mightContain(T t);
	
	void addToCache(T t);
}
