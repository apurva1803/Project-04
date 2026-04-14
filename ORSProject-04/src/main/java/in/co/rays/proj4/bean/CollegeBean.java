package in.co.rays.proj4.bean;

/**
 * CollegeBean represents the college entity. It stores details such as name,
 * address, state, city, and phone number. This bean is used to transfer
 * college-related data across layers.
 * 
 * @author Apurva Deshmukh
 *
 */
public class CollegeBean extends BaseBean{

	/** College Name */
	private String name;
	
	/** College Address */
	private String address;
	
	/** State of the College */
	private String state;
	
	/** City of the College */
	private String city;
	
	/** College Contact Number */
	private String phoneNo;
	
	/** Returns college name */
	public String getName() {
		return name;
	}
	
	/** Sets college name */
	public void setName(String name) {
		this.name = name;
	}
	
	/** Returns address */
	public String getAddress() {
		return address;
	}
	
	/** Sets address */
	public void setAddress(String address) {
		this.address = address;
	}
	
	/** Returns state */
	public String getState() {
		return state;
	}
	
	/** Sets state */
	public void setState(String state) {
		this.state = state;
	}
	
	/** Returns city */
	public String getCity() {
		return city;
	}
	
	/** Sets city */
	public void setCity(String city) {
		this.city = city;
	}
	
	/** Returns phone number */
	public String getPhoneNo() {
		return phoneNo;
	}
	
	/** Sets phone number */
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	/** Key used for dropdowns */
	@Override
	public String getKey() {
		return id + "";
	}

	/** Value shown in dropdowns */
	@Override
	public String getValue() {
		return name;
	}
	
}
