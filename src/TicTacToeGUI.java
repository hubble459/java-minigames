import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToeGUI implements ActionListener {
    private static String[] board;
    private JFrame f;
    private JPanel buttons;
    private boolean playerOne = true;

    public TicTacToeGUI() {
        f = new JFrame("TicTacToe");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel title = new JPanel();
        title.setLayout(new FlowLayout(FlowLayout.CENTER));
        title.setBorder(LineBorder.createBlackLineBorder());
        title.setPreferredSize(new Dimension(400,100));
        JLabel l = new JLabel("TicTacToe", JLabel.CENTER);
        l.setFont(new Font(l.getFont().getName(), l.getFont().getStyle(), 30));
        l.setPreferredSize(new Dimension(400,90));
        title.add(l);

        f.add(title, BorderLayout.PAGE_START);
        makeButtons();

        JButton b = new JButton("Reset");
        b.setPreferredSize(new Dimension(400,50));
        b.addActionListener(this);

        f.add(b, BorderLayout.SOUTH);
        f.pack();
        f.setVisible(true);
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

    public void makeButtons() {
        board = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8"};
        buttons = new JPanel();
        buttons.setLayout(new GridLayout(3, 3));
        buttons.setPreferredSize(new Dimension(400,300));
        for (int i = 0; i < 9; i++) {
            JButton button = new JButton("");
            button.setActionCommand(i + "");
            button.addActionListener(this);
            buttons.add(button);
        }
        f.add(buttons);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();
        String c = playerOne ? "X" : "O";
        if (s.equals("Reset")) {
            reset();
            playerOne = true;
        } else {
            JButton button = (JButton) e.getSource();
            if (button.getText().equals("")) {
                board[Integer.parseInt(s)] = c;
                button.setText(c);
                if (!win()) playerOne = !playerOne;
            } else {
                JOptionPane.showMessageDialog(f, "bruh");
            }
        }

        if (win()) {
            int result = JOptionPane.showConfirmDialog(f, "Player " + (playerOne ? "One" : "Two") + " WON!\n Play again?", "Game Over!", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                reset();
            } else {
                System.exit(0);
            }
        }

        boolean draw = true;
        for (int i = 0; i < 9; i++) {
            if (board[i].equals(String.valueOf(i))) {
                draw = false;
                break;
            }
        }

        if (draw) {
            int result = JOptionPane.showConfirmDialog(f, "It's a draw...\n Play again?", "Game Over!", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                reset();
                playerOne = true;
            } else {
                System.exit(0);
            }
        }
    }

    public void reset() {
        board = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8"};
        for (int i = 0; i < 9; i++) {
            JButton button = (JButton) buttons.getComponent(i);
            button.setText("");
        }
        playerOne = true;
    }

    public static void main(String[] args) {
        new TicTacToeGUI();
    }
}
