package com.bigdataminds.facebookdataset.util;

public class FacebookDatasetUtil {

	public static final int LIKES_COLUMN_INDEX = 10;

	public static final long START_YEAR = 1990;

	public static final long END_YEAR = 2000;

	public static final int YEAR_COLUMN_INDEX = 3;

	public static Long getLong(String value) {
		try {
			return Long.parseLong(value);
		} catch (NumberFormatException e) {
			return null;
		}
	}

}
