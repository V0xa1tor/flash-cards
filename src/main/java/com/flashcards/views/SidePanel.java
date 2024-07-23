package com.flashcards.views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.flashcards.controllers.Controller;
import com.flashcards.models.Card;

/**
 * View representing the <code>side panel</code>.
 * 
 * <p>
 * The side panel is used to display cards and easily manipule them.
 * </p>
 * 
 * @see {@link com.flashcards.views.SidePanel}
 */
class SidePanel extends JPanel {

    // Controller
    private Controller controller;

    // Card panel
    private CardPanel cardPanel;

    // Cards list
    private static final JList<Card> CARDS_LIST = new JList<>();

    // Scroll pane
    private static final JScrollPane SCROLL_PANE = new JScrollPane();

    // Context menu
    private final ContextMenu CONTEXT_MENU = new ContextMenu();

    /**
     * Initializes this view.
     * 
     * <p>
     * Stylizes and builds this view.
     * </p>
     * 
     * @param cardPanel the Card panel to load open cards
     */
    SidePanel(CardPanel cardPanel) {
        this.cardPanel = cardPanel;

        // Make
        style();
        build();
    }

    /**
     * Define controller and add actions.
     * 
     * @param controller the controller to set
     * @see {@link #addFileActions}
     * @see {@link #addViewActions}
     */
    public void setController(Controller controller) {
        this.controller = controller;

        // Actions
        if (controller != null) {
            addMouseActions();
            addKeyActions();
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
                if (e.getClickCount() == 2) {
                    cardPanel.setCard(CARDS_LIST.getSelectedValue());
                }

                // Right click
                if (e.getButton() == MouseEvent.BUTTON3) {
                    CONTEXT_MENU.show(CARDS_LIST, e.getX(), e.getY());
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
                    cardPanel.setCard(CARDS_LIST.getSelectedValue());
                }

                // Delete key
                if (e.getKeyCode() == KeyEvent.VK_DELETE) {
                    // deleteFile();
                }
            }
        });
    }

    /**
     * Builds this view.
     * 
     * <p>
     * Adds components in the right order, on the right place.
     * </p>
     * 
     * @see {@link #style}
     */
    private void build() {

        // Side panel
        add(SCROLL_PANE);

        // Scroll pane
        SCROLL_PANE.setViewportView(CARDS_LIST);
    }

    /**
     * Styles this view.
     * 
     * <p>
     * Sets some properties like: layout, size, border, margin, font, etc.
     * </p>
     * 
     * @see {@link #build}
     */
    private void style() {

        // Side panel
        setLayout(new BorderLayout());

        // Scroll pane
        SCROLL_PANE.setMinimumSize(new Dimension(100, 200));
    }

}
