/**
 *  Class is acting like a target to reach in game logic
 *
 * @author xdomra00 xdomra00@stud.fit.vutbr.cz
 * @author xgarip00 xgarip00@stud.fit.vutbr.cz
 */

package xdomra00.src.logic.game;
/**
 *  Class is acting like a target to reach in game logic
 */
public class TargetObject extends MazeObjectClass
{
    private boolean accessible;
    /**
     * Constructor for creating a TargetObject instance.
     * It sets the type of the object to MazeObjectType.TARGET
     * and sets the accessibility to false.
     */
    public TargetObject()
    {
        super();
        this.type = MazeObjectType.TARGET;
        accessible = false;
    }
    /**
     * Method to make the target accessible or inaccessible.
     * @param a A boolean value indicating if the target is accessible or not.
     */
    public void makeAccessible(boolean a)
    {
        accessible = a;
        notifyObservers();
    }
    /**
     * Method to check if the target is accessible or not.
     * @return A boolean value indicating if the target is accessible or not.
     */
    public boolean isAccessible()
    {
        return accessible;
    }
}
