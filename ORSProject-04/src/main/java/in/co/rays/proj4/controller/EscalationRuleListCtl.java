package in.co.rays.proj4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.CourseBean;
import in.co.rays.proj4.bean.EscalationRuleBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.model.CourseModel;
import in.co.rays.proj4.model.EscalationRuleModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "EscalationRuleListCtl", urlPatterns = { "/ctl/EscalationRuleListCtl" })
public class EscalationRuleListCtl extends BaseCtl{

	
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		
		EscalationRuleBean bean = new EscalationRuleBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setRuleCode(DataUtility.getString(request.getParameter("ruleCode")));
		bean.setLevel(DataUtility.getString(request.getParameter("level")));
		bean.setAssignedTo(DataUtility.getString(request.getParameter("assignedTo")));
		bean.setStatus(DataUtility.getString(request.getParameter("status")));


		return bean;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
		
		EscalationRuleBean bean = (EscalationRuleBean) populateBean(req);
		EscalationRuleModel model = new EscalationRuleModel();
		
		try {
			List<EscalationRuleBean> list = model.search(bean, pageNo, pageSize);
			List<EscalationRuleBean> next = model.search(bean, pageNo + 1, pageSize);
			
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
		
		EscalationRuleBean bean = (EscalationRuleBean) populateBean(req);
		EscalationRuleModel model = new EscalationRuleModel();

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
				ServletUtility.redirect(ORSView.ESCALATIONRULE_CTL, req, resp);
				return;

			} else if (OP_DELETE.equalsIgnoreCase(op)) {
				pageNo = 1;
				if (ids != null && ids.length > 0) {
					EscalationRuleBean deletebean = new EscalationRuleBean();
					for (String id : ids) {
						deletebean.setId(DataUtility.getInt(id));
						model.delete(deletebean);
						ServletUtility.setSuccessMessage("Data deleted successfully", req);
					}
				} else {
					ServletUtility.setErrorMessage("Select at least one record", req);
				}

			} else if (OP_RESET.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.ESCALATIONRULE_LIST_CTL, req, resp);
				return;

			} else if (OP_BACK.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.ESCALATIONRULE_LIST_CTL, req, resp);
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
		return ORSView.ESCALATIONRULE_LIST_VIEW;
	}

}
