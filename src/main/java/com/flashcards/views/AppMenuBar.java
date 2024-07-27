package com.flashcards.views;

import java.awt.event.ActionEvent;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import com.flashcards.controllers.Controller;
import com.flashcards.models.Card;
import com.flashcards.models.Model;

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

    // GUI
    private GUI gui;

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
     * @param gui the view wich have <code>Side panel</code> and
     *            <code>Card panel</code>
     */
    AppMenuBar(GUI gui) {
        this.gui = gui;

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
        EDITOR_MODE_CB.setText("Editor mode");
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
     * Initializes the default view.
     * <p>
     * Default is:
     * <ul>
     * <li><strong>Side panel</strong>: on
     * <li><strong>Editor mode</strong>: on
     * </ul>
     * </p>
     * 
     * @see {@link com.flashcards.App}
     */
    public void setDefaultView() {

        // Side panel
        setSidePanelSelected(true);

        // Editor mode
        setEditorModeSelected(false);
    }

    /**
     * Enables or disables <code>Side panel</code>.
     * This method change Side panel Check box, change Side panel view in GUI and
     * resets GUI size to fit the changes.
     * 
     * @param b the state to set
     */
    private void setSidePanelSelected(boolean b) {
        if (gui != null) {
            if (SIDE_PANEL_CB.isSelected() != b) {
                SIDE_PANEL_CB.setSelected(b);
            }
            gui.setSidePanelVisible(SIDE_PANEL_CB.isSelected());
            gui.resetSize();
        }
    }

    /**
     * Enables or disables <code>Editor mode</code>.
     * This method change Editor mode Check box, change Editor mode view in Card
     * panel and resets GUI size to fit the changes.
     * 
     * @param b the state to set
     */
    private void setEditorModeSelected(boolean b) {
        if (gui != null) {
            if (EDITOR_MODE_CB.isSelected() != b) {
                EDITOR_MODE_CB.setSelected(b);
            }
            gui.CARD_PANEL.setEditorModeVisible(EDITOR_MODE_CB.isSelected());
            gui.resetSize();
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
            gui.CARD_PANEL.setCard(new Card());
            gui.SIDE_PANEL.CARDS_LIST.setListData(Model.getCardsList());
        });

        // Open card
        OPEN.addActionListener((ActionEvent e) -> {
            Card card = controller.getCardFromFile(gui);
            if (card != null) {
                gui.CARD_PANEL.setCard(card);
                gui.SIDE_PANEL.CARDS_LIST.setListData(Model.getCardsList());
            }
        });

        // Save card
        SAVE.addActionListener((ActionEvent e) -> {
            controller.saveCard(gui.CARD_PANEL.getCard(), gui);
            gui.SIDE_PANEL.CARDS_LIST.setListData(Model.getCardsList());
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
            setSidePanelSelected(SIDE_PANEL_CB.isSelected());
            gui.CARD_PANEL.resetDividerLocation();
        });
        
        // Enter/exit editor mode
        EDITOR_MODE_CB.addActionListener((ActionEvent e) -> {
            setEditorModeSelected(EDITOR_MODE_CB.isSelected());
            gui.CARD_PANEL.resetDividerLocation();
        });
    }

    @Override
    public void setTheme(Theme theme) {}
}
