package service;

import static service.Server.logger;
import static service.Server.updateQueryStatistics;

import javax.jws.WebService;

import data.provider.Provider;

@WebService(endpointInterface="service.WalletService")  
public class WalletServiceEndpoint implements WalletService{
	
	public Object[] UpdatePlayerBalance(String userName, long transactionId, double balanceChange) {
		long startingTime = logger("IN", userName, new Object[]{transactionId, balanceChange});
		Object[] objectList = Provider.Singleton().UpdatePlayerBalance(userName, transactionId, balanceChange);
		updateQueryStatistics(false, startingTime, transactionId);
		logger("OUT", userName, objectList);
		return objectList;
	}

}
