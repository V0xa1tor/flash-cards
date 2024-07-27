package com.flashcards.views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicSplitPaneUI;

import com.flashcards.controllers.Controller;
import com.jthemedetecor.OsThemeDetector;

/**
 * The Graphical User Interface of <code>Flash cards App</code>.
 * 
 * <p>
 * This is the main view class, which implements all view classes
 * </p>
 * 
 * @see {@link com.flashcards.App}
 */
public class GUI extends JFrame implements View {

    // Look and Feel
    static {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Root panel
    private final JPanel ROOT_PANEL = new JPanel();

    // Split pane (Side panel + Card panel)
    private final JSplitPane SPLIT_PANE = new JSplitPane();

    // App components
    public final CardPanel CARD_PANEL = new CardPanel();
    public final SidePanel SIDE_PANEL = new SidePanel(this);
    public final AppMenuBar MENU_BAR = new AppMenuBar(this);

    // OS Theme
    private static final OsThemeDetector detector = OsThemeDetector.getDetector();
    public static Theme osTheme = detector.isDark() ? Theme.DARK : Theme.LIGHT;

    /**
     * Initializes the GUI and shows it.
     * <p>
     * Also sets the controller.
     * </p>
     * 
     * @see {@link com.flashcards.controllers.Controller}
     */
    public GUI(Controller controller) {

        // Make
        style();
        build();

        // Set controller
        SIDE_PANEL.setController(controller);
        MENU_BAR.setController(controller);
        
        // OS Theme
        detector.registerListener(isDark -> {
            if (isDark) { setTheme(Theme.DARK); }
            else { setTheme(Theme.LIGHT); }
        }); setTheme(osTheme);
        
        // Actions
        addSplitPaneActions();
        
        // Show
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        
        // Default view
        MENU_BAR.setDefaultView();
    }

    private void addSplitPaneActions() {

        // Divider
        ((BasicSplitPaneUI) SPLIT_PANE.getUI())
                .getDivider().addMouseListener(new MouseAdapter() {

            // Fit content
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
                    SPLIT_PANE.setDividerLocation(-1);
                }
            }
        });
    }

    @Override
    public void build() {

        // GUI
        add(MENU_BAR, BorderLayout.NORTH);
        add(ROOT_PANEL, BorderLayout.CENTER);

        // Root panel
        ROOT_PANEL.add(SPLIT_PANE);

        // Split pane
        SPLIT_PANE.setLeftComponent(SIDE_PANEL);
        SPLIT_PANE.setRightComponent(CARD_PANEL);
    }

    @Override
    public void style() {

        // GUI
        setTitle("Flash Cards");

        // Root panel
        ROOT_PANEL.setLayout(new BorderLayout());
        ROOT_PANEL.setPreferredSize(new Dimension(600, 200));

        // Split pane
        SPLIT_PANE.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        SPLIT_PANE.setBorder(new EmptyBorder(10, 10, 10, 10));
        SPLIT_PANE.setDividerSize(10);
    }

    /**
     * Sets GUI minimum size to fit sub components.
     * <p>
     * Use this method to reset GUI size after view change.
     * </p>
     */
    void resetSize() {
        SPLIT_PANE.resetToPreferredSizes();
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
        ROOT_PANEL.removeAll();

        // Change view
        if (flag) {
            ROOT_PANEL.add(SPLIT_PANE);
            SPLIT_PANE.setRightComponent(CARD_PANEL);
        } else {
            ROOT_PANEL.add(CARD_PANEL);
        }

        // Add/remove border
        if (flag) {
            CARD_PANEL.setBorder(null);
        } else {
            CARD_PANEL.setBorder(new EmptyBorder(10, 10, 10, 10));
        }
    }

    @Override
    public void setTheme(Theme theme) {
        osTheme = theme;

        // Side panel
        SIDE_PANEL.setTheme(theme);
    }

}