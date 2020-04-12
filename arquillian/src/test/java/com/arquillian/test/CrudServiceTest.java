package com.arquillian.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.test.ejb.CrudService;
import com.test.ejb.impl.CrudServiceBean;
import com.test.pojo.Course;
import com.test.pojo.CourseRating;
import com.test.pojo.CourseRatingKey;
import com.test.pojo.Student;

import entity.InventoryCategory;

@RunWith(Arquillian.class)
public class CrudServiceTest {

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addClasses(CrudServiceBean.class, CrudService.class, InventoryCategory.class)
                .addPackage(Student.class.getPackage())
                .addAsResource("META-INF/persistence.xml");
    }
    
    @EJB
    private CrudService crudSvc;

//    @Test @InSequence(1)
//    public void setUp() throws MalformedURLException {
//    	Map<String, Object> params = new HashMap<>();
//    	params.put("name", "name2");
//    	List<InventoryCategory> cats = crudSvc.findByNamedQuery(InventoryCategory.INVENTORY_CATEGORY_FIND_BY_NAME, params, InventoryCategory.class);
//    	for (InventoryCategory cat:cats) {
//    		System.out.println("deleting cat: " + cat.getCategoryName());
//    		crudSvc.deleteNewTransaction(cat);
//    	}
//    }
    
    
    @Test @InSequence(2)
    public void testCrudService() {
    	assertNotNull("CrudService is null", crudSvc);
    }
    
    @Test @InSequence(3)
    public void testInventoryCategoryCreation() {
    	Map<String, Object> params = new HashMap<>();
    	params.put("name", "name2");
    	List<InventoryCategory> cats = crudSvc.findByNamedQuery(InventoryCategory.INVENTORY_CATEGORY_FIND_BY_NAME, params, InventoryCategory.class);
//    	assertEquals("initially should be zero", cats.size(), 0);
    	InventoryCategory ic = new InventoryCategory();
    	ic.setCategoryDescription("description2");
    	ic.setCategoryName("name2");
    	ic.setVersion(15);
    	crudSvc.createNewTransaction(ic);
    	cats = crudSvc.findByNamedQuery(InventoryCategory.INVENTORY_CATEGORY_FIND_BY_NAME, params, InventoryCategory.class);
//    	assertEquals("initially should be zero", cats.size(), 1);
    }
    
    @Test @InSequence(4)
    public void testStudentCourseCreation() {
    	Student s1 = new Student(12345L);
    	Course c1 = new Course(654L);
    	Course c2 = new Course(321L);
    	Course c3 = new Course(987L);
    	CourseRatingKey key1 = new CourseRatingKey(12345L, 654L);
    	CourseRatingKey key2  = new CourseRatingKey(12345L, 321L);
    	CourseRatingKey key3  = new CourseRatingKey(12345L, 987L);
    	
    	CourseRating rating1 = new CourseRating(key1);
    	CourseRating rating2 = new CourseRating(key2);
    	CourseRating rating3 = new CourseRating(key3);
    	
    	Set <CourseRating> ratings = new HashSet<>();
    	ratings.add(rating1);
    	ratings.add(rating2);
    	ratings.add(rating3);
    	
    	s1.setRatings(ratings);
    	
    	crudSvc.createNewTransaction(s1);
//    	cats = crudSvc.findByNamedQuery(InventoryCategory.INVENTORY_CATEGORY_FIND_BY_NAME, params, InventoryCategory.class);
//    	assertEquals("initially should be zero", cats.size(), 1);
    }
    
//    @Test @InSequence(5)
//    public void cleanup() {
//    	Map<String, Object> params = new HashMap<>();
//    	params.put("name", "name2");
//    	List<InventoryCategory> cats = crudSvc.findByNamedQuery(InventoryCategory.INVENTORY_CATEGORY_FIND_BY_NAME, params, InventoryCategory.class);
//    	for (InventoryCategory cat:cats) {
//    		System.out.println("deleting cat: " + cat.getCategoryName());
//    		crudSvc.deleteNewTransaction(cat);
//    	}
//    }

}