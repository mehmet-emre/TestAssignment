package service;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface WalletService {
	
	  @WebMethod
	  public Object[] UpdatePlayerBalance(String userName, long transactionId, double balanceChange);

}
