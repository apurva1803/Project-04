package in.co.rays.proj4.test;

import java.sql.Timestamp;
import java.util.Date;

import in.co.rays.proj4.bean.MarksheetBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.MarksheetModel;

public class TestMarksheetModel {


	public static MarksheetModel model = new MarksheetModel();
	
	public static void main(String[] args) {
		//testAdd();
		//testUpdate();
		testDelete();
	}
	public static void testDelete() {
		try {
			MarksheetBean bean = new MarksheetBean();
			long pk = 2;
			bean.setId(pk);
			model.delete(bean);
			
			MarksheetBean deletedbean = model.findByPk(pk);
			if (deletedbean != null) {
				System.out.println("Test Delete fail");
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
		
	}

	public static void testUpdate() {
		try {
			MarksheetBean bean = model.findByPk(2);
			
			bean.setPhysics(92);
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
			
			model.update(bean);

			MarksheetBean updatedbean = model.findByPk(2);
			
			if (!"Admin".equals(updatedbean.getName())) {
				System.out.println("Test Update Success");
			}
		} catch (ApplicationException | DuplicateRecordException e) {
			e.printStackTrace();
		}
		
	}

	public static void testAdd() {
		
		MarksheetBean bean = new MarksheetBean();
		
		try {
			bean.setName("Shiva");
			bean.setRollNo("102");
			bean.setStudentId(2);
			bean.setPhysics(80);
			bean.setChemistry(85);
			bean.setMaths(87);
			bean.setCreatedBy("admin");
			bean.setModifiedBy("admin");
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
			
			long pk = model.add(bean);
			MarksheetBean addedbean = model.findByPk(pk);
			if (addedbean == null) {
				System.out.println("Test add fail");
			}
		} catch (ApplicationException | DuplicateRecordException e) {
			e.printStackTrace();
		}
		
	}
}
