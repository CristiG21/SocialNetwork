package ubb.scs.map.ui;

import ubb.scs.map.domain.Prietenie;
import ubb.scs.map.domain.Utilizator;
import ubb.scs.map.domain.validators.ValidationException;
import ubb.scs.map.exceptions.ServiceException;
import ubb.scs.map.service.Service;

import java.util.Scanner;

public class ConsoleUI extends AbstractUI {
    private static ConsoleUI instance = null;
    private Scanner cin;

    private ConsoleUI(Service service) {
        super(service);
        cin = new Scanner(System.in);
    }

    public static ConsoleUI getInstance(Service service) {
        if (instance == null) {
            instance = new ConsoleUI(service);
        }
        return instance;
    }

    public void run() {
        cin = new Scanner(System.in);

        while (true) {
            System.out.print(">>> ");
            String command = cin.nextLine();

            try {
                switch (command) {
                    case "exit":
                        cin.close();
                        return;

                    case "help":
                        System.out.println("add_utilizator - Add a utilizator");
                        System.out.println("remove_utilizator - Remove a utilizator by id");
                        System.out.println("add_prietenie - Add a prietenie");
                        System.out.println("remove_prietenie - Remove a prietenie by id");
                        System.out.println("utilizatori - Show all utilizatori");
                        System.out.println("prietenii - Show all prietenii");
                        System.out.println("communities - Number of communities");
                        System.out.println("most_sociable - Show the most sociable community");
                        System.out.println("exit - Exit");
                        break;

                    case "add_utilizator":
                        addUtilizator();
                        System.out.println("Utilizator added!");
                        break;

                    case "remove_utilizator":
                        removeUtilizator();
                        break;

                    case "add_prietenie":
                        addPrietenie();
                        System.out.println("Prietenie added!");
                        break;

                    case "remove_prietenie":
                        removePrietenie();
                        break;

                    case "utilizatori":
                        Iterable<Utilizator> utilizatori = service.getAllUtilizatori();
                        utilizatori.forEach(System.out::println);
                        break;

                    case "prietenii":
                        Iterable<Prietenie> prietenii = service.getAllPrietenii();
                        prietenii.forEach(this::printPrietenie);
                        break;

                    case "communities":
                        System.out.println("The number of communities is: " + service.numberOfCommunities());
                        break;

                    case "most_sociable": {
                        Iterable<Utilizator> community = service.mostSociableCommunity();
                        System.out.println("The most sociable community is:");
                        community.forEach(System.out::println);
                        break;
                    }

                    default:
                        System.out.println("Wrong Command!");
                }
            } catch (ValidationException | IllegalArgumentException | ServiceException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void addUtilizator() {
        System.out.print("First Name: ");
        String firstname = cin.nextLine();
        System.out.print("Last Name: ");
        String lastname = cin.nextLine();

        service.addUtilizator(firstname, lastname);
    }

    public void removeUtilizator() {
        long id;
        try {
            System.out.print("Utilizator Id: ");
            id = Long.parseLong(cin.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid id!");
            return;
        }

        if (service.removeUtilizator(id) == null)
            System.out.println("Given id doesn't exist!");
        else
            System.out.println("Utilizator removed!");
    }

    public void addPrietenie() {
        long utilizator1Id, utilizator2Id;
        try {
            System.out.print("First Utilizator Id: ");
            utilizator1Id = Long.parseLong(cin.nextLine());
            System.out.print("Second Utilizator Id: ");
            utilizator2Id = Long.parseLong(cin.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid id!");
            return;
        }

        service.addPrietenie(utilizator1Id, utilizator2Id);
    }

    public void removePrietenie() {
        long id;
        try {
            System.out.print("Prietenie Id: ");
            id = Long.parseLong(cin.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid id!");
            return;
        }

        if (service.removePrietenie(id) == null)
            System.out.println("Given id doesn't exist!");
        else
            System.out.println("Prietenie removed!");
    }

    public void printPrietenie(Prietenie prietenie) {
        System.out.print("id=" + prietenie.getId() + ", ");
        Utilizator utilizator1 = service.getUtilizator(prietenie.getUtilizator1Id());
        Utilizator utilizator2 = service.getUtilizator(prietenie.getUtilizator2Id());
        System.out.println("(" + utilizator1 + ") <-> (" + utilizator2 + ")");
    }
}