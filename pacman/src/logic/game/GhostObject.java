/**
 *  Class is acting like a ghost hunting a player
 *
 * @author xdomra00 xdomra00@stud.fit.vutbr.cz
 * @author xgarip00 xgarip00@stud.fit.vutbr.cz
 */

package xdomra00.src.logic.game;
import xdomra00.src.logic.common.*;
import xdomra00.src.visual.common.*;
import java.util.Random;
/**
 * Represents a ghost that hunts a player in the game.
 * Extends MazeObjectClass and implements CommonGhost.
 * Each instance of the GhostObject has a unique identifier.
 * The color of the ghost is randomly assigned from GhostColor enum.
 * The class provides methods to get the type, color, and id of the ghost.
 * The class also overrides the update method from the Observable interface.
 */
public class GhostObject extends MazeObjectClass implements CommonGhost
{
    /**
     * Represents the color of the ghost
     */
    public enum GhostColor
    {
        /**
         * Types of ghosts
         */
        RED,
        /**
         * PINK
         */
        PINK,
        /**
         * ORANGE
         */
        ORANGE,
        /**
         * BLUE
         */
        BLUE
    }

    private final GhostColor ghostColor;

    private static int instanceCounter = 1;
    private final int id;
    /**
     * Constructor that initializes the GhostObject instance.
     * Sets the type of the ghost to MazeObject.MazeObjectType.GHOST.
     * Assigns a random color from GhostColor enum to the ghost.
     * Assigns a unique identifier to the ghost.
     */
    public GhostObject()
    {
        super();
        this.type = MazeObject.MazeObjectType.GHOST;
        this.ghostColor = GhostColor.values()[new Random().nextInt(GhostColor.values().length)];
        id = instanceCounter;
        instanceCounter++;
    }
    /**
     * Returns the type of the ghost as MazeObjectType.GHOST.
     * @return the type of the ghost as MazeObjectType.GHOST
     */
    @Override
    public MazeObjectType getObjectType()
    {
        return MazeObjectType.GHOST;
    }
    /**
     * Returns the color of the ghost.
     * @return the color of the ghost
     */
    @Override
    public GhostColor getColor()
    {
        return this.ghostColor;
    }
    /**
     * An empty implementation of the update method from the Observable interface.
     * @param var1 the Observable object
     */
    @Override
    public void update(Observable var1)
    {

    }
    /**
     * Returns the unique identifier of the ghost.
     * @return the unique identifier of the ghost
     */
    public int getId()
    {
        return this.id;
    }

    /**
     * Resets the instance counter to 1.
     */
    public static void reinitIds()
    {
        instanceCounter = 1;
    }
}

