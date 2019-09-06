package com.ztysdmy.bloomfilter;

import java.util.function.Function;

public interface BloomFilter<T> {

	boolean mightContain(T t);
	
	boolean addToCache(T t);
	
	public static interface HashFunction<T> extends Function<T,Integer> {}
	
}
