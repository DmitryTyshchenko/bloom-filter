package com.ztysdmy.bloomfilter;

import org.junit.Assert;
import org.junit.Test;

import com.ztysdmy.bloomfilter.BloomFilter.HashFunction;

import static com.ztysdmy.bloomfilter.hash.HashFunctionsCollections.*;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;

public class LongAsCacheBloomFilterImplTest {
    
	@Test 
    public void testMightContain() throws Exception {
    
    	var hashFunctions = new ArrayList<HashFunction<String>>();

    	var bloomFilter  = new LongAsCacheBloomFilterImpl<String>(hashFunctions);
    	
    	Collections.addAll(hashFunctions, crc32(64));
    	
    	var testString = "dima";
    	
    	bloomFilter.addToCache(testString);
    	
    	assertTrue(bloomFilter.mightContain("dima"));
    }
}
