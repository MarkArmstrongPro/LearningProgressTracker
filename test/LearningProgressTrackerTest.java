import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.exception.outcomes.WrongAnswer;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.hyperskill.hstest.testing.expect.Expectation.expect;

public class LearningProgressTrackerTest extends StageTest<String> {
    private static final Random rnd = new Random();

    @DynamicTest(order = 1)
    CheckResult testStartAndExit() {
        TestedProgram main = new TestedProgram();
        String output = main.start();
        expect(output).toContain(1).lines();
        if (incorrectString(output, "Learning Progress Tracker")) {
            return CheckResult.wrong("When started, your program " +
                    "should print \"Learning Progress Tracker\"");
        }

        if (!main.isWaitingInput()) {
            return CheckResult.wrong("After the start, your program should " +
                    "be ready to accept commands from the user");
        }

        output = main.execute("back");
        expect(output).toContain(1).lines();
        if (!main.isWaitingInput()) {
            return CheckResult.wrong("Your program should keep running after the 'back' " +
                    "command is entered");
        }

        if (anyMissingKeywords(output, "enter", "exit", "program")) {
            return CheckResult.wrong("When 'back' command is entered your program " +
                    "should print the hint \"Enter 'exit' to exit the program.\"");
        }

        output = main.execute("exit");
        expect(output).toContain(1).lines();
        if (anyMissingKeywords(output, "bye")) {
            return CheckResult.wrong("When the 'exit' command is entered, " +
                    "your program should say bye to the user");
        }

        if (!main.isFinished()) {
            return CheckResult.wrong("After the 'exit' command has been entered, " +
                    "your program should stop working");
        }

        return CheckResult.correct();
    }

    @DynamicTest(order = 2, data = "getBlankInput")
    CheckResult testBlankInput(String input) {
        TestedProgram main = new TestedProgram();
        main.start();

        String output = main.execute(input);
        expect(output).toContain(1).lines();
        if (incorrectString(output, "no input")) {
            return CheckResult.wrong("When the user enters an empty or blank " +
                    "string, your program should print \"No input.\"");
        }

        return CheckResult.correct();
    }

    @DynamicTest(order = 3, data = "getUnknownCommands")
    CheckResult testUnknownCommands(String input) {
        TestedProgram main = new TestedProgram();
        main.start();

        String output = main.execute(input);
        expect(output).toContain(1).lines();
        if (anyMissingKeywords(output, "unknown", "command")) {
            return CheckResult.wrong("When an unknown command is entered, your " +
                    "program should display an error message: \"Unknown command!\"");
        }

        return CheckResult.correct();
    }

    @DynamicTest(order = 4)
    CheckResult testAddStudents1() {
        TestedProgram main = new TestedProgram();
        main.start();

        String output = main.execute("add students");
        expect(output).toContain(1).lines();
        if (anyMissingKeywords(output,
                "enter", "student", "credentials", "back", "return")) {
            return CheckResult.wrong("When 'add students' command is entered, your " +
                    "program should display the prompt \"Enter student credentials or " +
                    "'back' to return:\"");
        }

        output = main.execute("exit");
        expect(output).toContain(1).lines();
        if (anyMissingKeywords(output, "incorrect", "credentials")) {
            return CheckResult.wrong("Expected output: \"Incorrect credentials.\", " +
                    "but your output was: " + output);
        }

        output = main.execute("back");
        expect(output).toContain(1).lines();
        if (anyMissingKeywords(output, "total", "0", "students", "added")) {
            return CheckResult.wrong("Expected: \"Total 0 students have been added.\", but " +
                    "your output was: " + output);
        }

        output = main.execute("back");
        expect(output).toContain(1).lines();
        if (anyMissingKeywords(output, "enter", "exit", "program")) {
            return CheckResult.wrong("When 'back' command is entered your program " +
                    "should stop waiting for student credentials");
        }

        output = main.execute("exit");
        expect(output).toContain(1).lines();
        if (anyMissingKeywords(output, "bye")) {
            return CheckResult.wrong("When the 'exit' command is entered, " +
                    "your program should say bye to the user");
        }

        if (!main.isFinished()) {
            return CheckResult.wrong("After the 'exit' command has been entered, " +
                    "your program should stop working");
        }

        return CheckResult.correct();
    }

