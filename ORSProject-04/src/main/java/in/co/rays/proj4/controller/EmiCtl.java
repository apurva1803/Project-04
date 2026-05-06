
package in.co.rays.proj4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.EmiBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.EmiModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "EmiCtl", urlPatterns = { "/ctl/EmiCtl" })
public class EmiCtl extends BaseCtl {

   
    // ================= Populate Bean =================
    @Override
    protected BaseBean populateBean(HttpServletRequest request) {

        EmiBean bean = new EmiBean();

        bean.setId(DataUtility.getLong(request.getParameter("id")));
        bean.setAmount(DataUtility.getLong(request.getParameter("amount")));
        bean.setDueDate(DataUtility.getDate(request.getParameter("dueDate")));
        bean.setStatus(DataUtility.getString(request.getParameter("status")));

        populateDTO(bean, request);

        return bean;
    }

    // ================= Validate =================
    @Override
    protected boolean validate(HttpServletRequest request) {

        boolean pass = true;

        if (DataValidator.isNull(request.getParameter("amount"))) {
            request.setAttribute("amount",
                    PropertyReader.getValue("error.require", "amount"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("dueDate"))) {
            request.setAttribute("dueDate",
                    PropertyReader.getValue("error.require", "dueDate"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("status"))) {
            request.setAttribute("status",
                    PropertyReader.getValue("error.require", "status"));
            pass = false;
        }

        return pass;
    }

    // ================= GET =================
    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        long id = DataUtility.getLong(request.getParameter("id"));

        EmiModel model = new EmiModel();

        if (id > 0) {
            try {
                EmiBean bean = model.findByPk(id);
                ServletUtility.setBean(bean, request);
            } catch (ApplicationException e) {
                e.printStackTrace();
                ServletUtility.handleException(e, request, response, getView());
                return;
            }
        }

        ServletUtility.forward(getView(), request, response);
    }

    // ================= POST =================
    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String op = DataUtility.getString(request.getParameter("operation"));

        EmiModel model = new EmiModel();
        long id = DataUtility.getLong(request.getParameter("id"));

        if (OP_SAVE.equalsIgnoreCase(op)) {

            EmiBean bean = (EmiBean) populateBean(request);

            try {
                long pk = 0;
				try {
					pk = model.add(bean);
				} catch (DuplicateRecordException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                bean.setId(pk);  // IMPORTANT
                ServletUtility.setBean(bean, request);
                ServletUtility.setSuccessMessage("EMI Added Successfully", request);

            } catch (ApplicationException e) {
                e.printStackTrace();
                ServletUtility.handleException(e, request, response, getView());
                return;
            }

        } else if (OP_UPDATE.equalsIgnoreCase(op)) {

            EmiBean bean = (EmiBean) populateBean(request);

            try {
                if (id > 0) {
                    model.update(bean);
                }

                ServletUtility.setBean(bean, request);
                ServletUtility.setSuccessMessage("EMI Updated Successfully", request);

            } catch (ApplicationException e) {
                e.printStackTrace();
                ServletUtility.handleException(e, request, response, getView());
                return;
            } catch (DuplicateRecordException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

        } else if (OP_CANCEL.equalsIgnoreCase(op)) {
            ServletUtility.redirect(ORSView.EMI_LIST_CTL, request, response);
            return;

        } else if (OP_RESET.equalsIgnoreCase(op)) {
            ServletUtility.redirect(ORSView.EMI_CTL, request, response);
            return;
        }

        ServletUtility.forward(getView(), request, response);
    }

    @Override
    protected String getView() {
        return ORSView.EMI_VIEW;
    }
}