package in.co.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.GymworkoutBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.GymworkoutModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

/**
 * @author Apurva Deshmukh
 *
 */
@WebServlet(name = "GymworkoutCtl", urlPatterns = { "/ctl/GymworkoutCtl" })
public class GymworkoutCtl extends BaseCtl {

	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("name"))) {
			request.setAttribute("name", PropertyReader.getValue("error.require", "Name"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("name"))) {
			request.setAttribute("name", "Invalid Name");
			pass = false;
		}

		

		if (DataValidator.isNull(request.getParameter("workout"))) {
			request.setAttribute("workout", PropertyReader.getValue("error.require", "workout"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("trainer_name"))) {
			request.setAttribute("trainer_name", PropertyReader.getValue("error.require", "trainer_name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("schedule"))) {
			request.setAttribute("schedule", PropertyReader.getValue("error.require", "schedule"));
			pass = false;
		}

		return pass;
	}
	
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		GymworkoutBean bean = new GymworkoutBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setName(DataUtility.getString(request.getParameter("name")));
		bean.setWorkout(DataUtility.getString(request.getParameter("workout")));
		bean.setTrainerName(DataUtility.getString(request.getParameter("trainer_name")));
		bean.setSchedule(DataUtility.getString(request.getParameter("schedule")));
	

		populateDTO(bean, request);

		return bean;
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		long id = DataUtility.getLong(request.getParameter("id"));

		GymworkoutModel model = new GymworkoutModel();

		if (id > 0) {
			try {
				GymworkoutBean bean = model.findByPk(id);
				ServletUtility.setBean(bean, request);
			} catch (ApplicationException e) {
				e.printStackTrace();
				
				return;
			}
		}
		ServletUtility.forward(getView(), request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String op = DataUtility.getString(request.getParameter("operation"));

		GymworkoutModel model = new GymworkoutModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op)) {
			GymworkoutBean bean = (GymworkoutBean) populateBean(request);
			try {
				long pk = model.add(bean);
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Data is successfully saved", request);
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("code  already exists", request);
			} catch (ApplicationException e) {
				e.printStackTrace();
			
				return;
			}
		}
		else if (OP_UPDATE.equalsIgnoreCase(op)) {
			GymworkoutBean bean = (GymworkoutBean) populateBean(request);
			try {
				if (id > 0) {
					model.update(bean);
				}
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("User updated successfully", request);
				
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Login Id already exists", request);
			} catch (ApplicationException e) {
				e.printStackTrace();
				
				return;
			}
		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.GYMWORKOUT_LIST_CTL, request, response);
			return;
		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.GYMWORKOUT_CTL, request, response);
			return;
		}
		
		ServletUtility.forward(getView(), request, response);
	
	}
	@Override
	protected String getView() {
	
		
				return ORSView.GYMWORKOUT_VIEW;
	}

}