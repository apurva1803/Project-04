package in.co.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.EscalationRuleBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.EscalationRuleModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "EscalationRuleCtl", urlPatterns = { "/ctl/EscalationRuleCtl" })
public class EscalationRuleCtl extends BaseCtl {

protected boolean validate(HttpServletRequest request) {
		
		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("ruleCode"))) {
			request.setAttribute("ruleCode", PropertyReader.getValue("error.require", "ruleCode"));
			pass = false;
		} 

		if (DataValidator.isNull(request.getParameter("level"))) {
			request.setAttribute("level", PropertyReader.getValue("error.require", "level"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("assignedTo"))) {
			request.setAttribute("description", PropertyReader.getValue("error.require", "assignedTo"));
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
		
		EscalationRuleBean bean = new EscalationRuleBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setRuleCode(DataUtility.getString(request.getParameter("ruleCode")));
		bean.setLevel(DataUtility.getString(request.getParameter("level")));
		bean.setAssignedTo(DataUtility.getString(request.getParameter("assignedTo")));
		bean.setStatus(DataUtility.getString(request.getParameter("status")));

		populateDTO(bean, request);

		return bean;
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		long id = DataUtility.getLong(req.getParameter("id"));
		
		EscalationRuleModel model = new EscalationRuleModel();
		
		if(id>0) {
			try {
				EscalationRuleBean bean = model.findByPk(id);
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
		
		EscalationRuleModel model = new EscalationRuleModel();
		
		long id = DataUtility.getLong(req.getParameter("id"));
		
		if(OP_SAVE.equalsIgnoreCase(op)) {
			EscalationRuleBean bean = (EscalationRuleBean) populateBean(req);
			
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
			EscalationRuleBean bean = (EscalationRuleBean) populateBean(req);
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
			ServletUtility.redirect(ORSView.ESCALATIONRULE_LIST_CTL, req, resp);
			return;
		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.ESCALATIONRULE_CTL, req, resp);
			return;
		}
		ServletUtility.forward(getView(), req, resp);
	}

	
	
	@Override
	protected String getView() {
		
		return ORSView.ESCALATIONRULE_VIEW;
	}

}
