package com.flashcards.views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicSplitPaneUI;

import com.jthemedetecor.OsThemeDetector;

/**
 * The Graphical User Interface of <code>Flash cards App</code>.
 * 
 * <p>
 * This is the main view class, which implements all view classes.
 * </p>
 * 
 * @see {@link com.flashcards.App}
 */
public class GUI extends JFrame implements View {

    // Look and Feel
    private static boolean isSysLaf;
    static {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            isSysLaf = true;
        } catch (Exception ex) {
            ex.printStackTrace();
            isSysLaf = false;
        }
    }

    // OS Theme
    private static final OsThemeDetector detector = OsThemeDetector.getDetector();
    public static Theme osTheme = detector.isDark() ? Theme.DARK : Theme.LIGHT;

    // Root panel
    private JPanel rootPanel;

    // Split pane (Side panel + Card panel)
    private JSplitPane splitPane;

    // App components
    AppMenuBar menuBar;
    SidePanel sidePanel;
    CardPanel cardPanel;

    /**
     * Initializes the GUI and shows it.
     */
    public GUI() {

        // Init
        rootPanel = new JPanel();
        splitPane = new JSplitPane();
        menuBar   = new AppMenuBar(this);
        sidePanel = new SidePanel(this);
        cardPanel = new CardPanel(this);

        // OS Theme
        if (isSysLaf) {
            detector.registerListener(isDark -> {
                if (isDark) { setTheme(Theme.DARK); }
                else { setTheme(Theme.LIGHT); }
            }); setTheme(osTheme);
        }
        
        // Make view
        style();
        build();
        addActions();
        
        // Show
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        
        // Default view
        setDefaultView();
    }

    @Override
    public void style() {

        // GUI
        setTitle("Flash Cards");

        // Root panel
        rootPanel.setLayout(new BorderLayout());
        rootPanel.setPreferredSize(new Dimension(600, 200));

        // Split pane
        splitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        splitPane.setDividerSize(10);
    }

    @Override
    public void build() {

        // GUI
        add(menuBar, BorderLayout.NORTH);
        add(rootPanel, BorderLayout.CENTER);

        // Root panel
        rootPanel.add(splitPane);

        // Split pane
        splitPane.setLeftComponent(sidePanel);
        splitPane.setRightComponent(cardPanel);
    }

    /**
     * Sets the default view.
     * 
     * <p> Default is:
     * <ul>
     *     <li><strong>Side panel</strong>: on
     *     <li><strong>Editor mode</strong>: off
     * </ul>
     * </p>
     */
    void setDefaultView() {

        // Side panel
        menuBar.setSidePanelSelected(true);
        
        // Editor mode
        menuBar.setEditorModeSelected(false);
    }

    /**
     * Sets GUI minimum size to fit sub components.
     * 
     * <p>
     * Use this method to reset GUI size after view change.
     * </p>
     */
    void resetMinimumSize() {

        // Split pane
        splitPane.resetToPreferredSizes();

        // GUI
        setMinimumSize(this.getLayout().minimumLayoutSize(this));
        revalidate();
    }

    /**
     * Shows or hides the side panel,
     * based on the passed flag.
     * 
     * @param flag the flag to set the visibility
     */
    void setSidePanelVisible(boolean flag) {

        // Remove all components
        rootPanel.removeAll();

        // Change view
        if (flag) {
            rootPanel.add(splitPane);
            splitPane.setRightComponent(cardPanel);
        } else {
            rootPanel.add(cardPanel);
        }

        // Add/remove border
        if (flag) {
            cardPanel.setBorder(null);
        } else {
            cardPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        }
    }

    @Override
    public void setTheme(Theme theme) {
        osTheme = theme;

        // Sub views
        sidePanel.setTheme(theme);
        cardPanel.setTheme(theme);
        menuBar.setTheme(theme);
    }

    @Override
    public void addActions() {
        
        // Actions
        addSplitPaneActions();
    }

    /**
     * Adds Split pane actions for {@link #splitPane}.
     */
    void addSplitPaneActions() {

        // Divider
        ((BasicSplitPaneUI) splitPane.getUI())
                .getDivider().addMouseListener(new MouseAdapter() {

            // Fit content
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
                    splitPane.setDividerLocation(-1);
                }
            }
        });
    }

}