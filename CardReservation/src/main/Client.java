package main;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Client implements Runnable {
        static BufferedReader unosSaTastature;
        static BufferedReader inputSaServera;
        static PrintStream outputKaServeru;

        static boolean kraj = false;

        public static void main(String[] args) {

            String input;
            try {
                Socket soketZaKomunikaciju = null;

                //MOZDA DEKLARACIJA TREBA DA BUDE VAN MAIN-A, A U MAINU SAMO INICIJALIZACIJA!
                int port = 9009;
                soketZaKomunikaciju = new Socket("localhost", port);
                unosSaTastature = new BufferedReader(new InputStreamReader(System.in));
                inputSaServera = new BufferedReader(new InputStreamReader(soketZaKomunikaciju.getInputStream()));
                outputKaServeru = new PrintStream(soketZaKomunikaciju.getOutputStream());
                System.out.println("Povezan sa serverom!\n");

                new Thread(new Client()).start();

                while(true){

                    //vrti beskonacnu dokle god ne bude izlaza
                    input=inputSaServera.readLine();
                    if(input.equals("dovidjenja"))
                        break;

                    if(input.equals("slanje_fajla")){
                        DateTimeFormatter dateTime=DateTimeFormatter.ofPattern("dd-MM-yyy HH-mm-ss");
                        LocalDateTime sad=LocalDateTime.now();
                        String vreme=dateTime.format(sad);
                        String imeFajla="Karta" + vreme + ".txt";

                        byte[] bytes=new byte[35000];
                        InputStream is =soketZaKomunikaciju.getInputStream();
                        FileOutputStream fr=new FileOutputStream("Karte/" +imeFajla);
                        is.read(bytes);
                        fr.write(bytes);
                        System.out.println("Karta je primljena");
                        fr.close();
                        continue;

                    }
                    System.out.println(input);

                }
                soketZaKomunikaciju.close();


            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void run() {

            String poruka;
            int broj;
            while(true){
                try {

                    poruka=unosSaTastature.readLine();
                    if(poruka.equals("0")){

                        System.out.println("Gasenje klijenta!");
                        outputKaServeru.println(poruka);
                        break;
                    }else
                    outputKaServeru.println(poruka);



                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }


        }
    }