    @DynamicTest(order = 5)
    CheckResult testAddStudents2() {
        TestedProgram main = new TestedProgram();
        main.start();

        main.execute("add students");
        for (String input : getCorrectCredentials()) {
            String output = main.execute(input);
            expect(output).toContain(1).lines();
            if (anyMissingKeywords(output, "student", "added")) {
                return CheckResult.wrong("Expected output: \"The student has been added.\", but your " +
                        "output was: " + output);
            }
        }

        String output = main.execute("back");
        expect(output).toContain(1).lines();
        if (anyMissingKeywords(output, "total", "10", "students", "added")) {
            return CheckResult.wrong("Expected: \"Total 10 students have been added.\", but " +
                    "your output was: " + output);
        }

        return CheckResult.correct();
    }

    @DynamicTest(order = 6)
    CheckResult testAddStudents3() {
        TestedProgram main = new TestedProgram();
        main.start();
        main.execute("add students");

        for (String[] args : getIncorrectCredentials()) {
            String output = main.execute(args[0]);
            expect(output).toContain(1).lines();
            if (incorrectString(output, args[1])) {
                return CheckResult.wrong("Expected output: \"" + args[1] + "\", but your " +
                        "output was: " + output);
            }
        }

        String output = main.execute("back");
        expect(output).toContain(1).lines();
        if (anyMissingKeywords(output, "total", "0", "students", "added")) {
            return CheckResult.wrong("Expected: \"Total 0 students have been added.\", but " +
                    "your output was: " + output);
        }

        return CheckResult.correct();
    }

    @DynamicTest(order = 7)
    CheckResult testFindAll1() {
        TestedProgram main = new TestedProgram();
        main.start();
        main.execute("add students");
        main.execute("back");

        String output = main.execute("list");
        expect(output).toContain(1).lines();
        if (anyMissingKeywords(output, "no", "found")) {
            return CheckResult.wrong("Expected: \"No students found.\", but " +
                    "your output was: " + output);
        }

        return CheckResult.correct();
    }

    @DynamicTest(order = 8)
    CheckResult testAddDoubles() {
        TestedProgram main = new TestedProgram();
        main.start();
        main.execute("add students");

        String[] credentials = getRandomCredentials(12);
        for (String arg : credentials) {
            String output = main.execute(arg);
            expect(output).toContain(1).lines();
            if (anyMissingKeywords(output, "student", "added")) {
                return CheckResult.wrong("Expected output: \"The student has been added.\", but your " +
                        "output was: " + output);
            }

            output = main.execute(arg);
            expect(output).toContain(1).lines();
            if (anyMissingKeywords(output, "this", "email", "already", "taken")) {
                return CheckResult.wrong("Expected output: \"This email is already taken.\", but your " +
                        "output was: " + output);
            }
        }

        String output = main.execute("back");
        expect(output).toContain(1).lines();
        if (anyMissingKeywords(output, "total", String.valueOf(credentials.length), "students", "added")) {
            return CheckResult.wrong("Expected: \"Total " + credentials.length + "students have been added.\", but " +
                    "your output was: " + output);
        }

        return CheckResult.correct();
    }

    @DynamicTest(order = 9)
    CheckResult testFindAll2() {
        TestedProgram main = new TestedProgram();
        main.start();
        main.execute("add students");

        String[] credentials = getRandomCredentials(12);
        for (String arg : credentials) {
            String output = main.execute(arg);
            expect(output).toContain(1).lines();
            if (anyMissingKeywords(output, "student", "added")) {
                main.stop();
                return CheckResult.wrong("Expected output: \"The student has been added.\", but your " +
                        "output was: " + output);
            }
        }

        String output = main.execute("back");
        expect(output).toContain(1).lines();
        if (anyMissingKeywords(output, "total", String.valueOf(credentials.length), "students", "added")) {
            return CheckResult.wrong("Expected: \"Total " + credentials.length + "students have been added.\", but " +
                    "your output was: " + output);
        }

        output = main.execute("list");
        List<String> lines = expect(output).toContain(credentials.length + 1).lines();
        if (!lines.get(0).toLowerCase().contains("students")) {
            return CheckResult.wrong("Expected the header \"Students:\" but your first line was: " + lines.get(0));
        }

        List<String> ids = parseIds(lines);
        Set<String> uniqueIds = new HashSet<>(ids);
        if (uniqueIds.size() != ids.size()) {
            return CheckResult.wrong("Expected " + ids.size() +
                    " unique IDs but found only " + uniqueIds.size());
        }

        return CheckResult.correct();
    }

