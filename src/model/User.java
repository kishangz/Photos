package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.io.Serializable;

/**
 * This class defines a User
 * @author Kishan Zalora Eyob Tesfaye
 */
public class User implements Serializable, Comparable<User> {
	
	/**
	 * Serial Version ID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * String name
	 */
	private String name;
	
	/**
	 * String userkey
	 */
	private String userKey;
	
	/**
	 * Hashmap of Album
	 */
	private HashMap<String, Album> albumHashmap; // id,album

	/**
	 * Conctructor for user
	 * @param key
	 * @param name
	 */
	public User(String key, String name) {
		this.name = name;
		this.userKey = key;
		albumHashmap = new HashMap<String, Album>();
		
	}
	
	/**
	 * Gets Name
	 * @return
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * gets User key
	 * @return
	 */
	public String getUserKey() {
		return this.userKey;
	}
	
	/**
	 * sets name
	 * @param name
	 */
	public void SetName(String name) {
		this.name = name;
	}
	
	/**
	 * sets user key
	 * @param key
	 */
	public void setUserKey(String key) {
		this.userKey = key;
	}
	
	/**
	 * compare to method
	 */
	@Override
	public int compareTo(User other) {
		return this.userKey.compareTo(other.userKey);
		
	}
	
	/**
	 * hash map of album
	 * @return
	 */
	public HashMap<String, Album> getAlbumList() {
		return albumHashmap;
	}
	
	/**
	 * adds album
	 * @param newAlbum
	 */
	public void addAlbum(String newAlbum) {
		Album temp = new Album(newAlbum);
		
	}
	
	/**
	 * delets album
	 * @param deleteAlbum
	 */
	public void deleteAlbum(String deleteAlbum) {
		this.albumHashmap.remove(deleteAlbum);
	}
	
	/**
	 * array list of String
	 * @return
	 */
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
	
	/**
	 * renames album
	 * @param oldAlbum
	 * @param newAlbum
	 * @return
	 */
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
	
	/**
	 * to string method
	 */
	public String toString() {
		return this.userKey + " : " + this.name;
		
	}
}
