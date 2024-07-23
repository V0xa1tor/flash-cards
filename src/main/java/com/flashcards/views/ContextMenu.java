package com.flashcards.views;

import java.awt.event.ActionEvent;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 * View representing the context menu when right click on
 * <code>side panel</code>
 * 
 * @see {@link com.flashcards.views.SidePanel}
 */
class ContextMenu extends JPopupMenu {

    // Context menu item
    private static final JMenuItem DELETE = new JMenuItem();

    /**
     * Initialize this view.
     * 
     * <p>
     * Stylizes and builds this view. Also adds action behaviors.
     * </p>
     */
    ContextMenu() {

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
            // deleteFile();
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

        add(DELETE);
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

        DELETE.setText("Delete card");
    }

}
