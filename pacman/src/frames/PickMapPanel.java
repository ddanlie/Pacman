/**
 * The PickMapPanel class represents the panel that allows the player to choose a map to play on.
 * It extends JPanel and implements the OpenableFrame interface.
 * The panel contains buttons to navigate between maps and a "PLAY" button to start the game.
 * The chosen map is displayed in the center of the panel.
 * @author xdomra00 xdomra00@stud.fit.vutbr.cz
 * @author xgarip00 xgarip00@stud.fit.vutbr.cz
 */
package xdomra00.src.frames;

import xdomra00.src.custom.CustomDefaultJbutton;
import xdomra00.src.logic.game.*;
import xdomra00.src.visual.GamePresenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Objects;
/**
 * The PickMapPanel class represents the panel that allows the player to choose a map to play on.
 * It extends JPanel and implements the OpenableFrame interface.
 * The panel contains buttons to navigate between maps and a "PLAY" button to start the game.
 * The chosen map is displayed in the center of the panel.
 * @see CustomDefaultJbutton
 * @see GamePresenter
 */
public class PickMapPanel extends JPanel implements OpenableFrame
{
    /**
     * An integer representing the number of the currently selected map.
     */
    private int currentMap = 1;
    /**
     * A MazeClass object representing the maze of the currently selected map.
     */
    private MazeClass currentMaze = null;
    /**
     * A MazeConfigure object used to read and configure mazes.
     */
    private MazeConfigure mapReader = null;
    /**
     * A string representing the file path pattern for the map files.
     */
    private final String mapName = "../data/maps/mapa%d.txt";
    /**
     * An integer representing the maximum number of maps available.
     */
    private int maxMaps;
    /**
     * A JButton object used to start the game.
     */
    private JButton playButton;
    /**
     * Initializes the PickMapPanel and creates the necessary components.
     * The panel contains buttons to navigate between maps and a "PLAY" button to start the game.
     * The chosen map is displayed in the center of the panel.
     */
    public PickMapPanel()
    {
        super();

        setLayout(new OverlayLayout(this));

        JPanel borderPanel = new JPanel(new BorderLayout());
        JPanel flowPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 0));
        flowPanel.setOpaque(false);
        borderPanel.setOpaque(false);

        JButton prevMapButton = new CustomDefaultJbutton(
                new ImageIcon(new ImageIcon("../lib/icons/button_prev_icon.png").getImage().getScaledInstance(120, 80, Image.SCALE_SMOOTH)));
        prevMapButton.setOpaque(false);
        prevMapButton.setBorder(BorderFactory.createEmptyBorder());
        prevMapButton.addActionListener(e ->
        {
                reopen(false);
        });

        playButton = new CustomDefaultJbutton("PLAY");
        playButton.setContentAreaFilled(true);

        JButton nextMapButton = new CustomDefaultJbutton(
                new ImageIcon(new ImageIcon("../lib/icons/button_next_icon.png").getImage().getScaledInstance(120, 80, Image.SCALE_SMOOTH)));
        nextMapButton.setOpaque(false);
        nextMapButton.setBorder(BorderFactory.createEmptyBorder());
        nextMapButton.addActionListener(e ->
        {
                reopen(true);
        });

        flowPanel.add(prevMapButton);
        flowPanel.add(playButton);
        flowPanel.add(nextMapButton);
        borderPanel.add(flowPanel, BorderLayout.SOUTH);
        borderPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 100, 0));

        this.add(borderPanel);
        this.setComponentZOrder(borderPanel, 0);
    }

    /**
     * Reads the number of available maps and sets the current map to the first one.
     * Initializes the MazeConfigure object for reading maps.
     * Displays the current map in the center of the panel.
     */
    public void open()
    {
        try
        {
            File f = new File("../data/maps/");
            maxMaps = (Objects.requireNonNull(f.listFiles()).length);
        }
        catch(Exception e)
        {
            maxMaps = -1;
        }
        if(mapReader == null)
            mapReader = new MazeConfigure();

        currentMaze = mapReader.createMaze(String.format(mapName, currentMap));
        if(this.getComponentCount() > 1)
            this.remove(1);
        JPanel result;
        if(currentMaze == null)
        {
            result = new JPanel();
            result.add(new JLabel("Empty"), 1);
        }
        else
        {
            result = new GamePresenter(currentMaze);
            this.add(result, 1);
            ((GamePresenter)result).open();
        }
    }

    /**
     * The reopen method changes the current map that is being displayed by increasing
     * or decreasing the currentMap index, and then calls the open method to display the
     * new map. It also calls validate and repaint to update the GUI.
     * @param side a boolean value indicating the direction to move in the map list.
     * If false, the method moves to the previous map. If true, it moves
     * to the next map.
     */
    private void reopen(boolean side)
    {
        if(!side)
            currentMap--;
        else
            currentMap++;
        if(currentMap < 1)
        {
            currentMap = maxMaps;
        }
        if(currentMap > maxMaps)
        {
            currentMap = 1;
        }
        open();
        validate();
        repaint();
    }
    /**
     * Returns the play button component of the panel.
     * @return the JButton object that represents the play button
     */
    public JButton getPlayButton()
    {
        return playButton;
    }
    /**
     * Returns the MazeClass object that represents the currently selected map.
     * @return the MazeClass object that represents the currently selected map
     */
    public MazeClass getChosenMap()
    {
        return currentMaze;
    }
}
