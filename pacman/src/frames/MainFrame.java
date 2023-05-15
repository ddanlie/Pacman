/**
 * The MainFrame class represents the main JFrame of the game. It contains the menu panel, game panel, pick map panel,
 * and replay panel, and allows the user to navigate between them.
 * @author xdomra00 xdomra00@stud.fit.vutbr.cz
 * @author xgarip00 xgarip00@stud.fit.vutbr.cz
 */
package xdomra00.src.frames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.Toolkit;
/**
 * The MainFrame class represents the main JFrame of the game. It contains the menu panel, game panel, pick map panel,
 * and replay panel, and allows the user to navigate between them.
 */
public class MainFrame extends JFrame
{
    /**
     * An enumeration of panel names used to navigate between the panels of the game.
     */
    enum PanelNames
    {
        /**
         * Menu
         */
        MENU_PANEL("menu panel"),
        /**
         * Game
         */
        GAME_PANEL("game panel"),
        /**
         * Choosing map
         */
        PICK_MAP_PANEL("pick map panel"),
        /**
         * Replay
         */
        REPLAY_PANEL("replay panel");

        private final String stringValue;
        /**
         * Creates a new PanelNames enum constant with the specified string value.
         * @param stringValue the string value of the enum constant
         */
        PanelNames(String stringValue)
        {
            this.stringValue = stringValue;
        }
        /**
         * Returns the string value of the enumeration.
         *
         * @return The string value of the enumeration.
         */
        public String getStringValue()
        {
            return stringValue;
        }
    }

    /**
     * An instance of the MenuPanel class representing the main menu of the game.
     */
    private MenuPanel menuPanel;
    /**
     * An instance of the GamePanel class representing the game interface where the game is played.
     */
    private GamePanel gamePanel;
    /**
     * An instance of the PickMapPanel class representing the panel where the player can choose the map they want to play.
     */
    private PickMapPanel pickMapPanel;
    /**
     * An instance of the ReplayPanel class representing the panel where the player can replay a game.
     */
    private ReplayPanel replayPanel;
    /**
     * A JPanel instance representing the container panel that holds the other panels and enables the player to switch between them using a card layout.
     */
    private JPanel cardPanel;
    /**
     * Constructs a new instance of the MainFrame class, initializes its components and sets up its layout.
     */
    public MainFrame()
    {
        super();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setFocusable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1280, 720));
        setLocation((int)screenSize.getWidth()/2 - (1280/2), (int)screenSize.getHeight()/2 - (720/2));

        this.menuPanel = new MenuPanel();
        this.gamePanel = new GamePanel();
        this.pickMapPanel = new PickMapPanel();
        this.replayPanel = new ReplayPanel();
        this.cardPanel = new JPanel(new CardLayout());

        this.cardPanel.add(this.menuPanel, PanelNames.MENU_PANEL.getStringValue());
        this.cardPanel.add(this.gamePanel, PanelNames.GAME_PANEL.getStringValue());
        this.cardPanel.add(this.pickMapPanel, PanelNames.PICK_MAP_PANEL.getStringValue());
        this.cardPanel.add(this.replayPanel, PanelNames.REPLAY_PANEL.getStringValue());

        this.add(this.cardPanel);
        this.pack();

        this.addListeners();

        this.changePanel(PanelNames.MENU_PANEL);

        this.setVisible(true);
    }
    /**
     * Changes the current panel being displayed by the main frame.
     * @param name The name of the panel to switch to.
     */
    private void changePanel(PanelNames name)
    {
        ((OpenableFrame)cardPanel.getComponents()[name.ordinal()]).open();
        ((CardLayout)this.cardPanel.getLayout()).show(this.cardPanel, name.getStringValue());
    }
    /**
     * Adds listeners to various components of the main frame.
     */
    private void addListeners()
    {
        addKeyListener(gamePanel);
        this.menuPanel.getPlayGameButton().addActionListener(e -> this.changePanel(PanelNames.PICK_MAP_PANEL));
        this.menuPanel.getReplayButton().addActionListener(e -> this.changePanel(PanelNames.REPLAY_PANEL));
        this.menuPanel.getExitButton().addActionListener(e -> dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING)));
        //this.pickMapPanel.getMenuButton().addActionListener(e -> this.changePanel(PanelNames.MENU_PANEL));
        this.pickMapPanel.getPlayButton().addActionListener(e -> this.changePanel(PanelNames.GAME_PANEL));
        this.gamePanel.getMenuButton().addActionListener(e -> this.changePanel(PanelNames.MENU_PANEL));
        this.replayPanel.getMenuButton().addActionListener(e -> this.changePanel(PanelNames.MENU_PANEL));
    }
    /**
     * Returns the instance of the PickMapPanel in this MainFrame.
     * @return the PickMapPanel instance
     */
    public PickMapPanel getPickMapPanel()
    {
        return pickMapPanel;
    }
}