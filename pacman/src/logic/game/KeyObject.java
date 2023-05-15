/**
 * Class is acting like a key to take before reaching the target
 *
 * @author xdomra00 xdomra00@stud.fit.vutbr.cz
 * @author xgarip00 xgarip00@stud.fit.vutbr.cz
 */


package xdomra00.src.logic.game;

import xdomra00.src.logic.common.MazeObject;
/**
 * The KeyObject class represents a key object in the game maze that the player needs to take before reaching the target.
 * It extends the MazeObjectClass and sets its type as MazeObjectType.KEY.
 * It has a default constructor that calls the super constructor of MazeObjectClass to initialize its type.
 * This class does not have any additional fields or methods.
 * @see MazeObjectClass
 */
public class KeyObject  extends MazeObjectClass
{
    /**
     *  Constructor that creates a new KeyObject instance and sets its type as MazeObjectType.KEY.
     */
    public KeyObject()
    {
        super();
        this.type = MazeObjectType.KEY;
    }

}
