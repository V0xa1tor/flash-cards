package com.flashcards.views;

import java.awt.event.ActionEvent;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import com.flashcards.models.Model;

/**
 * View representing the context menu when right click on
 * <code>side panel</code>
 * 
 * @see {@link com.flashcards.views.SidePanel}
 */
class ContextMenu extends JPopupMenu implements View {

    // Context menu item
    private final JMenuItem DELETE = new JMenuItem();

    // Side panel
    private SidePanel sidePanel;

    /**
     * Initialize this view.
     * 
     * <p>
     * Stylizes and builds this view. Also adds action behaviors.
     * </p>
     * 
     * @param sidePanel the side panel to bind
     */
    ContextMenu(SidePanel sidePanel) {
        this.sidePanel = sidePanel;

        // Make
        style();
        build();

        // Actions
        addContextActions();
    }

    /**
     * Adds behaviors to context menu buttons.
     * Context menu appears when right click of the mouse
     * in cards list.
     */
    private void addContextActions() {

        // Delete card
        DELETE.addActionListener((ActionEvent e) -> {
            sidePanel.CARDS_LIST.getSelectedValue().delete();
            sidePanel.CARDS_LIST.setListData(Model.getCardsList());
        });
    }

    @Override
    public void build() {

        // Delete
        add(DELETE);
    }

    @Override
    public void style() {

        // Delete
        DELETE.setText("Delete card");
    }

}
