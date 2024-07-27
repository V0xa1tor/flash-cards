package com.flashcards.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.plaf.basic.BasicSplitPaneUI;

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

    // Main panel (panel with card)
    private final JPanel MAIN_PANEL = new JPanel();

    // Buttons to play Quiz Card
    private final JButton FLIP_BUTTON = new JButton();
    // private final JButton PREVIOUS_BUTTON = new JButton();
    // private final JButton NEXT_BUTTON = new JButton();

    // Question
    private final JPanel Q_PANEL = new JPanel();
    private final JLabel Q_LABEL = new JLabel();
    private final JTextArea Q_TEXT_AREA = new JTextArea();
    private final JScrollPane Q_SCROLL_PANE = new JScrollPane();

    // Split pane
    private final JSplitPane SPLIT_PANE = new JSplitPane();

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
        addSplitPaneActions();
    }

    @Override
    public void build() {

        // Card panel
        add(MAIN_PANEL, BorderLayout.CENTER);
        add(FLIP_BUTTON, BorderLayout.SOUTH);

        // Main panel
        MAIN_PANEL.add(SPLIT_PANE);

        // Split pane
        SPLIT_PANE.setLeftComponent(Q_PANEL);
        SPLIT_PANE.setRightComponent(A_PANEL);

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

        // Main panel
        MAIN_PANEL.setLayout(new BoxLayout(MAIN_PANEL, BoxLayout.X_AXIS));

        // Split pane
        SPLIT_PANE.setDividerSize(10);

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
        Q_TEXT_AREA.setDisabledTextColor(Color.BLACK);

        // ---------- Answer ----------
        A_PANEL.setLayout(new BorderLayout(0, 10));
        A_PANEL.setMinimumSize(new Dimension(200, 150));

        A_LABEL.setText("Answer");
        A_LABEL.setHorizontalAlignment(JLabel.CENTER);
        A_LABEL.setFont(new Font(null, Font.BOLD, 20));

        A_TEXT_AREA.setMargin(new Insets(10, 10, 10, 10));
        A_TEXT_AREA.setWrapStyleWord(true);
        A_TEXT_AREA.setLineWrap(true);
        A_TEXT_AREA.setDisabledTextColor(Color.BLACK);
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

    private void addSplitPaneActions() {

        // Divider
        ((BasicSplitPaneUI) SPLIT_PANE.getUI())
                .getDivider().addMouseListener(new MouseAdapter() {

            // Resize when double click
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
                    resetDividerLocation();
                }
            }
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
     * Sets divider location on midle.
     */
    void resetDividerLocation() {
        SPLIT_PANE.setDividerLocation(0.5);
    }

    /**
     * Shows or hides editor mode view.
     * 
     * @param flag the visibility to set
     */
    void setEditorModeVisible(boolean flag) {

        // Remove
        MAIN_PANEL.removeAll();

        if (flag) {

            // Enable both
            Q_TEXT_AREA.setEnabled(true);
            A_TEXT_AREA.setEnabled(true);

            // Show split pane
            MAIN_PANEL.add(SPLIT_PANE);
            SPLIT_PANE.setLeftComponent(Q_PANEL);
            Q_PANEL.setVisible(true);
            SPLIT_PANE.setRightComponent(A_PANEL);
            A_PANEL.setVisible(true);
            
            // Hide flip button
            FLIP_BUTTON.setVisible(false);
        } else {

            // Disable both
            Q_TEXT_AREA.setEnabled(false);
            A_TEXT_AREA.setEnabled(false);

            // Show just one
            MAIN_PANEL.add(Q_PANEL);
            Q_PANEL.setVisible(true);
            MAIN_PANEL.add(A_PANEL);
            A_PANEL.setVisible(false);

            // Show flip button
            FLIP_BUTTON.setVisible(true);
        }
    }

    @Override
    public void setTheme(Theme theme) {
        if (theme == Theme.DARK) {
            Q_TEXT_AREA.setDisabledTextColor(Color.WHITE);
            A_TEXT_AREA.setDisabledTextColor(Color.WHITE);
        } else {
            Q_TEXT_AREA.setDisabledTextColor(Color.BLACK);
            A_TEXT_AREA.setDisabledTextColor(Color.BLACK);
        }
    }
}
