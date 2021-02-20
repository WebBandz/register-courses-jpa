package jpa.service;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import jpa.dao.StudentDAO;
import jpa.entitymodels.Course;
import jpa.entitymodels.Student;

public class StudentService implements StudentDAO {

	static String persistenceUnitName = "SMS_Lenny";
	EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnitName);
	public EntityManager entityManager;

	public EntityManager getEntityManager() {
		this.entityManager = entityManagerFactory.createEntityManager();
		return this.entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public List<Student> getAllStudents() {
		entityManager = entityManagerFactory.createEntityManager();
		Query query = entityManager.createQuery("SELECT s FROM Student s");
		return query.getResultList();
	}

	@Override
	public Student getStudentByEmail(String sEmail) {
		entityManager = entityManagerFactory.createEntityManager();
		String select = "SELECT s FROM Student s WHERE s.email=:email";
		Query query = entityManager.createQuery(select);
		query.setParameter("email", sEmail);
		return (Student) query.getSingleResult();
	}

	@Override
	public boolean validateStudent(String sEmail, String sPass) {

		String select = "SELECT s.sEmail,s.sPass FROM Student s WHERE s.sEmail=:email and s.sPass=:password";
		entityManager = entityManagerFactory.createEntityManager();

		Query query = entityManager.createQuery(select);
		query.setParameter("email", sEmail);
		query.setParameter("password", sPass);

		if (!query.getResultList().isEmpty()) {
			return true;
		}
		return false;
	}

	@Override
	public void registerStudentToCourse(String sEmail, int cId) {
		
		String selectSemail = "SELECT s FROM Student s WHERE s.sEmail = :email";
		String selectCid = "SELECT c FROM Course c WHERE c.cId = :id";
		
		entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		
		Query query1 = entityManager.createQuery(selectSemail);
		query1.setParameter("email", sEmail);
		Student student = (Student) query1.getSingleResult();
		Query query2 = entityManager.createQuery(selectCid);
		query2.setParameter("id", cId);
		
		try {
			Course course = (Course) query2.getSingleResult();
			if (student.getsCourses().contains(course)) {
				throw new AssertionError("You registered for this course already.");
			}
			student.addNewCoursesToStudent(course);
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			throw new AssertionError("No such a course");
		}
		
	}
	
	@Override
	public List<Course> getStudentCourses(String sEmail) {
		
		entityManager = entityManagerFactory.createEntityManager();
		Query query = entityManager.createQuery("SELECT s FROM Student s WHERE s.sEmail = :email");
		query.setParameter("email", sEmail);
		Student student = (Student) query.getSingleResult();
		return student.getsCourses();
	}

}
