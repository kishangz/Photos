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

public class Photo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String caption;
	
	private String photoName;
	
	private File f;
	//Date date;
	
	private Calendar calendar;
	
	private ArrayList<Tag> listOfTags;
	
	private int counter;
	
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
	
	public void addTag(Tag tag) {
		
		int tagListdex = getIndexForTagList(tag, 0);
		this.listOfTags.add(tagListdex,tag);
	}

	private int getIndexForTagList(Tag newTag, int i) {
		
		int start = 0;
		int mid;
		int end = this.listOfTags.size()-1;
		// TODO Auto-generated method stub
		
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
	
	public void deleteTag(Tag tag) {
		
		int tagListdex = getIndexForTagList(tag, 1);
		this.listOfTags.remove(tagListdex);
		
	}
	
	public ArrayList<Tag> getTags(){
		return this.listOfTags;
	}
	
	public File getFile() {
		return this.f;
	}
	
	public String getDateFormat() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(this.calendar.getTime());
		
	}
	
	public Date getDt() {
		return (Date) calendar.getTime();
	}
	
	public Date getSearchDate() throws ParseException{
		
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		String temp = df.format(this.calendar.getTime());
		
		Date tempDate = new SimpleDateFormat("MM/dd/yyyy").parse(temp);
		
		return tempDate;
		
	}
	
	public Image getImage() {
		Image image = new Image(this.f.toURI().toString());
		return image;
	}
	
	/*
	private transient Image image;
	private transient ObservableList<Tag> tags = FXCollections.observableArrayList();

	private Calendar date = Calendar.getInstance();
	
	


	public Image getImage() {
		return image;

	}
	
	*/

	public String getDate() {

		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		return df.format(this.calendar.getTime());
	}
	
	/*

	public ObservableList<Tag> getTags() {
		return tags;
	}
	*/
	public void setPhotoName(String photoName) {
		this.photoName = photoName;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}
	
	public String getCaption() {
		return this.caption;
	}
	
	public String toString() {
		
		return this.photoName;
	}
	
	public String getPhotoName() {
		return photoName;
	}
	

}
