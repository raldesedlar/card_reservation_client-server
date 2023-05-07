package main;
//append ne radi izbacuje gresku ovu java.io.StreamCorruptedException: invalid type code: AC

import java.io.*;
import java.net.Socket;
import java.nio.Buffer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Formattable;
import java.util.LinkedList;
import java.util.Optional;

public class ClientHandler extends Thread {


    BufferedReader ulazniTokOdKlijenta = null;
    PrintStream izlazniTokKaKlijentu = null;
    Socket soketZaKomunikaciju;
    public static LinkedList<ClientHandler> klijenti = new LinkedList<>();
    public static LinkedList<User> registrovani = new LinkedList<>();
    boolean ulogovan = false;
    User ulogovanUser = null;

     public void kreirajKartuIPosalji(String tip, User kupac){


         try {
             izlazniTokKaKlijentu.println("Usao da ispise kartu");
             DateTimeFormatter dateTime=DateTimeFormatter.ofPattern("dd-MM-yyyy HH-mm-ss");
             LocalDateTime sad=LocalDateTime.now();
             String vreme=dateTime.format(sad);
             String imeFajla="Karta" + vreme + ".txt";
             PrintWriter pisanje=new PrintWriter("Karte/" + imeFajla);

             pisanje.println("Oppenheimer 2023." + tip + " karta" + "\n");;
             pisanje.println("Potvrda rezervacije");
             pisanje.println("###################################");
             pisanje.println("\t\t\t" + tip + " KARTA");
             pisanje.println("###################################");
             pisanje.println("Datum i vreme " + vreme);

             pisanje.println("Podaci kupca: ");
             pisanje.println(kupac.getIme());
             pisanje.println(kupac.getPrezime());
             pisanje.println(kupac.getEmail());
             pisanje.println(kupac.getJmbg());
             pisanje.println(kupac.getUsername());

            izlazniTokKaKlijentu.println("slanje_fajla");
            File fajl= new File("Karte/" + imeFajla);
            FileInputStream fr= new FileInputStream(fajl);
            byte[] bytes = new byte[35000];
            fr.read(bytes);
            OutputStream os=soketZaKomunikaciju.getOutputStream();
            os.write(bytes);
            fr.close();


         } catch (IOException e) {
             throw new RuntimeException(e);
         }

     }


