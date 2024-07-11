import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

/**
 * The Graphical User Interface of {@link App}.
 * This class don't implements behaviors. For behavior see {@link Behavior}.
 * 
 * @author Vítor Menezes Oliveira
 */
class GUI {

    // Look and Feel
    static {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Frame
    static final JFrame FRAME = new JFrame("Quiz Cards");
    
    // File menu items
    static final JMenuItem NEW = new JMenuItem("New card");
    static final JMenuItem OPEN = new JMenuItem("Open card");
    static final JMenuItem SAVE = new JMenuItem("Save card");
    
    // View menu items
    static final JCheckBoxMenuItem SIDE_PANEL = new JCheckBoxMenuItem("Side panel");
    static final JCheckBoxMenuItem EDITOR_MODE = new JCheckBoxMenuItem("Editor mode");
    
    // Context menu item
    static final JMenuItem DELETE = new JMenuItem("Delete card");

    // Buttons to play Quiz Card
    static final JButton FLIP = new JButton("Flip card");
    static final JButton PREVIOUS = new JButton("Previous");
    static final JButton NEXT = new JButton("Next");

    // Cards list
    static final JList<Card> CARDS_LIST = new JList<>();

    // Context menu
    static final JPopupMenu CONTEXT_MENU = new JPopupMenu();
    
    // Card (question/answer)
    private static final JPanel QUESTION_PANEL = new JPanel(new BorderLayout(0, 10));
    private static final JPanel ANSWER_PANEL = new JPanel(new BorderLayout(0, 10));

    // Text areas
    private static final JTextArea QUESTION = new JTextArea();
    private static final JTextArea ANSWER = new JTextArea();



    // Components to switch view
    private static JPanel rootPanel;
    private static JSplitPane splitPane;
    private static JPanel mainPanel;



    /**
     * Initialize the GUI (show it)
     */
    GUI() {
        init();

        // Initial view
        enableSidePanelView(true);
        enableEditorModeView(true);
    }



    /**
     * Initialize and show GUI components.
     */
    private void init() {

        // Card (question/answer)
        JLabel questionLabel = new JLabel("Question", JLabel.CENTER);
        QUESTION_PANEL.add(questionLabel, BorderLayout.NORTH);
        JScrollPane questionScrollPane = new JScrollPane(QUESTION);
        QUESTION_PANEL.add(questionScrollPane, BorderLayout.CENTER);
        QUESTION.setMargin(new Insets(10, 10, 10, 10));
        QUESTION.setWrapStyleWord(true);
        QUESTION.setLineWrap(true);
        
        JLabel answerLabel = new JLabel("Answer", JLabel.CENTER);
        ANSWER_PANEL.add(answerLabel, BorderLayout.NORTH);
        JScrollPane answerScrollPane = new JScrollPane(ANSWER);
        ANSWER_PANEL.add(answerScrollPane, BorderLayout.CENTER);
        ANSWER.setMargin(new Insets(10, 10, 10, 10));
        ANSWER.setWrapStyleWord(true);
        ANSWER.setLineWrap(true);
        
        
        
        // Menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);
        fileMenu.add(NEW);
        fileMenu.add(OPEN);
        fileMenu.add(SAVE);
        JMenu viewMenu = new JMenu("View");
        menuBar.add(viewMenu);
        viewMenu.add(SIDE_PANEL);
        viewMenu.add(EDITOR_MODE);

        // Root panel
        rootPanel = new JPanel(new BorderLayout());
        rootPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        rootPanel.setPreferredSize(new Dimension(600, 200));



        // Side panel
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        JScrollPane sideScrollPane = new JScrollPane(CARDS_LIST);
        splitPane.setLeftComponent(sideScrollPane);

        // Main panel
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.X_AXIS));
        cardPanel.setBorder(new EmptyBorder(0, 0, 10, 0));
        cardPanel.add(QUESTION_PANEL);
        cardPanel.add(ANSWER_PANEL);
        mainPanel.add(cardPanel, BorderLayout.CENTER);
        mainPanel.add(FLIP, BorderLayout.SOUTH);

        

        // Context menu
        CONTEXT_MENU.add(DELETE);
        


        // Frame
        FRAME.add(menuBar, BorderLayout.NORTH);
        FRAME.add(rootPanel, BorderLayout.CENTER);

        FRAME.pack();
        FRAME.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        FRAME.setVisible(true);
    }

    /**
     * Flips the card, if is not in editor mode.
     * This method just toglle visibility of question and answer panels.
     * For it works, need one set to false and the other set to true.
     */
    static void flipCard() {
        if (!EDITOR_MODE.isSelected()) {
            QUESTION_PANEL.setVisible(!QUESTION_PANEL.isVisible());
            ANSWER_PANEL.setVisible(!ANSWER_PANEL.isVisible());
        }
    }

    /**
     * Shows or hides the side panel.
     * Also set the state of checkbox view
     * 
     * @param state the state to set, enabled or disabled
     */
    static void enableSidePanelView(boolean state) {
        // Match check
        if (SIDE_PANEL.isSelected() != state) SIDE_PANEL.setSelected(state);
        
        // Remove all components
        rootPanel.removeAll();

        // Change component
        if (state) {
            rootPanel.add(splitPane);
            splitPane.add(mainPanel);
        } else {
            rootPanel.add(mainPanel);
        }

        // revalidate all components (show changes)
        rootPanel.revalidate();
    }

    /**
     * Shows or hides editor mode view.
     * 
     * @param state the state to set, enabled or disabled
     */
    static void enableEditorModeView(boolean state) {
        if (state) {
            //
        } else {
            //
        }
    }

    /**
     * Gets the text of the question component.
     * 
     * @return the question text
     */
    static String getQuestionText() {
        return QUESTION.getText();
    }
    
    /**
     * Sets the text to question component.
     * 
     * @param text the text to set
     */
    static void setQuestionText(String text) {
        QUESTION.setText(text);
    }

    /**
     * Gets the text of the answer component.
     * 
     * @return the answer text
     */
    static String getAnswerText() {
        return ANSWER.getText();
    }

    /**
     * Sets the text to answer component.
     * 
     * @param text the text to set
     */
    static void setAnswerText(String text) {
        ANSWER.setText(text);
    }

    /**
     * Resquest focus to question text area.
     */
    static void requestQuestionFocus() {
        QUESTION.requestFocusInWindow();
    }

    /**
     * Request focus to answer text area.
     */
    static void requestAnswerFocus() {
        ANSWER.requestFocusInWindow();
    }

}