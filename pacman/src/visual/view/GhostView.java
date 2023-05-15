/**
 *  Class is representing ghost object on the screen
 *
 * @author xdomra00 xdomra00@stud.fit.vutbr.cz
 * @author xgarip00 xgarip00@stud.fit.vutbr.cz
 */


package xdomra00.src.visual.view;

import xdomra00.src.logic.game.GhostObject;
import xdomra00.src.visual.common.CommonGhost;
import xdomra00.src.visual.common.CommonMazeObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
/**
 * Class representing the visual view of a ghost object on the screen.
 * It implements the ComponentView interface.
 * It loads the ghost image for the specified ghost color and paints it on the parent FieldView component.
 * If the image cannot be loaded, it displays a "Ghost" string in the center of the parent component.
 * @see GhostObject
 * @see CommonGhost
 * @see CommonMazeObject
 */
public class GhostView implements ComponentView
{
    private final CommonGhost model;
    private final FieldView parent;

    private Image ghostImage = null;
    /**
     * Constructor for GhostView class.
     * It initializes the instance with the specified CommonGhost model and FieldView parent.
     * It loads the ghost image for the specified ghost color and assigns it to the ghostImage field.
     *
     * @param parent the parent FieldView component where the ghost image will be painted
     * @param m the CommonGhost model for this view
     */
    public GhostView(FieldView parent, CommonGhost m)
    {
        this.model = m;
        this.parent = parent;
        try
        {
            String ghostFilename = "../lib/sprites/%s_ghost_sprite.gif";
            switch(model.getColor())
            {
                case RED:
                {
                    ghostFilename = String.format(ghostFilename, "red");
                    break;
                }
                case ORANGE:
                {
                    ghostFilename = String.format(ghostFilename, "orange");
                    break;
                }
                case PINK:
                {
                    ghostFilename = String.format(ghostFilename, "pink");
                    break;
                }
                case BLUE:
                {
                    ghostFilename = String.format(ghostFilename, "blue");
                    break;
                }
            }
            ghostImage = ImageIO.read(new File(ghostFilename));
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    /**
     * Paints the ghost image on the parent FieldView component.
     * If the image cannot be loaded, it displays a "Ghost" string in the center of the parent component.
     * @param g the Graphics object to be painted
     */
    public void paintComponent(Graphics g)
    {
        Graphics2D g2 = (Graphics2D)g;
        Rectangle bounds = this.parent.getBounds();
        int w = (int)bounds.getWidth();
        int h = (int)bounds.getHeight();

        if(ghostImage != null)
        {
            g.drawImage(ghostImage, 0, 0, (int)w, (int)h, null, null);
        }
        else
        {
            g.drawString("Ghost", (int)w/2, (int)h/2);
        }
    }
}