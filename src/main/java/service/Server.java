package service;

import static util.Constants.SERVER_ENDPOINT;
import static util.Constants.loadProperties;

import java.util.Date;

import javax.xml.ws.Endpoint;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Server {
	
	public static Logger log = Logger.getLogger(Server.class);
	
	public static int numberOfProcessedQueries = 0;
	public static long minQueryTime = 0;
	public static long maxQueryTime = 0;
	public static long avgQueryTime = 0;
	
	public static void updateQueryStatistics(boolean isRestart, long startingTime, long transactionId){
		if(isRestart){
			numberOfProcessedQueries = 0;
			minQueryTime = 0;
			maxQueryTime = 0;
			avgQueryTime = 0;
		}else{
			long queryTime = new Date().getTime() - startingTime;
			if(queryTime < minQueryTime || minQueryTime == 0) minQueryTime = queryTime;
			if(queryTime > maxQueryTime) maxQueryTime = queryTime;
			avgQueryTime = ((avgQueryTime * numberOfProcessedQueries) + queryTime) / (numberOfProcessedQueries + 1);
			numberOfProcessedQueries ++;
			log.info("Query with TransactionId: " + transactionId + " is completed in " + queryTime + " miliseconds");
		}
	}
	
	public static long logger(String type, String userName, Object[] paramList){
		String s = "( ";
		for(Object obj : paramList){
			s = s + obj.toString() + " : ";
		}
		s = s.substring(0, s.length() - 2) + ")";
		log.info(type + "-" + userName + s);
		return new Date().getTime();
	}

	public static void main(String[] args) {	
		PropertyConfigurator.configure("log4j.properties");
		try {
			loadProperties();
			Endpoint.publish(SERVER_ENDPOINT,new WalletServiceEndpoint()); 
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Server initialization error: " + e.getMessage());
		}
		
        Thread t = new Thread(new QueryProcessor());
        t.start();
    }

    private static class QueryProcessor implements Runnable {
    	
    	private int minute = 1000 * 60;
    	
        public void run() {
        	while(true){
        		try {
					Thread.sleep(minute);
					log.info("Number of Processed Queries:" + numberOfProcessedQueries);
					log.info("Minimum Query Time: " + minQueryTime + " miliseconds");
					log.info("Maximum Query Time: " + maxQueryTime + " miliseconds");
					log.info("Average Query Time: " + avgQueryTime + " miliseconds");
					
				} catch (InterruptedException e) {
					log.error("Query Processor Thread Error: " + e.getMessage());
					e.printStackTrace();
				}
        	}
        }
    }


}
