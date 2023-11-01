
package common;


import java.util.Scanner;

public class Algorithm {

    private Scanner SCANNER;

    public Scanner getScanner() {
        if (SCANNER == null) {
            SCANNER = new Scanner(System.in);
        }
        return SCANNER;
    }

    public int getInt(String title, String error) {
        String input;
        while (true) {
            System.out.println(title);
            input = getScanner().nextLine();
            if (input.matches("^[\\d]+")) {
                break;
            }
            System.err.println(error);
        }
        return Integer.parseInt(input);
    }

    public String getYN(String title, String error) {
        String input;
        while (true) {
            System.out.println(title);
            input = getScanner().nextLine();
            if (input.matches("^[YyNn]")) {
                break;
            }
            System.err.println(error);
        }
        return input;
    }

    public String getString(String title) {
        String pattern = "[a-zA-Z0-9\\s]+";
        String input;
        while (true) {
            System.out.println(title);
            input = getScanner().nextLine();
            if (input.matches(pattern)) {
                break;
            } else {
                System.out.println("Invalid input");
            }
        }
        return input;
    }

    public String getFolder(String title) {
        String input;
        String pattern = "([a-zA-Z]:)?(\\\\[a-zA-Z0-9_.-]+)+\\\\?";
        while (true) {
            System.out.println(title);
            input = getScanner().nextLine();
            if (input.matches(pattern)) {
                break;
            }
        }
        return input;
    }

}
