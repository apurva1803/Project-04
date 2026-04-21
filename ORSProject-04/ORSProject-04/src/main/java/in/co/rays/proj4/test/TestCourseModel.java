package in.co.rays.proj4.test;

import java.sql.Timestamp;
import java.util.Date;

import in.co.rays.proj4.bean.CourseBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.CourseModel;

public class TestCourseModel {
	
public static CourseModel model = new CourseModel();
	
	public static void main(String[] args) {
		//testAdd();
		//testUpdate();
		testDelete();
	}
	
	public static void testDelete() {
		try {
			CourseBean bean = new CourseBean();
			long pk = 2;
			bean.setId(pk);
			model.delete(bean);
			System.out.println("Record Deleted..");
			
			CourseBean deletedbean = model.findByPk(pk);
			if (deletedbean != null) {
				System.out.println("Test Delete fail");
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
		
	}

	public static void testUpdate() {
		try {
			CourseBean bean = model.findByPk(1);
			
			bean.setName("Engg");
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
			
			model.update(bean);

			CourseBean updatedbean = model.findByPk(2);
			
			if (!"Admin".equals(updatedbean.getName())) {
				System.out.println("Test Update Success");
			}
		} catch (ApplicationException | DuplicateRecordException e) {
			e.printStackTrace();
		}
		
	}

	public static void testAdd() {
		
		CourseBean bean = new CourseBean();
		
		try {
			bean.setName("BSC");
			bean.setDuration("3Y");
			bean.setDescription("BSC");
			bean.setCreatedBy("admin");
			bean.setModifiedBy("admin");
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
			
			long pk = model.add(bean);
			CourseBean addedbean = model.findByPk(pk);
			if (addedbean == null) {
				System.out.println("Test add fail");
			}
		} catch (ApplicationException | DuplicateRecordException e) {
			e.printStackTrace();
		}
		
	}
}
