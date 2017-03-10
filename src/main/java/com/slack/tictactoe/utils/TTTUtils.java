package com.slack.tictactoe.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.slack.tictactoe.Constants;
import com.slack.tictactoe.i18n.Messages;
import com.slack.tictactoe.logging.LogMessage;

public class TTTUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(TTTUtils.class);
	
	public static String encodeUserId(String userId) {
		String lessThan = "<";
		String greaterThan = ">";
		String encodedString = null;
		try {
			encodedString = URLEncoder.encode(lessThan + userId + greaterThan, Constants.UTF8);
		} catch (UnsupportedEncodingException ex) {
			logger.error("Unable to encode userId \n" + ex.getMessage());	
		}		
		return encodedString;		
	}
	
	public static String decodeString(String str) {
		String decodedStr = null;
		try {
			decodedStr = java.net.URLDecoder.decode(str, Constants.UTF8);			
		} catch (UnsupportedEncodingException ex) {
			logger.error(LogMessage.getLogMsg(Messages.TTT4003E));			
		}	
		
		return decodedStr;
	}

}
