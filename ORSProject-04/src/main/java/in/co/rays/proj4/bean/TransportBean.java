package in.co.rays.proj4.bean;

public class TransportBean  extends BaseBean {

	private String vehicleType;
	private String driverName;
	private int charges;
	
	public String getVehicleType() {
		return vehicleType;
	}
	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}
	public String getDriverName() {
		return driverName;
	}
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	public int getCharges() {
		return charges;
	}
	public void setCharges(int charges) {
		this.charges = charges;
	}
	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return driverName;
	}
	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return driverName;
	}
}
