# Proiect PAO - Etapa I

## Tema aleasa
Am ales o aplicatie simpla de booking pentru hoteluri. Ideea este sa fie suficient de clara pentru tema de facultate si sa bifeze toate cerintele din etapa I, fara sa incerce sa simuleze un sistem real foarte complex.

## Tipuri de obiecte definite
In proiect exista mai mult de 8 tipuri de obiecte:

1. `Person`
2. `Guest`
3. `Employee`
4. `Hotel`
5. `Room`
6. `StandardRoom`
7. `SuiteRoom`
8. `Booking`
9. `Payment`
10. `Review`

Observatie: `Person` si `Room` sunt clase de baza folosite pentru mostenire, iar clasele derivate sunt folosite efectiv in aplicatie.

## Actiuni / interogari implementate
Sunt implementate mai mult de 10 actiuni:

1. adaugare hotel
2. adaugare camera standard intr-un hotel
3. adaugare camera de tip suite intr-un hotel
4. inregistrare client
5. inregistrare angajat
6. afisare toate hotelurile
7. cautare hoteluri dupa oras
8. afisare camerele unui hotel
9. afisare camere disponibile sub un anumit pret
10. creare rezervare
11. anulare rezervare
12. afisare rezervarile unui client
13. inregistrare plata
14. adaugare review
15. afisare review-uri pentru un hotel
16. afisare angajati

O parte dintre aceste operatii sunt apelate direct din meniul din `Main`, iar toate sunt expuse prin serviciul `HotelBookingService`.

## Structura proiectului

### Clase de baza
- `Person` este clasa de baza pentru persoane.
- `Room` este clasa de baza pentru camere.

### Clase derivate
- `Guest` si `Employee` mostenesc `Person`.
- `StandardRoom` si `SuiteRoom` mostenesc `Room`.

### Clase principale de domeniu
- `Hotel` retine date despre hotel si lista camerelor.
- `Booking` retine date despre o rezervare.
- `Payment` retine date despre o plata.
- `Review` retine date despre un review lasat de un client.

### Serviciu
- `HotelBookingService` este clasa serviciu care gestioneaza operatiile sistemului.

### Clasa Main
- `Main` contine meniul din consola si face apeluri catre serviciu.

## Justificarea unor alegeri structurale

### 1. De ce am folosit mostenire?
Am folosit mostenire deoarece cerinta solicita explicit acest lucru, iar in cazul proiectului are sens natural:
- toate persoanele au campuri comune (`id`, `name`, `email`)
- toate camerele au campuri comune (`id`, `number`, `capacity`, `pricePerNight`, `available`)

Astfel se evita duplicarea de cod si proiectul ramane usor de explicat la prezentare.

### 2. De ce am folosit un serviciu separat?
Am separat logica de business in `HotelBookingService`, iar `Main` se ocupa doar de interactiunea cu utilizatorul. Este o alegere simpla si buna pentru un proiect de facultate deoarece:
- codul este mai ordonat
- metodele sunt mai usor de testat si urmarit
- se respecta cerinta cu “cel putin o clasa serviciu”

### 3. De ce am folosit mai multe colectii?
Cerinta cere minimum 2 colectii diferite, dintre care una sortata. In proiect am folosit:
- `TreeSet<Hotel>` pentru hoteluri
- `Map<Integer, Guest>` pentru clienti
- `Map<Integer, Employee>` pentru angajati
- `List<Booking>` pentru rezervari
- `List<Payment>` pentru plati
- `List<Review>` pentru review-uri

`TreeSet<Hotel>` este colectia sortata. Hotelurile sunt ordonate dupa oras, apoi dupa nume.

### 4. De ce aplicatia este minimalista?
Scopul a fost sa bifeze curat cerintele etapei I. De aceea:
- nu am introdus baza de date
- nu am introdus fisiere
- nu am complicat validarea sau autentificarea
- am pastrat interactiunea printr-un meniu simplu in consola

Aceste elemente pot fi extinse in etapa II.

## Cum sunt bifate cerintele

### 1. Clase simple cu atribute private / protected si metode de acces
Se observa in:
- `Person`
- `Guest`
- `Employee`
- `Hotel`
- `Room`
- `Booking`
- `Payment`
- `Review`

Clasele folosesc campuri `private` sau `protected`, constructori, getteri si setteri.

### 2. Cel putin 2 colectii diferite, dintre care una sortata
Se observa in `HotelBookingService`:
- `TreeSet<Hotel> hotels` - colectie sortata
- `Map<Integer, Guest> guests`
- `Map<Integer, Employee> employees`
- `List<Booking> bookings`
- `List<Payment> payments`
- `List<Review> reviews`

### 3. Utilizare mostenire pentru clase aditionale si folosirea lor in colectii
Se observa in:
- `Guest extends Person`
- `Employee extends Person`
- `StandardRoom extends Room`
- `SuiteRoom extends Room`

Clasele `StandardRoom` si `SuiteRoom` sunt stocate in lista de `Room` din `Hotel`, deci mostenirea este folosita si practic, nu doar declarativ.

### 4. Cel putin o clasa serviciu
Se observa in `HotelBookingService`, unde sunt implementate toate operatiile principale ale sistemului.

### 5. O clasa Main din care sunt facute apeluri catre servicii
Se observa in `Main`, care foloseste `HotelBookingService` pentru toate optiunile din meniu.

## Date demo
Metoda `seedData()` din `HotelBookingService` adauga:
- 3 hoteluri
- mai multe camere
- 2 clienti
- 2 angajati
- 1 rezervare
- 1 plata
- 1 review

Acest lucru ajuta la rularea rapida a aplicatiei fara sa fie nevoie sa introduci manual totul de la inceput.

## Cum se ruleaza
In terminal, din folderul proiectului:

```bash
javac *.java
java Main
```

## Observatie pentru prezentare
Pentru prezentare poti spune ca proiectul este gandit special pentru etapa I si ca arhitectura a fost pastrata simpla intentionat, pentru a pune accent pe:
- modelarea orientata pe obiect
- mostenire
- colectii
- separarea logicii de business in serviciu

Aceasta justificare este potrivita pentru cerinta si suficient de “student-level”, fara complexitate inutila.
