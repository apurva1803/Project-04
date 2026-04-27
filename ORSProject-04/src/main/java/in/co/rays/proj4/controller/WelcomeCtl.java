package in.co.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.proj4.util.ServletUtility;

@WebServlet("/WelcomeCtl")
public class WelcomeCtl extends BaseCtl{

	private static final Logger log = Logger.getLogger(WelcomeCtl.class);	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		log.debug("WelcomeCtl doGet() called - forwarding to welcome view");
		
        ServletUtility.forward(getView(), req, resp);
        
        log.info("Forwarded to view: " + getView());
	}
	
	@Override
	protected String getView() {
		return ORSView.WELCOME_VIEW;
	}

}