package main;

public class Meni {

    public static String pozoviMeni(){
        return  "Izaberite opciju: \n" + "1.Proveri broj slobodnih karata \n" + "2.Rezervisi obicnu kartu \n" + "3.Registruj se \n" + "4. Uloguj se \n" + "0.Izlaz";
    }
    public static String pozoviMeniUlogovanog(){
        return "Izaberite opciju: \n1. Kupi obicnu kartu\n2.Kupi VIP kartu\n3.Otkazi obicnu kartu\n4. Otkazi VIP kartu\n5.Izlaz\n";
    }
}
