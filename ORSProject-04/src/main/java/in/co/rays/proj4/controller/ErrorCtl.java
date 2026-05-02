package in.co.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.util.ServletUtility;

@WebServlet("/ctl/ErrorCtl")
public class ErrorCtl extends BaseCtl {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ServletUtility.forward(getView(), request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		ServletUtility.forward(getView(), request, response);
	}
	
	private void process(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		String lastCtl = (String) request.getAttribute("javax.servlet.error.request_uri");
		if (lastCtl == null) {
			lastCtl = (String) request.getAttribute("lastCtl");
		}
		String view = (String) request.getAttribute("view");


		ServletUtility.setErrorMessage("Database server down!!!", request);
		if (lastCtl != null && lastCtl.contains("ListCtl")) {

		    if (ServletUtility.getList(request) == null) {
		    	ServletUtility.setList(new java.util.ArrayList(), request);
		    }

		    request.setAttribute("pageNo", 1);
		    request.setAttribute("pageSize", 10);
		    request.setAttribute("nextListSize", 0);
		}
	}

	@Override
	protected String getView() {
		return ORSView.LOGIN_VIEW;
	}
}
