/**
 *   Class implementing this interface provides methods for ghost viewer
 *
 * @author xdomra00 xdomra00@stud.fit.vutbr.cz
 * @author xgarip00 xgarip00@stud.fit.vutbr.cz
 */

package xdomra00.src.visual.common;

import xdomra00.src.logic.game.GhostObject;
/**
 * This interface provides methods for ghost viewer
 * @see GhostObject
 */
public interface CommonGhost extends CommonMazeObject
{
    /**
     * Returns the color of the GhostObject instance.
     * @return the color of the GhostObject
     */
    GhostObject.GhostColor getColor();
}
