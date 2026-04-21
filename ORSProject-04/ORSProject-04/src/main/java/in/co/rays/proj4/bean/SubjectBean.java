package in.co.rays.proj4.bean;

/**
 * SubjectBean represents a subject entity.
 * It contains subject details along with the associated course.
 *
 * Author Apurva Deshmukh
 */
public class SubjectBean extends BaseBean{

	/** Subject name */
    private String name;

    /** Course ID (FK) */
    private long courseId;

    /** Course name */
    private String courseName;

    /** Subject description */
    private String description;

    /** Returns subject name */
    public String getName() {
        return name;
    }

    /** Sets subject name */
    public void setName(String name) {
        this.name = name;
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

    /** Returns description */
    public String getDescription() {
        return description;
    }

    /** Sets description */
    public void setDescription(String description) {
        this.description = description;
    }

    /** Dropdown key = ID */
    @Override
    public String getKey() {
        return id + "";
    }

    /** Dropdown value = Subject name */
    @Override
    public String getValue() {
        return name;
    }
	
}
