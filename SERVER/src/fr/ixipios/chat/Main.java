package fr.ixipios.chat;

import java.util.Scanner;

public class Main {
	public static SocketServer ts = null;
	//SERVER
	public static void main(String[] args) {
		System.out.println("SERVER : IP : ");
		Scanner sc = new Scanner(System.in);
		String host = "";
		if(sc.nextLine().equalsIgnoreCase("petit")) {
			host = "";
		}
		
		System.out.println("SERVER : pseudo : ");
		String name = sc.nextLine();
		
		int port = 14793;

		ts = new SocketServer(host, port);
		ts.open();
		
		Thread t = new Thread(new LocalCommands(name));
		t.start();
		
		System.out.println("Serveur initialisée");
	}
}