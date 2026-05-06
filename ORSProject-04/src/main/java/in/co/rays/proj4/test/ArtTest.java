package in.co.rays.proj4.test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.ArtBean;

import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.model.ArtModel;

public class ArtTest {

	public static void main(String[] args) {
		testList();
		
	}
	
	public static void testList() {
		try {
			ArtModel model=new ArtModel();
			ArtBean bean = null;
			List list = new ArrayList();
			list = model.list();
			if (list.size() < 0) {
				System.out.println("Test Serach fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (ArtBean) it.next();
				System.out.println("ID : " + bean.getId());
				System.out.println("State : " + bean.getTitle());
				System.out.println("Name : " + bean.getName());
				System.out.println("State : " + bean.getDate());
				System.out.println("City : " + bean.getPrice());
		
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}
}
