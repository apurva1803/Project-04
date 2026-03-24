package in.co.rays.proj4.test;

import java.sql.Timestamp;
import java.util.Date;

import in.co.rays.proj4.bean.FacultyBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.FacultyModel;

public class TestFacultyModel {

public static FacultyModel model = new FacultyModel();
	
	public static void main(String[] args) {
		//testAdd();
		testUpdate();
		//testDelete();
	}
	
	public static void testDelete() {
		try {
			FacultyBean bean = new FacultyBean();
			long pk = 1;
			bean.setId(pk);
			model.delete(bean);
			System.out.println("Record Deleted..");
			
			FacultyBean deletedbean = model.findByPk(pk);
			if (deletedbean != null) {
				System.out.println("Test Delete fail");
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
		
	}

	public static void testUpdate() {
		try {
			FacultyBean bean = model.findByPk(1);
			
			bean.setFirstName("Ameya");
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
			
			model.update(bean);

			FacultyBean updatedbean = model.findByPk(1);
			
			if (!"Admin".equals(updatedbean.getFirstName())) {
				System.out.println("Test Update Success");
			}
		} catch (ApplicationException | DuplicateRecordException e) {
			e.printStackTrace();
		}
		
	}

	public static void testAdd() {
		
		FacultyBean bean = new FacultyBean();
		
		try {
			bean.setFirstName("Apurva");
			bean.setLastName("Raut");
			bean.setDob(new Timestamp(new Date().getTime()));
			bean.setGender("Female");
			bean.setMobileNo("9988776655");
			bean.setEmail("raut@gmail.com");
			bean.setCollegeId(1);
			bean.setCourseId(1);
			bean.setSubjectId(1);
			bean.setCreatedBy("admin");
			bean.setModifiedBy("admin");
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
			
			long pk = model.add(bean);
			FacultyBean addedbean = model.findByPk(pk);
			if (addedbean == null) {
				System.out.println("Test add fail");
			}
		} catch (ApplicationException | DuplicateRecordException e) {
			e.printStackTrace();
		}
		
	}
}
