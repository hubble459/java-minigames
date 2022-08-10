import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

class Hangman {
    private String[] guessArray;
    private String fileName;
    private String randomWord;
    private String misses = "";
    private String hp = "HP: ❤❤❤❤❤❤❤❤❤";
    public void start() {
        boolean exit = false;

        chooseLanguage();
        String[] words = processWordList(fileName);
        randomWord = words[(int) (Math.random()*words.length)];

        guessArray = new String[randomWord.length()];
        for (int i = 0; i < randomWord.length(); i++) {
            guessArray[i] = "_ ";
        }

        System.out.println("Type \"*\" to guess the word. (if wrong -3 hp)");

        long startTime = System.currentTimeMillis();

        while (!exit) {
            String guessString = Arrays.toString(guessArray).replace(", ", "");
            guessString = guessString.replace("[", "");
            guessString = guessString.replace("]", "");

            System.out.println();
            System.out.println(hp);
            System.out.println("Word: " + guessString);
            System.out.println("Misses: " + misses);

            if (hp.length() == 4 || !guessString.contains("_")) {
                exit = true;
                System.out.println(randomWord);
            } else {
                System.out.print("Guess: ");
                char input = playerGuess();
                checkInput(input);
            }
        }
        System.out.println();
        long endTime = System.currentTimeMillis();
        int seconds = (int) ((endTime - startTime)/1000);
        System.out.println("Time: " + (seconds/60) + " minute(s) and " + (seconds % 60) + " seconds");
        System.out.println("Game over!");
    }

    private void checkInput(char input) {
        Scanner sc = new Scanner(System.in);
        int counter = 0;
        if (input == '*') {
            System.out.print("Guess the whole word: ");
            String in = sc.nextLine();
            if (in.equals(randomWord)) {
                System.out.println("You guessed the word correctly!");
                for (int i = 0; i < randomWord.length(); i++) {
                    guessArray[i] = randomWord.charAt(i) + " ";
                }
            } else {
                System.out.println("WONG! 你大便");
                hp = hp.replaceFirst("❤❤❤", "");
            }
            counter = 1;
        }

        for (int i = 0; i < randomWord.length(); i++) {
            if (input == randomWord.charAt(i)) {
                guessArray[i] = input + " ";
                counter++;
            }
        }

        if (counter == 0) {
            hp = hp.replaceFirst("❤", "");
            misses = misses + input + ", ";
        }


    }

    private char playerGuess() {
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine().toLowerCase();
        char inputChar = input.charAt(0);
        while ((inputChar < 'a' || inputChar > 'z') && inputChar != '*') {
            System.out.print("Value out of bounds, try again: ");
            input = sc.nextLine().toLowerCase();
            inputChar = input.charAt(0);
        }
        return inputChar;
    }

    private int languageInput() {
        Scanner sc = new Scanner(System.in);
        int input = sc.nextInt();
        while (input < 1 || input > 2) {
            System.out.println("Value out of bounds, try again: ");
            input = sc.nextInt();
        }
        return input;
    }

    private void chooseLanguage() {
        System.out.println("1) English");
        System.out.println("2) Dutch");
        System.out.print("Choose language: ");
        int language = languageInput();
        fileName = System.getProperty("user.dir") + "\\src\\Files\\";

        if (language == 1) {
            fileName += "wordsEnglish.txt";
        } else {
            fileName += "wordsDutch.txt";
        }
    }

    private String[] processWordList(String fileName) {
        ArrayList<String> words = new ArrayList<>();
        try {
            Scanner sc = new Scanner(new File(fileName));
            while (sc.hasNextLine()) {
                words.add(sc.nextLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return words.toArray(new String[0]);
    }
}