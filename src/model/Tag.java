package model;

import java.io.Serializable;

/**
 *  This class defines Tags.
 * Tags are a combination of type and value (type) Location, person etc. that
 * go along with the (value) name of location, or person.
 * @author Kishan Zalora Eyob Tesfaye
 *
 */
public class Tag implements Serializable  {
	
	/**
	 * Serial Versio ID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * String type and value
	 */
	public String type, value;
	
	/**
	 * Int total
	 */
	public int total;
	
	/**
	 * Creates a tag object
	 * @param type
	 * @param value
	 */
	public Tag(String type, String value) {
		
		this.type = type.toLowerCase();
		this.value = value.toLowerCase();
		this.total = 0;
	}
	
	/**
	 * Compares two tags
	 * @param tag
	 * @return
	 */
	public boolean equals(Tag tag) {
		
		if(type.equalsIgnoreCase(tag.type) && value.equalsIgnoreCase(tag.value)) {
			return true;
		}
		return false;
	}
	
	/**
	 * Gets the type of tag
	 * @return
	 */
	public String getType() {
      return this.type;
    }
	
	/**
	 * sets the type of tag
	 * @param t
	 */
	public void setType(String t) {
		
		this.type = t;
	}
	
	/**
	 * sets value of tag
	 * @param v
	 */
	public void setValue(String v) {
		this.value = v;
	}
	
	/**
	 * gets value of tag
	 * @return
	 */
	public String getValue() {
		return this.value;
	}
	
	/**
	 * gets tag
	 * @return
	 */
	public String getTag() {
		// TODO Auto-generated method stub
		return this.type;
	}
	
	/**
	 * to string method
	 */
	public String toString() {
		return this.type + ":" + this.value;
	}

}
