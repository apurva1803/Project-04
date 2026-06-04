package in.co.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.BranchManagerBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.BranchManagerModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "BranchManagerCtl", urlPatterns = { "/ctl/BranchManagerCtl" })
public class BranchManagerCtl extends BaseCtl{

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("managerName"))) {

			request.setAttribute("managerName", PropertyReader.getValue("error.require", "managerName"));
			pass = false;

		} else if (!DataValidator.isName(request.getParameter("managerName"))) {
			request.setAttribute("managerName", "Invalid name");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("branchName"))) {

			request.setAttribute("branchName", PropertyReader.getValue("error.require", "branchName"));
			pass = false;

		} 

		if (DataValidator.isNull(request.getParameter("contactNumber"))) {

			request.setAttribute("contactNumber", PropertyReader.getValue("error.require", "contactNumber"));
			pass = false;

		} else if (!DataValidator.isPhoneNo(request.getParameter("contactNumber"))) {
			request.setAttribute("contactNumber", "Invalid Contact Number");
			pass = false;
		}

		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		BranchManagerBean bean = new BranchManagerBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		
		bean.setManagerName(DataUtility.getString(request.getParameter("managerName")));
		bean.setBranchName(DataUtility.getString(request.getParameter("branchName")));
		bean.setContactNumber(DataUtility.getString(request.getParameter("contactNumber")));
		
		populateDTO(bean, request);

		return bean;
	}

	

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		long id = DataUtility.getLong(request.getParameter("id"));

		BranchManagerModel model = new BranchManagerModel();

		if (id > 0) {
			try {
				BranchManagerBean bean = model.findByPk(id);
				ServletUtility.setBean(bean, request);
			} catch (ApplicationException e) {
				e.printStackTrace();
				   ServletUtility.handleException(e, request, response, getView());
				return;
			}
		}
		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String op = DataUtility.getString(request.getParameter("operation"));
		System.out.println("Operation = " + op);

		BranchManagerModel model = new BranchManagerModel();
		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op)) {

			BranchManagerBean bean = (BranchManagerBean) populateBean(request);
			System.out.println(" id" + request.getParameter("id"));
			try {
				long pk = model.add(bean);
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage(" added successfully", request);

			} catch (ApplicationException e) {
				e.printStackTrace();
				   ServletUtility.handleException(e, request, response, getView());
				return;
			} catch (DuplicateRecordException e) {
				e.printStackTrace();
			}

		} else if (OP_UPDATE.equalsIgnoreCase(op)) {

			BranchManagerBean bean = (BranchManagerBean) populateBean(request);

			try {
				if (id > 0) {
					model.update(bean);
				}
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage(" updated successfully", request);

			} catch (ApplicationException e) {
				e.printStackTrace();
				   ServletUtility.handleException(e, request, response, getView());
				return;
			} catch (DuplicateRecordException e) {
				e.printStackTrace();
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.BRANCHMANAGER_LIST_CTL, request, response);
			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.BRANCHMANAGER_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {

		return ORSView.BRANCHMANAGER_VIEW;
	}
}
