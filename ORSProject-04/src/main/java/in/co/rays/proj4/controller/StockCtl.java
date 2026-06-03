package in.co.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.LabBean;
import in.co.rays.proj4.bean.StockBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.LabModel;
import in.co.rays.proj4.model.StockModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "StockCtl", urlPatterns = { "/ctl/StockCtl" })
public class StockCtl extends BaseCtl{

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("stockName"))) {

			request.setAttribute("stockName", PropertyReader.getValue("error.require", "stockName"));
			pass = false;

		} 
		
		if (DataValidator.isNull(request.getParameter("investmentAmount"))) {
			request.setAttribute("investmentAmount", PropertyReader.getValue("error.require", "investmentAmount"));
			pass = false;
		} 

		if (DataValidator.isNull(request.getParameter("investmentType"))) {
			request.setAttribute("investmentType", PropertyReader.getValue("error.require", "investmentType"));
			pass = false;
		}

		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		StockBean bean = new StockBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		
		bean.setStockName(DataUtility.getString(request.getParameter("stockName")));
		bean.setInvestmentAmount(DataUtility.getDouble(request.getParameter("investmentAmount")));
		bean.setInvestmentType(DataUtility.getString(request.getParameter("investmentType")));
		
		populateDTO(bean, request);

		return bean;
	}

	

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		long id = DataUtility.getLong(request.getParameter("id"));

		StockModel model = new StockModel();

		if (id > 0) {
			try {
				StockBean bean = model.findByPk(id);
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

		StockModel model = new StockModel();
		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op)) {

			StockBean bean = (StockBean) populateBean(request);
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

			StockBean bean = (StockBean) populateBean(request);

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
			ServletUtility.redirect(ORSView.STOCK_LIST_CTL, request, response);
			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.STOCK_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {

		return ORSView.STOCK_VIEW;
	}


}
