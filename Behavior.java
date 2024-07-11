import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.stream.Stream;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

/**
 * The behavior of {@link GUI}. This class defines all the behaviors of {@link GUI} 
 * like open, save, and delete a file.
 * 
 * @author Vítor Menezes Oliveira
 */
class Behavior {

    // Quiz Card (*.card) file filter
    private FileFilter cardFileFilter = new FileFilter() {

        @Override
        public boolean accept(File f) {
            if (f.isDirectory()) {
                return true;
            }
            String extension = Utils.getExtension(f).toLowerCase();
            if (extension != null) {
                if (extension.equals(Card.EXTENSION.toLowerCase())) {
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



    /**
     * Add all behaviors to {@link GUI}
     */
    Behavior() {
        addCardsBehavior();
        addFileBehavior();
        addViewBehavior();
        addContextMenuBehavior();
        addKeyboardBehavior();
        addMouseBehavior();
    }


    
    /**
     * Add behavior when playing cards.
     * Like flipping card and going to the next one.
     */
    private void addCardsBehavior() {

        // Flip card button
        GUI.FLIP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GUI.flipCard();
            }
        });

    }

    /**
     * Add funcionality for {@link Card} files.
     * Behaviors: New, Open and Save.
     */
    private void addFileBehavior() {

        // New card button
        GUI.NEW.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setCurrentCard(new Card());
            }
        });

        // Open card button
        GUI.OPEN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openCard();
            }
        });

        // Save card button
        GUI.SAVE.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveCard();
            }
        });
    }

    /**
     * Add behavior to "View" menu.
     * 
     * <ul>
     * <li>Show/hide side panel</li>
     * <li>Enter/exit editor mode</li>
     * </ul>
     */
    private void addViewBehavior() {

        // Show/hide side panel checkbox
        GUI.SIDE_PANEL.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GUI.enableSidePanelView(GUI.SIDE_PANEL.isSelected());
                updateCardsList();
            }
        });
        
        // Enter/exit editor mode checkbox
        GUI.EDITOR_MODE.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GUI.enableEditorModeView(GUI.EDITOR_MODE.isSelected());
                updateCardsList();
            }
        });

    }
    
    /**
     * Add behaviors to context menu buttons.
     * Context menu appears when right click of the mouse
     * in cards list.
     */
    private void addContextMenuBehavior() {
        
        // Delete card button
        GUI.DELETE.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // deleteFile();
            }
        });

    }

    /**
     * Add mouse behaviors to cards list.
     * Like double click to oppen a card
     * and oppen context menu.
     */
    private void addMouseBehavior() {

        GUI.CARDS_LIST.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Double click
                if (e.getClickCount() == 2) {
                    setCurrentCard(GUI.CARDS_LIST.getSelectedValue());
                }

                // Context menu
                if (e.getButton() == MouseEvent.BUTTON3) {
                    GUI.CONTEXT_MENU.show(GUI.CARDS_LIST, e.getX(), e.getY());
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

        });

    }

    /**
     * Add keyboard behaviors to cards list.
     * Like open card when press Entre
     * and delete card when press Delete.
     */
    private void addKeyboardBehavior() {

        GUI.CARDS_LIST.addKeyListener(new KeyListener() {

            @Override
            public void keyPressed(KeyEvent e) {
                // Open
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    setCurrentCard(GUI.CARDS_LIST.getSelectedValue());
                }

                // Delete
                if (e.getKeyCode() == KeyEvent.VK_DELETE) {
                    // deleteFile();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {}

            @Override
            public void keyTyped(KeyEvent e) {}
        });

    }

    /**
     * Sets the card which is current being displayed on App.
     * Also updates the cards which is displayed on list.
     * 
     * <ul>
     * <li> Sets the current card.
     * <li> Displays the question and answer of the card.
     * <li> Request focus to question text area.
     * </ul>
     * 
     * @param card the card to set to current
     */
    private void setCurrentCard(Card card) {
        App.currentCard = card;
        GUI.setQuestionText(card.getQuestion());
        GUI.setAnswerText(card.getAnswer());
        GUI.requestQuestionFocus();

        updateCardsList();
    }

    /**
     * Opens file chooser to select and open a card file.
     * Also updates the cards which is displayed on list.
     */
    private void openCard() {
        // File chooser
        JFileChooser fileChooser = new JFileChooser(App.cardsFolder);
        fileChooser.setFileFilter(cardFileFilter);
        fileChooser.showOpenDialog(GUI.FRAME);

        try {
            // Getting card
            ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(fileChooser.getSelectedFile())
            );
            Card card = (Card) ois.readObject();
            ois.close();

            // Setting card
            setCurrentCard(card); // and updating cards list
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Opens file chooser to select a local and a name to save the card.
     * Also updates the cards which is displayed on list.
     */
    private void saveCard() {
        // Save question/answer on card
        Card card = App.currentCard;
        card.setQuestion(GUI.getQuestionText());
        card.setAnswer(GUI.getAnswerText());

        // File chooser
        JFileChooser fileChooser = new JFileChooser(App.cardsFolder);
        fileChooser.setFileFilter(cardFileFilter);
        fileChooser.showSaveDialog(GUI.FRAME);

        try {
            // Get file to save the card
            String filePath = fileChooser.getSelectedFile().getPath();
            ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(filePath + "." + Card.EXTENSION)
            );

            // Save card
            oos.writeObject(card);
            oos.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // Reload cards list
        updateCardsList();
    }

    /**
     * Updates cards which is displaying in cards list.
     * Also creates cards directory, if it does not exists.
     */
    void updateCardsList() {
        File cardsDir = Utils.touchDirectory("./cards");
        Card[] cards = (Card[]) Stream.of(cardsDir.listFiles()).map(f -> {
            try {
                // Getting card from file
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
                Card card = (Card) ois.readObject();
                ois.close();
                return card;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }).toArray();
        GUI.CARDS_LIST.setListData(cards);
    }

}