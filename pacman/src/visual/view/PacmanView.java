/**
 *  Class is representing pacman object on the screen
 *
 * @author xdomra00 xdomra00@stud.fit.vutbr.cz
 * @author xgarip00 xgarip00@stud.fit.vutbr.cz
 */


package xdomra00.src.visual.view;

import xdomra00.src.logic.common.Field;
import xdomra00.src.logic.game.MazeObjectClass;
import xdomra00.src.logic.game.PacmanObject;
import xdomra00.src.visual.common.CommonMazeObject;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.*;
import javax.swing.*;
import java.awt.Image;
/**
 * This class represents the view of the Pacman object in the game. It displays the Pacman image on the screen
 * according to the Pacman's direction. It also contains the Pacman's model object and the field view where Pacman is located.
 * The Pacman images are loaded from the lib/sprites folder using ImageIO.read() method.
 * @see MazeObjectClass
 * @see CommonMazeObject
 * @see Field
 * @see PacmanObject
 */
public class PacmanView implements ComponentView
{
    private CommonMazeObject model;
    private FieldView parent;

    private Image pacmanImageUp = null;
    private Image pacmanImageDown = null;
    private Image pacmanImageLeft = null;
    private Image pacmanImageRight = null;
    /**
     *  Constructs a new PacmanView object with the specified FieldView parent and CommonMazeObject model.
     *  It loads the Pacman images from the lib/sprites folder using ImageIO.read() method and catches IO exception.
     *
     *  @param parent the FieldView parent of the PacmanView
     *  @param m the CommonMazeObject model of the PacmanView
     */
    public PacmanView(FieldView parent, CommonMazeObject m)
    {
        this.model = m;
        this.parent = parent;
        //System.out.println(((PacmanObject)m).getLives());
        try
        {
            pacmanImageUp = ImageIO.read(new File("../lib/sprites/pacman_up_sprite.png"));
            pacmanImageDown = ImageIO.read(new File("../lib/sprites/pacman_down_sprite.png"));
            pacmanImageLeft = ImageIO.read(new File("../lib/sprites/pacman_left_sprite.png"));
            pacmanImageRight = ImageIO.read(new File("../lib/sprites/pacman_right_sprite.png"));
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    /**
     *  Paints the Pacman object on the screen according to its direction.
     *  If the Pacman is not moving, it displays the text "Pacman" on the screen.
     *
     *  @param g the Graphics object to paint the Pacman image
     */
    public void paintComponent(Graphics g)
    {
        Rectangle bounds = this.parent.getBounds();
        int w = (int)bounds.getWidth();
        int h = (int)bounds.getHeight();

        Field.Direction dir = ((MazeObjectClass)model).getMovingDirection();
        if(dir != null)
        {
            switch(dir)
            {
                case U:
                {
                    g.drawImage(pacmanImageUp.getScaledInstance(w, h, Image.SCALE_SMOOTH), 0, 0, (int)w, (int)h, null, null);
                    break;
                }
                case D:
                {
                    g.drawImage(pacmanImageDown.getScaledInstance(w, h, Image.SCALE_SMOOTH), 0, 0, (int)w, (int)h, null, null);
                    break;
                }
                case L:
                {
                    g.drawImage(pacmanImageLeft.getScaledInstance(w, h, Image.SCALE_SMOOTH), 0, 0, (int)w, (int)h, null, null);
                    break;
                }
                case R:
                {
                    g.drawImage(pacmanImageRight.getScaledInstance(w, h, Image.SCALE_SMOOTH), 0, 0, (int)w, (int)h, null, null);
                    break;
                }
            }

        }
        else
        {
            g.drawString("Pacman", (int)w/2, (int)h/2);
        }

    }
}
