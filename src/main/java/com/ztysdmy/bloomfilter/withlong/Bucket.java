package com.ztysdmy.bloomfilter.withlong;

class Bucket {
	
	private long cache = 1l;
	
	long getCache() {
		
		return this.cache;
	}
	
	void setCache(long cache) {
		
		this.cache = cache;
	}

}
