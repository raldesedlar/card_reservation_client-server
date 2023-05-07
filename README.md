# card_reservation_client-server / Mrežno programiranje

Potrebno je napraviti klijent-server aplikaciju za rezervaciju karata.Aplikacija treba da omogući sledeće funkcionalnosti:
●Rezervaciju karata za svetsko prvenstvo;
●Registraciju korisnika;
●Prijavljivanje korisnika na sistem (username i password);
●Otkazivanje rezervacije.Osnovni zahtev (70%)
●Klijent se povezuje na server;
●Server šalje poruku o uspešnom povezivanju i daje klijentu opcije koje su definisane u nastavku;
●Korisnik odabirom odgovarajuće opcije može videti broj preostalih karata;
●Korisnik odabirom odgovarajuće opcije može izvršiti rezervaciju za koju je potrebno da navede svoje lične podatke (ime, prezime, JMBG i email);
●Nakon uspešne rezervacije korisnik dobija tekstualni fajl koji sadrži osnovne podatke o rezervaciji.

Ograničenja:
●Omogućiti da server može istovremeno da radi sa više klijenata;
●Obrada grešaka pri mrežnoj komunikaciji i radu programa je obavezna;
●Voditi računa o perzistentnosti podataka nakon gašenja servera;
●Ukupan broj karata koje su u ponudi je 20;
●Jedan korisnik može rezervisati maksimum 4 karte;
●Voditi računa o formatu JMBG-a.Dodatni zahtev 1 (15%)
●Implementirati registraciju i prijavljivanje korisnika na server;
●Registracija obuhvata osnovne podatke o korisniku kao što su username, password,ime, prezime, JMBG i email;
●Korisnik se prijavljuje na sistem unosom korisničkog imena i šifre.

Ograničenja:
●Obratiti pažnju da ne mogu da postoje dva korisnika sa istim korisničkim imenom.
Dodatni zahtev 2 (15%)
●Registrovani korisnik nakon prijavljivanja može da izvrši rezervaciju običnih karata ili karata u VIP loži.
●Prijavljen korisnik može da poništi svoju rezervaciju i tako oslobodi karte za druge korisnike.

Ograničenja:
●Broj karata u VIP loži je 5.

NAPOMENE:
Potrebno je da klijentska aplikacija sadrži korisnički interfejs. Nije neophodno da interfejs bude grafički, moguće je napraviti i konzolnu aplikaciju. Serverska i klijentska aplikacija su dva odvojena projekta.Svi projekti moraju biti razvijeni na takav način da budu otporni na greške u  radu  i  komunikaciji  (potrebno  je implementirati  alternativne  slučajeve korišćenja).

Ovo je bio domaći zadatak iz predmeta Računarske mreže i telekomunikacije. Kroz rad na ovom domaćem zadatku sam naučio osnovni rad sa Socketima.
Uprkos uspešno odbranjenom domaćem, postoje stvari koje bih želeo da zamenim, ili implementiram kada budem imao dovoljno vremena da se posvetimvrazradi ovog zadatka. 

Na čemu bih dalje radio:
•	Preimenovao metode i varijable tako da budu razumljive za svakog
•	Jasnije uredio kod, izbacio bih suvišne komentare
•	Implementirao bih bazu podataka, npr. Firebase, kako bih lakše mogao da unosim, brišem, čitam i ažuriram podatke. Trenutna baza je implementirana preko binarnih datoteka, iz razloga što sam u datom trenutku iskoristio meni najpoznatije rešenje, znajući da postoje daleko efikasniji i efektivniji načini za postizanje istog cilja.
•	Implementirao bih bolji korisnički interfejs, koji ne bi funkionisao preko konzole.
![image](https://user-images.githubusercontent.com/113210576/236691945-4a94d213-ccf8-4f34-8058-f919d1d5773a.png)

