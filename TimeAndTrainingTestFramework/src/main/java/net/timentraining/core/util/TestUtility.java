package net.timentraining.core.util;

import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class TestUtility {

	public static void sleep(long ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static boolean isNumeric(String s) {
		try {
		} catch (NumberFormatException e) {
			return false;
		}

		return true;
	}

	public static int get_string_occurrences(String source, String target) {
		String str = source;
		String findStr = target;
		int lastIndex = 0;
		int count = 0;

		while (lastIndex != -1) {

			lastIndex = str.indexOf(findStr, lastIndex);

			if (lastIndex != -1) {
				count++;
				lastIndex += findStr.length();
			}
		}
		return count;
	}

	public static String generate_random_password(int len) {
		final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz +_)(*&^%#@!~?/\\|/-><`";
		SecureRandom rnd = new SecureRandom();

		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++)
			sb.append(AB.charAt(rnd.nextInt(AB.length())));
		return sb.toString();

	}

	public static String generate_random_alphanumeric_string(int len) {
		final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		SecureRandom rnd = new SecureRandom();

		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++)
			sb.append(AB.charAt(rnd.nextInt(AB.length())));
		return sb.toString().toUpperCase();

	}

	public static String generate_random_string(int len) {
		final String AB = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		SecureRandom rnd = new SecureRandom();

		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++)
			sb.append(AB.charAt(rnd.nextInt(AB.length())));
		return sb.toString().toUpperCase();

	}

	public static String generate_random_numeric_string(int len) {
		final String AB = "0123456789";
		SecureRandom rnd = new SecureRandom();

		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++)
			sb.append(AB.charAt(rnd.nextInt(AB.length())));
		return sb.toString().toUpperCase();

	}

	public static String getTheNumbersOutOfString(final CharSequence input /* inspired by seh's comment */) {
		final StringBuilder sb = new StringBuilder(input.length() /* also inspired by seh's comment */);
		for (int i = 0; i < input.length(); i++) {
			final char c = input.charAt(i);
			if (c > 47 && c < 58) {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	/***
	 * This method can return a string of todays date in GMT. as MM/DD/YYYY format
	 * 
	 * @return: todays date
	 */
	public static String getTodaysDate() {
		TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
		Calendar cal = Calendar.getInstance(TimeZone.getDefault());
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		return df.format(cal.getTime());
	}
	public static String getTodaysDate(String timeZone) {
		TimeZone.setDefault(TimeZone.getTimeZone(timeZone));
		Calendar cal = Calendar.getInstance(TimeZone.getDefault());
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		return df.format(cal.getTime());
	}


	/**
	 * This method can return a string of past date in GMT. as MM/DD/YYYY format
	 * 
	 * @param numOfPastDay: number of extra days you want to remove in todays date
	 * @return
	 */
	public static String getPastDate(int numOfPastDay) {
		TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
		Calendar cal = Calendar.getInstance(TimeZone.getDefault());
		cal.setTime(new Date());
		cal.add(Calendar.DAY_OF_MONTH, -numOfPastDay);
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		return df.format(cal.getTime());

	}

	/***
	 * This method can get todays date in given pattern. Use {@link ACConstants} to
	 * get different patterns
	 * 
	 * @param ACConstantsDatePattern
	 * @return
	 */
	public static String get_date_time(String ACConstantsDatePattern) {

		Calendar cal = Calendar.getInstance();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(ACConstantsDatePattern);
		return simpleDateFormat.format(cal.getTime());
	}

	/**
	 * This method can get change (convert) the pattern for given date
	 * 
	 * @param date        - date as String that you want to convert
	 * @param datePattern - current pattern for date parameter
	 * @param newPattern  - new pattern for date parameter
	 * @return
	 * @throws ParseException
	 */
	public static String convertDateFormat(String date, String datePattern, String newPattern) {
		SimpleDateFormat pat1 = new SimpleDateFormat(datePattern);
		SimpleDateFormat pat2 = new SimpleDateFormat(newPattern);

		Date dateObj = null;
		try {
			dateObj = pat1.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return pat2.format(dateObj);

	}

	/***
	 * This method can parse String to date
	 * 
	 * @param date       - String to parse as date
	 * @param newPattern - new pattern. Use {@link ACConstants} to get different
	 *                   patterns
	 * @return
	 */
	public static Date parseStringToDate(String date, String newPattern) {

		Calendar cal = Calendar.getInstance();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(newPattern);
		try {
			cal.setTime(simpleDateFormat.parse(date));
			return cal.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * This method can return a string of future date in GMT. as MM/DD/YYYY format
	 * 
	 * @param numOfFutureDay: number of extra days you want to add in todays date
	 * @return
	 */
	public static String getFutureDate(int numOfFutureDay) {
		TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
		Calendar cal = Calendar.getInstance(TimeZone.getDefault());
		cal.setTime(new Date());
		cal.add(Calendar.DAY_OF_MONTH, numOfFutureDay);
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		return df.format(cal.getTime());

	}

	public static String removeparenthesesWhiteSpaceAndDash(String s) {
		return s.replace(" ", "").replace("-", "").replace("(", "").replace(")", "").trim();
	}

}
