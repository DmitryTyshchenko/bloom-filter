package com.ztysdmy.bloomfilter;

import java.util.Collection;
import java.util.function.Function;

public class LongAsCacheBloomFilterImpl<T> implements BloomFilter<T> {

	private Collection<Function<T, Integer>> hashFunctions;

	private volatile long cache = 0l;

	public LongAsCacheBloomFilterImpl(Collection<Function<T, Integer>> hashFunctions) {

		this.hashFunctions = hashFunctions;
	}

	@Override
	public boolean mightContain(T t) {

		for (Function<T, Integer> hashFunction : hashFunctions) {

			if ((cache & (0l << hashFunction.apply(t))) != 1) {
				return false;
			}
		}
		return true;
	}
		
	@Override
	public synchronized void addToCache(T t) {
		for (Function<T, Integer> hashFunction : hashFunctions) {
			//update cache
			var localCache = cache|(0l << hashFunction.apply(t));
			cache = localCache;
		}
	}

}
