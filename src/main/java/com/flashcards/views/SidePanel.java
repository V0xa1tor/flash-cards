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
    private Controller controller;

    // Card panel
    private GUI gui;

    // Cards list
    public final JList<Card> CARDS_LIST = new JList<>();

    // Scroll pane
    private final JScrollPane SCROLL_PANE = new JScrollPane();

    // Context menu
    private final ContextMenu CONTEXT_MENU = new ContextMenu(this);

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
                if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
                    gui.CARD_PANEL.setCard(CARDS_LIST.getSelectedValue());
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
                    gui.CARD_PANEL.setCard(CARDS_LIST.getSelectedValue());
                }

                // Delete key
                if (e.getKeyCode() == KeyEvent.VK_DELETE) {
                    if (!CARDS_LIST.isSelectionEmpty()) {
                        CARDS_LIST.getSelectedValue().delete();
                        CARDS_LIST.setListData(Model.getCardsList());
                    }
                }
            }
        });
    }

    @Override
    public void build() {

        // Side panel
        add(SCROLL_PANE);

        // Scroll pane
        SCROLL_PANE.setViewportView(CARDS_LIST);
    }

    @Override
    public void style() {

        // Side panel
        setLayout(new BorderLayout());

        // Scroll pane
        SCROLL_PANE.setMinimumSize(new Dimension(100, 200));
    }

}
