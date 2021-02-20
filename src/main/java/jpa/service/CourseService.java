package jpa.service;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import jpa.dao.CourseDAO;
import jpa.entitymodels.Course;

public class CourseService implements CourseDAO {

	String persistenceUnitName = "SMS_Lenny";
	EntityManagerFactory factory = Persistence.createEntityManagerFactory(persistenceUnitName);
	public EntityManager em;
	
	public void setEntityManger(EntityManager em) {
		this.em = factory.createEntityManager();

	    }
	
	public EntityManager getEntityManager() {
		return this.em;
	}
	
	@Override
	public List<Course> getAllCourses() {
		this.em = factory.createEntityManager();
		Query query = this.em.createQuery("SELECT c FROM Course c");
        return query.getResultList();		
	}

}
