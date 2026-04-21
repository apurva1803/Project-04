package in.co.rays.proj4.bean;

/**
 * MarksheetBean stores marksheet details of a student.
 * It includes roll number, student ID, student name,
 * and marks of three subjects: Physics, Chemistry, Maths.
 * Used in Marksheet Module of ORSProject-04.
 *
 * Author Apurva Deshmukh
 */
public class MarksheetBean extends BaseBean{

	/** Unique Roll Number of the student */
    private String rollNo;

    /** Student ID (Foreign Key) */
    private long studentId;

    /** Full name of the student */
    private String name;

    /** Physics marks */
    private Integer physics;

    /** Chemistry marks */
    private Integer chemistry;

    /** Maths marks */
    private Integer maths;

    /** Returns roll number */
    public String getRollNo() {
        return rollNo;
    }

    /** Sets roll number */
    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }

    /** Returns student ID */
    public long getStudentId() {
        return studentId;
    }

    /** Sets student ID */
    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    /** Returns student name */
    public String getName() {
        return name;
    }

    /** Sets student name */
    public void setName(String name) {
        this.name = name;
    }

    /** Returns physics marks */
    public Integer getPhysics() {
        return physics;
    }

    /** Sets physics marks */
    public void setPhysics(Integer physics) {
        this.physics = physics;
    }

    /** Returns chemistry marks */
    public Integer getChemistry() {
        return chemistry;
    }

    /** Sets chemistry marks */
    public void setChemistry(Integer chemistry) {
        this.chemistry = chemistry;
    }

    /** Returns maths marks */
    public Integer getMaths() {
        return maths;
    }

    /** Sets maths marks */
    public void setMaths(Integer maths) {
        this.maths = maths;
    }

    /**
     * Returns key for dropdown (roll number)
     */
    @Override
    public String getKey() {
        return rollNo;
    }

    /**
     * Returns value for dropdown (student name)
     */
    @Override
    public String getValue() {
        return name;
    }
	
	
}
