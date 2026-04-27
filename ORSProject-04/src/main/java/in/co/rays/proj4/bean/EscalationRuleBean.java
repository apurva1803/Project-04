package in.co.rays.proj4.bean;

public class EscalationRuleBean extends BaseBean{

	
	private String ruleCode;
	private String level;
	private String assignedTo;
	private String status;
	
	
	public String getRuleCode() {
		return ruleCode;
	}
	public void setRuleCode(String ruleCode) {
		this.ruleCode = ruleCode;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getAssignedTo() {
		return assignedTo;
	}
	public void setAssignedTo(String assignedTo) {
		this.assignedTo = assignedTo;
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
		// TODO Auto-generated method stub
		return assignedTo;
	}
	
	
}
