package tracker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = null;
        String studentFullInfo = null;
        Student student = null;

        System.out.println("Learning Progress Tracker");
        do {
//            System.out.print("> ");
            input = scanner.nextLine().trim();
            if ("exit".equals(input)) {
                System.out.println("Bye!");
                return;
            }
            if ("back".equals(input)) {
                System.out.println("Enter 'exit' to exit the program");
                continue;
            }
            if ("".equals(input)) {
                System.out.println("No input.");
            } else if ("add students".equals(input)) {
                System.out.println("Enter student credentials or 'back' to return:");
//                System.out.print("> ");
                int numberOfStudents = 0;

                here:
                while (true) {
//                    System.out.print("> ");
                    studentFullInfo = scanner.nextLine();

                    if ("back".equals(studentFullInfo)) {
                        System.out.println("Total " + numberOfStudents + " students have been added.");
                        break;
                    }


                    List<String> studentInfoSplited = new ArrayList<>(Arrays.asList(studentFullInfo.split(" ")));
                    if (studentInfoSplited.size() < 3) {
                        System.out.println("Incorrect credentials.");
                        continue;
                    }


                    String firstName = studentInfoSplited.get(0);
                    studentInfoSplited.remove(0);
                    if (!isValidFirstName(firstName)) {
                        System.out.println("Incorrect first name.");
                        continue;
                    }

                    String email = studentInfoSplited.get(studentInfoSplited.size() - 1);
                    studentInfoSplited.remove(studentInfoSplited.size() - 1);
                    if (!isValidEmail(email)) {
                        System.out.println("Incorrect email.");
                        continue;
                    }

                    StringBuilder lastNameSb = new StringBuilder();
                    for (String word : studentInfoSplited) {
                        if (!isValidLastName(word)) {
                            System.out.println("Incorrect last name.");
                            continue here;
                        }
                        lastNameSb.append(word);
                        lastNameSb.append(" ");
                    }
                    String lastName = lastNameSb.toString().trim();

                    student = new Student(firstName, lastName, email);
                    numberOfStudents++;
                    System.out.println("The student has been added.");
                }

            } else {
                System.out.println("Error: unknown command!");
            }
        } while (true);
    }

    public static boolean isValidFirstName(String word) {
        String namePattern = "^[a-zA-Z][a-zA-Z-']{1,}";         //matches
        if (patternMatches(word, namePattern)) {
            if (word.substring(word.length() - 1).matches("[-']")
                    || twoSpecialCharsInRow(word)) {
                return false;
            }
            return true;
        }
        return false;
    }

    public static boolean isValidLastName(String word) {
        String lastNamePattern = "^[a-zA-Z][a-zA-Z-']{1,}";
        if (patternMatches(word, lastNamePattern)) {
            if (word.substring(word.length() - 1).matches("[-']")
                    || twoSpecialCharsInRow(word)) {
                return false;
            }
            return true;
        }
        return false;
    }

    public static boolean isValidEmail(String email) {
        String emailPattern = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{1,}$";
        if (patternMatches(email, emailPattern)) {
            return true;
        }
        return false;
    }

    public static boolean patternMatches(String emailAddress, String regexPattern) {
        return Pattern.compile(regexPattern)
                .matcher(emailAddress)
                .matches();
    }

    public static boolean twoSpecialCharsInRow(String word) {
        for (int i = 0; i < word.length() - 1; i++) {
            if (word.charAt(i) == '-' && (word.charAt(i + 1) == '-' || word.charAt(i + 1) == '\'')
                    || word.charAt(i) == '\'' && (word.charAt(i + 1) == '-' || word.charAt(i + 1) == '\'')) {
                return true;
            }
        }
        return false;
    }
}

