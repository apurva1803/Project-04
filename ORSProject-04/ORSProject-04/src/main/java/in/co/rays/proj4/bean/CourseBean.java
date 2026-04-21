package in.co.rays.proj4.bean;

/**
 * CourseBean represents a course entity in the system. It holds information
 * such as course name, duration, and description. This bean is used for
 * transferring course-related data between layers.
 *
 * Author Apurva Deshmukh
 */
public class CourseBean extends BaseBean{

	/** Course Name */
	private String name;

	/** Duration of the Course */
	private String duration;

	/** Description of the Course */
	private String description;

	/** Returns course name */
	public String getName() {
		return name;
	}

	/** Sets course name */
	public void setName(String name) {
		this.name = name;
	}

	/** Returns course duration */
	public String getDuration() {
		return duration;
	}

	/** Sets course duration */
	public void setDuration(String duration) {
		this.duration = duration;
	}

	/** Returns course description */
	public String getDescription() {
		return description;
	}

	/** Sets course description */
	public void setDescription(String description) {
		this.description = description;
	}

	/** Returns key for dropdown lists */
	@Override
	public String getKey() {
		return id + "";
	}

	/** Returns value for dropdown lists */
	@Override
	public String getValue() {
		return name;
	}
	
}
