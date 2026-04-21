package in.co.rays.proj4.bean;

import java.util.Date;

/**
 * FacultyBean represents the details of a faculty member.
 * It stores personal information along with college, course,
 * and subject mapping. This bean is used across Model, Controller,
 * and View layers in ORSProject-04.
 *
 * Author Apurva Deshmukh
 */
public class FacultyBean extends BaseBean{
	
	/** Faculty first name */
    private String firstName;

    /** Faculty last name */
    private String lastName;

    /** Date of Birth */
    private Date dob;

    /** Gender (Male/Female/Other) */
    private String gender;

    /** Mobile Number */
    private String mobileNo;

    /** Email ID */
    private String email;

    /** College ID (FK) */
    private long collegeId;

    /** College Name */
    private String collegeName;

    /** Course ID (FK) */
    private long courseId;

    /** Course Name */
    private String courseName;

    /** Subject ID (FK) */
    private long subjectId;

    /** Subject Name */
    private String subjectName;

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

    /** Returns Date of Birth */
    public Date getDob() {
        return dob;
    }

    /** Sets Date of Birth */
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

    /** Returns email address */
    public String getEmail() {
        return email;
    }

    /** Sets email address */
    public void setEmail(String email) {
        this.email = email;
    }

    /** Returns college ID */
    public long getCollegeId() {
        return collegeId;
    }

    /** Sets college ID */
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

    /** Returns course ID */
    public long getCourseId() {
        return courseId;
    }

    /** Sets course ID */
    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    /** Returns course name */
    public String getCourseName() {
        return courseName;
    }

    /** Sets course name */
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    /** Returns subject ID */
    public long getSubjectId() {
        return subjectId;
    }

    /** Sets subject ID */
    public void setSubjectId(long subjectId) {
        this.subjectId = subjectId;
    }

    /** Returns subject name */
    public String getSubjectName() {
        return subjectName;
    }

    /** Sets subject name */
    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    /**
     * Returns key for dropdown lists (Primary Key)
     */
    @Override
    public String getKey() {
        return id + "";
    }

    /**
     * Returns value for dropdown lists (Full Name)
     */
    @Override
    public String getValue() {
        return firstName + " " + lastName;
    }

}
