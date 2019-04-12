package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.io.Serializable;

public class User implements Serializable, Comparable<User> {
	
	private static final long serialVersionUID = 1L;
	
	private String name;
	
	private String userKey;
	
	private HashMap<String, Album> albumHashmap; // id,album
	
	public HashMap<String,ArrayList<Tag>> tagHashMap;
	
	public HashMap<String,Photo> userPhotoHashMap;
	
	public User(String key, String name) {
		this.name = name;
		this.userKey = key;
		albumHashmap = new HashMap<String, Album>();
		tagHashMap = new HashMap<String, ArrayList<Tag>>();
		userPhotoHashMap = new HashMap<String,Photo>();
	}
	
	/*
	public User(String name) {
		
		this.name = name; 
		albums = new HashMap<>();
		
	}
	*/
	
	public String getName() {
		return this.name;
	}
	
	public String getUserKey() {
		return this.userKey;
	}
	
	public void SetName(String name) {
		this.name = name;
	}
	
	public void setUserKey(String key) {
		this.userKey = key;
	}
	
	@Override
	public int compareTo(User other) {
		return this.userKey.compareTo(other.userKey);
		
	}
	
	public HashMap<String, Album> getAlbumList() {
		return albumHashmap;
	}
	
	public void addAlbum(String newAlbum) {
		Album temp = new Album(newAlbum);
		
	}
	
	public void deleteAlbum(String deleteAlbum) {
		this.albumHashmap.remove(deleteAlbum);
	}
	
	public ArrayList<String> getListOfAlbums(){
		
		Iterator<String> it = albumHashmap.keySet().iterator();
		String key;
		ArrayList<String> a = new ArrayList<String>();
		while(it.hasNext()) {
			key = it.next();
			String album = albumHashmap.get(key).toString();
			a.add(album);
		}
		return a;
	}
	
	public boolean renameAlbum(String oldAlbum, String newAlbum) {
		
		if(!(this.albumHashmap.containsKey(newAlbum))) {
			if(this.albumHashmap.containsKey(oldAlbum)) {
				Album tempAlbum = this.albumHashmap.get(oldAlbum);
				tempAlbum.setAlbumName(newAlbum);
				this.albumHashmap.put(newAlbum, tempAlbum);
				this.albumHashmap.remove(oldAlbum);
				return true;
			}
		}
		return false;
	}
	
	public String toString() {
		return this.userKey + " : " + this.name;
		
	}
	
	
}
