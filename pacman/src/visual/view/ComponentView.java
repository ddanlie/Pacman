/**
 *   Class implementing this interface can be painted
 *
 * @author xdomra00 xdomra00@stud.fit.vutbr.cz
 * @author xgarip00 xgarip00@stud.fit.vutbr.cz
 */

package xdomra00.src.visual.view;

import java.awt.Graphics;
/**
 * This interface can be painted
 */
public interface ComponentView
{
    /**
     * Paints this component with the given graphics context.
     * @param var1 the graphics context to use for painting
     */
    void paintComponent(Graphics var1);
}
