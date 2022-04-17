package fr.ixipios.chat;

import java.util.Scanner;

public class LocalCommands implements Runnable {

	private String pseudo = "SERVER";
	private boolean chat = false;
	
	public LocalCommands(String pseudo) {
		this.pseudo = pseudo;
	}
	
	public void run() {
		while (true) {
			Scanner sc = new Scanner(System.in);
			String command = sc.nextLine();
			
			if (command.startsWith("/")) {
				String[] params = command.substring(1).split(" ");
				switch (params[0]) {
				case "list":
					String clients = String.join(", ", Main.ts.pseudos.values());
					print("Liste des " + Main.ts.pseudos.size() + " personnes en ligne :");
					print(clients);
					break;
				case "name":
					print("Votre pseudo est : " + pseudo);
					print("Voulez-vous le changez ? O/N");
					if(sc.nextLine().equalsIgnoreCase("O")) {
						print("Entre votre nouveau pseudo : ");
						pseudo = sc.nextLine();
					}
					break;
				case "chat":
					chat = Boolean.parseBoolean(params[1]);
					break;
				case "debug":
					SocketServer.debug = Boolean.parseBoolean(params[1]);
					break;
				default:
					print("Erreur : Commande inconnue");
					break;
				}
			}
			else {
				Main.ts.getChatManager().send(command, null, pseudo);
			}
		}
	}
	
	private void print(String msg) {
		System.out.println("SERVER : " + msg);
	}
}
