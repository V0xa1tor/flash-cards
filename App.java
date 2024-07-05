import java.awt.*;
import java.awt.event.KeyEvent;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

class QuizCardPlayer {

    private JFrame frame;
    private JTextArea questionTextArea;
    private JTextArea answerTextArea;

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
        JPanel centralPanel = new JPanel();
        JPanel questionPanel = new JPanel(new BorderLayout(0, 10));
        JPanel answerPanel = new JPanel(new BorderLayout(0, 10));

        JList<QuizCard> cardsList = new JList<>();
        
        JLabel questionLabel = new JLabel("Question", JLabel.CENTER);
        JLabel answerLabel = new JLabel("Answer", JLabel.CENTER);
        
        questionTextArea = new JTextArea();
        answerTextArea = new JTextArea();
        
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

        JScrollPane sideScrollPane = new JScrollPane(cardsList);
        JScrollPane questionScrollPane = new JScrollPane(questionTextArea);
        JScrollPane answerScrollPane = new JScrollPane(answerTextArea);
        
        frame = new JFrame("Quiz Cards");
        
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
        panel.setPreferredSize(new Dimension(600, 200));
        panel.add(splitPane);

        splitPane.setLeftComponent(sideScrollPane);
        splitPane.setRightComponent(centralPanel);

        centralPanel.setLayout(new BoxLayout(centralPanel, BoxLayout.X_AXIS));
        centralPanel.add(questionPanel);
        centralPanel.add(answerPanel);
        
        // Question card
        questionPanel.setBorder(new EmptyBorder(10, 10, 10, 5));
        questionPanel.add(questionLabel, BorderLayout.NORTH);
        questionPanel.add(questionScrollPane, BorderLayout.CENTER);
        //button
        
        // Answer card
        answerPanel.setBorder(new EmptyBorder(10, 5, 10, 10));
        answerPanel.add(answerLabel, BorderLayout.NORTH);
        answerPanel.add(answerScrollPane, BorderLayout.CENTER);
        //button
        
        // Text area
        questionTextArea.setMargin(new Insets(10, 10, 10, 10));
        questionTextArea.setLineWrap(true);
        answerTextArea.setMargin(new Insets(10, 10, 10, 10));
        answerTextArea.setLineWrap(true);
        
        // Scroll pane
        sideScrollPane.setMinimumSize(new Dimension(100, 0));
        questionScrollPane.setMinimumSize(new Dimension(200, 0));
        answerScrollPane.setMinimumSize(new Dimension(200, 0));
        
        // Frame
        frame.add(menuBar, BorderLayout.NORTH);
        frame.add(panel, BorderLayout.CENTER);

        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    
}