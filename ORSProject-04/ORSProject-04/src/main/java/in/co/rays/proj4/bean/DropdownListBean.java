package in.co.rays.proj4.bean;

/**
 * DropdownListBean is a common interface implemented by all beans
 * that are used in dropdown lists. It provides two methods:
 *
 * getKey()   – returns the unique identifier of the record.
 * getValue() – returns the display value shown in dropdown menus.
 *
 * Author Apurva Deshmukh
 */
public interface DropdownListBean {

	/** Returns the key used in dropdown lists */
	public String getKey();

	/** Returns the value displayed in dropdown lists */
	public String getValue();
}
