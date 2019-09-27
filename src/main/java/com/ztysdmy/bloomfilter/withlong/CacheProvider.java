package com.ztysdmy.bloomfilter.withlong;

public interface CacheProvider {

	Bucket bucket(int hash);
}
