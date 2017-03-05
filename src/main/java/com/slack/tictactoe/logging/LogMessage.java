package com.slack.tictactoe.logging;

import java.text.MessageFormat;
import com.slack.tictactoe.i18n.Messages;

public class LogMessage {
	
	/**
	 * 
	 * Method to generate log message 
	 * 
	 * @param msg
	 * @param msgArgs
	 * @return
	 */
	public static String getLogMsg(Messages msg, Object... msgArgs) {

		String logmsg;

		if (msgArgs != null && msgArgs.length > 0) {
			String logTemplate = Messages.getString(msg);
			logmsg = MessageFormat.format(logTemplate, msgArgs);
		} else {
			logmsg = Messages.getString(msg);
		}

		return logmsg;
	}
}
