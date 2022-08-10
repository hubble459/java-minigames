import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class FourRowGUI implements ActionListener {
    public static void main(String[] args) {
        new FourRowGUI();
    }

    private JFrame f;
    private JPanel b;

    private int[] board = new int[42];
    private int[] winButtons;
    private int win;
    private boolean playerOne = true;
    private boolean bot;
    private boolean verbose = true;

    public FourRowGUI() {
        // Board
        //  0  1  2  3  4  5  6
        //  7  8  9 10 11 12 13
        // 14 15 16 17 18 19 20
        // 21 22 23 24 25 26 27
        // 28 29 30 31 32 33 34
        // 35 36 37 38 39 40 41

        // Game
        for (int i = 0; i < 42; i++) board[i] = i;

        f = new JFrame("Four in a Row");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JMenuBar mb = new JMenuBar();
        JMenu m = new JMenu("Options");
        JMenuItem mi = new JMenuItem("Enable Bot");
        mi.addActionListener(this);
        mi.setActionCommand("bot");
        m.add(mi);
        mb.add(m);
        f.setJMenuBar(mb);

        JPanel p = new JPanel(new GridLayout(2, 0));
        p.setPreferredSize(new Dimension(600, 40));

        JLabel l = new JLabel("Four in a Row", SwingConstants.CENTER);
        p.add(l);

        JButton reset = new JButton("Reset");
        reset.addActionListener(this);
        p.add(reset);

        b = new JPanel(new GridLayout(6, 7));
        b.setPreferredSize(new Dimension(600, 300));
        for (int i = 0; i < 42; i++) {
            JButton b = new JButton("");
            b.setActionCommand("" + i);
            b.addActionListener(this);
            b.setBackground(Color.DARK_GRAY);
            b.setForeground(Color.WHITE);
            this.b.add(b);
        }

        f.add(p, BorderLayout.PAGE_START);
        f.add(b);
        f.pack();
        f.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Reset")) {
            reset();
        } else if (e.getActionCommand().equals("bot")) {
            bot = !bot;
            if (!bot) ((JMenuItem) e.getSource()).setText("Enable Bot");
            if (bot) ((JMenuItem) e.getSource()).setText("Disable Bot");
        } else {
            JButton button = (JButton) e.getSource();
            int butNum = Integer.parseInt(button.getActionCommand());
            String fontStop = ">●</font></html>";
            String c = "<html><font size='10' color=" + (playerOne ? "blue" : "red") + fontStop;
            int placement = placement(butNum, board);
            if (button.getText().equals("") && placement != -1) {
                ((JButton) b.getComponent(placement)).setText(c);
                board[placement] = (playerOne ? -1 : -2);
                if (!won(board)) playerOne = !playerOne;
                if (bot && !playerOne) {
                    c = "<html><font size='10' color=red" + fontStop;
                    placement = placement(getBestMove(), board);
                    ((JButton) b.getComponent(placement)).setText(c);
                    board[placement] = -2;
                    if (!won(board)) playerOne = !playerOne;
                }
            } else {
                JOptionPane.showMessageDialog(f, "bruh");
            }
        }

        if (won(board)) {
            for (int winButton : winButtons) {
                b.getComponent(winButton).setBackground(Color.PINK);
            }
            if (playerOne) ;
            else ;
            int result = JOptionPane.showConfirmDialog(f, "Player " + (playerOne ? "One" : "Two") + " WON!\n Play again?", "Game Over!", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                reset();
            } else {
                System.exit(0);
            }
        }

        if (draw(board)) {
            int result = JOptionPane.showConfirmDialog(f, "It's a draw...\n Play again?", "Game Over!", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                reset();
                playerOne = true;
            } else {
                System.exit(0);
            }
        }
    }

    private boolean draw(int[] board) {
        for (int i = 0; i < 9; i++) {
            if (board[i] == i) {
                return false;
            }
        }
        return true;
    }

    private boolean won(int[] board) {
        for (int i = 0; i < 42; i += 7) {
            for (int j = i; j < i + 4; j++) {
                if ((board[j] == board[j + 1] && board[j + 1] == board[j + 2] && board[j + 2] == board[j + 3])) {
                    win = board[j];
                    winButtons = new int[]{j, j + 1, j + 2, j + 3};
                    return true;
                }
            }
        }
        int[] vertical = {0, 7, 14, 21, 28, 35};
        for (int i = 0; i < 7; i++) {
            int k = 0;
            for (int j = 0; j < 3; j++) {
                if ((board[i + vertical[k]] == board[i + vertical[k + 1]] && board[i + vertical[k + 1]] == board[i + vertical[k + 2]] && board[i + vertical[k + 2]] == board[i + vertical[k + 3]])) {
                    win = board[i + vertical[k]];
                    winButtons = new int[]{i + vertical[k], i + vertical[k + 1], i + vertical[k + 2], i + vertical[k + 3]};
                    return true;
                }
                k++;
            }
        }
        int[] diagonal = {14, 15, 16, 17};
        for (int j = 0; j < 4; j++) {
            for (int i = j; i <= diagonal[j]; i += 7) {
                if ((board[i] == board[i + 8] && board[i + 8] == board[i + 16] && board[i + 16] == board[i + 24])) {
                    win = board[i];
                    winButtons = new int[]{i, i + 8, i + 16, i + 24};
                    return true;
                }
            }
        }
        int[] diagonal2 = {20, 19, 18, 17};
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j <= diagonal2[i]; j += 7) {
                if ((board[6 + j - i] == board[12 + j - i] && board[12 + j - i] == board[18 + j - i] && board[18 + j - i] == board[24 + j - i])) {
                    win = board[6 + j - i];
                    winButtons = new int[]{6 + j - i, 12 + j - i, 18 + j - i, 24 + j - i};
                    return true;
                }
            }
        }
        return false;
    }

    private int getBestMove() {
        int move = -1;

        for (int i = 0; i < 50; i++) {
            int[] botBoard = Arrays.copyOf(board, 42);
            int p1 = bot(botBoard);
            if (p1 == -1) p1 = getRandom(botBoard);
            botBoard[placement(p1, botBoard)] = -2;
            move = p1;
            if (won(botBoard) && win == -2) {
                break;
            }
            if (!draw(botBoard)) {
                int p2 = bot(botBoard);
                if (p2 == -1) p2 = getRandom(botBoard);
                botBoard[placement(p2, botBoard)] = -1;
                if (won(botBoard) && win == -1) {
                    if (verbose) System.out.println("OWO");
                } else if (!draw(botBoard)) {
                    int p3 = bot(botBoard);
                    if (p3 == -1) p3 = getRandom(botBoard);
                    botBoard[placement(p3, botBoard)] = -2;
                    if (won(botBoard) && win == -2) {
                        break;
                    }
                    if (!draw(botBoard)) {
                        int p4 = bot(botBoard);
                        if (p4 == -1) p4 = getRandom(botBoard);
                        botBoard[placement(p4, botBoard)] = -1;
                        if (won(botBoard) && win == -1) {
                            if (verbose) System.out.println("OWO");
                        } else {
                            int p5 = bot(botBoard);
                            if (p5 == -1) p5 = getRandom(botBoard);
                            botBoard[placement(p5, botBoard)] = -2;
                            if (won(botBoard) && win == -2) {
                                break;
                            }
                        }
                    }
                }
            }
        }

        if (verbose) System.out.println("[MOVE2] " + move);
        return move;
    }

    private void reset() {
        for (int i = 0; i < 42; i++) {
            board[i] = i;
            JButton button = (JButton) b.getComponent(i);
            button.setText("");
        }
        for (int i = 0; i < b.getComponentCount(); i++) {
            b.getComponent(i).setBackground(Color.DARK_GRAY);
        }
        playerOne = true;
    }

    private int placement(int input, int[] board) {
        input %= 7;
        int i = input + 35;
        while (board[i] != i) {
            if (i == input) return -1;
            i -= 7;
        }
        return i;
    }

    private int bot(int[] board) {
        // Horizontal
        for (int i = 0; i < 42; i += 7) {
            for (int j = i; j < i + 4; j++) {
                boolean one = board[j] == board[j + 1];
                boolean two = board[j + 1] == board[j + 2];
                boolean three = board[j + 2] == board[j + 3];
                boolean four = board[j] == board[j + 3];
                if (one == two && one) {    // 1 1 1 0
                    if (j + 3 == placement(j + 3, board)) {
                        if (verbose) System.out.println("[HORIZONTAL] " + (j + 3));
                        return j + 3;
                    }
                }
                if (two == three && two) {  // 0 1 1 1
                    if (j == placement(j, board)) {
                        if (verbose) System.out.println("[HORIZONTAL] " + (j));
                        return j;
                    }
                }
                if (one && four) {  // 1 1 0 1
                    if (j + 2 == placement(j + 2, board)) {
                        if (verbose) System.out.println("[HORIZONTAL] " + (j + 2));
                        return j + 2;
                    }
                }
                if (three && four) { // 1 0 1 1
                    if (j + 1 == placement(j + 1, board)) {
                        if (verbose) System.out.println("[HORIZONTAL] " + (j + 1));
                        return j + 1;
                    }
                }
            }
        }

        // Vertical
        int[] vertical = {0, 7, 14, 21, 28, 35};
        for (int i = 0; i < 7; i++) {
            int k = 0;
            for (int j = 0; j < 4; j++) {
                if ((board[i + vertical[k]] == board[i + vertical[k + 1]] && board[i + vertical[k + 1]] == board[i + vertical[k + 2]])) {
                    if ((i + vertical[k]) - 7 > 0 && board[i + vertical[k] - 7] == ((i + vertical[k]) - 7)) {
                        if (verbose) System.out.println("[VERTICAL] " + ((i + vertical[k]) - 7));
                        return (i + vertical[k]) - 7;
                    }
                }
                k++;
            }
        }

        // Diagonal NORTH_WEST
        int[] diagonal1 = {14, 15, 16, 17};
        for (int j = 0; j < 4; j++) {
            for (int i = j; i <= diagonal1[j]; i += 7) {
                boolean one = board[i] == board[i + 8];
                boolean two = board[i + 8] == board[i + 16];
                boolean three = board[i + 16] == board[i + 24];
                boolean four = board[i] == board[i + 24];
                if (one == two && one) {   // 1 1 1 0 == true
                    if (i + 24 == placement(i + 24, board)) {
                        if (verbose) System.out.println("[DIAGONAL] " + (i + 24));
                        return i + 24;
                    }
                }
                if (two == three && two) { // 0 1 1 1 == true
                    if (i == placement(i, board)) {
                        if (verbose) System.out.println("[DIAGONAL] " + i);
                        return i;
                    }
                }
                if (one && four) {  // 1 1 0 1 == true
                    if (i + 16 == placement(i + 16, board)) {
                        if (verbose) System.out.println("[DIAGONAL] " + (i + 16));
                        return i + 16;
                    }
                }
                if (three && four) {// 1 0 1 1 == true
                    if (i + 8 == placement(i + 8, board)) {
                        if (verbose) System.out.println("[DIAGONAL] " + (i + 8));
                        return i + 8;
                    }
                }
            }
        }

        // Diagonal NORTH_EAST
        int[] diagonal2 = {20, 19, 18, 17};
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j <= diagonal2[i]; j += 7) {
                boolean one = board[6 + j - i] == board[12 + j - i];
                boolean two = board[12 + j - i] == board[18 + j - i];
                boolean three = board[18 + j - i] == board[24 + j - i];
                boolean four = board[6 + j - i] == board[24 + j - i];
                if (one == two && one) {   // 1 1 1 0 == true
                    if (24 + j - i == placement(24 + j - i, board)) {
                        if (verbose) System.out.println("[DIAGONAL] " + (24 + j - i));
                        return 24 + j - i;
                    }
                }
                if (two == three && two) { // 0 1 1 1 == true
                    if (6 + j - i == placement(6 + j - i, board)) {
                        if (verbose) System.out.println("[DIAGONAL] " + (6 + j - i));
                        return 6 + j - i;
                    }
                }
                if (one && four) {  // 1 1 0 1 == true
                    if (18 + j - i == placement(18 + j - i, board)) {
                        if (verbose) System.out.println("[DIAGONAL] " + (18 + j - i));
                        return 18 + j - i;
                    }
                }
                if (three && four) {// 1 0 1 1 == true
                    if (12 + j - i == placement(12 + j - i, board)) {
                        if (verbose) System.out.println("[DIAGONAL] " + (12 + j - i));
                        return 12 + j - i;
                    }
                }
            }
        }

        return -1;
    }

    public int getRandom(int[] board) {
        int random;
        int placement;
        do {
            random = (int) (Math.random() * 7);
            placement = placement(random, board);
        } while (placement == -1);
        return random;
    }
}
