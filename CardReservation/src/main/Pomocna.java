package main;

import java.io.*;

public class Pomocna {

    public static void napraviBinarnuDat(){

        try (FileOutputStream fOut = new FileOutputStream("clients.bin");
             BufferedOutputStream bOut = new BufferedOutputStream(fOut);
             ObjectOutputStream out = new ObjectOutputStream(bOut)) {

            System.out.println("Napravio si datoteku clanovi.bin! ");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }





}
