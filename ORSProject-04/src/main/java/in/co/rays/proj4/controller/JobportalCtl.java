package in.co.rays.proj4.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.BudgetBean;
import in.co.rays.proj4.bean.JobportalBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.BudgetModel;
import in.co.rays.proj4.model.JobportalModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "JobportalCtl", urlPatterns = { "/ctl/JobportalCtl" })
public class JobportalCtl extends BaseCtl{

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("companyName"))) {
			request.setAttribute("companyName", PropertyReader.getValue("error.require", "Company Name"));
			pass = false;
		} 

		if (DataValidator.isNull(request.getParameter("jobRole"))) {
			request.setAttribute("jobRole", PropertyReader.getValue("error.require", "Job Role"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("salaryPackage"))) {
			request.setAttribute("salaryPackage", PropertyReader.getValue("error.require", "Salary Package"));
			pass = false;
		}
		
		if (DataValidator.isNull(request.getParameter("experienceRequired"))) {
			request.setAttribute("experienceRequired", PropertyReader.getValue("error.require", "Experience Required"));
			pass = false;
		}
		
		return pass;
	}
	
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		JobportalBean bean = new JobportalBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setCompanyName(DataUtility.getString(request.getParameter("companyName")));
		bean.setJobRole(DataUtility.getString(request.getParameter("jobRole")));
		bean.setSalaryPackage(DataUtility.getDouble(request.getParameter("salaryPackage")));
		bean.setExperienceRequired(DataUtility.getInt(request.getParameter("experienceRequired")));

		populateDTO(bean, request);

		return bean;
	}
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	
		long id = DataUtility.getLong(request.getParameter("id"));

		JobportalModel model = new JobportalModel();

		if (id > 0) {
			try {
				JobportalBean bean = model.findByPk(id);
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		

		String op = DataUtility.getString(request.getParameter("operation"));

		JobportalModel model = new JobportalModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op)) {

			JobportalBean bean = (JobportalBean) populateBean(request);

			try {
				long pk = model.add(bean);
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Data is successfully saved", request);
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage(" already exists", request);
			} catch (ApplicationException e) {
				e.printStackTrace();
				   ServletUtility.handleException(e, request, response, getView());
				return;
			}
		}else if (OP_UPDATE.equalsIgnoreCase(op)) {

			JobportalBean bean = (JobportalBean) populateBean(request);

			try {
				if (id > 0) {
					model.update(bean);
				}
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Data is successfully updated", request);
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage(" already exists", request);
			} catch (ApplicationException e) {
				e.printStackTrace();
				   ServletUtility.handleException(e, request, response, getView());
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.BUDGET_LIST_CTL, request, response);
			return;
		}
		
		 else if (OP_RESET.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.BUDGET_CTL, request, response);
				return;
			}
		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		return ORSView.JOBPORTAL_VIEW;
	}

}
