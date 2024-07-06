import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;

class App {

    private JFrame frame;
    private JPanel panel;
    private JPanel centralPanel;
    private JPanel boxLayoutPanel;
    private JSplitPane splitPane;
    private JPanel questionPanel;
    private JPanel answerPanel;
    private JList<String> cardsList;
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
        new App();
    }

    App() {
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
        
        cardsList = new JList<>();
        
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        
        JScrollPane sideScrollPane = new JScrollPane(cardsList);
        JScrollPane questionScrollPane = new JScrollPane(questionTextArea);
        JScrollPane answerScrollPane = new JScrollPane(answerTextArea);
        
        panel = new JPanel(new BorderLayout());
        centralPanel = new JPanel(new BorderLayout());
        boxLayoutPanel = new JPanel();
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

        // Split pane
        splitPane.setLeftComponent(sideScrollPane);

        // Central panel
        centralPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        centralPanel.add(boxLayoutPanel, BorderLayout.CENTER);
        centralPanel.add(flipCardButton, BorderLayout.SOUTH);

        // Box layout panel
        boxLayoutPanel.setBorder(new EmptyBorder(0, 0, 10, 0));
        boxLayoutPanel.setLayout(new BoxLayout(boxLayoutPanel, BoxLayout.X_AXIS));
        boxLayoutPanel.add(questionPanel);
        boxLayoutPanel.add(answerPanel);
        
        // Minimum size
        sideScrollPane.setMinimumSize(new Dimension(100, 0));
        questionPanel.setMinimumSize(new Dimension(200, 0));
        answerPanel.setMinimumSize(new Dimension(200, 0));
        
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

        loadCards();
        updateGUI();
        
        // Frame
        frame.add(menuBar, BorderLayout.NORTH);
        frame.add(panel, BorderLayout.CENTER);

        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private void addGUIListeners() {

        FileFilter fileFilter = new FileFilter() {
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

        };

        menu.NEW.menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadCards();
                questionTextArea.setText("");
                answerTextArea.setText("");
                questionTextArea.requestFocusInWindow();
            }
        });

        menu.OPEN.menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadCards();
                JFileChooser fileChooser = new JFileChooser("./cards");
                fileChooser.setFileFilter(fileFilter);
                fileChooser.showOpenDialog(frame);

                try {
                    ObjectInputStream oi = new ObjectInputStream(
                        new FileInputStream(fileChooser.getSelectedFile())
                    );
                    QuizCard card = (QuizCard) oi.readObject();
                    questionTextArea.setText(card.getQuestion());
                    answerTextArea.setText(card.getAnswer());
                    oi.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (ClassNotFoundException e1) {
                    e1.printStackTrace();
                }
            }
        });

        menu.SAVE.menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                QuizCard card = new QuizCard(
                    questionTextArea.getText(),
                    answerTextArea.getText()
                );

                loadCards();
                JFileChooser fileChooser = new JFileChooser("./cards");
                fileChooser.setFileFilter(fileFilter);
                fileChooser.showSaveDialog(frame);

                try {
                    ObjectOutputStream oo = new ObjectOutputStream(
                        new FileOutputStream(
                            fileChooser.getSelectedFile().getPath() + "." + QuizCard.extension
                        )
                    );
                    oo.writeObject(card);
                    oo.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        menu.SIDE_PANEL.menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadCards();
                updateGUI();
            }
        });

        menu.EDITOR_MODE.menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadCards();
                updateGUI();
            }
        });

        flipCardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                questionPanel.setVisible(!questionPanel.isVisible());
                answerPanel.setVisible(!answerPanel.isVisible());
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
            questionPanel.setBorder(new EmptyBorder(0, 0, 0, 5));
            answerPanel.setBorder(new EmptyBorder(0, 5, 0, 0));

            flipCardButton.setVisible(false);
            
            questionPanel.setVisible(true);
            answerPanel.setVisible(true);
            
            questionTextArea.requestFocusInWindow();
            questionTextArea.setEditable(true);
            answerTextArea.setEditable(true);
            
            centralPanel.validate();
        } else {
            questionPanel.setBorder(null);
            answerPanel.setBorder(null);
            
            questionTextArea.requestFocusInWindow();
            
            flipCardButton.setVisible(true);

            questionPanel.setVisible(true);
            answerPanel.setVisible(false);

            questionTextArea.setEditable(false);
            answerTextArea.setEditable(false);

            centralPanel.validate();
        }
    }

    void loadCards() {
        touchCardsDirectory();
        File cardsDir = new File("./cards");
        cardsList.setListData(cardsDir.list());
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