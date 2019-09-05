package com.ztysdmy.bloomfilter;

import java.util.Collection;
import java.util.function.Function;

public class LongAsCacheBloomFilterImpl<T> implements BloomFilter<T> {

	private Collection<Function<T, Integer>> hashFunctions;

	private long cache = 0l;

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
	public void addToCache(T t) {
		for (Function<T, Integer> hashFunction : hashFunctions) {
			//set bits in cache
			cache = cache|(0l << hashFunction.apply(t));
		}
	}

}
