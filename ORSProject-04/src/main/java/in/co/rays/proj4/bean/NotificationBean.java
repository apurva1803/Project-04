package in.co.rays.proj4.bean;

import java.util.Date;

public class NotificationBean extends BaseBean {
	
	private String code;
	private String type;
	private Date time;
	private String status;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		   return type;
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return type;
	}

}