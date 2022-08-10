import java.util.Scanner;

public class Main {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String input = "";
        System.out.println("Mini Games! Type" + ANSI_YELLOW + " help " + ANSI_RESET + "for help.");
        while (!input.equals("exit")) {
            System.out.print(ANSI_CYAN + "/~: " + ANSI_RESET);
            input = sc.nextLine().toLowerCase();
            switch (input) {
                case "fourinarow":
                case "fiar":
                    new FourRow().start();
                    break;
                case "tictactoe":
                case "ttt":
                    new TicTacToe().start();
                    break;
                case "wordsnake":
                    new WordChain().start();
                    break;
                case "hangman":
                    new Hangman().start();
                    break;
                case "help":
                    new Main().help();
                    break;

                default:
                    System.err.println(input + " is not a command!");
                    break;
                case "exit":
                    System.out.println("Exiting...");
                    break;
            }
        }
    }

    protected void help() {
        System.out.println("+==+==+==+==+ help +==+==+==+==+");
        System.out.println("Command        |   Explanation");
        System.out.println("+==+==+==+==+==+==+===+==+==+==+");
        System.out.println("fiar           |   Four in a Row");
        System.out.println("ttt            |   TicTacToe");
        System.out.println("wordsnake      |   Word Snake/Chain");
        System.out.println("hangman        |   Hangman");
        System.out.println("+==+==+==+==+==+==+===+==+==+==+");
    }
}