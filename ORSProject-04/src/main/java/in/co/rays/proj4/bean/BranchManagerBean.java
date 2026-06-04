package in.co.rays.proj4.bean;

public class BranchManagerBean extends BaseBean{

	private String managerName;
	private String branchName;
	private String contactNumber;
	
	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	@Override
	public String getKey() {
		return managerName + " " + branchName;
	}

	@Override
	public String getValue() {
		return managerName + " " + branchName;
	}

	
}
