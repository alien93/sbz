#Projektni zadatak iz predmeta Sistemi bazirani na znanju 
#2015/2016

Implementirati aplikaciju koja podržava internet prodaju, što obuhvata vođenje evidencije o robi i poručivanje robe. 
Aplikaciju mogu da koriste tri grupe korisnika: kupci, menadžeri prodaje i prodavci. Da bi korisnik mogao da koristi aplikaciju, neophodno je da se prethodno uspešno prijavi na sistem.

<b>Korisnik</b> je opisan sledećim podacima: 
korisničko ime (jedinstveno), lozinka, ime, prezime, uloga i datum registrovanja. 

Za <b>kupce</b> se dodatno definiše profil kupca koga čine podaci: adresa isporuke, nagradni bodovi i kategorija kupca. Pored toga, profil kupca sadrži informacije o svim dosadašnjim uspešno realizovanim kupovinama (istorija kupovina).

<h4>Aplikacija treba da obezbedi rad sa sledećim entitetima:</h4>

<b>Kategorija kupca koja je opisana sa sledećim podacima</b>: šifra kategorije (jedinstveno), naziv i pragovi potrošnje za uspešno realizovane kupovine.

Prag potrošnje definiše broj nagradnih bodova koje kupac dobija u zavisnosti od svoje kategorije i potrošenog novca za jednu kupovinu. Ovaj entitet je opisan sledećim podacima: opsegom potrošnje (od-do) i funkcijom dodele nagradnih bodova. Na primer, zlatni kupac koji potroši između 5000 i 10000 dinara dobija bodove u vrednosti od 10% ukupno potrošenog novca. 

<b>Kategorija artikla</b> koja je opisana sa sledećim podacima: šifra kategorije (jedinstveno), nadkategorija i naziv. Na primer, kategorije artikala bi mogle da budu: roba široke potrošnje (podkategorije prehrambeni proizvodi, kozmetika, kućna hemija, mali kućni aparati), novogodišnji ukrasi i jelke, čestitke, oprema za kampovanje, oprema za roštilj, televizori, audio i video oprema, računari, laptopovi, računarske periferije, bela tehnika, mali kućni aparati, baštenski pribor, baštenski nameštaj, kućna hemija, domaća alkoholna pića, uvozna alkoholna piće i prehrambeni proizvodi. Pored toga, za kategoriju artikala definiše se i maksimalni dozvoljeni popusta pri kupovini artikla iz te kategorije izražen u procentima.

<b>Artikal</b> (proizvodi koji se prodaju u prodavnici) je opisan sa sledećim podacima: šifra (jedinstveno), naziv, kategorija artikla, cena, brojno stanje artikla u prodavnici, datum kreiranja zapisa o artiklu u prodavnici, naznaka da je potrebno popuniti zalihe artikla za prodavnicu i status zapisa (aktivan ili arhiviran). Za svaki artikal se definiše i minimalno stanja na lageru. Ukoliko brojno stanje artikla bude ispod minimalnog stanja na lageru, prodavac dobija obaveštenje da je potrebno poručiti dodatne količine artikala.

<b>Akcijski događaji</b> (na primer Nova godina, 1 maj, sezona letovanja …). Za akcijski događaj se definiše: šifra (jedinstveno), naziv, vremenski period trajanja događaja (na primer 27.april - 2.maj), procentualni popust za događaj, lista kategorija artikala na koje se događaj odnosi. Pri tome, u periodu trajanja događaja, na sve artikle iz liste kategorija odnosi se zadati procentualni popust. 

<b>Račun opisan sledećim podacima:</b> šifra (jedinstveno), datum, kupac, stanje, originalna ukupna cena, procenat umanjenja, konačna cena (cena kada se na nju primene svi popusti i umanjenja za akcijske bodove), broj potrošenih nagradnih bodova sa profila kupca, broj ostvarenih nagradnih bodova koji se dodaju na profila kupca, lista primenjenih popusta za kupovinu i lista stavki računa. Stanje rauna može biti poručeno, otkazano ili uspešno realizovano.

<b>Stavka računa</b> koja je opisana sa sledećim podacima: račun, redni broj stavke (jedinstveno u okviru računa), artikal koji se prodaje, jedinična cena artikla na dan kupovine, količina kupljenih artikala, originalna ukupna cena stavke, procenat umanjenja, konačna cena stavke (cena kada se na nju primene svi popusti) i lista primenjenih popusta za stavku.

<b>Popust za ceo račun</b> koji je opisan sledećim podacima: šifra (jedinstveno), račun, procenat umanjenja, oznaka da li je u pitanju osnovi popust ili dodatni popust.

