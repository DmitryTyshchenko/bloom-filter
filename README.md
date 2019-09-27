# Java Bloom Filter implementation

## Example of Usage

@Test
	public void testMightContain() throws Exception {

		var hashFunctions = new ArrayList<HashFunction<String>>();

		var bloomFilter = new LongAsCacheBloomFilterImpl<String>(hashFunctions);

		Collections.addAll(hashFunctions, crc32(CapacityProviderImpl.CapacityProvider_64));

		var testString = "test";

		bloomFilter.addToCache(testString);

		assertTrue(bloomFilter.mightContain("test"));
	}
