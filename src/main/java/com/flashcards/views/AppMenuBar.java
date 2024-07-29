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
 * <strong>File menu</strong>
 * <ul>
 *     <li>New card
 *     <li>Open card
 *     <li>Save card
 * </ul>
 * </p>
 * 
 * <p>
 * <strong>View menu</strong>
 * <ul>
 *     <li>Side panel
 *     <li>Editor mode
 * </ul>
 * </p>
 */
class AppMenuBar extends JMenuBar implements View {

    // GUI
    private GUI gui;

    // File menu
    private JMenu fileMenu;
    private JMenuItem newItem;
    private JMenuItem openItem;
    private JMenuItem saveItem;

    // View menu
    private JMenu viewMenu;
    private JCheckBoxMenuItem sidePaneItem;
    private JCheckBoxMenuItem editorModeItem;

    /**
     * Initialize this view.
     * 
     * <p>
     * Stylizes, builds and add actions.
     * </p>
     * 
     * @param gui the GUI
     */
    AppMenuBar(GUI gui) {

        // Init
        this.gui = gui;
        fileMenu = new JMenu();
        newItem = new JMenuItem();
        openItem = new JMenuItem();
        saveItem = new JMenuItem();
        viewMenu = new JMenu();
        sidePaneItem = new JCheckBoxMenuItem();
        editorModeItem = new JCheckBoxMenuItem();

        // Make view
        style();
        build();
        addActions();
    }

    @Override
    public void style() {

        // File menu
        fileMenu.setText("File");

        newItem.setText("New card");
        openItem.setText("Open card");
        saveItem.setText("Save card");

        // View menu
        viewMenu.setText("View");

        sidePaneItem.setText("Side panel");
        editorModeItem.setText("Editor mode");
    }

    @Override
    public void build() {

        // Menu bar
        add(fileMenu);
        add(viewMenu);

        // File menu
        fileMenu.add(newItem);
        fileMenu.add(openItem);
        fileMenu.add(saveItem);

        // View menu
        viewMenu.add(sidePaneItem);
        viewMenu.add(editorModeItem);
    }

    @Override
    public void setTheme(Theme theme) {}

    /**
     * Enables or disables <code>Side panel</code>.
     * This method change Side panel Check box, change Side panel view in GUI and
     * resets GUI size to fit the changes.
     * 
     * @param b the state to set
     */
    void setSidePanelSelected(boolean b) {
        if (sidePaneItem.isSelected() != b) {
            sidePaneItem.setSelected(b);
        }
        gui.setSidePanelVisible(sidePaneItem.isSelected());
        gui.cardPanel.resetDividerLocation();
        gui.resetMinimumSize();
    }

    /**
     * Enables or disables <code>Editor mode</code>.
     * This method change Editor mode Check box, change Editor mode view in Card
     * panel and resets GUI size to fit the changes.
     * 
     * @param b the state to set
     */
    void setEditorModeSelected(boolean b) {
        if (editorModeItem.isSelected() != b) {
            editorModeItem.setSelected(b);
        }
        gui.cardPanel.setEditorModeVisible(editorModeItem.isSelected());
        gui.cardPanel.resetDividerLocation();
        gui.resetMinimumSize();
    }

    @Override
    public void addActions() {

        // Actions
        addFileMenuActions();
        addViewMenuActions();
    }

    /**
     * Adds funcionality for <code>Card</code> files.
     * 
     * <p>
     * Actions: New, Open and Save.
     * </p>
     */
    private void addFileMenuActions() {

        // New card
        newItem.addActionListener((ActionEvent e) -> {
            gui.cardPanel.setCard(new Card());
            gui.sidePanel.refreshCardsList();
        });

        // Open card
        openItem.addActionListener((ActionEvent e) -> {
            Card card = Controller.openCard(gui);
            if (card != null) {
                gui.cardPanel.setCard(card);
                gui.sidePanel.refreshCardsList();
            }
        });

        // Save card
        saveItem.addActionListener((ActionEvent e) -> {
            Controller.saveCard(gui.cardPanel.getCard(), gui);
            gui.sidePanel.refreshCardsList();
        });
    }

    /**
     * Adds actions to "View" menu.
     * Uses other views to bind actions.
     * 
     * <ul>
     *     <li>Show/hide side panel
     *     <li>Enter/exit editor mode
     * </ul>
     */
    private void addViewMenuActions() {

        // Show/hide side panel
        sidePaneItem.addActionListener((ActionEvent e) -> {
            setSidePanelSelected(sidePaneItem.isSelected());
        });
        
        // Enter/exit editor mode
        editorModeItem.addActionListener((ActionEvent e) -> {
            setEditorModeSelected(editorModeItem.isSelected());
        });
    }

}
