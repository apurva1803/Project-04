package in.co.rays.proj4.test;

import java.sql.Timestamp;
import java.util.Date;

import in.co.rays.proj4.bean.CollegeBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.CollegeModel;

public class TestCollegeModel {

	public static CollegeModel model = new CollegeModel();
	
	public static void main(String[] args) {
		//testAdd();
		//testUpdate();
		testDelete();
	}
	
	public static void testDelete() {
		try {
			CollegeBean bean = new CollegeBean();
			long pk = 3;
			bean.setId(pk);
			model.delete(bean);
			System.out.println("Record Deleted..");
			
			CollegeBean deletedbean = model.findByPk(pk);
			if (deletedbean != null) {
				System.out.println("Test Delete fail");
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
		
	}

	public static void testUpdate() {
		try {
			CollegeBean bean = model.findByPk(2);
			
			bean.setName("Shiva");
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
			
			model.update(bean);

			CollegeBean updatedbean = model.findByPk(2);
			
			if (!"Admin".equals(updatedbean.getName())) {
				System.out.println("Test Update Success");
			}
		} catch (ApplicationException | DuplicateRecordException e) {
			e.printStackTrace();
		}
		
	}

	public static void testAdd() {
		
		CollegeBean bean = new CollegeBean();
		
		try {
			bean.setName("Ameya");
			bean.setAddress("Sangli");
			bean.setState("Maharashtra");
			bean.setCity("Mumbai");
			bean.setPhoneNo("9778776655");
			bean.setCreatedBy("admin");
			bean.setModifiedBy("admin");
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
			
			long pk = model.add(bean);
			CollegeBean addedbean = model.findByPk(pk);
			if (addedbean == null) {
				System.out.println("Test add fail");
			}
		} catch (ApplicationException | DuplicateRecordException e) {
			e.printStackTrace();
		}
		
	}
}
