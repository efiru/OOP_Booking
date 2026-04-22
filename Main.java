import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final HotelBookingService service = new HotelBookingService();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        service.seedData();
        runMenu();
    }

    private static void runMenu() {
        boolean running = true;

        while (running) {
            printMenu();
            int option = readInt("Alege optiunea: ");

            try {
                switch (option) {
                    case 1:
                        printList("Lista hotelurilor", service.getAllHotels());
                        break;
                    case 2:
                        searchHotelsByCity();
                        break;
                    case 3:
                        showRoomsForHotel();
                        break;
                    case 4:
                        showRoomsUnderPrice();
                        break;
                    case 5:
                        registerGuest();
                        break;
                    case 6:
                        showGuests();
                        break;
                    case 7:
                        createBooking();
                        break;
                    case 8:
                        showBookingsForGuest();
                        break;
                    case 9:
                        cancelBooking();
                        break;
                    case 10:
                        registerPayment();
                        break;
                    case 11:
                        addReview();
                        break;
                    case 12:
                        showReviewsForHotel();
                        break;
                    case 13:
                        showEmployees();
                        break;
                    case 0:
                        running = false;
                        System.out.println("Aplicatia se inchide.");
                        break;
                    default:
                        System.out.println("Optiune invalida.");
                }
            } catch (IllegalArgumentException exception) {
                System.out.println("Eroare: " + exception.getMessage());
            }

            System.out.println();
        }
    }

    private static void printMenu() {
        System.out.println("===== HOTEL BOOKING APP =====");
        System.out.println("1. Afiseaza toate hotelurile");
        System.out.println("2. Cauta hoteluri dupa oras");
        System.out.println("3. Afiseaza camerele unui hotel");
        System.out.println("4. Afiseaza camere disponibile sub un pret");
        System.out.println("5. Inregistreaza client");
        System.out.println("6. Afiseaza clientii");
        System.out.println("7. Creeaza rezervare");
        System.out.println("8. Afiseaza rezervarile unui client");
        System.out.println("9. Anuleaza rezervare");
        System.out.println("10. Inregistreaza plata");
        System.out.println("11. Adauga review");
        System.out.println("12. Afiseaza review-uri pentru hotel");
        System.out.println("13. Afiseaza angajatii");
        System.out.println("0. Iesire");
    }

    private static void searchHotelsByCity() {
        String city = readText("Oras: ");
        printList("Hoteluri gasite", service.findHotelsByCity(city));
    }

    private static void showRoomsForHotel() {
        int hotelId = readInt("Id hotel: ");
        printList("Camerele hotelului", service.getRoomsForHotel(hotelId));
    }

    private static void showRoomsUnderPrice() {
        double price = readDouble("Pret maxim pe noapte: ");
        printList("Camere disponibile", service.getAvailableRoomsUnderPrice(price));
    }

    private static void registerGuest() {
        String name = readText("Nume client: ");
        String email = readText("Email client: ");
        Guest guest = service.registerGuest(name, email);
        System.out.println("Client adaugat: " + guest);
    }

    private static void showGuests() {
        printList("Lista clientilor", service.getAllGuests());
    }

    private static void createBooking() {
        int guestId = readInt("Id client: ");
        int hotelId = readInt("Id hotel: ");
        int roomId = readInt("Id camera: ");
        String dateText = readText("Data check-in (YYYY-MM-DD): ");
        int nights = readInt("Numar nopti: ");

        Booking booking = service.createBooking(guestId, hotelId, roomId, LocalDate.parse(dateText), nights);
        System.out.println("Rezervare creata: " + booking);
    }

    private static void showBookingsForGuest() {
        int guestId = readInt("Id client: ");
        printList("Rezervarile clientului", service.getBookingsForGuest(guestId));
    }

    private static void cancelBooking() {
        int bookingId = readInt("Id rezervare: ");
        boolean cancelled = service.cancelBooking(bookingId);

        if (cancelled) {
            System.out.println("Rezervarea a fost anulata.");
        } else {
            System.out.println("Rezervarea nu a putut fi anulata.");
        }
    }

    private static void registerPayment() {
        int bookingId = readInt("Id rezervare: ");
        String method = readText("Metoda plata: ");
        Payment payment = service.registerPayment(bookingId, method);
        System.out.println("Plata inregistrata: " + payment);
    }

    private static void addReview() {
        int guestId = readInt("Id client: ");
        int hotelId = readInt("Id hotel: ");
        int stars = readInt("Numar stele (1-5): ");
        String comment = readText("Comentariu: ");

        Review review = service.addReview(guestId, hotelId, stars, comment);
        System.out.println("Review adaugat: " + review);
    }

    private static void showReviewsForHotel() {
        int hotelId = readInt("Id hotel: ");
        printList("Review-uri", service.getReviewsForHotel(hotelId));
    }

    private static void showEmployees() {
        printList("Lista angajatilor", service.getAllEmployees());
    }

    private static int readInt(String message) {
        while (true) {
            System.out.print(message);
            String line = scanner.nextLine();

            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException exception) {
                System.out.println("Introdu un numar intreg.");
            }
        }
    }

    private static double readDouble(String message) {
        while (true) {
            System.out.print(message);
            String line = scanner.nextLine();

            try {
                return Double.parseDouble(line);
            } catch (NumberFormatException exception) {
                System.out.println("Introdu un numar valid.");
            }
        }
    }

    private static String readText(String message) {
        System.out.print(message);
        return scanner.nextLine();
    }

    private static void printList(String title, List<?> items) {
        System.out.println(title + ":");
        if (items.isEmpty()) {
            System.out.println("Nu exista elemente.");
            return;
        }

        for (Object item : items) {
            System.out.println(item);
        }
    }
}
