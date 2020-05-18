public class CommandHandler {
	ChatWindow cw;
	Contacts contacts;
	String output, finaloutput;

	public String command(String input) {
		if (input.equals("/contacts")) {
			finaloutput = contacts();
		}
		return finaloutput;
	}

	public String contacts() {
		contacts = new Contacts();
		StringBuilder sb = new StringBuilder();
		System.out.println(contacts.entries);
		for (int i = 0; i < contacts.entries; i++) {
			String name = (contacts.loadcontact(i)[0]).toString();
			String ip = (contacts.loadcontact(i)[1]).toString();

			sb.append("name: " + name + "; ipadress: " + ip + "\n");
		}
		output = sb.toString();
		// System.out.println(output);
		// cw.outputToJTextArea(cw.jta, "BRUH!!!!");
		return output;
	}

}
