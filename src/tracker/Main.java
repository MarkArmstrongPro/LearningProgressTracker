package tracker;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = null;

        System.out.println("Learning Progress Tracker");
        do {
//            System.out.print("> ");
            input = scanner.nextLine().trim();
            if ("exit".equals(input)) {
                System.out.println("Bye!");
                return;
            }
            if ("".equals(input)) {
                System.out.println("No input.");
            } else {
                System.out.println("Error: unknown command!");
            }
        } while (!"exit".equals(input));
    }
}

