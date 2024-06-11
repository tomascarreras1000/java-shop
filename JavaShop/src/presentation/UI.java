package presentation;

import java.util.InputMismatchException;
import java.util.Scanner;

public class UI {
    private Scanner scanner;

    public UI() {
        this.scanner = new Scanner(System.in);
    }

    public void showArt() {
        System.out.println("        ________      ____");
        System.out.println("  ___  / / ____/___  / __/_______");
        System.out.println(" / _ \\/ / /   / __ \\/ /_/ ___/ _ \\");
        System.out.println("/  __/ / /___/ /_/ / __/ /  /  __/");
        System.out.println("\\___/_/\\____/\\____/_/ /_/   \\___/");

    }

    public void intro() {
        System.out.println("\nWelcome to elCofre Digital Shopping Experiences.\n");
    }

    public void showMainMenu() {
        System.out.println("\n    1) Manage Products");
        System.out.println("    2) Manage Shops");
        System.out.println("    3) Search Products");
        System.out.println("    4) List Shops");
        System.out.println("    5) Your Cart\n");
        System.out.println("    6) Exit\n");

    }

    public void showProductsMenu() {
        showMessage("\n    1) Create a business.Product");
        showMessage("    2) Remove a business.Product");
        showMessage("\n    3) Back");
    }

    public void showShopsMenu() {
        showMessage("\n    1) Create a Shop");
        showMessage("    2) Expand a Shop's Catalogue");
        showMessage("    3) Reduce a Shop's Catalogue");
        showMessage("\n    4) Back");
    }

    /**
     * Generic helper function to ask the user for a String.
     * @param message The message to show the user before asking for a string.
     * @return The user's entered string.
     */
    public String askForString(String message) {
        System.out.print(message);
        return scanner.nextLine();
    }

    public String askForStringFormatted(String message) {
        System.out.print(message);
        return formatString(scanner.nextLine());
    }
    /**
     * Generic helper function to show a message to the user. While it may seem unnecessary to wrap a single line of code
     * in a function, doing so provides flexibility, as in the future we may decide to change how messages are shown
     * (e.g., visually) or add side-effects such as output logging to a file
     * @param message The message to show the user.
     */
    public void showMessage(String message) {
        System.out.println(message);
    }
    /**
     * Generic helper function to ask the user for an integer (as many times as needed, until it's correct).
     * @param message The message to show the user before asking for an integer.
     * @return A correct integer, as entered by the user.
     */
    public int askForInteger(String message) {
        while (true) {
            System.out.print(message);
            try {
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("\nError, please enter a valid integer.\n");
            } finally {
                scanner.nextLine();
            }
        }
    }
    /**
     * Generic helper function to ask the user for a double (as many times as needed, until it's correct).
     * @param message The message to show the user before asking for a double.
     * @return A correct double entered by the user.
     */
    public double askForDouble(String message) {
        do {
            System.out.print(message);
            try {
                return scanner.nextDouble();
            } catch (InputMismatchException e) {
                System.out.println("\nError, please enter a number.\n");
            } finally {
                scanner.nextLine();
            }
        } while (true);
    }

    public boolean confirm(String message) {
        System.out.print(message);
        return scanner.nextLine().equalsIgnoreCase("Yes");
    }

    private String formatString(String string) {
        String[] words = string.toLowerCase().split("\\s+");
        StringBuilder formattedProductBrand = new StringBuilder();

        for (String word : words) {
            String formattedWord = word.substring(0, 1).toUpperCase() + word.substring(1);
            formattedProductBrand.append(formattedWord).append(" ");
        }

        return formattedProductBrand.toString().trim();
    }
}
