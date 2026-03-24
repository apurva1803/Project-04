package in.co.rays.proj4.test;

import java.sql.Timestamp;
import java.util.Date;

import in.co.rays.proj4.bean.CourseBean;
import in.co.rays.proj4.bean.SubjectBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.SubjectModel;

public class TestSubjectModel {

	public static SubjectModel model = new SubjectModel();
	
	public static void main(String[] args) {
		testAdd();
		//testUpdate();
		//testDelete();
	}
	
	public static void testDelete() {
		try {
			SubjectBean bean = new SubjectBean();
			long pk = 1;
			bean.setId(pk);
			model.delete(bean);
			System.out.println("Record Deleted..");
			
			SubjectBean deletedbean = model.findByPk(pk);
			if (deletedbean != null) {
				System.out.println("Test Delete fail");
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
		
	}

	public static void testUpdate() {
		try {
			SubjectBean bean = model.findByPk(1);
			
			bean.setCourseName("BDS");
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
			
			model.update(bean);

			SubjectBean updatedbean = model.findByPk(1);
			
			if (!"Admin".equals(updatedbean.getCourseName())) {
				System.out.println("Test Update Success");
			}
		} catch (ApplicationException | DuplicateRecordException e) {
			e.printStackTrace();
		}
		
	}

	public static void testAdd() {
		
		SubjectBean bean = new SubjectBean();
		
		try {
			bean.setName("BSC");
			bean.setCourseId(2);
			bean.setCourseName("BSC");
			bean.setDescription("BSC");
			bean.setCreatedBy("admin");
			bean.setModifiedBy("admin");
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
			
			long pk = model.add(bean);
			SubjectBean addedbean = model.findByPk(pk);
			if (addedbean == null) {
				System.out.println("Test add fail");
			}
		} catch (ApplicationException | DuplicateRecordException e) {
			e.printStackTrace();
		}
		
	}
}
