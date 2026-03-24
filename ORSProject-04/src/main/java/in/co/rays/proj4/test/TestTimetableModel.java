package in.co.rays.proj4.test;

import java.sql.Timestamp;
import java.util.Date;

import in.co.rays.proj4.bean.TimetableBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.TimetableModel;

public class TestTimetableModel {

public static TimetableModel model = new TimetableModel();
	
	public static void main(String[] args) {
		testAdd();
		//testUpdate();
		//testDelete();
	}
	
	public static void testDelete() {
		try {
			TimetableBean bean = new TimetableBean();
			long pk = 1;
			bean.setId(pk);
			model.delete(bean);
			System.out.println("Record Deleted..");
			
			TimetableBean deletedbean = model.findByPk(pk);
			if (deletedbean != null) {
				System.out.println("Test Delete fail");
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
		
	}

	public static void testUpdate() {
		try {
			TimetableBean bean = model.findByPk(1);
			
			bean.setCourseId(5);
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
			
			model.update(bean);

			TimetableBean updatedbean = model.findByPk(1);
			
			if (!"Admin".equals(updatedbean.getCourseName())) {
				System.out.println("Test Update Success");
			}
		} catch (ApplicationException | DuplicateRecordException e) {
			e.printStackTrace();
		}
		
	}

	public static void testAdd() {
		
		TimetableBean bean = new TimetableBean();
		
		try {
			bean.setSemester("4");
			bean.setDescription("Engg");
			bean.setExamDate(new Timestamp(new Date().getTime()));
			bean.setExamTime("3 Hrs");
			bean.setCourseId(1);
			//bean.setCourseName("Engg");
			bean.setSubjectId(1);
			//bean.setSubjectName("DB");
			bean.setCreatedBy("admin");
			bean.setModifiedBy("admin");
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
			
			long pk = model.add(bean);
			TimetableBean addedbean = model.findByPk(pk);
			if (addedbean == null) {
				System.out.println("Test add fail");
			}
		} catch (ApplicationException | DuplicateRecordException e) {
			e.printStackTrace();
		}
		
	}
}
