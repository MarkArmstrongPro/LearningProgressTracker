package tracker;

import java.util.*;

import static tracker.Student.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = null;
        String studentFullInfo = null;
        Map<String, String> currentStudentMap = new HashMap<>();    // for add points and find blocks
        Student student = null;

        List<Map<String, String>> studentsList = new ArrayList<>();
        Map<String, String> studentInfoMap = new HashMap<>();

        System.out.println("Learning Progress Tracker");
        do {
//            System.out.print("> ");
            input = scanner.nextLine().strip();
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

                addNewStudent:
                while (true) {

//                    System.out.print("> ");
                    studentFullInfo = scanner.nextLine().strip();

                    if ("back".equals(studentFullInfo)) {
                        System.out.println("Total " + numberOfStudents + " students have been added.");

                        // temprorary
//                        for (Map<String, String> studentInfo : studentsList) {
//                            System.out.println(studentInfo);
//                        }

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
                            continue addNewStudent;
                        }
                        lastNameSb.append(word);
                        lastNameSb.append(" ");
                    }
                    String lastName = lastNameSb.toString().trim();

                    for (Map<String, String> studentInfo : studentsList) {
                        if (studentInfo.get("email").equals(email)) {
                            System.out.println("This email is already taken.");
                            continue addNewStudent;
                        }
                    }

                    student = new Student(firstName, lastName, email);
                    studentsList.add(student.makeStudentInfoMap());
                    numberOfStudents++;

                    System.out.println("The student has been added.");
                }
            } else if ("list".equals(input)) {                          /* "list" - prints the list of students */
                if (studentsList.size() == 0) {
                    System.out.println("No students found");
                } else {
                    System.out.println("Students:");
                    for (Map<String, String> studentsInfo : studentsList) {
                        System.out.println(studentsInfo.get("id"));
                    }
                }
            } else if ("add points".equals(input)) {
                System.out.println("Enter an id and points or 'back' to return:");
                String addPointsById = "";
                List<String> pointsList = new ArrayList<>();
                updatePoints:
                while (true) {
                    addPointsById = scanner.nextLine().strip();
                    if ("back".equals(addPointsById)) {
                        System.out.println("Enter 'exit' to exit the program");
//                        scanner.nextLine();
                        break;
                    }
                    // format: studentId number number number number
                    pointsList = new ArrayList<>(Arrays.asList(addPointsById.strip().split(" ")));
                    // if incorrect number of points
                    if (pointsList.size() != 5) {
                        System.out.println("Incorrect points format");
                        continue;
                    }
                    for (String point : pointsList.subList(1, pointsList.size())) {
                        try {
                            if (Integer.parseInt(point) < 0) {
                                System.out.println("Incorrect points format");
                                continue updatePoints;
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Incorrect points format");
                            continue updatePoints;
                        }
                    }
                    // is student added?
                    boolean isStudenAdded = false;
                    for (Map<String, String> studentsInfo : studentsList) {
                        if (studentsInfo.get("id").equals(pointsList.get(0))) {
                            currentStudentMap = studentsInfo;
                            isStudenAdded = true;
                        }
                    }
                    if (!isStudenAdded) {
                        System.out.println("No student is found for id=" + pointsList.get(0));
                        continue;
                    }
                    // update points
                    currentStudentMap.put("javaCoursePoints", Integer.parseInt(currentStudentMap.get("javaCoursePoints")) + Integer.parseInt(pointsList.get(1)) + "");
                    currentStudentMap.put("dsaCoursePoints", Integer.parseInt(currentStudentMap.get("dsaCoursePoints")) + Integer.parseInt(pointsList.get(2)) + "");
                    currentStudentMap.put("dbCoursePoints", Integer.parseInt(currentStudentMap.get("dbCoursePoints")) + Integer.parseInt(pointsList.get(3)) + "");
                    currentStudentMap.put("springCoursePoints", Integer.parseInt(currentStudentMap.get("springCoursePoints")) + Integer.parseInt(pointsList.get(4)) + "");
                    System.out.println("Points updated");
                }
            } else if ("find".equals(input)) {                // Find block
                System.out.println("Enter an id or 'back' to return:");
                String findID = "";
                int findIDInt;
                while (true) {
                    findID = scanner.nextLine().strip();


                    if ("back".equals(findID)) {
                        scanner.nextLine();
                        System.out.println("Enter 'exit' to exit the program");
                        break;
                    }

                    // is student added?
                    boolean isStudenAdded = false;
                    for (Map<String, String> studentsInfo : studentsList) {
                        if (Integer.parseInt(studentsInfo.get("id")) == (Integer.parseInt(findID))) {
                            isStudenAdded = true;
                            currentStudentMap = studentsInfo;
                        }
                    }
                    if (isStudenAdded) {
                        System.out.printf("%s points: Java=%s; DSA=%s; Databases=%s; Spring=%s\n",
                                Integer.parseInt(findID),
                                currentStudentMap.get("javaCoursePoints"),
                                currentStudentMap.get("dsaCoursePoints"),
                                currentStudentMap.get("dbCoursePoints"),
                                currentStudentMap.get("springCoursePoints"));
                        continue;
                    } else {
                        System.out.printf("No student is found for id=%s.\n", findID);
                        continue;
                    }
                }
            } else {
                System.out.println("Error: unknown command!");
            }
        } while (true);
    }
}

