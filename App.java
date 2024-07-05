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

        frame = new JFrame("Quiz Cards");

        questionTextArea = new JTextArea();
        answerTextArea = new JTextArea();

        JLabel questionLabel = new JLabel("Question", JLabel.CENTER);
        JLabel answerLabel = new JLabel("Answer", JLabel.CENTER);
        
        JMenuBar menuBar = new JMenuBar();
        JMenu optionsMenu = new JMenu("Options");
        JSeparator menuSeparator = new JSeparator();
        
        JList<QuizCard> cardsList = new JList<>();
        
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        
        JScrollPane sideScrollPane = new JScrollPane(cardsList);
        JScrollPane questionScrollPane = new JScrollPane(questionTextArea);
        JScrollPane answerScrollPane = new JScrollPane(answerTextArea);
        
        JPanel panel = new JPanel(new BorderLayout());
        JPanel centralPanel = new JPanel();
        JPanel questionPanel = new JPanel(new BorderLayout(0, 10));
        JPanel answerPanel = new JPanel(new BorderLayout(0, 10));
        
        addGUIListeners();
        
        // Menu
        menuBar.add(optionsMenu);
        optionsMenu.add(menu.NEW.menuItem);
        optionsMenu.add(menu.OPEN.menuItem);
        optionsMenu.add(menu.SAVE.menuItem);
        optionsMenu.add(menuSeparator);
        optionsMenu.add((JCheckBoxMenuItem) menu.SHOW_SIDE_PANEL.menuItem);
        optionsMenu.add((JCheckBoxMenuItem) menu.EDITOR_MODE.menuItem);

        // Menu shortcuts
        optionsMenu.setMnemonic(KeyEvent.VK_P);
        menu.NEW.menuItem.setMnemonic(KeyEvent.VK_N);
        menu.OPEN.menuItem.setMnemonic(KeyEvent.VK_O);
        menu.SAVE.menuItem.setMnemonic(KeyEvent.VK_S);
        menu.SHOW_SIDE_PANEL.menuItem.setMnemonic(KeyEvent.VK_I);
        menu.EDITOR_MODE.menuItem.setMnemonic(KeyEvent.VK_E);
        
        // Principal panel
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.setPreferredSize(new Dimension(600, 200));
        panel.add(splitPane);

        // Split pane
        splitPane.setLeftComponent(sideScrollPane);
        splitPane.setRightComponent(centralPanel);

        // Central panel
        centralPanel.setLayout(new BoxLayout(centralPanel, BoxLayout.X_AXIS));
        centralPanel.add(questionPanel);
        centralPanel.add(answerPanel);
        
        // Minimum size
        sideScrollPane.setMinimumSize(new Dimension(100, 0));
        questionScrollPane.setMinimumSize(new Dimension(200, 0));
        answerScrollPane.setMinimumSize(new Dimension(200, 0));
        
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

        updateGUI();
        
        // Frame
        frame.add(menuBar, BorderLayout.NORTH);
        frame.add(panel, BorderLayout.CENTER);

        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    
}