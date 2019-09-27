package com.ztysdmy.bloomfilter.hash;

import org.junit.Assert;
import org.junit.Test;


import static com.ztysdmy.bloomfilter.hash.HashFunctionsCollections.*;

public class HashFunctionsProviderTest {
	
	@Test
	public void shouldNotExceedNAndBeGreaterThanZero() throws Exception {
		var charsToTest = "qwertyuiop[]asdfghjkl;zxcvbnm,.";
		for (int i=0; i<charsToTest.length(); ++i) {
			var hash = crc32(new CapacityWith4()).apply(charsToTest.charAt(i));
			Assert.assertTrue(hash<4);
			Assert.assertTrue(hash>0);
		}
	}	
	
	static class CapacityWith4 implements CapacityProvider {

		@Override
		public int capacity() {
			// TODO Auto-generated method stub
			return 4;
		}
		
	}
}
