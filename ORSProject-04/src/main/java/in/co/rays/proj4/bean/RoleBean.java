package in.co.rays.proj4.bean;

/**
 * RoleBean represents different user roles in the system.
 * It stores role information like name and description.
 * Common system roles include Admin, Student, Faculty and Kiosk.
 *
 * Author Apurva Deshmukh
 */
public class RoleBean extends BaseBean{
	
	/** System Role: Administrator */
	public static final int ADMIN = 1;

    /** System Role: Student */
    public static final int STUDENT = 2;

    /** System Role: college */
    public static final int COLLEGE = 3;

    /** System Role: Kiosk User */
    public static final int KIOSK = 4;

    
    /** System Role: faculty User */
	
	
	public static final int FACULTY = 5;
	
	
    /** Name of the Role */
    private String name;

    /** Description of the Role */
    private String description;

    /** Returns role name */
    public String getName() {
        return name;
    }

    /** Sets role name */
    public void setName(String name) {
        this.name = name;
    }

    /** Returns description of role */
    public String getDescription() {
        return description;
    }

    /** Sets description of role */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns primary key as dropdown key
     */
    @Override
    public String getKey() {
        return id + "";
    }

    /**
     * Returns role name as dropdown value
     */
    @Override
    public String getValue() {
        return name;
    }
	
}
