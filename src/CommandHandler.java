public class CommandHandler {
	ChatWindow cw;
	Contacts contacts;
	String output, finaloutput;
	String[] ip, name;
	String[] commandsplit;
	int contactentries = 3;
	boolean modifierfound, commandfound;

	public String executeCommand(String input) {
		String[] commandsplit;
		modifierfound = false;
		commandfound = true;
		commandsplit = input.split(" ", 3);
		String command = null, modifier = null;
		command = commandsplit[0];
		if (commandsplit.length == 1) {
			modifierfound = false;
		} else if (commandsplit[1] != null) {
			modifier = commandsplit[1];
		} else {
			modifier = "invalid";
		}
		System.out.println(command);
		System.out.println(modifier);
		try {
			switch (command) {
			case "/contacts":
				finaloutput = contacts(modifier);
				break;
			case "/chat":
				finaloutput = establishConnection(modifier);
				break;
			case "/username":
				finaloutput = setUsername(modifier);
				break;
			default:
				commandfound = false;
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
			finaloutput = "command not found. for help type /help or /?\n";
		}
		if (modifierfound == false) {
			finaloutput = "modifier not found. try " + command + " -h, -help or -?\n";
		}
		if (commandfound == false) {
			finaloutput = "command not found. for help type /help or /?\n";
		}
		System.out.println();
		return finaloutput;

	}

	public String establishConnection(String ip) {
		String output;
		modifierfound = true;
		if (new ConnectionHandler().establishConnection(ip) == true) {
			output = "succesfully connected to [" + ip + "].\n";
			Thread chat = new Thread() {
				@Override
				public void run() {
					new ChatServer().listen();
				}
			};
			chat.start();
			new ChatClient().addHost(ip);
		} else {
			output = "establishing connection to [" + ip + "] failed.\n";
		}
		return output;
	}

	public String setUsername(String username) {
		ChatClient.username = username;
		modifierfound = true;
		return "succesfully changed username to '" + ChatClient.username + "'.\n";
	}

	public String contacts(String modifier) {
		if (modifier.equals("-l") || modifier.equals("-list")) {
			contacts = new Contacts();
			contactentries = contacts.loadentries();
			name = new String[contactentries];
			ip = new String[contactentries];
			StringBuilder sb = new StringBuilder();
			System.out.println(contactentries);
			for (int i = 0; i < contactentries; i++) {
				name[i] = (contacts.loadcontact(i)[0]).toString();
				ip[i] = (contacts.loadcontact(i)[1]).toString();
				String status = new ConnectionHandler().clientStatus(ip[i], 500);
				sb.append("name: " + name[i] + " | ipadress: " + ip[i] + " | status: " + status + "\n");
			}
			modifierfound = true;
			output = sb.toString();
		} else if (modifier.equals("-a") || modifier.equals("-add")) {
			String name = commandsplit[2];
			String ipadress = commandsplit[3];
			contacts.contactlist.add(new Contacts(contactentries + 1, name, ipadress));
			contacts.safecontacts();
			contactentries++;
			modifierfound = true;
			output = "new contact with name: '" + name + "' and ipadress: " + ipadress + "' saved.";
		}
		return output;
	}

}
