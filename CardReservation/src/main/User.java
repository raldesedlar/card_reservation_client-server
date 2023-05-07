package main;

import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {


    private String ime;
    private String prezime;
    private String jmbg;
    private String email;
    private String username;
    private String password;

    private int brojObicnih=0;

    private int brojVip=0;

    public String getIme() {

        if(ime==null)
            return "";
        else
        return ime;
    }

    public String getPrezime() {

        if(prezime==null)
            return "";
            else
        return prezime;
    }

    public String getJmbg() {

        if(jmbg==null)
            return "";
        else
        return jmbg;
    }

    public String getEmail() {
        if(email==null)
            return "";
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getBrojObicnih() {
        return brojObicnih;
    }

    public void setBrojObicnih(int brojObicnih) {
        this.brojObicnih = brojObicnih;
    }

    public int getBrojVip() {
        return brojVip;
    }

    public void setBrojVip(int brojVip) {
        this.brojVip = brojVip;
    }


    public User(String ime,String prezime,String jmbg,String email){
        this.ime=ime;
        this.prezime=prezime;
        this.jmbg=jmbg;
        this.email=email;
        this.username="";
        this.password="";
        this.brojVip=getBrojVip();
        this.brojObicnih=getBrojObicnih();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(ime, user.ime) && Objects.equals(prezime, user.prezime) && Objects.equals(jmbg, user.jmbg) && Objects.equals(email, user.email) && Objects.equals(username, user.username) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ime, prezime, jmbg, email, username, password);
    }

    public User(String ime, String prezime, String jmbg, String email, String username, String password) {
        this.ime = ime;
        this.prezime = prezime;
        this.jmbg = jmbg;
        this.email = email;
        this.username = username;
        this.password = password;
        this.brojObicnih=0;
        this.brojVip=0;
    }
}
