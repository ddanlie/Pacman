/**
 *  Class is representing point object on the screen
 *
 * @author xdomra00 xdomra00@stud.fit.vutbr.cz
 * @author xgarip00 xgarip00@stud.fit.vutbr.cz
 */



package xdomra00.src.visual.view;
import xdomra00.src.visual.common.CommonMazeObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
/**
 * Class representing a view for a Point object in the game.
 * Displays the Point object on the screen as an image.
 * Implements the ComponentView interface.
 * @see CommonMazeObject
 */
public class PointView implements ComponentView
{
    private CommonMazeObject model;
    private FieldView parent;

    private Image keyImage = null;
    /**
     * Constructor for PointView class.
     * Initializes the model and parent variables, and loads the image to be displayed.
     *
     * @param parent The FieldView object that will contain the PointView object
     * @param m The CommonMazeObject object representing the Point in the game
     */
    public PointView(FieldView parent, CommonMazeObject m)
    {
        this.model = m;
        this.parent = parent;
        try
        {
            keyImage = ImageIO.read(new File("../lib/sprites/point_sprite.png"));
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    /**
     * Paints the Point object on the screen.
     * Displays the Point object as an image scaled to the size of the FieldView object.
     * If the image fails to load, displays "Point" as a string.
     *
     * @param g The Graphics object used to paint the Point on the screen
     */
    public void paintComponent(Graphics g)
    {
        Rectangle bounds = this.parent.getBounds();
        int w = (int)bounds.getWidth();
        int h = (int)bounds.getHeight();

        if(keyImage != null)
        {
            g.drawImage(keyImage.getScaledInstance(w, h, Image.SCALE_SMOOTH), 0, 0, (int)w, (int)h, null, null);
        }
        else
        {
            g.drawString("Point", (int)w/2, (int)h/2);
        }

    }
}
