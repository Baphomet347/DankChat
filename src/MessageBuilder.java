
public class MessageBuilder {

	public String[] buildMessage(String username, String sendtext, String fontColor, int messageFlag) {
		//		String fontColorString = Integer.toString(fontColor);
		String messageFlagString = Integer.toString(messageFlag);
		String[] message = {username, sendtext, fontColor, messageFlagString};

		return message;
	}

	public String getUser(String[] message) {
		String username = message[0];
		return username;
	}

	public String getSendtext(String[] message) {
		String sendtext = message[1];
		return sendtext;

	}
	public String getFontColor(String[] message) {
		String fontColor = message[2];
		return fontColor;

	}
	public int getMessageFlag(String[] message) {
		int messageFlag = Integer.parseInt(message[3]);
		return messageFlag;
	}

}
