public class CommandHandler {
	ChatWindow cw;
	Contacts contacts;
	String output, finaloutput;
	String[] ip, name;
	String[] commandsplit;

	public String executeCommand(String input) {
		try {
			String[] commandsplit;
			commandsplit = input.split(" ", 4);
			String command, modifier;
			command = commandsplit[0];
			modifier = commandsplit[1];

			System.out.println(command);
			System.out.println(modifier);

			if (command.equals("/contacts")){
				finaloutput = contacts(modifier);

			}
		} catch(NullPointerException e) {
			e.printStackTrace();
			finaloutput = "invalid command!";
		}

		return finaloutput;

	}

	public String contacts(String modifier) {
		if (modifier.equals("-l") || modifier.equals("-list")) {
			contacts = new Contacts();
			name = new String[Contacts.entries];
			ip = new String[Contacts.entries];
			StringBuilder sb = new StringBuilder();
			System.out.println(Contacts.entries);
			for (int i = 0; i < Contacts.entries; i++) {
				name[i] = (contacts.loadcontact(i)[0]).toString();
				ip[i] = (contacts.loadcontact(i)[1]).toString();
				String status = new ListenServer().clientStatus(ip[i], 4000);
				sb.append("name: " + name[i] + " | ipadress: " + ip[i] + " | status: "+status+"\n");

			}
			output = sb.toString();
		}
		if (modifier.equals("-a") || modifier.equals("-add")) {
			String name = commandsplit[2];
			String ipadress = commandsplit[3];
			contacts.contactlist.add(new Contacts(Contacts.entries + 1, name, ipadress));
			contacts.safecontacts();
			Contacts.entries++;
			output = "new contact with name: '" + name + "' and ipadress: "+ipadress+"' saved.";
		}
		return output;
	}

}
