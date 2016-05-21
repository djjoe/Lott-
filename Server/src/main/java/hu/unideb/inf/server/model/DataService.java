package hu.unideb.inf.server.model;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataService {
	
	private static final Logger LOG = LoggerFactory.getLogger(DataService.class);

	EntityManagerFactory entityManagerFactory;
	EntityManager entityManager;
	
	public DataService() {
		entityManagerFactory = Persistence.createEntityManagerFactory("Lottery");
		entityManager = entityManagerFactory.createEntityManager();
	}
	
	public void close() {
		entityManager.close();
		entityManagerFactory.close();
	}
	
	public User createUser(String username, String password, String name, String email, Integer age) {
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		user.setName(name);
		user.setEmail(email);
		user.setAge(age);
		user.setBalance(5000);
		try {
			entityManager.getTransaction().begin();
			entityManager.persist(user);
			entityManager.getTransaction().commit();
		} catch (RollbackException e) {
			LOG.error("Hiba a belejelentkezés során.");
			return null;
		}
		return user;
	}
	
	public User findUser(String username) {
		return entityManager.find(User.class, username);
	}
	
	public void createTicket(Ticket ticket) {
		entityManager.persist(ticket);
	}
	
	public List<Ticket> findAllTicket(Integer typeID) {
		TypedQuery<Ticket> query = entityManager.createQuery("SELECT t from hu.unideb.inf.server.model.Ticket t where typeID=" + typeID, Ticket.class);
		return query.getResultList();
	}

	public User getUserByUsernameAndPassword(String username, String password) {
		TypedQuery<User> query = entityManager.createQuery("SELECT u FROM hu.unideb.inf.server.model.User u where username='"+username+"' and password='"+password+"'", User.class);
		return query.getSingleResult();
	}
	
	public Integer getLastDrawID() {
		TypedQuery<Integer> query = 
				entityManager.createQuery("select max(u.id) from Draw u", Integer.class);
		return query.getSingleResult();
	}

	public Integer getFullBalance(Integer i) {
		return (Integer) entityManager.createQuery("SELECT t.fullBalance FROM TicketTypes t where typeID="+i, Integer.class)
				.getSingleResult();
	}
}