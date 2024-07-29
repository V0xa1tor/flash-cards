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
 * 
 * <p>
 * This panel contains the card (question and answer)
 * and the buttons to play with, like <code>Flip</code> card,
 * <code>Next</code> and <code>previous</code> card.
 * </p>
 * 
 * @see {@link com.flashcards.models.Card}
 */
public class CardPanel extends JPanel implements View {

    // GUI
    private GUI gui;

    // Current card
    private Card currentCard;

    // Main panel (panel with card)
    private JPanel mainPanel;

    // Buttons to play Quiz Card
    private JButton flipButton;

    // Question
    private JPanel qPanel;
    private JLabel qLabel;
    private JTextArea qTextArea;
    private JScrollPane qScrollPane;

    // Split pane
    private JSplitPane splitPane;

    // Answer
    private JPanel aPanel;
    private JLabel aLabel;
    private JTextArea aTextArea;
    private JScrollPane aScrollPane;

    /**
     * Initialize this view.
     * 
     * <p>
     * Stylizes, builds and add actions.
     * </p>
     * 
     * @param gui the GUI
     */
    CardPanel(GUI gui) {

        // Init
        this.gui = gui;
        currentCard = new Card();
        mainPanel = new JPanel();
        flipButton = new JButton();
        qPanel = new JPanel();
        qLabel = new JLabel();
        qTextArea = new JTextArea();
        qScrollPane = new JScrollPane();
        splitPane = new JSplitPane();
        aPanel = new JPanel();
        aLabel = new JLabel();
        aTextArea = new JTextArea();
        aScrollPane = new JScrollPane();

        // Make view
        style();
        build();
        addActions();
    }

    @Override
    public void style() {

        // Card panel
        setLayout(new BorderLayout(0, 10));

        // Main panel
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));

        // Split pane
        splitPane.setDividerSize(10);

        // Buttons
        flipButton.setText("Flip card");

        // ---------- Question ----------
        qPanel.setLayout(new BorderLayout(0, 10));
        qPanel.setMinimumSize(new Dimension(200, 150));

        qLabel.setText("Question");
        qLabel.setHorizontalAlignment(JLabel.CENTER);
        qLabel.setFont(new Font(null, Font.BOLD, 20));

        qTextArea.setMargin(new Insets(10, 10, 10, 10));
        qTextArea.setWrapStyleWord(true);
        qTextArea.setLineWrap(true);
        qTextArea.setDisabledTextColor(Color.BLACK);

        // ---------- Answer ----------
        aPanel.setLayout(new BorderLayout(0, 10));
        aPanel.setMinimumSize(new Dimension(200, 150));

        aLabel.setText("Answer");
        aLabel.setHorizontalAlignment(JLabel.CENTER);
        aLabel.setFont(new Font(null, Font.BOLD, 20));

        aTextArea.setMargin(new Insets(10, 10, 10, 10));
        aTextArea.setWrapStyleWord(true);
        aTextArea.setLineWrap(true);
        aTextArea.setDisabledTextColor(Color.BLACK);
    }

    @Override
    public void build() {

        // Card panel
        add(mainPanel, BorderLayout.CENTER);
        add(flipButton, BorderLayout.SOUTH);

        // Main panel
        mainPanel.add(splitPane);

        // Split pane
        splitPane.setLeftComponent(qPanel);
        splitPane.setRightComponent(aPanel);

        // Question
        qPanel.add(qLabel, BorderLayout.NORTH);
        qPanel.add(qScrollPane, BorderLayout.CENTER);
        qScrollPane.setViewportView(qTextArea);

        // Answer
        aPanel.add(aLabel, BorderLayout.NORTH);
        aPanel.add(aScrollPane, BorderLayout.CENTER);
        aScrollPane.setViewportView(aTextArea);
    }

    /**
     * Sets the card which is current being displayed.
     * 
     * @param card the card to set to current
     */
    void setCard(Card card) {
        currentCard = card;
        qTextArea.setText(currentCard.getQuestion());
        aTextArea.setText(currentCard.getAnswer());
    }

    /**
     * Gets the card which is current being displayed.
     */
    Card getCard() {
        currentCard.setQuestion(qTextArea.getText());
        currentCard.setAnswer(aTextArea.getText());
        return currentCard;
    }

    /**
     * Sets divider location to midle.
     */
    void resetDividerLocation() {
        splitPane.setDividerLocation(0.5);
    }

    /**
     * Shows or hides editor mode view.
     * 
     * @param flag the visibility to set
     */
    void setEditorModeVisible(boolean flag) {

        // Remove
        mainPanel.removeAll();

        if (flag) {

            // Enable both
            qTextArea.setEnabled(true);
            aTextArea.setEnabled(true);

            // Show split pane
            mainPanel.add(splitPane);
            splitPane.setLeftComponent(qPanel);
            qPanel.setVisible(true);
            splitPane.setRightComponent(aPanel);
            aPanel.setVisible(true);

            // Hide flip button
            flipButton.setVisible(false);
        } else {

            // Disable both
            qTextArea.setEnabled(false);
            aTextArea.setEnabled(false);

            // Show just one
            mainPanel.add(qPanel);
            qPanel.setVisible(true);
            mainPanel.add(aPanel);
            aPanel.setVisible(false);

            // Show flip button
            flipButton.setVisible(true);
        }
    }

    @Override
    public void setTheme(Theme theme) {
        if (theme == Theme.DARK) {
            qTextArea.setDisabledTextColor(Color.WHITE);
            aTextArea.setDisabledTextColor(Color.WHITE);
        } else {
            qTextArea.setDisabledTextColor(Color.BLACK);
            aTextArea.setDisabledTextColor(Color.BLACK);
        }
    }

    @Override
    public void addActions() {

        // Actions
        addButtonActions();
        addSplitPaneActions();
    }

    /**
     * Adds button actions to play with cards.
     */
    private void addButtonActions() {

        // Flip question/answer
        flipButton.addActionListener((ActionEvent e) -> {
            qPanel.setVisible(!qPanel.isVisible());
            aPanel.setVisible(!aPanel.isVisible());
        });
    }

    /**
     * Adds Split pane actions for {@link #splitPane}.
     */
    private void addSplitPaneActions() {

        // Divider
        ((BasicSplitPaneUI) splitPane.getUI())
                .getDivider().addMouseListener(new MouseAdapter() {

            // Relocates to midle when double click
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
                    resetDividerLocation();
                }
            }
        });
    }

}
