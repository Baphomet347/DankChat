
public class MessageBuilder {
	String username, sendtext, fontColor;

	public String[] buildMessage(String username, String sendtext, String fontColor, int messageFlag) {
		//		String fontColorString = Integer.toString(fontColor);

		String messageFlagString = Integer.toString(messageFlag);
		String[] message = {username, sendtext, fontColor, messageFlagString};
		System.out.println(sendtext);

		return message;
	}

	public String getUser(String[] message) {
		String username = message[0];
		System.out.println("USER: "+username);
		return username;
	}

	public String getSendtext(String[] message) {
		String sendtext = message[1];
		System.out.println("TEXT: "+sendtext);
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
