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

    // App
    private GUI gui;
    private ContextMenu contextMenu;

    // Components
    private JLabel label;
    private JButton refreshButton;
    private JScrollPane scrollPane;
    private JList<Card> cardsList;
    private ImageIcon refreshIcon;

    /**
     * Initialize this view.
     * 
     * <p>
     * Stylizes, builds and add actions.
     * </p>
     * 
     * @param gui the GUI
     */
    SidePanel(GUI gui) {

        // Init
        this.gui = gui;
        contextMenu = new ContextMenu(gui);
        label = new JLabel();
        refreshButton = new JButton();
        scrollPane = new JScrollPane();
        cardsList = new JList<>();
        refreshIcon = new ImageIcon();

        // Make view
        style();
        build();
        addActions();

        // Get cards
        refreshCardsList();
    }

    @Override
    public void style() {

        // Side panel
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(100, 200));

        // Label
        label.setText("Cards");

        // Refresh icon
        refreshIcon.setImage(new ImageIcon("./src/main/resources/refresh_black.png",
                "Refresh").getImage()
                .getScaledInstance(20, 20, Image.SCALE_SMOOTH));

        // Refresh button
        refreshButton.setIcon(refreshIcon);
        refreshButton.setBorder(new EmptyBorder(0, 0, 0, 0));
    }

    @Override
    public void build() {

        // Auxiliar panel
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.add(label);
        panel.add(Box.createHorizontalGlue());
        panel.add(refreshButton);

        // Side panel
        add(panel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Scroll pane
        scrollPane.setViewportView(cardsList);
    }

    /**
     * Gets all cards in default <code>Cards Folder</code> and sets in
     * <code>Cards list</code>.
     * 
     * @see {@link com.flashcards.models.Model#CARDS_FOLDER}
     */
    void refreshCardsList() {
        cardsList.setListData(Model.getCardsList());
    }

    /**
     * Gets the current selected card in side panel list.
     * 
     * @return the current selected card
     */
    Card getSelectedCard() {
        return cardsList.getSelectedValue();
    }

    @Override
    public void setTheme(Theme theme) {

        String fileName;
        if (theme == Theme.DARK) { fileName = "./src/main/resources/refresh_white.png"; }
        else { fileName = "./src/main/resources/refresh_black.png"; }

        refreshIcon.setImage(new ImageIcon(fileName,"Refresh").getImage()
                .getScaledInstance(20, 20, Image.SCALE_SMOOTH));
    }

    @Override
    public void addActions() {

        // Actions
        addMouseActions();
        addKeyActions();
        addButtonActions();
    }

    /**
     * Adds mouse behaviors to cards list.
     * Like double click to oppen a card
     * and right click to oppen context menu.
     */
    private void addMouseActions() {

        cardsList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                // Open (double click)
                if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
                    if (!cardsList.isSelectionEmpty()) {
                        gui.cardPanel.setCard(cardsList.getSelectedValue());
                    }
                }

                // Show context menu (right click)
                if (e.getButton() == MouseEvent.BUTTON3) {

                    // Select with right click
                    int i = cardsList.locationToIndex(e.getPoint());
                    cardsList.setSelectedIndex(i);

                    // Context menu
                    if (!cardsList.isSelectionEmpty()) {
                        contextMenu.show(cardsList, e.getX(), e.getY());
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

        cardsList.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {

                // Open card (Enter key)
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (!cardsList.isSelectionEmpty()) {
                        gui.cardPanel.setCard(cardsList.getSelectedValue());
                    }
                }

                // Delete card (Delete key)
                if (e.getKeyCode() == KeyEvent.VK_DELETE) {
                    if (!cardsList.isSelectionEmpty()) {
                        Controller.deleteCard(cardsList.getSelectedValue());
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
        refreshButton.addActionListener((ActionEvent e) -> {
            refreshCardsList();
        });
    }

}
