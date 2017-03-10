package com.slack.tictactoe.utils;

import java.io.UnsupportedEncodingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.slack.tictactoe.common.Constants;
import com.slack.tictactoe.i18n.Messages;
import com.slack.tictactoe.logging.LogMessage;

/**
 * Utilities class that maintains common functions 
 */
public class TTTUtils {

	private static final Logger logger = LoggerFactory.getLogger(TTTUtils.class);

	/**
	 * formats the user_id that can be decoded and turned into a URL on Slack
	 * @param userId
	 * @return
	 */
	public static String formatUserId(String userId) {
		String lessThan = "<";
		String greaterThan = ">";

		return lessThan + Constants.AT + userId + greaterThan;
	}

	/**
	 * Decode the encoded response_url provided by Slack for making delayed responses
	 * @param str
	 * @return
	 */
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
