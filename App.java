import java.awt.*;
import java.awt.event.*;
import java.io.File;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;

class QuizCardPlayer {

    private JFrame frame;
    private JPanel panel;
    private JPanel centralPanel;
    private JSplitPane splitPane;
    private JPanel questionPanel;
    private JPanel answerPanel;
    private JButton flipCardButton;
    private JTextArea questionTextArea;
    private JTextArea answerTextArea;
    private enum menu {
        NEW(new JMenuItem("New card")), 
        OPEN(new JMenuItem("Open card")), 
        SAVE(new JMenuItem("Save card")), 
        SIDE_PANEL(new JCheckBoxMenuItem("Side panel")), 
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

        flipCardButton = new JButton("Flip card");

        JLabel questionLabel = new JLabel("Question", JLabel.CENTER);
        JLabel answerLabel = new JLabel("Answer", JLabel.CENTER);
        
        JMenuBar menuBar = new JMenuBar();
        JMenu optionsMenu = new JMenu("Options");
        JSeparator menuSeparator = new JSeparator();
        
        JList<QuizCard> cardsList = new JList<>();
        
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        
        JScrollPane sideScrollPane = new JScrollPane(cardsList);
        JScrollPane questionScrollPane = new JScrollPane(questionTextArea);
        JScrollPane answerScrollPane = new JScrollPane(answerTextArea);
        
        panel = new JPanel(new BorderLayout());
        centralPanel = new JPanel();
        questionPanel = new JPanel(new BorderLayout(0, 10));
        answerPanel = new JPanel(new BorderLayout(0, 10));
        
        addGUIListeners();
        
        // Menu
        menuBar.add(optionsMenu);
        optionsMenu.add(menu.NEW.menuItem);
        optionsMenu.add(menu.OPEN.menuItem);
        optionsMenu.add(menu.SAVE.menuItem);
        optionsMenu.add(menuSeparator);
        optionsMenu.add((JCheckBoxMenuItem) menu.SIDE_PANEL.menuItem);
        optionsMenu.add((JCheckBoxMenuItem) menu.EDITOR_MODE.menuItem);

        // Menu shortcuts
        optionsMenu.setMnemonic(KeyEvent.VK_P);
        menu.NEW.menuItem.setMnemonic(KeyEvent.VK_N);
        menu.OPEN.menuItem.setMnemonic(KeyEvent.VK_O);
        menu.SAVE.menuItem.setMnemonic(KeyEvent.VK_S);
        menu.SIDE_PANEL.menuItem.setMnemonic(KeyEvent.VK_I);
        menu.EDITOR_MODE.menuItem.setMnemonic(KeyEvent.VK_E);
        
        // Principal panel
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.setPreferredSize(new Dimension(600, 200));

        // Central panel
        centralPanel.setLayout(new BoxLayout(centralPanel, BoxLayout.X_AXIS));

        // Split pane
        splitPane.setLeftComponent(sideScrollPane);
        
        // Minimum size
        sideScrollPane.setMinimumSize(new Dimension(100, 0));
        questionScrollPane.setMinimumSize(new Dimension(200, 0));
        answerScrollPane.setMinimumSize(new Dimension(200, 0));
        
        // Question card
        questionPanel.add(questionLabel, BorderLayout.NORTH);
        questionPanel.add(questionScrollPane, BorderLayout.CENTER);
        
        // Answer card
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

    private void addGUIListeners() {

        menu.NEW.menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                questionTextArea.setText("");
                answerTextArea.setText("");
                questionTextArea.requestFocusInWindow();
            }
        });

        menu.OPEN.menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                touchCardsDirectory();
                JFileChooser fileChooser = new JFileChooser("./cards");
                fileChooser.setFileFilter(new FileFilter() {
                    @Override
                    public boolean accept(File f) {

                        if (f.isDirectory()) {
                            return true;
                        }
                    
                        String extension = getExtension(f);
                        if (extension != null) {
                            if (extension.equals(QuizCard.extension)) {
                                return true;
                            } else {
                                return false;
                            }
                        } else {
                            return false;
                        }
                    }
                    @Override
                    public String getDescription() {
                        return "Quiz card (*.card)";
                    }
                });
                fileChooser.showOpenDialog(frame);
            }
        });

        menu.SAVE.menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                touchCardsDirectory();
                JFileChooser fileChooser = new JFileChooser("./cards");
                fileChooser.showSaveDialog(frame);
            }
        });

        menu.SIDE_PANEL.menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateGUI();
            }
        });

        menu.EDITOR_MODE.menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateGUI();
            }
        });

        flipCardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cards = (CardLayout) centralPanel.getLayout();
                cards.next(centralPanel);
                if (flipCardButton.getParent().equals(questionPanel)) {
                    answerPanel.add(flipCardButton, BorderLayout.SOUTH);
                    answerTextArea.requestFocusInWindow();
                } else {
                    questionPanel.add(flipCardButton, BorderLayout.SOUTH);
                    questionTextArea.requestFocusInWindow();
                }
            }
        });

    }

    void touchCardsDirectory() {
        File cardsDirectory = new File("./cards");
        cardsDirectory.mkdir();
    }

    void updateGUI() {
        if (menu.SIDE_PANEL.menuItem.isSelected()) {
            panel.removeAll();
            splitPane.setRightComponent(centralPanel);
            panel.add(splitPane);
            panel.validate();
        } else {
            panel.removeAll();
            panel.add(centralPanel);
            panel.validate();
        }

        if (menu.EDITOR_MODE.menuItem.isSelected()) {
            questionPanel.setBorder(new EmptyBorder(10, 10, 10, 5));
            answerPanel.setBorder(new EmptyBorder(10, 5, 10, 10));

            flipCardButton.setVisible(false);

            questionTextArea.requestFocusInWindow();
            
            centralPanel.removeAll();
            centralPanel.setLayout(new BoxLayout(centralPanel, BoxLayout.X_AXIS));
            centralPanel.add(questionPanel);
            centralPanel.add(answerPanel);
            centralPanel.revalidate();
        } else {
            questionPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
            answerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
            
            flipCardButton.setVisible(true);
            questionPanel.add(flipCardButton, BorderLayout.SOUTH);
            
            questionTextArea.requestFocusInWindow();
            
            centralPanel.removeAll();
            centralPanel.setLayout(new CardLayout());
            centralPanel.add(questionPanel);
            centralPanel.add(answerPanel);
            centralPanel.revalidate();
        }
    }

    String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 && i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
    }
    
}