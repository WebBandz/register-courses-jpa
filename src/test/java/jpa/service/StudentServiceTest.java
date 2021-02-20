package jpa.service;

import static org.junit.Assert.assertEquals;

import javax.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import jpa.entitymodels.Student;

@RunWith(PowerMockRunner.class)
@PrepareForTest(fullyQualifiedNames = "jpa.*")
public class StudentServiceTest {

	@Test
	void getStudentByEmailTest() {
		
		Student sampleStudent = new Student();
		sampleStudent.setsEmail("testsample@headache.com");
		sampleStudent.setsName("Rocky");
	
		EntityManager entityManager = Mockito.mock(EntityManager.class);
		Mockito.when(entityManager.find(Student.class, 1L)).thenReturn(sampleStudent);
		StudentService studentService = new StudentService();
		studentService.setEntityManager(entityManager);
			
		String actual = sampleStudent.getsEmail();
		String actual2 = sampleStudent.getsName();
		assertEquals("testsample@headache.com", actual);
		assertEquals("Rocky", actual2);
		
//		emMock = Mockito.mock(EntityManager.class);
//		Mockito.when(ssMock.getEntityManager()).thenReturn(emMock);
//		
//		tqMock = (Query) Mockito.mock(Query.class);
//		
//		Mockito.when(emMock.createQuery("SELECT s FROM Student s")).thenReturn(tqMock);
//		List<Student> expected = new ArrayList<>();
//		Mockito.when(tqMock.getResultList()).thenReturn(expected);
//		ssMock.setEntityManager(emMock);
//		List<Student> actual = ssMock.getAllStudents();
//		assertEquals(expected, actual);
	}
}
