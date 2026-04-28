package in.co.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.RepairBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.RepairModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "RepairCtl", urlPatterns = { "/ctl/RepairCtl" })
public class RepairCtl extends BaseCtl{

protected boolean validate(HttpServletRequest request) {
		
		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("deviceName"))) {
			request.setAttribute("ruleCode", PropertyReader.getValue("error.require", "device Name"));
			pass = false;
		} 

		if (DataValidator.isNull(request.getParameter("repairDate"))) {
			request.setAttribute("level", PropertyReader.getValue("error.require", "repair Date"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("cost"))) {
			request.setAttribute("description", PropertyReader.getValue("error.require", "cost"));
			pass = false;
		}
		

		return pass;
	}
	
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		
		RepairBean bean = new RepairBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setDeviceName(DataUtility.getString(request.getParameter("deviceName")));
		bean.setRepairDate(DataUtility.getDate(request.getParameter("repairDate")));
		bean.setCost((double) DataUtility.getInt(request.getParameter("cost")));
		

		populateDTO(bean, request);

		return bean;
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		long id = DataUtility.getLong(req.getParameter("id"));
		
		RepairModel model = new RepairModel();
		
		if(id>0) {
			try {
				RepairBean bean = model.findByPk(id);
				ServletUtility.setBean(bean, req);
			} catch (Exception e) {
				e.printStackTrace();
				ServletUtility.handleException(e, req, resp);
				return;
			}
		}	
		ServletUtility.forward(getView(), req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String op = DataUtility.getString(req.getParameter("operation"));
		
		RepairModel model = new RepairModel();
		
		long id = DataUtility.getLong(req.getParameter("id"));
		
		if(OP_SAVE.equalsIgnoreCase(op)) {
			RepairBean bean = (RepairBean) populateBean(req);
			
			try {
				long pk = model.add(bean);
				ServletUtility.setBean(bean, req);
				ServletUtility.setSuccessMessage("Data added successfully", req);
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, req);
				ServletUtility.setErrorMessage("Data already exists", req);
			} catch (ApplicationException e) {
				e.printStackTrace();
				ServletUtility.handleException(e, req, resp);
				return;
			}
		}else if (OP_UPDATE.equalsIgnoreCase(op)) {
			RepairBean bean = (RepairBean) populateBean(req);
			try {
				if (id > 0) {
					model.update(bean);
				}
				ServletUtility.setBean(bean, req);
				ServletUtility.setSuccessMessage("Data updated successfully", req);
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, req);
				ServletUtility.setErrorMessage("Data already exists", req);
			} catch (ApplicationException e) {
				e.printStackTrace();
				ServletUtility.handleException(e, req, resp);
				return;
			}
		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.REPAIR_LIST_CTL, req, resp);
			return;
		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.REPAIR_CTL, req, resp);
			return;
		}
		ServletUtility.forward(getView(), req, resp);
	}

	
	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.REPAIR_VIEW;
	}

}
