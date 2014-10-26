package data.access;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import data.access.Player;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public class PlayerDAOImpl implements PlayerDAO {

	@PersistenceContext
    private EntityManager em;	
     
    public Player getPlayer(String userName) {
    	Query query = em.createQuery("select p from " + Player.class.getName() + " p WHERE USERNAME=:userName");
		query.setParameter("userName", userName);
        return (Player) query.getResultList().get(0);
    }

	public String updatePlayer(Player player) {
		em.merge(player);
		return "";
	}

}
