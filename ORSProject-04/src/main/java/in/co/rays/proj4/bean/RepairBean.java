package in.co.rays.proj4.bean;

import java.util.Date;

public class RepairBean extends BaseBean{

	private String deviceName;
	private Date repairDate;
	private Double cost;
	
	
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public Date getRepairDate() {
		return repairDate;
	}
	public void setRepairDate(Date repairDate) {
		this.repairDate = repairDate;
	}
	public Double getCost() {
		return cost;
	}
	public void setCost(Double cost) {
		this.cost = cost;
	}
	@Override
	public String getKey() {
		return null;
	}
	@Override
	public String getValue() {
		return null;
	}
	
	
}
