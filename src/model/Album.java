package model;

import java.io.Serializable;
import java.util.ArrayList;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

/**
 * This class defines characteristics of an album
 * @author Kishan Zalora Eyob Tesfaye
 *
 */
public class Album implements Serializable, Comparable<Album> {
	
	/**
	 * Serial Version ID 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * String name
	 */
	
	private String name;
	
	/**
	 * HashMap of Photo
	 */
	private HashMap<String, Photo> photos;
	
	/**
	 * 	Album Constructor
	 * @param name
	 */
	public Album(String name) {
		this.name = name; 
		this.photos = new HashMap<String, Photo>();
		
	}
	
	/**
	 * Gets Name
	 * @return
	 */
	public String getName() {
		
		return this.name;
		
	}
	
	/**
	 * Sets Album name
	 * @param name
	 */
	public void setAlbumName(String name) {
		this.name = name;
		
	}
	
	/**
	 * Loads the photos from a serialized file into the Album
	 * @param photos
	 */
	public void setPhotoHashMap(HashMap<String, Photo> photos) {
		this.photos = photos; 
	}
	
	/**
	 * 
	 * @return hashMap containing photos of the album
	 */
	public HashMap<String, Photo> getListOfPhotos(){
		return this.photos;
	}
	
	/**
	 * Adds Photo
	 * @param photo
	 */
	public void addPhoto(Photo photo) {
		this.photos.put(photo.getPhotoName(), photo);
	}
	
	/**
	 * Removes Photo
	 * @param photoName
	 */
	public void removePhoto(String photoName) {
		this.photos.remove(photoName);
	}
	
	/**
	 * Gets the size of album
	 * @return
	 */
	public String getAlbumSize() {
		String s = "" + photos.size();
		return s;
	}

	/**
	 * Compares Album
	 */
	@Override
	public int compareTo(Album otherAlbum) {
		// TODO Auto-generated method stub
		return this.name.compareTo(otherAlbum.getName());
	}
	
	/**
	 * Gets Latest Date
	 * @return
	 */
	public String getLatestDate()
	{
		Collection<Photo> tempPhoto = new ArrayList<Photo>(this.photos.values());
		ArrayList<Photo> PhotoArray = new ArrayList<Photo>(tempPhoto);
		
		
		if(PhotoArray.isEmpty())
		{
			return "";
		}
		
		
		DateComparator dc = new DateComparator();
		Collections.sort(PhotoArray, dc);
		
		for(int i = 0; i < PhotoArray.size(); i++)
		{
		}
		
		return PhotoArray.get(PhotoArray.size()-1).getDate();

	}
	
	/**
	 * Gets The Earliest Date
	 * @return
	 */
	public String getEarliestDate() {
		
		Collection<Photo> tempPhoto = new ArrayList<Photo>(this.photos.values());
		ArrayList<Photo> PhotoArray = new ArrayList<Photo>(tempPhoto);
		
		if(PhotoArray.isEmpty()) {
			return "";
		}
		
		DateComparator dc = new DateComparator();
		Collections.sort(PhotoArray, dc);
		
		return PhotoArray.get(0).getDate();
	}
	
	/**
	 * To string method
	 */
	public String toString() {
		return this.name;
	}
}
