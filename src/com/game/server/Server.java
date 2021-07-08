/**
 * 
 */
package com.game.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
/**
 * @author ramamohan.gogula
 *
 */
public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(2000);
        Socket socket = null;
        while (true) {
            //listening for client 1
            System.out.println("waiting for client 1...........");
            socket = serverSocket.accept();
            Client c1 = new Client(socket);
            System.out.println("client 1- "+c1.name+" joined..........");

            //listening for client2
            System.out.println("waiting for client 2...........");
            socket = serverSocket.accept();
            Client c2 = new Client(socket);
            System.out.println("client 2- "+c2.name+" joined..........");

            //starting game with the clients
            GameEngine ge = new GameEngine(c1, c2);
            ge.run();
        }
    }
}
