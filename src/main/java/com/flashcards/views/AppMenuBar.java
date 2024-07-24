package com.flashcards.views;

import java.awt.event.ActionEvent;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import com.flashcards.controllers.Controller;
import com.flashcards.models.Card;

/**
 * View representing the menu bar.
 * 
 * <p>
 * The menu contains:
 * </p>
 * 
 * <p>
 * <strong>File</strong>
 * <ul>
 * <li>New card
 * <li>Open card
 * <li>Save card
 * </ul>
 * </p>
 * 
 * <p>
 * <strong>View</strong>
 * <ul>
 * <li>Side panel
 * <li>Editor mode
 * </ul>
 * </p>
 */
class AppMenuBar extends JMenuBar implements View {

    // Controller
    private Controller controller;

    // File menu
    private final JMenu FILE_MENU = new JMenu();
    private final JMenuItem NEW = new JMenuItem();
    private final JMenuItem OPEN = new JMenuItem();
    private final JMenuItem SAVE = new JMenuItem();

    // View menu
    private final JMenu VIEW_MENU = new JMenu();
    private final JCheckBoxMenuItem SIDE_PANEL_CB = new JCheckBoxMenuItem();
    private final JCheckBoxMenuItem EDITOR_MODE_CB = new JCheckBoxMenuItem();

    // Components to bind view actions
    private GUI gui;
    private CardPanel cardPanel;

    /**
     * Initialize this view.
     * 
     * <p>
     * Stylizes and builds this view.
     * </p>
     * 
     * <p>
     * For <code>Side panel</code> and <code>Editor mode</code> actions to work, is
     * needed bind other views.
     * </p>
     * 
     * <ul>
     * <li><strong>Side panel</strong>: need a GUI with a function
     * to switch side panel.
     * <li><strong>Editor mode</strong>: need a card panel
     * to enable card editor mode.
     * </ul>
     * 
     * @param gui       the view to use <code>Side panel</code>
     * @param cardPanel the view to use <code>Editor mode</code>
     */
    AppMenuBar(GUI gui, CardPanel cardPanel) {
        this.gui = gui;
        this.cardPanel = cardPanel;

        // Make
        style();
        build();
    }

    @Override
    public void build() {

        // Menu bar
        add(FILE_MENU);
        add(VIEW_MENU);

        // File menu
        FILE_MENU.add(NEW);
        FILE_MENU.add(OPEN);
        FILE_MENU.add(SAVE);

        // View menu
        VIEW_MENU.add(SIDE_PANEL_CB);
        VIEW_MENU.add(EDITOR_MODE_CB);
    }

    @Override
    public void style() {

        // File menu
        FILE_MENU.setText("File");

        NEW.setText("New card");
        OPEN.setText("Open card");
        SAVE.setText("Save card");

        // View menu
        VIEW_MENU.setText("View");

        SIDE_PANEL_CB.setText("Side panel");
        SIDE_PANEL_CB.setSelected(true);
        EDITOR_MODE_CB.setText("Editor mode");
        EDITOR_MODE_CB.setSelected(true);
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
            addFileActions();
            addViewActions();
        }
    }

    /**
     * Adds funcionality for <code>Card</code> files.
     * 
     * <p>
     * Actions: New, Open and Save.
     * </p>
     */
    private void addFileActions() {

        // New card
        NEW.addActionListener((ActionEvent e) -> {
            cardPanel.setCard(new Card());
        });

        // Open card
        OPEN.addActionListener((ActionEvent e) -> {
            cardPanel.setCard(controller.getCardFromFile(gui));
        });

        // Save card
        SAVE.addActionListener((ActionEvent e) -> {
            controller.saveCard(cardPanel.getCard(), gui);
        });
    }

    /**
     * Adds behavior to "View" menu.
     * Uses other views to bind actions.
     * 
     * <ul>
     * <li>Show/hide side panel
     * <li>Enter/exit editor mode
     * </ul>
     */
    private void addViewActions() {

        // Show/hide side panel
        SIDE_PANEL_CB.addActionListener((ActionEvent e) -> {
            if (gui != null) {
                gui.setSidePanelVisible(SIDE_PANEL_CB.isSelected());
            }
        });

        // Enter/exit editor mode
        EDITOR_MODE_CB.addActionListener((ActionEvent e) -> {
            if (cardPanel != null) {
                cardPanel.setEditorModeVisible(EDITOR_MODE_CB.isSelected());
            }
        });
    }
}