<b>Popust za pojedinačnu stavku</b> koji je opisan sledećim podacima: šifra (jedinstveno), račun, stavka, procenat umanjenja, oznaka da li je u pitanju osnovi popust ili dodatni popust.

<b>Kupcima je omogućena sledeća funkcionalnost:</b>
1. Prikaz svih podataka iz svog profila.
2. Pregled istorijata svojih računa. 
3. Kupovina artikla i kreiranje računa:
a) Pretraga i prikaz artikla po šifri, nazivu, kategorijama artikla i opsegu cene. Za svaki artikal se prikazuje: šifra, naziv, kategorija artikla, cena, podaci o akcijskom događaju (ukoliko se popust ostvaren po osnovu akcijskog događaja odnosi na kategoriju kojoj pripada taj artikal) i opcija dodavanja određene količine artikla u korpu. Prikazuju se samo aktivni artikli.

b) Dodavanje artikla u korpu.

c) Odlazak na korpu i kreiranje računa. Prilikom formiranja računa, primenom rezonera određuje se ukupna cena u koju su uključene sve akcije, pri čemu korisnik opiciono može ukupan iznos ili deo iznosa računa da pokrije nagradnim bodovima.

d) Za kupovinu se primenom rezonera određjuje broj nagradnih bodova i ti bodovi se dodaju na korisnički profil.

e) Nakon formiranja računa, korisniku se prikazuju: originalna ukupna cena računa, procenat umanjenja, plaćena cena (cena kada se na nju primene svi popusti i umanjenja za nagradne bodove), broj potrošenih nagradnih bodova, broj ostvarenih nagradnih bodova koji će se dodati na profil kupca, lista primenjenih popusta za ceo račun i lista primenjenih popusta za stavke računa.

f) Sistem perzistira podatke o računu i popustima.
Prodavcima je omogućena sledeća funkcionalnost:

1. Poručivanje artikala (popunjavanje zaliha).

a) Sistem pokreće rezoner i izvršava Jess pravila za poručivanje (popunjavanje zaliha) artikala, za artikal menja njegovu naznaku da je potrebno popuniti zalihe artikla za prodavnicu.

b) Sistem obaveštava prodavca koje artikle je potrebno poručiti. Spisak se formira na osnovu naznaka da je potrebno popuniti zalihe artikla za prodavnicu. Svaka stavka spiska ima podatke i o razlogu zašto je potrebno poručiti nove artikle.

c) Poručivanjem artikla za prodavnicu povećava se brojno stanje artikla u prodavnici i ažurira se naznaka za naručivanje artikla (ako je ona postavljena).

2. Obrada aktuelnih računa (one koju poručeni, a nisu uspešno realizovani i nisu otkazani).

a) Prodavcu se tabelarno prikazuju računi pri čemu je moguće filtrirati ih po statusu.

b) Prodavac pokreće obradu pojedinčanog računa. Sistem obrađuje taj račun tj. proverava da li je moguća uspešno realizovati račun (u zavisnosti od brojnog stanja artikla u prodavnici). Ukoliko se ispune svi potrebni uslovi sistem menja status računa u uspešno realizovan, ažurira profil kupca i brojno stanje artikla u prodavnici.

c) Prodavac ima opciju da otkaže određeni račun.

d) Sistem perzistira izmenjene podatke o kupcu, narudžbenici i artiklima.

<b>Menadžeri</b> prodaje imaju opciju da definišu poslovnu logiku i pravila vezana za kategorije kupca, kategorije artikla i akcije u prodavnici.
Menadžerima je omogućena sledeća funkcionalnost:
1. Ažuriranje liste kategorija kupaca (npr. obični, srebrni i zlatni kupac).

a) Prikaz svih kategorija i ažuriranje odabrane kategorije.

b) Za odabranu kategoriju omogućeno je ažuriranje pragova potrošnje. Svakom pragu se dodeljuje procentualna vrednost na osnovu koje će se izvršiti konverzija potrošenog novca u nagradne bodove.

2. Dodavanje i ažuriranje liste kategorija artikala. 

a) Dodavanje nove kategorije. Prodavac definiše šifru, naziv, maksimalan dozvoljeni popusta pri kupovini artikla (izražen u procentima) i oznaku da artikal predstavlja robu široke potrošnje na veliko.

b) Prikaz svih kategorija i ažuriranje odabrane kategorije. Dozvoljeno je ažuriranje svih podataka osim šifre.

3. Ažuriranje liste akcijskih događaja. Procentualni popust za događaj se primenjuje na artikle iz odabranih kategorija i sabira se sa ostalim popustima definisanim za artikle. 

