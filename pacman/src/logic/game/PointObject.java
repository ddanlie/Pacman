/**
 *  Class is acting like a score point in game logic
 *
 * @author xdomra00 xdomra00@stud.fit.vutbr.cz
 * @author xgarip00 xgarip00@stud.fit.vutbr.cz
 */

package xdomra00.src.logic.game;
import xdomra00.src.logic.common.MazeObject;
/**
 * Represents a score point in the game logic.
 * This class inherits from the abstract class MazeObjectClass and
 * defines the POINT object type. It is used to create a point
 * object that can be added to the game maze.
 *
 * @see MazeObject
 */
public class PointObject extends MazeObjectClass
{
    /**
     * Constructs a new PointObject instance.
     * Creates a point object with the POINT object type.
     */
    public PointObject()
    {
        super();
        this.type = MazeObjectType.POINT;
    }
}
