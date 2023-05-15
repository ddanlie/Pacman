/**
 *  Class is representing game status on the screen
 *
 * @author xdomra00 xdomra00@stud.fit.vutbr.cz
 * @author xgarip00 xgarip00@stud.fit.vutbr.cz
 */


package xdomra00.src.visual.view;

import xdomra00.src.frames.MainFrame;
import xdomra00.src.logic.game.PacmanObject;
import xdomra00.src.visual.common.*;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.*;
/**
 * Class is representing game status on the screen
 * @see PacmanObject
 * @see MainFrame
 * @see Observable
 * @see CommonPacman
 */
public class GameStatusView extends JPanel implements Observable.Observer
{
    /**
     * An instance of CommonPacman which represents the Pacman in the game.
     */
    private CommonPacman model;
    /**
     * An ImageIcon object used to represent a heart image on the UI.
     */
    private ImageIcon heartIcon = null;
    /**
     *  A JLabel object used to display the current score on the UI.
     */
    JLabel score;
    /**
     * A JPanel object used as a container for the flowPanel.
     */
    JPanel borderPanel;
    /**
     *  A JPanel object used as a container for the heart icons and score label on the UI.
     */
    JPanel flowPanel;
    /**
     * A view class that displays the current game status including the remaining lives of the player
     * represented by heart icons.
     * This class implements the Observer interface and observes changes in the CommonPacman model.
     * @param m the CommonPacman model to observe
     */
    public GameStatusView(CommonPacman m)
    {
        this.model = m;
        this.model.addObserver(this);

        setPreferredSize(new Dimension(100, 50));
        setBackground(Color.BLUE);
        score = new JLabel("SCORE: ");
        score.setForeground(Color.white);
        borderPanel = new JPanel(new BorderLayout());
        borderPanel.setOpaque(false);
        flowPanel = new JPanel();
        flowPanel.setOpaque(false);
        borderPanel.add(score, BorderLayout.EAST);
        borderPanel.add(Box.createRigidArea(new Dimension(100, 0)), BorderLayout.CENTER);
        borderPanel.add(flowPanel, BorderLayout.WEST);
        this.add(borderPanel);
        try {
            Image heartImage = ImageIO.read(new File("../lib/sprites/heart_sprite.png"));
            heartIcon = new ImageIcon(heartImage.getScaledInstance(30, 30, Image.SCALE_SMOOTH));
        } catch (IOException e) {
            e.printStackTrace();
        }
        update(model);
    }
    /**
     * Overrides the default paintComponent method to paint the component. Draws a heart icon if available, otherwise draws a "Heart" string at the center of the component.
     * @param g the graphics object used to paint the component
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Rectangle bounds = this.getBounds();
        int w = (int) bounds.getWidth();
        int h = (int) bounds.getHeight();

        if (heartIcon == null) {
            g.drawString("Heart", (int) w / 2, (int) h / 2);
        }
    }
    /**
     * This method updates the GameStatusView whenever the model is changed.
     * It removes all components from the view, creates a new JLabel for each life of the PacmanObject model,
     * adds them to the view, and then validates and repaints the view.
     * It is executed on the Event Dispatch Thread.
     * @param var1 the observable object that triggered the update
     */
    @Override
    public void update(Observable var1) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                flowPanel.removeAll();
                int lives = ((PacmanObject) model).getLives();
                int maxLives = 5;
                for (int i = 0; i < maxLives; i++, lives--)
                {
                    JLabel heartLabel = new JLabel(heartIcon);
                    Dimension size = heartLabel.getPreferredSize();
                    if(lives <= 0)
                    {
                        JLabel emptyLabel = new JLabel();
                        emptyLabel.setPreferredSize(size);
                        flowPanel.add(emptyLabel);
                    }
                    else {
                        flowPanel.add(heartLabel);
                    }

                }
                ((JLabel)borderPanel.getComponents()[0]).setText("Points: %d".formatted(((PacmanObject) model).getScore()));
                validate();
                repaint();
            }
        });

    }



}