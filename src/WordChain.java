import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class WordChain {
    private Object[] words;
    private String doubles = "";
    private long startTime;
    public void start() {
        Scanner sc = new Scanner(System.in);

        // Setting variables
        boolean exit = false;
        boolean word = false;
        String input = "";
        String[] doubleArray = doubles.split(" ");
        int rand = (int)(Math.random()*369983);
        loadDictionary();

        System.out.println("== Word Snake ==");
        System.out.println("You have 30 seconds to answer");
        System.out.println(words[rand]);
        doubles = doubles + words[rand] + " ";
        char lastChar = words[rand].toString().charAt(words[rand].toString().length()-1);

        while (!exit) {
            startTime = System.currentTimeMillis();
            if (word) {
                lastChar = input.charAt(input.length() - 1);
                char randomChar = (char) ((Math.random() * 25) + 97);
                for (int i = 0; i < 369983; i++) {
                    if (words[i].toString().charAt(0) == lastChar && words[i].toString().charAt(1) == randomChar) {
                        lastChar = words[i].toString().charAt(words[i].toString().length() - 1);
                        doubles += words[i] + " ";
                        break;
                    }
                    randomChar = (char) ((Math.random() * 25) + 97);
                }
            }
            input = sc.nextLine();
            while (validateWord(input)) {
                System.out.println("You have " + (30 - seconds()) + " seconds left");
                if (timeout()) break;
                System.out.print("That's not a word! Try again: ");
                input = sc.nextLine();
            }
            while (input.charAt(0) != lastChar) {
                System.out.println("You have " + (30 - seconds()) + " seconds left");
                if (timeout()) break;
                System.out.println("The first character does not match the last character of the word prior.");
                System.out.print("Try again: ");
                input = sc.nextLine();
            }
            for (String s : doubleArray) {
                while (s.equals(input)) {
                    System.out.println("You have " + (30 - seconds()) + " seconds left");
                    if (timeout()) break;
                    System.out.println("This word has already been used.");
                    System.out.print("Try again: ");
                    input = sc.nextLine();
                }
            }
            doubles += input + " ";
            exit = timeout();
            doubleArray = doubles.split(" ");
            word = true;
        }
    }

    private boolean timeout() {
        if (seconds() > 30) {
            System.out.println("You took too long to answer.");
            return true;
        }
        return false;
    }

    private int seconds() {
        return (int) ((System.currentTimeMillis() - startTime)/1000);
    }

    private boolean validateWord(String input) {
        int counter = 0;
        for (int i = 0; i < 369983; i++) {
            if (words[i].equals(input)) {
                counter++;
                break;
            }
        }
        return counter != 1;
    }

    private void loadDictionary() {
        String filePath = System.getProperty("user.dir") + "\\src\\Files\\EnglishWords.txt";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            words = reader.lines().toArray();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
