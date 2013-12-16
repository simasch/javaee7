package web;

import java.io.Serializable;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import model.User;

@Named
@RequestScoped
public class UserBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@PersistenceContext
	private EntityManager em;

	@Inject
	private Principal principal;

	private List<User> users = new ArrayList<>();

	@PostConstruct
	protected void load() {
		users = em.createQuery("SELECT u FROM User u", User.class).getResultList();
	}
	
	@Transactional
	public void save() {
		User user = new User();
		user.setName(principal.getName());
		em.persist(user);
		load();
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

}
