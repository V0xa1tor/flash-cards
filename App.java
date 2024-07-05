import java.awt.*;
import java.awt.event.*;
import java.io.File;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;

class QuizCardPlayer {

    private JFrame frame;
    private JTextArea questionTextArea;
    private JTextArea answerTextArea;
    private enum menu {
        NEW(new JMenuItem("New card")), 
        OPEN(new JMenuItem("Open card")), 
        SAVE(new JMenuItem("Save card")), 
        SHOW_SIDE_PANEL(new JCheckBoxMenuItem("Show side panel")), 
        EDITOR_MODE(new JCheckBoxMenuItem("Editor mode"));

        JMenuItem menuItem;
        menu(JMenuItem menuItem) {this.menuItem = menuItem;}
    };

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
        
        JSeparator menuSeparator = new JSeparator();
        
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
        
        // Menu
        menuBar.add(optionsMenu);
        optionsMenu.add(menu.NEW.menuItem);
        optionsMenu.add(menu.OPEN.menuItem);
        optionsMenu.add(menu.SAVE.menuItem);
        optionsMenu.add(menuSeparator);
        optionsMenu.add((JCheckBoxMenuItem) menu.SHOW_SIDE_PANEL.menuItem);
        optionsMenu.add((JCheckBoxMenuItem) menu.EDITOR_MODE.menuItem);
        
        menuBar.add(openBuilderButton);
        openBuilderButton.setMnemonic(KeyEvent.VK_B);
        openBuilderButton.setEnabled(false);

        // Menu shortcuts
        optionsMenu.setMnemonic(KeyEvent.VK_P);
        menu.NEW.menuItem.setMnemonic(KeyEvent.VK_N);
        menu.OPEN.menuItem.setMnemonic(KeyEvent.VK_O);
        menu.SAVE.menuItem.setMnemonic(KeyEvent.VK_S);
        menu.SHOW_SIDE_PANEL.menuItem.setMnemonic(KeyEvent.VK_I);
        menu.EDITOR_MODE.menuItem.setMnemonic(KeyEvent.VK_E);
        
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
        
        // Answer card
        answerPanel.setBorder(new EmptyBorder(10, 5, 10, 10));
        answerPanel.add(answerLabel, BorderLayout.NORTH);
        answerPanel.add(answerScrollPane, BorderLayout.CENTER);
        
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