package in.co.rays.proj4.bean;

import java.util.Date;

/**
 * StudentBean represents a student entity in the application.
 * It contains personal details, college information and audit data
 * inherited from BaseBean.
 *
 * Author Apurva Deshmukh
 */
public class StudentBean extends BaseBean{
	
	/** Student first name */
    private String firstName;

    /** Student last name */
    private String lastName;

    /** Date of Birth */
    private Date dob;

    /** Gender (Male/Female/Other) */
    private String gender;

    /** Mobile number */
    private String mobileNo;

    /** Email ID */
    private String email;

    /** College Primary Key */
    private long collegeId;

    /** College Name */
    private String collegeName;

    /** Returns first name */
    public String getFirstName() {
        return firstName;
    }

    /** Sets first name */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /** Returns last name */
    public String getLastName() {
        return lastName;
    }

    /** Sets last name */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /** Returns date of birth */
    public Date getDob() {
        return dob;
    }

    /** Sets date of birth */
    public void setDob(Date dob) {
        this.dob = dob;
    }

    /** Returns gender */
    public String getGender() {
        return gender;
    }

    /** Sets gender */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /** Returns mobile number */
    public String getMobileNo() {
        return mobileNo;
    }

    /** Sets mobile number */
    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    /** Returns email */
    public String getEmail() {
        return email;
    }

    /** Sets email */
    public void setEmail(String email) {
        this.email = email;
    }

    /** Returns college ID (FK) */
    public long getCollegeId() {
        return collegeId;
    }

    /** Sets college ID (FK) */
    public void setCollegeId(long collegeId) {
        this.collegeId = collegeId;
    }

    /** Returns college name */
    public String getCollegeName() {
        return collegeName;
    }

    /** Sets college name */
    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    /**
     * Dropdown key = Student ID
     */
    @Override
    public String getKey() {
        return id + "";
    }

    /**
     * Dropdown value = Full name
     */
    @Override
    public String getValue() {
        return firstName + " " + lastName;
    }
	
}