    @DynamicTest(order = 10)
    CheckResult testBackFromAddPoints() {
        TestedProgram main = new TestedProgram();
        main.start();

        String output = main.execute("add points");
        expect(output).toContain().lines();
        if (anyMissingKeywords(output, "enter", "id", "points", "back", "return")) {
            return CheckResult.wrong("When 'add points' command is entered, your program should print " +
                    "\"Enter an id and points or 'back' to return:\" but your output was: " + output);
        }

        main.execute("back");
        output = main.execute("back");
        expect(output).toContain(1).lines();
        if (anyMissingKeywords(output, "enter", "exit", "program")) {
            return CheckResult.wrong("When 'back' command is entered your program " +
                    "should stop waiting for student id and points");
        }

        output = main.execute("exit");
        expect(output).toContain(1).lines();
        if (anyMissingKeywords(output, "bye")) {
            return CheckResult.wrong("When the 'exit' command is entered, " +
                    "your program should say bye to the user");
        }

        if (!main.isFinished()) {
            return CheckResult.wrong("After the 'exit' command has been entered, " +
                    "your program should stop working");
        }

        return CheckResult.correct();
    }

    @DynamicTest(order = 11)
    CheckResult testStudentPoints1() {
        TestedProgram main = new TestedProgram();
        main.start();
        main.execute("add students");

        String[] credentials = getRandomCredentials(6);
        for (String arg : credentials) {
            main.execute(arg);
        }

        main.execute("back");
        String output = main.execute("list");
        List<String> lines = expect(output).toContain().lines();
        List<String> ids = parseIds(lines);

        main.execute("add points");
        String[] points = getIncorrectPoints();
        for (String point : points) {
            output = main.execute(ids.get(0) + " " + point);
            expect(output).toContain(1).lines();
            if (anyMissingKeywords(output, "incorrect", "points", "format")) {
                return CheckResult.wrong("Expected output: \"Incorrect points format.\", " +
                        "but your output was: " + output);
            }
        }

        output = main.execute("imsurethereisnosuchstudentid 1 1 1 1");
        expect(output).toContain().lines();
        if (anyMissingKeywords(output, "no", "student", "found") ||
                !output.contains("imsurethereisnosuchstudentid")) {
            return CheckResult.wrong("Expected output was: \"No student is found " +
                    "for id=imsurethereisnosuchstudentid\", but your output was: " + output);
        }

        return CheckResult.correct();
    }

    @DynamicTest(order = 12)
    CheckResult testStudentPoints2() {
        TestedProgram main = new TestedProgram();
        main.start();
        main.execute("add students");

        String[] credentials = getRandomCredentials(6);
        for (String arg : credentials) {
            main.execute(arg);
        }
        main.execute("back");

        String output = main.execute("list");
        List<String> lines = expect(output).toContain().lines();
        List<String> ids = parseIds(lines);

        main.execute("add points");
        String[] points = getCorrectPoints(6);

        for (int i = 0; i < points.length; i++) {
            output = main.execute(ids.get(i) + " " + points[i]);
            expect(output).toContain(1).lines();
            if (anyMissingKeywords(output, "points", "updated")) {
                return CheckResult.wrong("Expected \"Points updated.\" but your output was " + output);
            }
        }

        return CheckResult.correct();
    }

