package com.slack.tictactoe.i18n;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum Messages {
	TTT4000I,
	TTT4001E,
	TTT4002E,
	TTT4003E,
	TTT4004E,
	TTT5000I
	;
	
	private static final String BUNDLE_NAME = "com.slack.tictactoe.i18n.messages"; 

	private static Map<Locale,ResourceBundle> bundleMap = new HashMap<Locale,ResourceBundle>();

	private static final Logger logger = LoggerFactory.getLogger(Messages.class);

	private Messages()
	{
	}
	
	public String toString(Locale locale){
		ResourceBundle bundle = null;
		
		synchronized(BUNDLE_NAME){
			if (bundleMap.get(locale) == null){
				try{
					bundle = ResourceBundle.getBundle(BUNDLE_NAME, locale);
					
					bundleMap.put(locale, bundle);
				}
				catch (MissingResourceException ex){
					logger.error("Unable to find resource bundle '" + BUNDLE_NAME + "' for locale '" + locale + "'.");
				}
			}else{
				bundle = bundleMap.get(locale);
			}
		}
		
		try{
			String name = this.name();
			// This internally uses stringbuilder so don't need to do it ourselves
			String completeMsg = bundle.getString(name);
			return completeMsg;
		}catch (NullPointerException e){
			return '!' + this.name() + '!';
		}catch (MissingResourceException e){
			return '!' + this.name() + '!';
		}
	}

	public String toString(){
		return toString(Locale.getDefault());
	}
	
	public static String getString(Messages msg, Locale locale){
		return msg.toString(locale);
	}
	
	public static String getString(Messages msg){
		return msg.toString();
	}
}


