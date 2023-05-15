/**
 * A custom implementation of JPanel that allows for a background image to be set and painted.
 * It extends the JPanel class and overrides the paintComponent method to paint the background image.
 * The background image is scaled to the size of the panel using the Image.SCALE_SMOOTH algorithm.
 * @author xdomra00 xdomra00@stud.fit.vutbr.cz
 * @author xgarip00 xgarip00@stud.fit.vutbr.cz
 */
package xdomra00.src.custom;

import javax.swing.*;
import java.awt.*;
/**
 * A custom implementation of JPanel that allows for a background image to be set and painted.
 * It extends the JPanel class and overrides the paintComponent method to paint the background image.
 * The background image is scaled to the size of the panel using the Image.SCALE_SMOOTH algorithm.
 * @see JPanel
 * @see Image
 */
public class CustomJpanelBackground extends JPanel
{
    /**
     * This variable stores the background image used by the GUI. It is initialized as null and assigned an image later on.
     */
    private Image bckImg = null;
    /**
     * Sets the background image to be painted in the panel.
     * @param background the image to be set as the background
     */
    public void setBackgroundImage(Image background)
    {
        bckImg = background;
    }
    /**
     * Paints the component, including the background image if it is set.
     * Overrides the paintComponent method of the parent class.
     * @param g the Graphics object to paint with
     */
    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        if(bckImg == null)
            return;
        Rectangle bounds = this.getBounds();
        int w = (int)bounds.getWidth();
        int h = (int)bounds.getHeight();
        g.drawImage(bckImg.getScaledInstance(w, h, Image.SCALE_SMOOTH), 0, 0, (int)w, (int)h, null, null);
    }
}
