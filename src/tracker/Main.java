package tracker;

import java.util.*;

import static tracker.Student.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = null;
        String studentFullInfo = null;
        Student student = null;

        List<Map<String, String>> studentsList = new ArrayList<>();

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
                    Map<String, String> studentInfoMap = new HashMap<>();
//                    System.out.print("> ");
                    studentFullInfo = scanner.nextLine();

                    if ("back".equals(studentFullInfo)) {
                        System.out.println("Total " + numberOfStudents + " students have been added.");

                        // temprorary
                        for (Map<String, String> studentInfo : studentsList) {
                            System.out.println(studentInfo);
                        }

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
                    studentsList.add(student.makeStudentInfoMap());
                    numberOfStudents++;

                    System.out.println("The student has been added.");
                }

            } else {
                System.out.println("Error: unknown command!");
            }
        } while (true);
    }
}