    public void serializuj(LinkedList<User> users) {
        try (FileOutputStream fOut = new FileOutputStream("clients.bin"); BufferedOutputStream bOut = new BufferedOutputStream(fOut); ObjectOutputStream out = new ObjectOutputStream(bOut);) {

            for (User user : users)
                out.writeObject(user);

            
            out.close();
        } catch (EOFException eof) {
        	

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public LinkedList<User> deserijalizuj(String datoteka) {
        LinkedList<User> lista = new LinkedList<>();
        try (FileInputStream fIn = new FileInputStream(datoteka); BufferedInputStream bIn = new BufferedInputStream(fIn); ObjectInputStream in = new ObjectInputStream(bIn)) {


            while (true) {
                User u = (User) in.readObject();
                lista.add(u);
            }

            
        } catch (EOFException eof) {
            return lista;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }


    public ClientHandler(Socket socket) {
        this.soketZaKomunikaciju = socket;
    }

    @Override
    public void run() {
        String linija = null;

        try {
            ulazniTokOdKlijenta = new BufferedReader(new InputStreamReader(soketZaKomunikaciju.getInputStream()));
            izlazniTokKaKlijentu = new PrintStream(soketZaKomunikaciju.getOutputStream());

            boolean izabranaOpcija = false;

            int broj;
            Pomocna.napraviBinarnuDat();

            while (true) {
                do {
                    izlazniTokKaKlijentu.println(Meni.pozoviMeni());
                    linija = ulazniTokOdKlijenta.readLine();
                    if (linija != null && linija.equals("0") || linija.equals("1") || linija.equals("2") || linija.equals("3") || linija.equals("4")) {
                        izabranaOpcija = true;
                    } else {
                        izlazniTokKaKlijentu.println("Izabrali ste losu opciju pokusajte ponovo!");
                    }
                } while (!izabranaOpcija);

                switch (linija) {
                    case "0": {
                        izlazniTokKaKlijentu.println("dovidjenja");
                        soketZaKomunikaciju.close();
                        break;
                    }
                    case "1": {
                        //treba uslov ako ne postoji fajl!
                        try (FileInputStream fIn = new FileInputStream("clients.bin"); BufferedInputStream bIn = new BufferedInputStream(fIn); ObjectInputStream in = new ObjectInputStream(bIn)) {
                            int obicni = 0;
                            int vip = 0;
                            while (true) {
                                try {
                                    User u = (User) (in.readObject());
                                    obicni = obicni + u.getBrojObicnih();
                                    vip = vip + u.getBrojVip();
                                } catch (EOFException ex) {
                                    break;
                                } catch (ClassNotFoundException e) {
                                    throw new RuntimeException(e);
                                }
                            }

                            izlazniTokKaKlijentu.println("Broj slobodnih obicnih karata je: " + (20 - obicni) + " a VIP karata: " + (5 - vip));
                            break;
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                    }
                    case "3": {
                        boolean flag = false;
                        while (!flag) {

                            izlazniTokKaKlijentu.println("\n\t\t######## Registracija ########");
                            String ime, prezime, jmbg, email, username, password;
                            try {
                                while (true) {
                                    izlazniTokKaKlijentu.println("Unesi ime: \n");
                                    ime = ulazniTokOdKlijenta.readLine();
                                    if (ime.contains(" "))
                                        izlazniTokKaKlijentu.println("Pogresio si kod imena!, kreni opet!");
                                    else {
                                        izlazniTokKaKlijentu.println("Unesi prezime: \n");
                                        prezime = ulazniTokOdKlijenta.readLine();
                                        if (prezime.contains(" "))
                                            izlazniTokKaKlijentu.println("Pogresio si kod prezimena!, kreni opet!");
                                        else {
                                            izlazniTokKaKlijentu.println("Unesi jmbg\n");
                                            jmbg = ulazniTokOdKlijenta.readLine();
                                            if (jmbg.length() != 13)
                                                izlazniTokKaKlijentu.println("Pogresio si kod jmbga, kreni opet!");
                                            else {
                                                izlazniTokKaKlijentu.println("Unesi email\n");
                                                email = ulazniTokOdKlijenta.readLine();
                                                if (!(email.contains("@")))
                                                    izlazniTokKaKlijentu.println("Pogresio si kod emaila, kreni opet!");
                                                else {
                                                    izlazniTokKaKlijentu.println("Unesi username: ");
                                                    username = ulazniTokOdKlijenta.readLine();
                                                    izlazniTokKaKlijentu.println("Unesi password: ");
                                                    password = ulazniTokOdKlijenta.readLine();
                                                    break;
                                                }


                                            }

                                        }

                                    }

                                }

//uzimam sve objekte iz baze i proveravam da li ima username taj

                                //uslov ako ne postoji fajl
                                try (FileInputStream fIn = new FileInputStream("clients.bin"); BufferedInputStream bIn = new BufferedInputStream(fIn); ObjectInputStream in = new ObjectInputStream(bIn)) {

//provera
                                    boolean postoji = false;
                                    while (true) {
                                        try {

                                            User u = (User) (in.readObject());
//ovde zapravo proveravam
                                            if (u.getUsername().equals(username)) {
                                                postoji = true;
                                                break;
                                            }

                                        } catch (EOFException ex) {
                                            break;
                                        } catch (ClassNotFoundException | IOException e) {
                                            throw new RuntimeException(e);
                                        }
                                    }
//ako username ne postoji, uvedi ga u bazu
                                    if (!postoji) {
                                        flag = true;
                                        //uslov ako ne postoji fajl
                                        registrovani = deserijalizuj("clients.bin");
                                        try (FileOutputStream fOut = new FileOutputStream("clients.bin"); BufferedOutputStream bOut = new BufferedOutputStream(fOut); ObjectOutputStream out = new ObjectOutputStream(bOut)) {

                                            User novi = new User(ime, prezime, jmbg, email, username, password);
                                            registrovani.add(novi);
                                            serializuj(registrovani);
                                            izlazniTokKaKlijentu.println("\n\t\t######## Uspesno ste se registrovali! ########");
                                            break;
                                        } catch (EOFException ex) {
                                            break;
                                        } catch (Exception e) {
                                            throw new RuntimeException(e);
                                        }

//ako username postoji, ispocetka unos
                                    } else {
                                        izlazniTokKaKlijentu.println("Username iskoriscen, pokusaj ponovo! \n");
                                    }


                                }

                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }

                        }
                        break;
                    }
                    case "4": {
                        izlazniTokKaKlijentu.println("\n\t\t######## Prijava ########");

                        String uname;
                        String pw;
                        try {
                            izlazniTokKaKlijentu.println("Username: ");
                            uname = ulazniTokOdKlijenta.readLine();
                            izlazniTokKaKlijentu.println("Password: ");
                            pw = ulazniTokOdKlijenta.readLine();
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                        boolean postoji = false;
                        User u = null;
                        try (FileInputStream fIn = new FileInputStream("clients.bin"); BufferedInputStream bIn = new BufferedInputStream(fIn); ObjectInputStream in = new ObjectInputStream(bIn)) {

                            while (true) {
                                u = (User) in.readObject();
                                if (u.getUsername().equals(uname) && u.getPassword().equals(pw)) {
                                    postoji = true;
                                    break;
                                }
                            }

                        } catch (EOFException eof) {
                        } catch (ClassNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                        if (!postoji) {
                            izlazniTokKaKlijentu.println("Losi login kredencijali, pokusajte ponovo!");

                        } else {


                            izlazniTokKaKlijentu.println("Ulogovao si se, dobrodosao, " + uname + "!\n");
                            boolean logOut = false;
                            while (!logOut) {
                                izlazniTokKaKlijentu.println(Meni.pozoviMeniUlogovanog());

                                linija = ulazniTokOdKlijenta.readLine();
//petlja za ulogovanog

                                switch (linija) {
                                    case "1": {
                                    	
                                    	
                                    	
                                        registrovani = deserijalizuj("clients.bin");
                                        
                                        int sumUk=0;
                                        for (User us : registrovani) {
                                            sumUk = sumUk + us.getBrojObicnih();
                                        }
                                        
                                        if(sumUk>=20) {
                                            izlazniTokKaKlijentu.println("Sve karte zauzete!");
                                            break;
                                        }else {
                                        
                                        int sum = 0;
                                        for (User us : registrovani) {
                                            sum = sum + us.getBrojObicnih() + us.getBrojVip();
                                        }

                                        for (User user : registrovani) {
//proveri ujutru dal radi ovo
                                            if (u.getUsername().equals(user.getUsername()) && u.getPassword().equals(user.getPassword()) && sum < 4 || sum < 4 && u.getJmbg().equals(user.getJmbg())) {
                                                u.setBrojObicnih(u.getBrojObicnih() + 1);
                                                registrovani.remove(user);
                                                registrovani.add(u);
                                                serializuj(registrovani);
                                                izlazniTokKaKlijentu.println("Zavrsena kupovina obicne karte!");
                                                kreirajKartuIPosalji("Obicna", u);
                                                //ispisi tekst datoteku o podacima karte
                                            }

                                        }
                                        }
                                        break;
                                    }
                                    case "2": {
                                    	
                                    	

                                        registrovani = deserijalizuj("clients.bin");
                                        
                                        int sumUk=0;
                                        for (User us : registrovani) {
                                            sumUk = sumUk + us.getBrojVip();
                                        }
                                        
                                        if(sumUk>=5) {
                                        	izlazniTokKaKlijentu.println("Sve karte zauzete!");
                                            break;
                                        }else {                                
                                        int sum = 0;
                                        for (User us : registrovani) {
                                            sum = sum + us.getBrojVip() + us.getBrojObicnih();
                                        }
                                        registrovani=deserijalizuj("clients.bin");
                                        for (User user : registrovani) {
//proveri ujutru da l radi ovo
                                            if (u.getUsername().equals(user.getUsername()) && u.getPassword().equals(user.getPassword()) && sum < 4 || sum < 4 && u.getJmbg().equals(user.getJmbg())) {
                                                u.setBrojVip(u.getBrojVip() + 1);
                                                registrovani.remove(user);
                                                registrovani.add(u);
                                                serializuj(registrovani);
                                                izlazniTokKaKlijentu.println("Zavrsena kupovina VIP karte!");
                                                kreirajKartuIPosalji("VIP", u);
                                                //ispisi tekst datoteku o podacima karte

                                            }
                                        }
                                        }
                                        break;
                                    }
                                    case "3": {
                                        if (u.getBrojObicnih() <= 0) {
                                            izlazniTokKaKlijentu.println("Vec imas 0 obicnih karata!");
                                        } else {
                                            registrovani = deserijalizuj("clients.bin");

                                            for (User user : registrovani) {

                                                if (u.getUsername().equals(user.getUsername()) && u.getPassword().equals(user.getPassword())) {
                                                    u.setBrojObicnih(u.getBrojObicnih() - 1);
                                                    registrovani.remove(user);
                                                    registrovani.add(u);
                                                    serializuj(registrovani);
                                                    izlazniTokKaKlijentu.println("Izbrisana obicna karta!");
                                                }
                                            }

                                        }

                                        break;
                                    }
                                    case "4": {
                                        if (u.getBrojVip() <= 0) {
                                            izlazniTokKaKlijentu.println("Vec imas 0 VIP karata!");
                                        } else {
                                            registrovani = deserijalizuj("clients.bin");

                                            for (User user : registrovani) {

                                                if (u.getUsername().equals(user.getUsername()) && u.getPassword().equals(user.getPassword())) {
                                                    u.setBrojVip(u.getBrojVip() - 1);
                                                    registrovani.remove(user);
                                                    registrovani.add(u);
                                                    serializuj(registrovani);
                                                    izlazniTokKaKlijentu.println("Izbrisana VIP karta!");
                                                }
                                            }

                                        }

                                        break;
                                    }
                                    case "5": {
                                        izlazniTokKaKlijentu.println("Izlogovao si se! Pozdrav, " + u.getUsername());
                                        u = null;
                                        logOut = true;
                                        break;
                                    }
                                    default: {
                                        izlazniTokKaKlijentu.println("Niste lepo uneli unesite opet");
                                        break;
                                    }
                                }
                            }


                        }
                        break;
                    }
                    case "2": {
                        izlazniTokKaKlijentu.println("\n\t\t######## Rezervacija karte ########");

                        String ime;
                        String prezime;
                        String jmbg;
                        String email;
                        while (true) {
                            izlazniTokKaKlijentu.println("Unesi ime: \n");
                            ime = ulazniTokOdKlijenta.readLine();
                            if (ime.contains(" ")) izlazniTokKaKlijentu.println("Pogresio si kod imena!, kreni opet!");
                            else {
                                izlazniTokKaKlijentu.println("Unesi prezime: \n");
                                prezime = ulazniTokOdKlijenta.readLine();
                                if (prezime.contains(" "))
                                    izlazniTokKaKlijentu.println("Pogresio si kod prezimena!, kreni opet!");
                                else {
                                    izlazniTokKaKlijentu.println("Unesi jmbg\n");
                                    jmbg = ulazniTokOdKlijenta.readLine();
                                    if (jmbg.length() != 13)
                                        izlazniTokKaKlijentu.println("Pogresio si kod jmbga, kreni opet!");
                                    else {
                                        izlazniTokKaKlijentu.println("Unesi email\n");
                                        email = ulazniTokOdKlijenta.readLine();
                                        if (!(email.contains("@")))
                                            izlazniTokKaKlijentu.println("Pogresio si kod emaila, kreni opet!");
                                        else break;


                                    }

                                }

                            }

                        }


                        User novi = new User(ime, prezime, jmbg, email);

                        registrovani = deserijalizuj("clients.bin");
                        int sum1 = 0;
                        boolean postoji = false;
                        User postojeci = null;
                        boolean unet = false;
                        if (registrovani.size() != 0) {
//ovo ne radi
                            for (User user : registrovani) {
                                if (novi.equals(user)) {
                                    postoji = true;
                                    break;
                                }
                            }
                            for (User user : registrovani) {
                                if (novi.getJmbg().equals(user.getJmbg())) {
                                    sum1 = sum1 + user.getBrojObicnih() + user.getBrojVip();
                                }
                            }


                        } else {
                            novi.setBrojObicnih(novi.getBrojObicnih() + 1);
                            registrovani.add(novi);
                            serializuj(registrovani);
                            izlazniTokKaKlijentu.println("Kupljena karta i registrovan u bazu bez usernamea! (1)");
                            kreirajKartuIPosalji("Obicna", novi);
                            //ispisi tekst datoteku o podacima karte

                            unet = true;
                        }
                        if (!unet) {
                            if (sum1 < 4) {
                                if (postoji) {
//
                                    User izbaci = null;
                                    for (User user : registrovani) {
                                        if (user.equals(novi)) {
                                            novi.setBrojObicnih(user.getBrojObicnih() + 1);
                                            izbaci = user;
                                        }
                                    }
                                    registrovani.remove(izbaci);
                                    registrovani.add(novi);
                                    serializuj(registrovani);
                                    izlazniTokKaKlijentu.println("Kupljena karta i dodata korisniku bez usernamea,koji vec ima registrovan bez usernamea, a vec ima registrovan akaunt sa usernameom (2)");
                                    kreirajKartuIPosalji("Obicna", novi);
                                    //ispisi tekst datoteku o podacima karte


                                } else {
                                    novi.setBrojObicnih(novi.getBrojObicnih() + 1);
                                    registrovani.add(novi);
                                    serializuj(registrovani);
                                    izlazniTokKaKlijentu.println("Kupljena karta i dodata korisniku bez usernamea,koji je sad registrovan bez usernamea, a ima vec registrovan akaunt sa usernameom (3)");
                                    kreirajKartuIPosalji("Obicna", novi);
                                    izlazniTokKaKlijentu.println("Fajl sacuvan");
                                    //ispisi tekst datoteku o podacima karte

                                }
                            } else {
                                izlazniTokKaKlijentu.println("Imate vise od 4 karte rezervisane!");
                                break;
                            }
                        }


                        break;
                    }

                }
                //iznad ovoga ide default za glavni svic
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