    @DynamicTest(order = 13)
    CheckResult testBackFromFind() {
        TestedProgram main = new TestedProgram();
        main.start();

        String output = main.execute("find");
        expect(output).toContain(1).lines();
        if (anyMissingKeywords(output, "enter", "id", "back", "return")) {
            return CheckResult.wrong("When 'find' command is entered, you program should " +
                    "print \"Enter an id or 'back' to return:\", but your output was: " + output);
        }

        main.execute("back");
        output = main.execute("back");
        expect(output).toContain(1).lines();
        if (anyMissingKeywords(output, "enter", "exit", "program")) {
            return CheckResult.wrong("When 'back' command is entered your program " +
                    "should stop waiting for student id");
        }

        output = main.execute("exit");
        expect(output).toContain(1).lines();
        if (anyMissingKeywords(output, "bye")) {
            return CheckResult.wrong("When the 'exit' command is entered, " +
                    "your program should say bye to the user");
        }

        if (!main.isFinished()) {
            return CheckResult.wrong("After the 'exit' command has been entered, " +
                    "your program should stop working");
        }

        return CheckResult.correct();
    }

    @DynamicTest(order = 14)
    CheckResult testFindByID() {
        TestedProgram main = new TestedProgram();
        main.start();
        main.execute("add students");

        String[] credentials = getRandomCredentials(5);
        for (String arg : credentials) {
            main.execute(arg);
        }
        main.execute("back");

        String output = main.execute("list");
        List<String> lines = expect(output).toContain().lines();
        List<String> ids = parseIds(lines);

        main.execute("add points");
        for (int i = 0; i < ids.size(); i++) {
            main.execute(String.format("%s %d %d %d %d", ids.get(i), i, i, i, i));
            main.execute(String.format("%s %d %d %d %d", ids.get(i), i, i, i, i));
        }

        main.execute("back");
        output = main.execute("find");
        expect(output).toContain(1).lines();
        if (anyMissingKeywords(output, "enter", "id", "back", "return")) {
            return CheckResult.wrong("When 'find' command is entered, you program should " +
                    "print \"Enter an id or 'back' to return:\", but your output was: " + output);
        }

        for (int i = 0; i < ids.size(); i++) {
            output = main.execute(ids.get(i));
            expect(output).toContain(1).lines();
            String expected = String.format(
                    "%s points: Java=%d; DSA=%d; Databases=%d; Spring=%d",
                    ids.get(i), i * 2, i * 2, i * 2, i * 2
            );
            if (incorrectString(output, expected)) {
                return CheckResult.wrong("Expected output: " + expected +
                        ", but your output was: " + output);
            }
        }

        return CheckResult.correct();
    }

    private boolean anyMissingKeywords(String output, String... keywords) {
        List<String> tokens = Arrays.asList(
                output.trim().toLowerCase().split("\\W+")
        );

        return !tokens.containsAll(Arrays.stream(keywords)
                .map(String::toLowerCase)
                .collect(Collectors.toList()));
    }

    private boolean incorrectString(String output, String expected) {
        String normalizedOutput = output.replaceAll("\\W+", "").toLowerCase();
        String normalizedModel = expected.replaceAll("\\W+", "").toLowerCase();

        return !normalizedOutput.contains(normalizedModel);
    }

    private String[] getBlankInput() {
        return new String[]{"", "  ", "\t", " \t"};
    }

    private String[] getUnknownCommands() {
        return new String[]{"abc", "quit", "  brexit ", "exi  t", "?", "break",
                "-exit", "Ctrl+C", "exit please", ":q"};
    }

    private String[] getCorrectCredentials() {
        return new String[]{"John Smith jsmith@hotmail.com", "Anny Doolittle anny.md@mail.edu",
                "Jean-Claude O'Connor jcda123@google.net", "Mary Emelianenko 125367at@zzz90.z9",
                "Al Owen u15da125@a1s2f4f7.a1c2c5s4", "Robert Jemison Van de Graaff robertvdgraaff@mit.edu",
                "Ed Eden a1@a1.a1", "na'me s-u ii@ii.ii", "n'a me su aa-b'b ab@ab.ab", "nA me 1@1.1"};
    }

