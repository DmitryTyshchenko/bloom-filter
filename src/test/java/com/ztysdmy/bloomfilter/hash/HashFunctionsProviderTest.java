package com.ztysdmy.bloomfilter.hash;

import org.junit.Assert;
import org.junit.Test;


import static com.ztysdmy.bloomfilter.hash.HashFunctionsProvider.*;

public class HashFunctionsProviderTest {
	
	@Test
	public void shouldNotExceedNAndBeGreaterThanZero() throws Exception {
		var charsToTest = "qwertyuiop[]asdfghjkl;zxcvbnm,.";
		for (int i=0; i<charsToTest.length(); ++i) {
			var hash = crc32(4).apply(charsToTest.charAt(i));
			Assert.assertTrue(hash<4);
			Assert.assertTrue(hash>0);
		}
	}
	
}
