package data.access;

public interface PlayerDAO {
	
	public Player getPlayer(String userName);
	public String updatePlayer(Player player);
}