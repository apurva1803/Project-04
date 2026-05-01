package in.co.rays.proj4.bean;

public class JobQueueBean extends BaseBean{

	private String jobCode;
	private String jobName;
	private String priority;
	private String status;
	
	public String getJobCode() {
		return jobCode;
	}
	public void setJobCode(String jobCode) {
		this.jobCode = jobCode;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Override
	public String getKey() {
		 return id + "";
	}
	@Override
	public String getValue() {
		return jobName + "" + status;
	}
	
}
