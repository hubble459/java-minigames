import java.util.Scanner;
class FourRow {
    private String[] board = new String[42];
    private Scanner sc = new Scanner(System.in);

    void start() {
        for (int i = 0; i < 42; i++) board[i] = "[   ]";
        System.out.println("Four in a Row");
        System.out.println("Type \"0\" to reset");
        boolean running = true;
        int playerCounter = 0;
        while (running) {
            String player = playerCounter % 2 == 0 ? "X" : "O";
            printBoard();
            if (checkIfWon()) {
                player = playerCounter % 2 != 0 ? "X" : "O";
                System.out.println("Player " + player + " has won!");
                running = false;
            } else if (playerCounter == 42) {
                running = false;
            } else {
                System.out.print("Player "+player+": ");
                int placement = placement();
                if (placement == -1) {
                    System.out.println("Resetting...");
                    for (int i = 0; i < 42; i++) board[i] = "[   ]";
                    playerCounter = -1;
                } else if (placement == -2) {
                    System.err.println("This is not a valid option");
                    playerCounter--;
                } else {
                    board[placement] = "[ "+player+" ]";
                }
                System.out.println();
            }
            playerCounter++;
        }
        System.out.println();
        System.out.println("Game over!");
    }

    private boolean checkIfWon() {
        for (int i = 0; i < 42; i += 7) {
            for (int j = i; j < i + 4; j++) {
                int k = j;
                if ((board[k++].equals(board[k]) && board[k++].equals(board[k]) && board[k++].equals(board[k])) && !board[k].equals("[   ]"))
                    return true;
            }
        }
        int[] vertical = {0,7,14,21,28,35};
        for (int i = 0; i < 7; i++) {
            int k = 0;
            for (int j = 0; j < 3; j++) {
                if ((board[i+vertical[k]].equals(board[i+vertical[k+1]]) && board[i+vertical[k+1]].equals(board[i+vertical[k+2]]) && board[i+vertical[k+2]].equals(board[i+vertical[k+3]])) && !board[i+vertical[k+3]].equals("[   ]"))
                    return true;
                k++;
            }
        }
        int[] diagonal = {14,15,16,17};
        for (int j = 0; j < 4; j++) {
            for (int i = j; i <= diagonal[j]; i+=7) {
                if ((board[i].equals(board[i+8]) && board[i+8].equals(board[i+16]) && board[i+16].equals(board[i+24])) && !board[i+24].equals("[   ]"))
                    return true;
            }
        }
        int[] diagonal2 = {20,19,18,17};
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j <= diagonal2[i]; j+=7) {
                if ((board[6+j-i].equals(board[12+j-i]) && board[12+j-i].equals(board[18+j-i]) && board[18+j-i].equals(board[24+j-i])) && !board[24+j-i].equals("[   ]"))
                    return true;
            }
        }
        return false;
    }

    private void printBoard() {
        System.out.println("[ 1 ][ 2 ][ 3 ][ 4 ][ 5 ][ 6 ][ 7 ]");
        for (int i = 0; i < 42; i++) {
            System.out.println(board[i++] + board[i++] + board[i++] + board[i++] + board[i++] + board[i++] + board[i]);
        }
    }

    private int placement() {
        int input = sc.nextInt() - 1;
        if (input == -1) return -1;
        if (input < 0 || input > 6) return -2;
        int j = input + 35;
        while (!board[j].equals("[   ]")) {
            if (j == input) return -1;
            j -= 7;
        }
        return j;
    }
}
