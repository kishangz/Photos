package model;

import java.util.Comparator;

public class DateComparator implements Comparator<Photo> {

	/**
	 * This compares the dates of two photos.
	 * @param Photo
	 * @param Photo
	 */
	public int compare(Photo p1, Photo p2)
	{
		return p1.getDt().compareTo(p2.getDt());
	}
}
