package test;

import java.io.IOException;

import junit.framework.TestCase;
import util.Constants;
import data.provider.Provider;

public class ProviderTest extends TestCase {

	public void testSingleton() {
		
		assertNotNull(Provider.Singleton());

	}

	public void testUpdatePlayerBalance() throws IOException {
		
		Constants.loadProperties();
		
		Object[] result = Provider.Singleton().UpdatePlayerBalance("Emre", 1, 1);
		if(result == null)
			fail("UpdatePlayerBalance returns null");
		if(!result[1].toString().equals(Constants.E000001))
			fail("UpdatePlayerBalance returns wrong result for invalid username");
		
		result = Provider.Singleton().UpdatePlayerBalance("PLAYER_A", 1, 50);
		if(result == null)
			fail("UpdatePlayerBalance returns null");
		if(!result[1].toString().equals(Constants.E000000))
			fail("UpdatePlayerBalance returns wrong result for successful operation");
		
		result = Provider.Singleton().UpdatePlayerBalance("PLAYER_B", 1, -1000);
		if(result == null)
			fail("UpdatePlayerBalance returns null");
		if(!result[1].toString().equals(Constants.E000002))
			fail("UpdatePlayerBalance returns wrong result for insufficient balance");

		assert(true);
	}
}
