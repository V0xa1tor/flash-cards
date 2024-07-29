package com.flashcards.views;

import java.awt.event.ActionEvent;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

import com.flashcards.controllers.Controller;
import com.flashcards.models.Card;

/**
 * View representing the context menu when right click on
 * <code>side panel</code>
 * 
 * @see {@link com.flashcards.views.SidePanel}
 */
class ContextMenu extends JPopupMenu implements View {

    // GUI
    private GUI gui;

    // Context menu item
    private JMenuItem renameItem;
    private JMenuItem deleteItem;

    /**
     * Initialize this view.
     * 
     * <p>
     * Stylizes, builds and add actions.
     * </p>
     * 
     * @param gui the GUI
     */
    ContextMenu(GUI gui) {

        // Init
        this.gui = gui;
        renameItem = new JMenuItem();
        deleteItem = new JMenuItem();

        // Make view
        style();
        build();
        addActions();
    }

    @Override
    public void style() {

        // Rename
        renameItem.setText("Rename card");

        // Delete
        deleteItem.setText("Delete card");
    }

    @Override
    public void build() {

        // Rename
        add(renameItem);

        // Delete
        add(deleteItem);
    }

    @Override
    public void setTheme(Theme theme) {}

    @Override
    public void addActions() {
        
        // Actions
        addContextActions();
    }

    /**
     * Adds behaviors to context menu buttons.
     * Context menu appears when right click of the mouse
     * in cards list.
     */
    private void addContextActions() {

        // Rename
        renameItem.addActionListener((ActionEvent e) -> {


            // Get new name
            Card card = gui.sidePanel.getSelectedCard();
            String newName = (String) JOptionPane.showInputDialog(gui,
                    "New name:",
                    "Rename \"" + card.toString() + "\" card",
                    JOptionPane.PLAIN_MESSAGE);
            newName = (newName == null) ? "" : newName;

            if (!newName.equals("")) {

                // Rename card
                Controller.renameCard(card, newName);
                
                // Refresh cards list
                gui.sidePanel.refreshCardsList();
            }
        });

        // Delete
        deleteItem.addActionListener((ActionEvent e) -> {

            // Delete card
            Controller.deleteCard(gui.sidePanel.getSelectedCard());

            // Refresh cards list
            gui.sidePanel.refreshCardsList();
        });
    }

}
