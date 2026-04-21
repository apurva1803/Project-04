package in.co.rays.proj4.test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.RoleBean;
import in.co.rays.proj4.bean.UserBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.UserModel;

public class TestUserModel {

	public static UserModel model = new UserModel();
	
	public static void main(String[] args) {
		//testAdd();
		//testUpdate();
		//testDelete();
		//testfindByPk();
		//testFindByLogin();
		testSearch();
	}
	
	public static void testSearch() {
		try {
			UserBean bean = new UserBean();
			List list = new ArrayList();
			//bean.setFirstName("Apurva");
			list = model.search(bean, 0, 0);
			
			if (list.size() < 0) {
				System.out.println("Test Serach fail");
			}
			
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (UserBean) it.next();
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
	
	public static void testFindByLogin() {
		try {
			UserBean bean = model.findByLogin("apurva@gmail.com");
			
			if (bean == null) {
				System.out.println("Test Find By Login fail");
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
	
	public static void testfindByPk() {
		try {
			UserBean bean = model.findByPk(2);
			
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
			UserBean bean = new UserBean();
			long pk = 3;
			bean.setId(pk);
			model.delete(bean);
			System.out.println("Record Deleted..");
			
			UserBean deletedbean = model.findByPk(pk);
			if (deletedbean != null) {
				System.out.println("Test Delete fail");
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
		
	}

	public static void testUpdate() {
		try {
			UserBean bean = model.findByPk(2);
			
			bean.setFirstName("Shiva");
			bean.setLastName("Deshmukh");
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
			
			model.update(bean);

			UserBean updatedbean = model.findByPk(2);
			
			if (!"Admin".equals(updatedbean.getFirstName())) {
				System.out.println("Test Update Success");
			}
		} catch (ApplicationException | DuplicateRecordException e) {
			e.printStackTrace();
		}
		
	}

	public static void testAdd() {
		
		UserBean bean = new UserBean();
		
		try {
			bean.setFirstName("Shivansh");
			bean.setLastName("Deshmukh");
			bean.setLogin("Shiva@gmail.com");
			bean.setPassword("Shiva");
			bean.setDob(new Timestamp(new Date().getTime()));
			bean.setMobileNo("8778776655");
			bean.setRoleId(104);
			bean.setGender("male");
			bean.setCreatedBy("admin");
			bean.setModifiedBy("admin");
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
			
			long pk = model.add(bean);
			UserBean addedbean = model.findByPk(pk);
			if (addedbean == null) {
				System.out.println("Test add fail");
			}
		} catch (ApplicationException | DuplicateRecordException e) {
			e.printStackTrace();
		}
		
	}
	
}
