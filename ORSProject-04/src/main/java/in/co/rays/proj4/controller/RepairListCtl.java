package in.co.rays.proj4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.RepairBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.model.RepairModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "RepairListCtl", urlPatterns = { "/ctl/RepairListCtl" })
public class RepairListCtl extends BaseCtl{

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		
		RepairBean bean = new RepairBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setDeviceName(DataUtility.getString(request.getParameter("deviceName")));
		bean.setRepairDate(DataUtility.getDate(request.getParameter("repairDate")));
		bean.setCost((double) DataUtility.getInt(request.getParameter("cost")));
		
		return bean;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
		
		RepairBean bean = (RepairBean) populateBean(req);
		RepairModel model = new RepairModel();
		
		try {
			List<RepairBean> list = model.search(bean, pageNo, pageSize);
			List<RepairBean> next = model.search(bean, pageNo + 1, pageSize);
			
			if (list == null || list.isEmpty()) {
				ServletUtility.setErrorMessage("No record found", req);
			}

			ServletUtility.setList(list, req);
			ServletUtility.setPageNo(pageNo, req);
			ServletUtility.setPageSize(pageSize, req);
			ServletUtility.setBean(bean, req);
			req.setAttribute("nextListSize", next.size());

			ServletUtility.forward(getView(), req, resp);
		} catch (ApplicationException e) {
			e.printStackTrace();
			ServletUtility.handleException(e, req, resp);
			return;
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		List list = null;
		List next = null;
		
		int pageNo = DataUtility.getInt(req.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(req.getParameter("pageSize"));
		
		pageNo = (pageNo == 0) ? 1 : pageNo;
		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;
		
		RepairBean bean = (RepairBean) populateBean(req);
		RepairModel model = new RepairModel();

		String op = DataUtility.getString(req.getParameter("operation"));
		String[] ids = req.getParameterValues("ids");

		try {

			if (OP_SEARCH.equalsIgnoreCase(op) || "Next".equalsIgnoreCase(op) || "Previous".equalsIgnoreCase(op)) {

				if (OP_SEARCH.equalsIgnoreCase(op)) {
					pageNo = 1;
				} else if (OP_NEXT.equalsIgnoreCase(op)) {
					pageNo++;
				} else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
					pageNo--;
				}

			} else if (OP_NEW.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.REPAIR_CTL, req, resp);
				return;

			} else if (OP_DELETE.equalsIgnoreCase(op)) {
				pageNo = 1;
				if (ids != null && ids.length > 0) {
					RepairBean deletebean = new RepairBean();
					for (String id : ids) {
						deletebean.setId(DataUtility.getInt(id));
						model.delete(deletebean);
						ServletUtility.setSuccessMessage("Data deleted successfully", req);
					}
				} else {
					ServletUtility.setErrorMessage("Select at least one record", req);
				}

			} else if (OP_RESET.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.REPAIR_LIST_CTL, req, resp);
				return;

			} else if (OP_BACK.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.REPAIR_LIST_CTL, req, resp);
				return;
			}

			list = model.search(bean, pageNo, pageSize);
			next = model.search(bean, pageNo + 1, pageSize);

			if (list == null || list.size() == 0) {
				ServletUtility.setErrorMessage("No record found ", req);
			}

			ServletUtility.setList(list, req);
			ServletUtility.setPageNo(pageNo, req);
			ServletUtility.setPageSize(pageSize, req);
			ServletUtility.setBean(bean, req);
			req.setAttribute("nextListSize", next.size());

			ServletUtility.forward(getView(), req, resp);

		} catch (ApplicationException e) {
			e.printStackTrace();
			ServletUtility.handleException(e, req, resp);
			return;
		}
	}
	
	@Override
	protected String getView() {
		return ORSView.REPAIR_LIST_VIEW;
	}

}
