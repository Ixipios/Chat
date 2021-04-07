package fr.ixipios.pccontroler;

import java.util.Scanner;

public class Main {
	public static SocketServer ts = null;
	//SERVER
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String host = "192.168.1.9";
		if(sc.nextLine().equalsIgnoreCase("petit")) {
			host = "192.168.1.27";
		}
		
		String name = sc.nextLine();
		
		int port = 5546;

		ts = new SocketServer(host, port);
		ts.open();
		
		Thread t = new Thread(new LocalCommands(name));
		t.start();
		
		System.out.println("Serveur initialisée");
	}
}