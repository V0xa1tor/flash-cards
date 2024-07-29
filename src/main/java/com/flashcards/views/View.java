package com.flashcards.views;

interface View {

    /**
     * Styles this view.
     * 
     * <p>
     * Sets some properties like:
     * text, layout, size, border, margin, font, etc.
     * </p>
     * 
     * @see {@link #build}
     */
    void style();

    /**
     * Builds this view.
     * 
     * <p>
     * Adds components in the right order, on the right place.
     * </p>
     * 
     * @see {@link #style}
     */
    void build();

    /**
     * Adds all actions of this view.
     */
    void addActions();

    /**
     * Sets view theme.
     * 
     * @param theme the theme to set
     * @see {@link Theme}
     */
    void setTheme(Theme theme);
    
}
