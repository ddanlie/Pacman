/**
 * The MenuPanel class represents the menu panel of the game. It contains three buttons: play game, replay, and exit.
 * The panel is used as the main menu screen of the game.
 * The class implements the OpenableFrame interface, which requires the implementation of the open() method, but
 * this method does nothing in this class.
 * The class uses a custom JButton implementation called CustomDefaultJbutton.
 * The class loads a background image from a file located in the "lib/backgrounds/menu_background.jpg" path and displays
 * it as the background of the panel.
 * @author xdomra00 xdomra00@stud.fit.vutbr.cz
 * @author xgarip00 xgarip00@stud.fit.vutbr.cz
 */
package xdomra00.src.frames;


import xdomra00.src.custom.CustomDefaultJbutton;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
/**
 * The MenuPanel class represents the menu panel of the game. It contains three buttons: play game, replay, and exit.
 * The panel is used as the main menu screen of the game.
 * The class implements the OpenableFrame interface, which requires the implementation of the open() method, but
 * this method does nothing in this class.
 * The class uses a custom JButton implementation called CustomDefaultJbutton.
 * The class loads a background image from a file located in the "lib/backgrounds/menu_background.jpg" path and displays
 * it as the background of the panel.
 */
public class MenuPanel extends JPanel implements OpenableFrame
{
    /**
     * Constructs a new instance of the MenuPanel class.
     * Creates a new JPanel with a BorderLayout, adds a flow panel to the south border and sets its layout to FlowLayout.
     * Creates three buttons using the CustomDefaultJbutton implementation and adds them to the flow panel.
     * Adds an empty border to the flow panel to position it at the bottom of the panel.
     */
    public MenuPanel()
    {
        super();
        setLayout(new BorderLayout());
        JPanel flowPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 0));
        flowPanel.setOpaque(false);

        JButton playGameButton = new CustomDefaultJbutton("PLAY");
        JButton replayButton = new CustomDefaultJbutton("REPLAY");
        JButton exitButton = new CustomDefaultJbutton("EXIT");

        flowPanel.add(playGameButton);
        flowPanel.add(replayButton);
        flowPanel.add(exitButton);
        flowPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 100, 0));
        this.add(flowPanel, BorderLayout.SOUTH);
    }
    /**
     * Returns the button with the specified name from the menu panel.
     * @param name the name of the button to retrieve.
     * @return the button with the specified name, or null if no button with that name is found.
     */
    private JButton getButton(String name)
    {
        for(Component c : ((JPanel)this.getComponents()[0]).getComponents())
        {
            if(c instanceof JButton)
            {
                if(((JButton)c).getText().equals(name))
                    return (JButton)c;
            }
        }
        return null;
    }
    /**
     * Returns the play game button from the menu panel.
     * @return the play game button.
     */
    public JButton getPlayGameButton()
    {
        return getButton("PLAY");
    }
    /**
     * Returns the replay button from the menu panel.
     * @return the replay button.
     */
    public JButton getReplayButton()
    {
        return getButton("REPLAY");
    }
    /**
     * Returns the exit button from the menu panel.
     * @return the exit button.
     */
    public JButton getExitButton()
    {
        return getButton("EXIT");
    }
    /**
     * Overrides the paintComponent() method to draw the background image of the menu panel.
     * @param g the Graphics object used to draw the component.
     */
    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Image backgroundImage = null;
        try
        {
            backgroundImage = ImageIO.read(new File("../lib/backgrounds/menu_background.jpg"));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
    }
    /**
     * The open method implements the OpenableFrame interface and is called when this frame is opened.
     * In this case, the method does nothing since the MenuPanel does not require any additional actions
     * to be performed when opened.
     */
    @Override
    public void open()
    {

    }
}
