package com.slack.tictactoe.i18n;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Enum to keep track of all the message codes added in messages.properties
 *
 */
public enum Messages {
	// Service messages
	TTT4000I, TTT4001E, TTT4002E, TTT4003E, TTT4004E, 
	TTT4005I,
	// Command messages 
	TTT5000I, TTT5001E, TTT5002E, TTT5003E, TTT5004E,
	TTT5005E, TTT5006E, TTT5007E, TTT5008E, TTT5009I, 
	TTT5010I, TTT5011I, TTT5012I, TTT5013I, TTT5014D,
	TTT5015I, TTT5016I, TTT5017I, TTT5018I, TTT5019I,
	TTT5020E,
	// Game messages
	TTT6000E, TTT6001E;

	private static final String BUNDLE_NAME = "com.slack.tictactoe.i18n.messages";

	private static Map<Locale, ResourceBundle> bundleMap = new HashMap<Locale, ResourceBundle>();

	private static final Logger logger = LoggerFactory.getLogger(Messages.class);

	private Messages() {
	}
	
	/**
	 * Retrieves the bundle returns the appropriate string according to the locale 
	 * @param locale
	 * @return
	 */
	public String toString(Locale locale) {
		ResourceBundle bundle = null;

		synchronized (BUNDLE_NAME) {
			if (bundleMap.get(locale) == null) {
				try {
					bundle = ResourceBundle.getBundle(BUNDLE_NAME, locale);

					bundleMap.put(locale, bundle);
				} catch (MissingResourceException ex) {
					logger.error("Unable to find resource bundle '" + BUNDLE_NAME + "' for locale '" + locale + "'.");
				}
			} else {
				bundle = bundleMap.get(locale);
			}
		}

		try {
			String name = this.name();
			// This internally uses stringbuilder so don't need to do it
			// ourselves
			String completeMsg = bundle.getString(name);
			return completeMsg;
		} catch (NullPointerException e) {
			return '!' + this.name() + '!';
		} catch (MissingResourceException e) {
			return '!' + this.name() + '!';
		}
	}
	
	/**
	 * get the default locale
	 * @return String
	 */
	public String toString() {
		return toString(Locale.getDefault());
	}

	/**
	 * Get the message string based on the provided code
	 * @param msg
	 * @param locale
	 * @return String
	 */
	public static String getString(Messages msg, Locale locale) {
		return msg.toString(locale);
	}
	
	/**
	 * return the message.
	 * @param msg
	 * @return String
	 */
	public static String getString(Messages msg) {
		return msg.toString();
	}
}
