package in.co.rays.proj4.test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.StudentBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.StudentModel;

public class TestStudentModel {
	
	public static StudentModel model = new StudentModel();
	
	public static void main(String[] args) {
		//testAdd();
		//testUpdate();
		//testDelete();
		//testfindByPk();
		testSearch();
	}
	
	public static void testSearch() {
		try {
			StudentBean bean = new StudentBean();
			List list = new ArrayList();
			list = model.search(bean, 0, 0);
			
			if (list.size() < 0) {
				System.out.println("Test Serach fail");
			}
			
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (StudentBean) it.next();
				System.out.println("Id: " + bean.getId());
				System.out.println("Name: " + bean.getFirstName());
				System.out.println("Description: " + bean.getLastName());
				System.out.println("CreatedBy: " + bean.getCreatedBy());
				System.out.println("ModifiedBy: " + bean.getModifiedBy());
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void testfindByPk() {
		try {
			StudentBean bean = model.findByPk(2);
			
			if (bean == null) {
				System.out.println("Test Find By PK fail");
			}
			
			System.out.println("Id: " + bean.getId());
			System.out.println("Name: " + bean.getFirstName());
			System.out.println("Description: " + bean.getLastName());
			System.out.println("CreatedBy: " + bean.getCreatedBy());
			System.out.println("ModifiedBy: " + bean.getModifiedBy());
			System.out.println("CreatedDatetime: " + bean.getCreatedDatetime());
			System.out.println("ModifiedDatetime: " + bean.getModifiedDatetime());
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}
	
	public static void testDelete() {
		try {
			StudentBean bean = new StudentBean();
			long pk = 3;
			bean.setId(pk);
			model.delete(bean);
			System.out.println("Record Deleted..");
			
			StudentBean deletedbean = model.findByPk(pk);
			if (deletedbean != null) {
				System.out.println("Test Delete fail");
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
		
	}

	public static void testUpdate() {
		try {
			StudentBean bean = model.findByPk(2);
			
			bean.setFirstName("Shiva");
			bean.setLastName("Deshmukh");
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
			
			model.update(bean);

			StudentBean updatedbean = model.findByPk(2);
			
			if (!"Admin".equals(updatedbean.getFirstName())) {
				System.out.println("Test Update Success");
			}
		} catch (ApplicationException | DuplicateRecordException e) {
			e.printStackTrace();
		}
		
	}

	public static void testAdd() {
		
		StudentBean bean = new StudentBean();
		
		try {
			bean.setFirstName("Ameya");
			bean.setLastName("Deshmukh");
			bean.setDob(new Timestamp(new Date().getTime()));
			bean.setGender("male");
			bean.setMobileNo("8778776655");
			bean.setEmail("Ameya@gmail.com");
			bean.setCollegeId(4);
			bean.setCreatedBy("admin");
			bean.setModifiedBy("admin");
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
			
			long pk = model.add(bean);
			StudentBean addedbean = model.findByPk(pk);
			if (addedbean == null) {
				System.out.println("Test add fail");
			}
		} catch (ApplicationException | DuplicateRecordException e) {
			e.printStackTrace();
		}
		
	}
}
