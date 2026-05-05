package in.co.rays.proj4.test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.RoleBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.RoleModel;

public class TestRoleModel {
	
	public static RoleModel model = new RoleModel();
	
	public static void main(String[] args) throws DatabaseException {
		
		testAdd();
		//testUpdate();
		//testDelete();
		//testfindByPk();
		//testFindByName();
		//testSearch();
	}
	
	public static void testSearch() {
		try {
			RoleBean bean = new RoleBean();
			List list = new ArrayList();
			//bean.setName("student");
			list = model.search(bean, 0, 0);
			
			if (list.size() < 0) {
				System.out.println("Test Serach fail");
			}
			
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (RoleBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getName());
				System.out.println(bean.getDescription());
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}
	
	public static void testFindByName() {
		try {
			RoleBean bean = model.findByName("college");
			
			if (bean == null) {
				System.out.println("Test Find By PK fail");
			}
			
			System.out.println(bean.getId());
			System.out.println(bean.getName());
			System.out.println(bean.getDescription());
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}
	
	public static void testfindByPk() {
		try {
			RoleBean bean = model.findByPk(2);
			
			if (bean == null) {
				System.out.println("Test Find By PK fail");
			}
			
			System.out.println("Id: " + bean.getId());
			System.out.println("Name: " + bean.getName());
			System.out.println("Description: " + bean.getDescription());
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
			RoleBean bean = new RoleBean();
			long pk = 1;
			bean.setId(pk);
			model.delete(bean);
			
			RoleBean deletedbean = model.findByPk(pk);
			if (deletedbean != null) {
				System.out.println("Test Delete fail");
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
		
	}

	public static void testUpdate() {
		try {
			RoleBean bean = model.findByPk(2);
			
			bean.setName("Student");
			bean.setDescription("Student");
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
			
			model.update(bean);

			RoleBean updatedbean = model.findByPk(2);
			
			if (!"Admin".equals(updatedbean.getName())) {
				System.out.println("Test Update Success");
			}
		} catch (ApplicationException | DuplicateRecordException e) {
			e.printStackTrace();
		}
		
	}

	public static void testAdd() throws DatabaseException {
		
		RoleBean bean = new RoleBean();
		
		try {
			bean.setName("Faculty");
			bean.setDescription("Faculty");
			bean.setCreatedBy("admin");
			bean.setModifiedBy("admin");
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
			long pk = model.add(bean);
			RoleBean addedbean = model.findByPk(pk);
			if (addedbean == null) {
				System.out.println("Test add fail");
			}
		} catch (ApplicationException | DuplicateRecordException e) {
			e.printStackTrace();
		}
		
	}
}
