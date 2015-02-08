package com.triadic.webapp.persistence;

import com.triadic.webapp.bean.GenericDAO;
import com.triadic.webapp.model.Customer;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.List;

@Named
@Singleton
public class CustomerDAO extends GenericDAO implements Serializable {

	@Inject
	private EntityManager em;

    public Customer validateUser(String useremail, String password){
        Query query = em.createQuery("select d from Customer d where d.email =:email and d.password =:pass");
        query.setParameter("email", useremail);
        query.setParameter("pass", password);
        query.setMaxResults(1);
        Customer r = null;
        List rl = query.getResultList();
        if(rl != null &&  !rl.isEmpty()) {
            r = (Customer) query.getResultList().get(0);
        }
        return r;
    }
	
	public void persist(Customer customer) {
		EntityTransaction tx = em.getTransaction();
	    tx.begin();
	    em.persist(customer);
	    tx.commit();
	}
	
	public Customer load(Long id) {
	    Query query = em.createQuery("select d from Customer d where d.id =:id");
	    query.setParameter("id", id);
	    query.setMaxResults(1);
	    Customer r = (Customer) query.getResultList().get(0);
	    return r;
	}
	
	public void update(Customer customer) {
		em.merge(customer);
	}
	
	@SuppressWarnings("unchecked")
	public List<Customer> findAll() {
	    EntityTransaction tx = em.getTransaction();
	    tx.begin();
	    Query query = em.createQuery("select d from Customer d");
	    List<Customer> res = query.getResultList();
	    tx.commit();
	    return res;
	}

	public void delete(Long id) {
		EntityTransaction tx = em.getTransaction();
	    tx.begin();
		Query query = em.createQuery("delete Customer c where c.id=:id");
		query.setParameter("id", id);
		query.executeUpdate();
		tx.commit();
	}
}
