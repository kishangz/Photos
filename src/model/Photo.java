package model;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javafx.scene.image.Image;

/**
 * This class defines properties of a photo
 * Properties might include: photoName, Caption, Location etc.
 * which are all related to a specific photo.
 * @author Kishan Zalora Eyob Tesfaye
 *
 */
public class Photo implements Serializable {
	
	/**
	 * Serial Version ID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * String caption
	 */
	private String caption;
	
	/**
	 *  String photoName
	 */
	private String photoName;
	
	/**
	 * File f
	 */
	private File f;
	
	/**
	 * Claendar claendar
	 */
	private Calendar calendar;
	
	/**
	 * ArrayList of tag
	 */
	private ArrayList<Tag> listOfTags;
	
	/**
	 * Counter
	 */
	private int counter;
	
	/**
	 * Initalizes the photo object
	 * @param photoName
	 * @param caption
	 * @param f
	 */
	public Photo(String photoName, String caption, File f) {
		try {
			this.photoName = f.getCanonicalPath();
		}
		
		catch (IOException e) {
			e.printStackTrace();
		}
		
		this.caption = caption;
		this.f = f;
		this.listOfTags = new ArrayList<Tag>();
		Date filedate = new Date(f.lastModified());
		calendar = new GregorianCalendar();
		calendar.setTime(filedate);
		calendar.set(Calendar.MILLISECOND, 0);
		counter = 0;
		
	}
	
	/**
	 * Adds tag
	 * @param tag
	 */
	public void addTag(Tag tag) {
		
		int tagListdex = getIndexForTagList(tag, 0);
		this.listOfTags.add(tagListdex,tag);
	}

	/**
	 * Gets Index for the tag list
	 * @param newTag
	 * @param i
	 * @return
	 */
	private int getIndexForTagList(Tag newTag, int i) {
		
		int start = 0;
		int mid;
		int end = this.listOfTags.size()-1;
				
		while(end >= start) {
			
			mid = (start + end)/ 2;
			
			if(this.listOfTags.get(mid).equals(newTag)) {
				if(i ==1) {
					return mid;
				}
				return -1;
			}
			else if (this.listOfTags.get(mid).toString().compareToIgnoreCase(newTag.toString()) < 0) {
				start = mid +1;
			}
			else {
				end = mid-1;
			}
			
		} if (i ==1) {
			return -1;
		}
		return start;
	}
	
	/**
	 * Delets tag
	 * @param tag
	 */
	public void deleteTag(Tag tag) {
		
		int tagListdex = getIndexForTagList(tag, 1);
		this.listOfTags.remove(tagListdex);
		
	}
	
	/**
	 * ArrayList of tag that returns list
	 * @return
	 */
	public ArrayList<Tag> getTags(){
		return this.listOfTags;
	}
	
	/**
	 * File 
	 * @return file
	 */
	public File getFile() {
		return this.f;
	}
	
	/**
	 * Gets the date format
	 * @return
	 */
	public String getDateFormat() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(this.calendar.getTime());
		
	}
	
	/**
	 * gets Date
	 * @return
	 */
	public Date getDt() {
		return (Date) calendar.getTime();
	}
	
	/**
	 * Search range of dates 
	 * @return
	 * @throws ParseException
	 */
	public Date getSearchDate() throws ParseException{
		
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		String temp = df.format(this.calendar.getTime());
		
		Date tempDate = new SimpleDateFormat("MM/dd/yyyy").parse(temp);
		
		return tempDate;
		
	}
	
	/**
	 * Gets Image
	 * @return
	 */
	public Image getImage() {
		Image image = new Image(this.f.toURI().toString());
		return image;
	}
	
	/**
	 * gets Date
	 * @return
	 */
	public String getDate() {

		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		return df.format(this.calendar.getTime());
	}

	/**
	 * Sets Photo Name
	 * @param photoName
	 */
	public void setPhotoName(String photoName) {
		this.photoName = photoName;
	}

	/**
	 * Sets Caption
	 * @param caption
	 */
	public void setCaption(String caption) {
		this.caption = caption;
	}
	
	/**
	 * Gets Caption
	 * @return
	 */
	public String getCaption() {
		return this.caption;
	}
	
	/**
	 * Tostring method
	 */
	public String toString() {
		
		return this.photoName;
	}
	
	/**
	 * Gets photo name
	 * @return
	 */
	public String getPhotoName() {
		return photoName;
	}
	

}
