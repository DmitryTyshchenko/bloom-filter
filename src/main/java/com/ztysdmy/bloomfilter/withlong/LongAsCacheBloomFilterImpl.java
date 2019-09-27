package com.ztysdmy.bloomfilter.withlong;

import java.util.Collection;
import java.util.function.Function;

import com.ztysdmy.bloomfilter.BloomFilter;

public class LongAsCacheBloomFilterImpl<T> implements BloomFilter<T> {

	private Collection<HashFunction<T>> hashFunctions;

	CacheProvider cache;

	public LongAsCacheBloomFilterImpl(CacheProvider cache, Collection<HashFunction<T>> hashFunctions) {

		this.cache = cache;
		this.hashFunctions = hashFunctions;
	}

	public LongAsCacheBloomFilterImpl(Collection<HashFunction<T>> hashFunctions) {

		this(CacheProviderImpl.Cache_64, hashFunctions);
	}

	@Override
	public boolean mightContain(T t) {

		Function<HashFunction<T>, Boolean> mightContain = hashFunction -> {

			var hash = hashFunction.apply(t);

			if ((getCache(hash) & (1l << hash)) == 0) {
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

			var hash = hashFunction.apply(t);

			var localCache = getCache(hash) | (1l << hash);
			setCache(localCache, hash);
			return true;
		};

		return withAllHashFunctions(addToCacheFunc);
	}

	private long getCache(int hash) {

		return this.cache.bucket(hash).getCache();
	}

	private void setCache(long cache, int hash) {
		this.cache.bucket(hash).setCache(cache);
	}
}
