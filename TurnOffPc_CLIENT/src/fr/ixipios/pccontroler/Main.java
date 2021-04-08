package fr.ixipios.pccontroler;

import java.util.Scanner;

public class Main {

	// CLIENT
	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		System.out.println("Pseudo : ");
		String pseudo = "fqsfd";
		pseudo = sc.nextLine();
		while(pseudo.contains(" ")) {
			if (pseudo.contains(" ")) {
				System.out.println("ERREUR : Votre pseudo ne doit pas contenir d'espace");
			}
			pseudo = sc.nextLine();
		}
		System.out.println("ip serveur : ");
		String host = sc.nextLine();
		if (host.equalsIgnoreCase("default")) {
			host = "91.160.42.186";
		} else if (host.equalsIgnoreCase("petit")) {
			host = "192.168.1.27";
		}
		int port = 14793;

		Thread t = new Thread(new ServerConnexion(host, port, pseudo));
		t.start();
	}
}