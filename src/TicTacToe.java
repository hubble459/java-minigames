import java.util.Scanner;
class TicTacToe {
    private static String[] board = {"1", "2", "3", "4", "5", "6", "7", "8", "9"};
    private static int input;
    private static Scanner sc;

    void start() {
        sc = new Scanner(System.in);
        String player = "X";
        String playerName;
        while(!win()) {
            printBoard();
            System.out.print("Pick a number (1-9): ");
            input = sc.nextInt() - 1;
            validation();
            board[input] = player;
            if (player.equals("X")) {
                player = "O";
                playerName = "one";
            } else {
                player = "X";
                playerName = "two";
            }
            if (win()) {
                printBoard();
                System.out.println("Player " + playerName + " is the winner!!");
            }
        }
    }

    private static void printBoard() {
        System.out.println();
        System.out.println(board[0] + " | " + board[1] + " | " + board[2]);
        System.out.println("----------");
        System.out.println(board[3] + " | " + board[4] + " | " + board[5]);
        System.out.println("----------");
        System.out.println(board[6] + " | " + board[7] + " | " + board[8]);
        System.out.println();
    }

    private static boolean win() {
        boolean win1 = (board[0].equals(board[3]) && board[0].equals(board[6]));
        boolean win2 = (board[1].equals(board[4]) && board[1].equals(board[7]));
        boolean win3 = (board[2].equals(board[5]) && board[2].equals(board[8]));
        boolean win4 = (board[0].equals(board[1]) && board[0].equals(board[2]));
        boolean win5 = (board[3].equals(board[4]) && board[3].equals(board[5]));
        boolean win6 = (board[6].equals(board[7]) && board[6].equals(board[8]));
        boolean win7 = (board[0].equals(board[4]) && board[0].equals(board[8]));
        boolean win8 = (board[2].equals(board[4]) && board[2].equals(board[6]));
        return win1 || win2 || win3 || win4 || win5 || win6 || win7 || win8;
    }

    private static void validation() {
        while ((input < 0) || (input > 8)) {
            System.out.print("Value out of range. Pick again (1-9): ");
            input = sc.nextInt() - 1;
        }
        while (board[input].equals("X") || board[input].equals("O")) {
            System.out.print("This spot is already taken. Pick again (1-9): ");
            input = sc.nextInt() - 1;
        }
    }
}
