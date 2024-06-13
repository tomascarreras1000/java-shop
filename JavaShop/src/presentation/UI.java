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
        System.out.print("\nWelcome to elCofre Digital Shopping Experiences.\n\n");
    }

    public void showMainMenu() {
        System.out.print("""
        
            1) Manage Products
            2) Manage Shops
            3) Search Products
            4) List Shops
            5) Your Cart
        
            6) Exit
            
        """);
    }

    public void showProductsMenu() {
        System.out.print("""
            
            1) Create a Product
            2) Remove a Product
            3) Back
            
        """);
    }

    public void showShopsMenu() {
        System.out.print("""
            
            1) Create a Shop
            2) Expand a Shop's Catalogue
            3) Reduce a Shop's Catalogue
            
            4) Back
            
        """);
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
     * Generic helper function to ask the user for a float (as many times as needed, until it's correct).
     * @param message The message to show the user before asking for a float.
     * @return A correct float entered by the user.
     */
    public float askForFloat(String message) {
        do {
            System.out.print(message);
            try {
                return scanner.nextFloat();
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
