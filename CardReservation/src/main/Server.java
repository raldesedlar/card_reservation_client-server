package main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

public class Server {
    public static LinkedList<ClientHandler> onlineUsers = new LinkedList<>();

    public static void main(String[] args) {

        int port=9009;


        try (ServerSocket serverSocket = new ServerSocket(port)){

            while(true) {
                System.out.println("Waiting for connection..");
                Socket soketZaKomunikaciju=serverSocket.accept();
                System.out.println("Connected");

                ClientHandler klijent = new ClientHandler(soketZaKomunikaciju);
                onlineUsers.add(klijent);
                klijent.start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }



        }

    }


