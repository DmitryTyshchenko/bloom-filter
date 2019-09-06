package com.ztysdmy.bloomfilter;

import java.util.Collection;
import java.util.function.Function;

public class LongAsCacheBloomFilterImpl<T> implements BloomFilter<T> {

	private Collection<HashFunction<T>> hashFunctions;

	private long cache = 1l;

	public LongAsCacheBloomFilterImpl(Collection<HashFunction<T>> hashFunctions) {

		this.hashFunctions = hashFunctions;
	}

	@Override
	public boolean mightContain(T t) {

		Function<HashFunction<T>, Boolean> mightContain = hashFunction -> {

			if ((cache & (1l << hashFunction.apply(t))) != 1) {
				return false;
			}
			return true;
		};
		return withAllHashFunctions(mightContain);
	}

	private boolean withAllHashFunctions(Function<HashFunction<T>, Boolean> func) {

		for (HashFunction<T> hashFunction : hashFunctions) {

			if (!func.apply(hashFunction))
				return false;
		}

		return true;
	}

	@Override
	public boolean addToCache(T t) {

		Function<HashFunction<T>, Boolean> addToCacheFunc = hashFunction -> {

			var localCache = cache | (1l << hashFunction.apply(t));
			cache = localCache;
			return true;
		};

		return withAllHashFunctions(addToCacheFunc);
	}

}
