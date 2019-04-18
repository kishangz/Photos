package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * This class is to assist with the userList
 * @author Kishan Zalora Eyob Tesfaye
 *
 */
public class UsersList implements Serializable{
	
	/**
	 * Serial Version ID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * HashMap of User
	 */
	
	private HashMap<String, User> userList;
	
	public static final String storeDir = "data";
	
	public static final String storeFile = "users";
	
	/**
	 * Constructor for the user list
	 */
	public UsersList() {
		userList = new HashMap<String, User>();
	}
	
	/**
	 * Adds User
	 * @param key
	 * @param userName
	 */
	public void addUser(String key, String userName) {
		
		User temp = new User(key, userName);
		userList.put(key, temp);
	}
	
	/**
	 * Deletes User
	 * @param key
	 */
	public void deleteUser(String key) {
		userList.remove(key);
	}
	
	/**
	 * Gets User
	 * @param key
	 * @return
	 */
	public User getUser(String key) {
		return userList.get(key);
	}
	
	/**
	 * HashMap of User
	 * @return
	 */
	public HashMap<String, User> getUserList(){
		return userList;
	}
	
	/**
	 * SetUser list
	 * @param usersList
	 */
	public void setUserList(HashMap<String, User> usersList) {
		
		this.userList = usersList;
	}
	
	/**
	 * ArrayList of String
	 * @return
	 */
	public ArrayList<String> getListOfUsers(){
		
		Iterator<String> it = userList.keySet().iterator();
		String key;
		ArrayList<String> u = new ArrayList<String>();
		while(it.hasNext()) {
			key = it.next();
			String user = userList.get(key).toString();
			u.add(user.substring(0, user.indexOf(' ')));
		}
		return u;
	}
	
	/**
	 * read function
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public void read() throws FileNotFoundException, IOException, ClassNotFoundException{
		
		File file = new File(storeDir + File.separator + storeFile);
		
		try {
			if(file.length() == 0) {
				file.createNewFile();
			}
			else {
				ObjectInputStream tempIn = new ObjectInputStream(new FileInputStream(file));
				userList = (HashMap<String, User>) tempIn.readObject();
				
			}
		}catch(IOException e){
			
		}
		
	}
	
	/**
	 * saves user list
	 * @param userList
	 * @throws IOException
	 */
	public static void save(HashMap<String, User> userList) throws IOException
	{
		ObjectOutputStream tempOut = new ObjectOutputStream(new FileOutputStream(storeDir + File.separator + storeFile));
		tempOut.writeObject(userList);
	}
	
	
}
