package data.provider;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import util.Constants;
import data.access.Player;
import data.access.PlayerDAOImpl;

public class Provider {
	
	private static Provider singleton;
	
	private ClassPathXmlApplicationContext context;
	
	private Provider(){
		context = new ClassPathXmlApplicationContext("applicationContext.xml");
	}
	
	public static Provider Singleton()
	{
		if(singleton == null){
			singleton = new Provider();	
		}
		return singleton;
	}
	
	public Object[] UpdatePlayerBalance(String userName, long transactionId, double balanceChange) {
			String errorMessage = Constants.E000000;
			PlayerDAOImpl dao = (PlayerDAOImpl) context.getBean("playerDao");
			Player p = new Player();
			try{
				p = dao.getPlayer(userName);
			}catch(Exception e){
				if(e.getClass().equals(IndexOutOfBoundsException.class))
					errorMessage = Constants.E000001;
				else
					errorMessage = Constants.E000003 + ":" + e.getMessage();
				return new Object[]{transactionId, errorMessage, -1, balanceChange, -1};
			}
			if(p.getBalance() + balanceChange >= 0){
				p.setBalance_Version(p.getBalance_Version() + 1);
				p.setBalance(p.getBalance() + balanceChange);
				dao.updatePlayer(p);
			}else{
				errorMessage = Constants.E000002;
			}

		return new Object[]{transactionId, errorMessage, p.getBalance_Version(), balanceChange, p.getBalance()};
	}
	
}
