package service;

import javax.jws.WebService;
import javax.jws.WebMethod;

@WebService
public interface WalletService {
	
	  @WebMethod
	  public Object[] UpdatePlayerBalance(String userName, long transactionId, double balanceChange);

}
