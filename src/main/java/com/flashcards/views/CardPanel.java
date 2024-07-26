package com.flashcards.views;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.flashcards.models.Card;

/**
 * View representing Card Panel.
 * This panel contains the card (question and answer) and the buttons to with,
 * like <code>Flip</code> card, <code>Next</code> and <code>previous</code>
 * card.
 * 
 * @see {@link com.flashcards.models.Card}
 */
public class CardPanel extends JPanel implements View {

    // Current card
    private Card currentCard = new Card();

    // Box panel
    private final JPanel BOX_PANEL = new JPanel();

    // Buttons to play Quiz Card
    private final JButton FLIP_BUTTON = new JButton();
    // private final JButton PREVIOUS_BUTTON = new JButton();
    // private final JButton NEXT_BUTTON = new JButton();

    // Question
    private final JPanel Q_PANEL = new JPanel();
    private final JLabel Q_LABEL = new JLabel();
    private final JTextArea Q_TEXT_AREA = new JTextArea();
    private final JScrollPane Q_SCROLL_PANE = new JScrollPane();

    // Divider
    private final Component BOX_DIVIDER = Box.createHorizontalStrut(10);

    // Answer
    private final JPanel A_PANEL = new JPanel();
    private final JLabel A_LABEL = new JLabel();
    private final JTextArea A_TEXT_AREA = new JTextArea();
    private final JScrollPane A_SCROLL_PANE = new JScrollPane();

    /**
     * Initializes this view.
     * 
     * <p>
     * Stylizes and builds this view. Also adds action behaviors.
     * </p>
     */
    CardPanel() {

        // Make
        style();
        build();

        // Actions
        addButtonActions();
    }

    @Override
    public void build() {

        // Card panel
        add(BOX_PANEL, BorderLayout.CENTER);
        add(FLIP_BUTTON, BorderLayout.SOUTH);

        // Box panel
        BOX_PANEL.add(Q_PANEL);
        BOX_PANEL.add(BOX_DIVIDER);
        BOX_PANEL.add(A_PANEL);

        // Question
        Q_PANEL.add(Q_LABEL, BorderLayout.NORTH);
        Q_PANEL.add(Q_SCROLL_PANE, BorderLayout.CENTER);
        Q_SCROLL_PANE.setViewportView(Q_TEXT_AREA);
        
        // Answer
        A_PANEL.add(A_LABEL, BorderLayout.NORTH);
        A_PANEL.add(A_SCROLL_PANE, BorderLayout.CENTER);
        A_SCROLL_PANE.setViewportView(A_TEXT_AREA);
    }

    @Override
    public void style() {

        // Card panel
        setLayout(new BorderLayout(0, 10));

        // Box panel
        BOX_PANEL.setLayout(new BoxLayout(BOX_PANEL, BoxLayout.X_AXIS));

        // Buttons
        FLIP_BUTTON.setText("Flip card");
        // PREVIOUS_BUTTON.setText("Previous");
        // NEXT_BUTTON.setText("Next");

        // ---------- Question ----------
        Q_PANEL.setLayout(new BorderLayout(0, 10));
        Q_PANEL.setMinimumSize(new Dimension(200, 150));

        Q_LABEL.setText("Question");
        Q_LABEL.setHorizontalAlignment(JLabel.CENTER);
        Q_LABEL.setFont(new Font(null, Font.BOLD, 20));

        Q_TEXT_AREA.setMargin(new Insets(10, 10, 10, 10));
        Q_TEXT_AREA.setWrapStyleWord(true);
        Q_TEXT_AREA.setLineWrap(true);

        // ---------- Answer ----------
        A_PANEL.setLayout(new BorderLayout(0, 10));
        A_PANEL.setMinimumSize(new Dimension(200, 150));

        A_LABEL.setText("Answer");
        A_LABEL.setHorizontalAlignment(JLabel.CENTER);
        A_LABEL.setFont(new Font(null, Font.BOLD, 20));

        A_TEXT_AREA.setMargin(new Insets(10, 10, 10, 10));
        A_TEXT_AREA.setWrapStyleWord(true);
        A_TEXT_AREA.setLineWrap(true);
    }

    /**
     * Adds button actions to play with cards.
     */
    private void addButtonActions() {

        // Flip question/answer
        FLIP_BUTTON.addActionListener((ActionEvent e) -> {
            Q_PANEL.setVisible(!Q_PANEL.isVisible());
            A_PANEL.setVisible(!A_PANEL.isVisible());
        });
    }

    /**
     * Sets the card which is current being displayed.
     * 
     * @param card the card to set to current
     */
    public void setCard(Card card) {
        currentCard = card;
        Q_TEXT_AREA.setText(currentCard.getQuestion());
        A_TEXT_AREA.setText(currentCard.getAnswer());
    }

    /**
     * Gets the card which is current being displayed.
     */
    public Card getCard() {
        currentCard.setQuestion(Q_TEXT_AREA.getText());
        currentCard.setAnswer(A_TEXT_AREA.getText());
        return currentCard;
    }

    /**
     * Shows or hides editor mode view.
     * 
     * @param flag the visibility to set
     */
    void setEditorModeVisible(boolean flag) {

        if (flag) {
            // Show both
            Q_PANEL.setVisible(true);
            BOX_DIVIDER.setVisible(true);
            A_PANEL.setVisible(true);
            // Hide flip button
            FLIP_BUTTON.setVisible(false);
        } else {
            // Show just one
            Q_PANEL.setVisible(true);
            BOX_DIVIDER.setVisible(false);
            A_PANEL.setVisible(false);
            // Show flip button
            FLIP_BUTTON.setVisible(true);
        }

        // reset size
        Q_PANEL.setPreferredSize(Q_PANEL.getMinimumSize());
        A_PANEL.setPreferredSize(A_PANEL.getMinimumSize());
    }

    @Override
    public void setTheme(Theme theme) {}
}
