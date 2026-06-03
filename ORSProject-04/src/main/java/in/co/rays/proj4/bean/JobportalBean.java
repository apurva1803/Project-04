package in.co.rays.proj4.bean;

import java.util.Date;

public class JobportalBean extends BaseBean{

	private String companyName;
	private String jobRole;
	private double salaryPackage;
	private int experienceRequired;
	
	
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getJobRole() {
		return jobRole;
	}

	public void setJobRole(String jobRole) {
		this.jobRole = jobRole;
	}

	public double getSalaryPackage() {
		return salaryPackage;
	}

	public void setSalaryPackage(double salaryPackage) {
		this.salaryPackage = salaryPackage;
	}

	public int getExperienceRequired() {
		return experienceRequired;
	}

	public void setExperienceRequired(int experienceRequired) {
		this.experienceRequired = experienceRequired;
	}

	@Override
	public String getKey() {
		return jobRole;
	}

	@Override
	public String getValue() {
		return jobRole;
	}

}
