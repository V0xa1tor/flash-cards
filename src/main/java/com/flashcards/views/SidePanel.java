package com.flashcards.views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import com.flashcards.controllers.Controller;
import com.flashcards.models.Card;
import com.flashcards.models.Model;

/**
 * View representing the <code>side panel</code>.
 * 
 * <p>
 * The side panel is used to display cards and easily manipule them.
 * </p>
 * 
 * @see {@link com.flashcards.views.SidePanel}
 */
class SidePanel extends JPanel implements View {

    // Controller
    // private Controller controller;

    // GUI
    private GUI gui;

    // Context menu
    private final ContextMenu CONTEXT_MENU = new ContextMenu(this);

    // Components
    private final JLabel LABEL = new JLabel();
    private final JButton REFRESH_BUTTON = new JButton();
    public final JList<Card> CARDS_LIST = new JList<>();
    private final JScrollPane SCROLL_PANE = new JScrollPane();

    // Icon
    private final ImageIcon refreshIcon = new ImageIcon();

    /**
     * Initializes this view.
     * 
     * <p>
     * Stylizes and builds this view.
     * </p>
     * 
     * @param gui the GUI to get <code>Card panel</code>
     */
    SidePanel(GUI gui) {
        this.gui = gui;

        // Make
        style();
        build();

        // Get cards
        refreshCardsList();
    }

    /**
     * Define controller and add actions.
     * 
     * @param controller the controller to set
     * @see {@link #addFileActions}
     * @see {@link #addViewActions}
     */
    public void setController(Controller controller) {
        // this.controller = controller;

        // Actions
        if (controller != null) {
            addMouseActions();
            addKeyActions();
            addButtonActions();
        }
    }

    /**
     * Adds mouse behaviors to cards list.
     * Like double click to oppen a card
     * and right click to oppen context menu.
     */
    private void addMouseActions() {

        CARDS_LIST.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                // Double click
                if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
                    if (!CARDS_LIST.isSelectionEmpty()) {
                        gui.CARD_PANEL.setCard(CARDS_LIST.getSelectedValue());
                    }
                }

                // Right click
                if (e.getButton() == MouseEvent.BUTTON3) {

                    // Select with right click
                    int i = CARDS_LIST.locationToIndex(e.getPoint());
                    CARDS_LIST.setSelectedIndex(i);

                    // Context menu
                    if (!CARDS_LIST.isSelectionEmpty()) {
                        CONTEXT_MENU.show(CARDS_LIST, e.getX(), e.getY());
                    }
                }
            }
        });
    }

    /**
     * Adds keyboard behaviors to cards list.
     * Like open card when press <code>Enter</code>
     * and delete card when press <code>Delete</code>.
     */
    private void addKeyActions() {

        CARDS_LIST.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {

                // Enter key
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (!CARDS_LIST.isSelectionEmpty()) {
                        gui.CARD_PANEL.setCard(CARDS_LIST.getSelectedValue());
                    }
                }

                // Delete key
                if (e.getKeyCode() == KeyEvent.VK_DELETE) {
                    if (!CARDS_LIST.isSelectionEmpty()) {
                        CARDS_LIST.getSelectedValue().delete();
                        refreshCardsList();
                    }
                }
            }
        });
    }

    /**
     * Adds buttons behaviors to cards list.
     * Like refresh card when press <code>Refresh button</code>.
     */
    private void addButtonActions() {

        // Refresh
        REFRESH_BUTTON.addActionListener((ActionEvent e) -> {
            refreshCardsList();
        });
    }

    @Override
    public void build() {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.add(LABEL);
        panel.add(Box.createHorizontalGlue());
        panel.add(REFRESH_BUTTON);

        // Side panel
        add(panel, BorderLayout.NORTH);
        add(SCROLL_PANE, BorderLayout.CENTER);

        // Scroll pane
        SCROLL_PANE.setViewportView(CARDS_LIST);
    }

    @Override
    public void style() {

        // Side panel
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(100, 200));

        // Label
        LABEL.setText("Cards");

        // Refresh icon
        refreshIcon.setImage(new ImageIcon("./src/main/resources/refresh_black.png",
                "Refresh").getImage()
                .getScaledInstance(20, 20, Image.SCALE_SMOOTH));

        // Refresh button
        REFRESH_BUTTON.setIcon(refreshIcon);
        REFRESH_BUTTON.setBorder(new EmptyBorder(0, 0, 0, 0));
    }

    /**
     * Gets all cards in default <code>Cards Folder</code> and sets in
     * <code>Cards list</code>.
     * 
     * @see {@link com.flashcards.models.Model#CARDS_FOLDER}
     */
    private void refreshCardsList() {
        CARDS_LIST.setListData(Model.getCardsList());
    }

    @Override
    public void setTheme(Theme theme) {

        String fileName;
        if (theme == Theme.DARK) { fileName = "./src/main/resources/refresh_white.png"; }
        else { fileName = "./src/main/resources/refresh_black.png"; }

        refreshIcon.setImage(new ImageIcon(fileName,
                "Refresh").getImage()
                .getScaledInstance(20, 20, Image.SCALE_SMOOTH));
    }

}
