package in.co.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.JobQueueBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.JobQueueModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "JobQueueCtl", urlPatterns = { "/ctl/JobQueueCtl" })
public class JobQueueCtl extends BaseCtl{

protected boolean validate(HttpServletRequest request) {
		
		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("jobCode"))) {
			request.setAttribute("jobCode", PropertyReader.getValue("error.require", "jobCode"));
			pass = false;
		} 

		if (DataValidator.isNull(request.getParameter("jobName"))) {
			request.setAttribute("jobName", PropertyReader.getValue("error.require", "jobName"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("priority"))) {
			request.setAttribute("priority", PropertyReader.getValue("error.require", "priority"));
			pass = false;
		}
		
		if (DataValidator.isNull(request.getParameter("status"))) {
			request.setAttribute("status", PropertyReader.getValue("error.require", "status"));
			pass = false;
		}

		return pass;
	}
	
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		
		JobQueueBean bean = new JobQueueBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setJobCode(DataUtility.getString(request.getParameter("jobCode")));
		bean.setJobName(DataUtility.getString(request.getParameter("jobName")));
		bean.setPriority(DataUtility.getString(request.getParameter("priority")));
		bean.setStatus(DataUtility.getString(request.getParameter("status")));

		populateDTO(bean, request);

		return bean;
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		long id = DataUtility.getLong(req.getParameter("id"));
		
		JobQueueModel model = new JobQueueModel();
		
		if(id>0) {
			try {
				JobQueueBean bean = model.findByPk(id);
				ServletUtility.setBean(bean, req);
			} catch (Exception e) {
				e.printStackTrace();
				ServletUtility.handleException(e, req, resp, getView());
				return;
			}
		}	
		ServletUtility.forward(getView(), req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String op = DataUtility.getString(req.getParameter("operation"));
		
		JobQueueModel model = new JobQueueModel();
		
		long id = DataUtility.getLong(req.getParameter("id"));
		
		if(OP_SAVE.equalsIgnoreCase(op)) {
			JobQueueBean bean = (JobQueueBean) populateBean(req);
			
			try {
				long pk = model.add(bean);
				ServletUtility.setBean(bean, req);
				ServletUtility.setSuccessMessage("Data added successfully", req);
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, req);
				ServletUtility.setErrorMessage("Data already exists", req);
			} catch (ApplicationException e) {
				e.printStackTrace();
				ServletUtility.handleException(e, req, resp, getView());
				return;
			}
		}else if (OP_UPDATE.equalsIgnoreCase(op)) {
			JobQueueBean bean = (JobQueueBean) populateBean(req);
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
				ServletUtility.handleException(e, req, resp, getView());
				return;
			}
		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.JOBQUEUE_LIST_CTL, req, resp);
			return;
		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.JOBQUEUE_CTL, req, resp);
			return;
		}
		ServletUtility.forward(getView(), req, resp);
	}

	
	
	@Override
	protected String getView() {
		
		return ORSView.JOBQUEUE_VIEW;
	}
}
