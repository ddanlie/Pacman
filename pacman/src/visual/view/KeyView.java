/**
 *  Class is representing key object on the screen
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
 * This class is responsible for the visual representation of a key object on the screen.
 * It implements the ComponentView interface.
 * It loads the image of the key from a file and paints it on the screen.
 * If the image is not found, it displays a simple "Key" text instead.
 * @see CommonMazeObject
 */
public class KeyView implements ComponentView
{
    private CommonMazeObject model;
    private FieldView parent;

    private Image keyImage = null;
    /**
     * Constructor for the KeyView class. It takes a parent FieldView and a CommonMazeObject as parameters.
     * It initializes the class fields and loads the key image from a file.
     * @param parent the parent FieldView
     * @param m the CommonMazeObject representing the key
     */
    public KeyView(FieldView parent, CommonMazeObject m)
    {
        this.model = m;
        this.parent = parent;
        try
        {
            keyImage = ImageIO.read(new File("../lib/sprites/key_sprite.png"));
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    /**
     * This method paints the key image on the screen.
     * If the image is not found, it displays a simple "Key" text instead.
     * @param g the Graphics object used to paint the component
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
            g.drawString("Key", (int)w/2, (int)h/2);
        }

    }
}