    private String[][] getIncorrectCredentials() {
        return new String[][]{
                {"", "Incorrect credentials"}, {" \t", "Incorrect credentials."},
                {"name surname", "Incorrect credentials."},
                {"n surname email@email.xyz", "Incorrect first name."},
                {"'name surname email@email.xyz", "Incorrect first name."},
                {"-name surname email@email.xyz", "Incorrect first name."},
                {"name- surname email@email.xyz", "Incorrect first name."},
                {"name' surname email@email.xyz", "Incorrect first name."},
                {"nam-'e surname email@email.xyz", "Incorrect first name."},
                {"na'-me surname email@email.xyz", "Incorrect first name."},
                {"na--me surname email@email.xyz", "Incorrect first name."},
                {"na''me surname email@email.xyz", "Incorrect first name."},
                {"námé surname email@email.xyz", "Incorrect first name."},
                {"name s email@email.xyz", "Incorrect last name."},
                {"name -surname email@email.xyz", "Incorrect last name."},
                {"name 'surname email@email.xyz", "Incorrect last name."},
                {"name surnam''e email@email.xyz", "Incorrect last name."},
                {"name surn--ame email@email.xyz", "Incorrect last name."},
                {"name s'-urname email@email.xyz", "Incorrect last name."},
                {"name su-'rname email@email.xyz", "Incorrect last name."},
                {"name surname- email@email.xyz", "Incorrect last name."},
                {"name surname' email@email.xyz", "Incorrect last name."},
                {"name surnámé email@email.xyz", "Incorrect last name."},
                {"name surname emailemail.xyz", "Incorrect email."},
                {"name surname email@emailxyz", "Incorrect email."},
                {"name surname email@e@mail.xyz", "Incorrect email."},
        };
    }

    private String[] getIncorrectPoints() {
        return new String[]{"", "-1 1 1 1", "1 1 2 A", "1 1 1", "1 1 1 1 1"};
    }

    private String[] getCorrectPoints(int n) {
        return Stream.generate(String::new)
                .limit(n)
                .map(it -> String.format("%d %d %d %d", nextPoint(), nextPoint(), nextPoint(), nextPoint()))
                .toArray(String[]::new);
    }

    private int nextPoint() {
        return rnd.nextInt(10) + 1;
    }

    private List<String> parseIds(List<String> lines) {
        try {
            return lines.stream()
                    .skip(1)
                    .map(String::trim)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new WrongAnswer("Error occurred while parsing your output " + e.getMessage());
        }
    }

    private List<String> generateNames(int n) {
        List<String> names = List.of("Shoshana Utica", "Marisa Firman", "Gwenette Anagnos", "Charlena Girardo",
                "Alexina Belcher", "Karee Antoinetta", "Dolley Panther", "Elysha Quinlan", "Trixie Winer",
                "Ricki Trovillion", "Amye Uriisa", "Hedwig Wally", "Gwenette Kironde", "Jermaine Naaman",
                "Olga Rosanne", "Annecorinne Ause", "Aurie Dorisa", "Van Fawnia", "Carmella Campman",
                "Francesca Francis", "Elwira Florrie", "Nonna Miko", "Natka Herculie", "Roxi Hett", "Brandise Hardan",
                "Toby Bleier", "Dalia Gleeson", "Emelia Annnora", "Beatrisa Jegar", "Barbara-Anne Chicky",
                "Ann Agnella", "Lebbie Alabaster", "Leola Whelan", "Starlin Griz", "Anjanette Uis", "Tasha Chem");

        List<String> selectedNames = new ArrayList<>(names);
        Collections.shuffle(selectedNames);
        return selectedNames.stream().limit(n).collect(Collectors.toList());
    }

    private List<String> generateEmails(int n) {
        return IntStream.rangeClosed(1, n).mapToObj(it -> "address" + it + "@mail.com").collect(Collectors.toList());
    }

    private String[] getRandomCredentials(int n) {
        List<String> names = generateNames(n);
        List<String> emails = generateEmails(n);
        return IntStream.range(0, n)
                .mapToObj(it -> String.format("%s %s", names.get(it), emails.get(it)))
                .toArray(String[]::new);
    }
}