a) Kreiranje novog događaja. Unosi se šifra, naziv, vremenski period trajanja događaja, procentualni popust za događaj i spisak kategorija artikala za koje se događaj definiše.

b) Prikaz svih događaja i ažuriranje odabranog događaja. Dozvoljeno je ažuriranje svih podataka osim šifre.




<h4>Definisati sledeća pravila u sistemu:</h4>
Za stavke računa:

a) Kreiraj osnovni popust od 10% za stavku ukoliko se u njoj naručuje više od 20 artikla, a artikal ne pripada kategoriji široke potrošnje.

b) Kreiraj osnovni popust od 5% za stavku ukoliko se u njoj naručuje više od 5 artikla, a artikal pripada kategoriji televizori, računari ili laptopovi.

c) Kreiraj osnovni popust od 7% za stavku ukoliko ukupna naručena vrednost stavke prelazi 5000 din i ukoliko njen artikal pripada kategoriji široke potrošnje.

NAPOMENA: Osnovni popust za artikle može biti samo 1, pri čemu se uzima najpovoljniji.

d) Kreiraj dodatni popust od 2% za stavku ukoliko je artikal iz stavke već kupovan u prethodnih 15 dana.

e) Kreiraj dodatni popust od 1% za stavku ukoliko su proizvodi iz kategorije tog artikla kupovani u prethodnih 30 dana.

f) Ukoliko datum narudžbenice pripada nekom vremenskom periodu akcijskog događaja i ukoliko za artikal iz stavke njegova kategorija pripada listi kategorija za koje se akcijski događaj definiše, tada kreiraj dodatni popust za stavku. Visina dodatnog popusta se preuzima iz akcijskog događaja.
NAPOMENA: Dodatni popusti će se sabirati sa osnovnim popustom.

g) Određivanje finalnog popusta za stavku i izračunavanje ukupne cene stavke.

1. Za stavku računa saberi sve popuste (osnovni + svi dodatni) koji su za nju definisani.
2. Potom na osnovu kategorije artikla iz stavke uporedi rezultat sabiranja popusta (izražen u procentima) sa maksimalno dozvoljenim popustom (izražen u procentima) koje se primenjuje za artikle iz posmatrane kategorije. 
3. Ukoliko vrednost ne prelazi maksimalnu dozvoljeni popust, tada se rezultat upisuje u polje procenat umanjenja za stavku. U suprotnom se upisuje maksimalno dozvoljeni popust za artikle koji pripadaju ovoj kategoriji.
4. Na osnovu procenata umanjenja stavke, jedinične cene artikla na dan naručivanja i količine naručenih artikala, izračunaj i upiši plaćenu cenu za stavku. Cena se izračunava tako što se pomnoži jedinična cena sa količinom, te se na dobijeni proizvod primeni procenat umanjenja stavke.
Za cele narudžbine:

h) Osnovni popust od 5% na ceo račun ukoliko ukupna vrednost naručenih artikala prelazi 200000 dinara.

i) Dodatni popust od 1% na ceo račun ukoliko je kupac korisnik sistema više od 2 godine (nagrađuju stari kupci).

j) Dodatni popust od 1% na ceo račun ukoliko je kupac pripada kategoriji srebrni kupci ili zlatni kupci (nagrađuju kupci sa posebnim privilegijama).

k) Dodatni popust od 3% na ceo račun ukoliko ukupna vrednost naručenih artikala prelazi 50000 dinara i ukoliko u računu postoje barem 10 artikala čija ukupna cena prelazi 50% cene ukupne vrednosti naručenih artikala.
NAPOMENA: Proračuni popusta za celu narudžbinu uvažavaju sve prethodne ostvarene popuste za artikle.

l) Određivanje finalnog popusta za račun i izračunavanje konačne cene.

1. Za račun saberi sve popuste (osnovni + svi dodatni).
2. Potom se rezultat sabiranja popusta (izražen u procentima) upiše u polje procenat umanjenja za račun. 
3. Na osnovu procenata umanjenja računa i konačne cene za svaku stavku računa,  izračunaj i upiši konačnu cenu računa. Cena se izračunava tako što se saberu sve konačne cene za svaku stavku, te se na dobijeni zbir primeni procenat umanjenja za račun.

Za dobijanje nagradnih bodova:

m) Na osnovu konačne cene računa i kategorija kupca identifikuj prag potrošnje kupca. Iz identifikovanog praga preuzmi procentualnu vrednost i primeni je na plaćenu cenu računa. Dobijeni broj zaokruži i upiši kao nove akcijske bodove u računu.
Za naručivanje (popunjavanje zaliha) artikala za prodavnicu :

n) Ako je brojno stanje artikla manje od minimalnog, postavi naznaku u artiklu.
