/**
 *   Class implementing this interface provides methods for maze object viewer
 *
 * @author xdomra00 xdomra00@stud.fit.vutbr.cz
 * @author xgarip00 xgarip00@stud.fit.vutbr.cz
 */

package xdomra00.src.visual.common;

import xdomra00.src.logic.common.Maze;
import xdomra00.src.logic.common.MazeObject;
/**
 * This interface provides methods for maze object viewer
 * @see MazeObject
 */
public interface CommonMazeObject extends Observable
{
    /**
     * Returns the type of the maze object.
     * @return the type of the maze object
     */
    MazeObject.MazeObjectType getObjectType();
    //public abstract CommonField getField();
}
