import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class HangmanGUI extends JFrame implements ActionListener {

    public static void main(String[] args) {
        new HangmanGUI();
    }

    private JTextField gChar;
    private JTextField gWord;
    private JLabel guessString;
    private JLabel wrongLabel;
    private JLabel hp;
    private Timer timer;
    private String randomWord;
    private boolean[] guessed;
    private ArrayList<String> words;
    private ArrayList<Character> wrong = new ArrayList<>();
    private int count = 0;


    public HangmanGUI() {
        // FRAME TITLE
        super("Hangman");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        //setSize(new Dimension(400, 500));

        // MENU BAR WITH REST
        JMenuBar mb = new JMenuBar();
        JMenu m = new JMenu("Options");
        JMenuItem mi = new JMenuItem("Reset");
        mi.addActionListener(this);
        m.add(mi);
        mb.add(m);
        setJMenuBar(mb);

        // TITLE
        JPanel p = new JPanel();
        p.setLayout(new GridLayout(2,0));
        JLabel l = new JLabel("Hangman", SwingConstants.CENTER);
        l.setFont(new Font(l.getFont().getName(), l.getFont().getStyle(), 30));
        p.add(l, SwingConstants.CENTER);

        // TIMER & HP & WRONG
        JPanel u = new JPanel();
        u.setLayout(new GridLayout(3, 0));
        JLabel t = new JLabel("Time: " + count, SwingConstants.CENTER);
        timer = new Timer(1000, e -> {
            count++;
            if (count < 300000) {
                t.setText("Time: " + count);
            } else {
                ((Timer) (e.getSource())).stop();
            }
        });
        timer.setInitialDelay(0);
        timer.start();
        u.add(t);

        hp = new JLabel("HP: ❤❤❤❤❤❤❤❤❤", SwingConstants.CENTER);
        u.add(hp);

        wrongLabel = new JLabel(getWrongList(), SwingConstants.CENTER);
        u.add(wrongLabel);

        p.add(u);

        // GAME WORD
        chooseLanguage();
        if (words == null) System.exit(0);
        randomWord = words.get((int)(Math.random()*words.size()));
        guessed = new boolean[randomWord.length()];

        JPanel w = new JPanel();
        w.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 5));
        w.setPreferredSize(new Dimension(400, 50));
        guessString = new JLabel(getHiddenString());
        guessString.setFont(new Font(l.getFont().getName(), l.getFont().getStyle(), 40));
        w.setPreferredSize(guessString.getPreferredSize());
        w.add(guessString);

        // GAME GUESS

        final String character = "Guess a Character";
        final String word = " Guess the Word";

        JPanel f = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel cards = new JPanel(new CardLayout());
        gChar = new JTextField(2);
        gChar.addActionListener(this);
        gChar.setActionCommand("Guess!");
        gWord = new JTextField(10);
        gWord.addActionListener(this);
        gWord.setActionCommand("Guess!");
        cards.add(gChar, character);
        cards.add(gWord, word);
        f.add(cards);

        JPanel comboBoxPane = new JPanel(new FlowLayout());
        String[] comboBoxItems = { character, word };
        JComboBox<String> cb = new JComboBox<>(comboBoxItems);
        cb.setEditable(false);
        cb.addItemListener(e -> {
            CardLayout cl = (CardLayout)(cards.getLayout());
            cl.show(cards, (String)e.getItem());
        });
        comboBoxPane.add(cb);
        f.add(comboBoxPane, BorderLayout.PAGE_START);
        f.add(cards, BorderLayout.CENTER);

        //GUESS BUTTON
        JButton b = new JButton("Guess!");
        b.addActionListener(this);
        f.add(b);

        add(p, BorderLayout.NORTH);
        add(w, BorderLayout.CENTER);
        add(f, BorderLayout.SOUTH);
        pack();
        setVisible(true);

    }

    private void chooseLanguage() {
        int result = JOptionPane.showOptionDialog(this, "Choose Language", "Language", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, new String[]{"English", "Dutch"}, JOptionPane.YES_OPTION);
        if (result == JOptionPane.CLOSED_OPTION) return;
        String filename;
        if (result == JOptionPane.YES_OPTION) {
            filename = "/Files/wordsEnglish.txt";
        } else {
            filename = "/Files/wordsDutch.txt";
        }

        words = processWordList(filename);
    }

    private ArrayList<String> processWordList(String fileName) {
        ArrayList<String> words = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(fileName)));
        reader.lines().forEach(words::add);
        return words;
    }

    private String getHiddenString() {
        StringBuilder result = new StringBuilder(" ");
        for (int i = 0; i < randomWord.length(); i++) {
            if (guessed[i]) {
                result.append(randomWord.charAt(i)).append(" ");
            } else {
                result.append("_ ");
            }
        }
        return result.toString();
    }

    private String getWrongList() {
        Collections.sort(wrong);
        StringBuilder wrongList = new StringBuilder(" ");
        for (int i = 0; i < wrong.size(); i++) {
            wrongList.append(wrong.get(i));
            if (i != wrong.size() - 1) wrongList.append(" ");
        }
        return wrongList.toString();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        if (action.equals("Guess!")) {
            String s = gWord.getText().toLowerCase();
            char c = '0';

            if (gChar.getText().length() + s.length() == 0) return;

            boolean isChar = true;
            if (gChar.getText().equals("")) isChar = false;

            if (isChar) {
                if (gChar.getText().length() > 1) {
                    JOptionPane.showMessageDialog(this, "Oi, just one letter, please!", "Cheater!", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                c = gChar.getText().toLowerCase().charAt(0);
                if (c < 'a' || c > 'z') {
                    JOptionPane.showMessageDialog(this, "Letters only!", "Imbecile!", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            // Guess Word Check
            if (!isChar) {
                if (s.equals(randomWord)) {
                    Arrays.fill(guessed, true);
                    guessString.setText(getHiddenString());
                    timer.stop();
                    int result = JOptionPane.showConfirmDialog(this, "You guessed the word correctly!\nReset?", "Game Over!", JOptionPane.YES_NO_OPTION);
                    if (result == JOptionPane.YES_OPTION) {
                        reset();
                    } else {
                        System.exit(0);
                    }
                } else {
                    if (hp.getText().contains("❤❤❤")) {
                        JOptionPane.showMessageDialog(this, "WONG! 你大便", "Wrong!", JOptionPane.PLAIN_MESSAGE);
                        hp.setText(hp.getText().replaceFirst("❤❤❤", ""));
                    } else {
                        timer.stop();
                        int result = JOptionPane.showConfirmDialog(this, "WRONG! And you ran out of lives...\nThe word was: " + randomWord + "\nReset?", "Game Over!", JOptionPane.YES_NO_OPTION);
                        if (result == JOptionPane.YES_OPTION) {
                            reset();
                        } else {
                            System.exit(0);
                        }
                    }
                }
            } else { // Guess Char Check
                if (wrong.contains(c) || guessString.getText().contains(c + "")) return;

                int counter = 0;
                for (int i = 0; i < randomWord.length(); i++) {
                    if (c == randomWord.charAt(i)) {
                        guessed[i] = true;
                        counter++;
                    }
                }
                if (counter == 0) {
                    hp.setText(hp.getText().replaceFirst("❤", ""));
                    wrong.add(c);
                }
                if (!hp.getText().contains("❤")) {
                    timer.stop();
                    int result = JOptionPane.showConfirmDialog(this, "You ran out of HP...\nThe word was: " + randomWord + "\nReset?", "Game Over", JOptionPane.YES_NO_OPTION);
                    if (result == JOptionPane.YES_OPTION) {
                        reset();
                    } else {
                        System.exit(0);
                    }
                }
                if (getHiddenString().replace(" ", "").equals(randomWord)) {
                    Arrays.fill(guessed, true);
                    guessString.setText(getHiddenString());
                    timer.stop();
                    int result = JOptionPane.showConfirmDialog(this, "You guessed the word correctly!\nReset?", "Game Over!", JOptionPane.YES_NO_OPTION);
                    if (result == JOptionPane.YES_OPTION) {
                        reset();
                    } else {
                        System.exit(0);
                    }
                }
            }

            wrongLabel.setText(getWrongList());
            guessString.setText(getHiddenString());
            gChar.setText("");
            gWord.setText("");
        }

        if (action.equals("Reset")) {
            reset();
        }
    }

    public void reset() {
        count = 0;
        randomWord = words.get((int) (Math.random() * words.size()));
        hp.setText("HP: ❤❤❤❤❤❤❤❤❤");
        wrong = new ArrayList<>();
        guessed = new boolean[randomWord.length()];
        guessString.setText(getHiddenString());
        wrongLabel.setText(getWrongList());
        timer.start();
    }
}
