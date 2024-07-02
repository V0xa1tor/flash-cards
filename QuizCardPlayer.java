import java.awt.*;
import java.awt.event.KeyEvent;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicBorders;

class QuizCardPlayer {

    private JFrame frame;

    public static void main(String args[]) {
        new QuizCardPlayer();
    }

    QuizCardPlayer() {
        initGUI();
    }
    
    private void initGUI() {
        JMenuBar menuBar = new JMenuBar();
        
        JMenu optionsMenu = new JMenu("Options");
        JMenu openBuilderButton = new JMenu("Quiz Card Builder");
        
        JMenuItem newMenuItem = new JMenuItem("New card", KeyEvent.VK_N);
        JMenuItem openMenuItem = new JMenuItem("Open card", KeyEvent.VK_O);
        JMenuItem saveMenuItem = new JMenuItem("Save card", KeyEvent.VK_S);
        
        JPanel panel = new JPanel(new BorderLayout());
        JPanel sidePanel = new JPanel();
        JPanel centralPanel = new JPanel();
        JPanel questionPanel = new JPanel(new BorderLayout(0, 10));
        JPanel answerPanel = new JPanel(new BorderLayout(0, 10));

        JList<String> cardsList = new JList<>(new String[] {
            "Card 1",
            "Card 2",
            "Card 3",
            "Card 4"
        });

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

        JLabel questionLabel = new JLabel("Question", JLabel.CENTER);
        JLabel answerLabel = new JLabel("Answer", JLabel.CENTER);
        
        JTextArea questionTextArea = new JTextArea("Hello, world!", 2, 2);
        JTextArea answerTextArea = new JTextArea("Hello, world!", 2, 2);
        
        frame = new JFrame("Quiz Card Player");
        
        // Menu bar
        menuBar.add(optionsMenu);
        optionsMenu.setMnemonic(KeyEvent.VK_P);
        optionsMenu.add(newMenuItem);
        optionsMenu.add(openMenuItem);
        optionsMenu.add(saveMenuItem);
        
        menuBar.add(openBuilderButton);
        openBuilderButton.setMnemonic(KeyEvent.VK_B);
        openBuilderButton.setEnabled(false);
        
        // Panel
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.setPreferredSize(new Dimension(400, 300));
        panel.add(splitPane);

        splitPane.setLeftComponent(sidePanel);
        splitPane.setRightComponent(centralPanel);

        sidePanel.add(cardsList);

        centralPanel.setLayout(new BoxLayout(centralPanel, BoxLayout.X_AXIS));
        centralPanel.add(questionPanel);
        centralPanel.add(answerPanel);
        
        // Question card
        questionPanel.add(questionLabel, BorderLayout.NORTH);
        questionPanel.add(questionTextArea, BorderLayout.CENTER);
        //button
        
        // Answer card
        answerPanel.add(answerLabel, BorderLayout.NORTH);
        answerPanel.add(answerTextArea, BorderLayout.CENTER);
        //button

        // Frame
        frame.add(menuBar, BorderLayout.NORTH);
        frame.add(panel, BorderLayout.CENTER);

        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    
}