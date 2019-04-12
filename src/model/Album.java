package model;

import java.io.Serializable;
import java.util.ArrayList;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

public class Album implements Serializable, Comparable<Album> {
	
	private static final long serialVersionUID = 1L;
	
	private String name;
	
	private HashMap<String, Photo> photos;
	
	//ArrayList<Photo> photos;
	
	public Album(String name) {
		this.name = name; 
		this.photos = new HashMap<String, Photo>();
		
	}
	
	/*
	public List<Photo> getPhotos() {
		return photos;
		
	}
	*/
	
	public String getName() {
		
		return this.name;
		
	}
	
	public void setAlbumName(String name) {
		this.name = name;
		
	}
	
	public void setPhotoHashMap(HashMap<String, Photo> photos) {
		this.photos = photos; 
	}
	
	public HashMap<String, Photo> getListOfPhotos(){
		return this.photos;
	}
	
	public void addPhoto(Photo photo) {
		this.photos.put(photo.getPhotoName(), photo);
	}
	
	public void removePhoto(String photoName) {
		this.photos.remove(photoName);
	}
	
	public String getAlbumSize() {
		String s = "" + photos.size();
		return s;
	}

	@Override
	public int compareTo(Album otherAlbum) {
		// TODO Auto-generated method stub
		return this.name.compareTo(otherAlbum.getName());
	}
	
	public String getLastestDate() {
		
		Collection<Photo> tempPhoto = new ArrayList<Photo>(this.photos.values());
		ArrayList<Photo> PhotoArray = new ArrayList<Photo>(tempPhoto);
		
		if(PhotoArray.isEmpty()) {
			return "";
		}
		
		DateComparator dc = new DateComparator();
		Collections.sort(PhotoArray, dc);
		
		return PhotoArray.get(0).getDate();
	}
	
	public String toString() {
		return this.name;
	}
}
