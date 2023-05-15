/**
 *  Class is acting like a player in game logic
 *
 * @author xdomra00 xdomra00@stud.fit.vutbr.cz
 * @author xgarip00 xgarip00@stud.fit.vutbr.cz
 */

package xdomra00.src.logic.game;
import xdomra00.src.logic.common.*;
import xdomra00.src.visual.common.CommonPacman;
import xdomra00.src.visual.common.Observable;

import java.util.ArrayList;
/**
 * This class represents the Pacman player in the game logic. It extends the MazeObjectClass and implements CommonPacman interface.
 *  It contains information about lives, scores, keys found and whether it is on target.
 *  It also has boolean flags that indicate whether it needs to get key, get point, or lose a life.
 *  It has methods for moving, getting a key or point, losing a life, and updating the object state.
 *  It can also set a GameLogger object for logging game events.
 * @see CommonPacman
 * @see Observable
 */
public class PacmanObject extends MazeObjectClass implements CommonPacman
{
    private int lives;
    private int scores;
    private int keysFound;
    private boolean onTarget;

    private boolean toGetKey;
    private boolean toGetPoint;

    private boolean toloseLive;
    private GameLogger logger = null;
    /**
     * Constructs a new PacmanObject with 3 lives, 0 keys found, and 0 scores.
     * It also sets the type of object as PLAYER.
     */
    public PacmanObject()
    {
        super();
        this.type = MazeObject.MazeObjectType.PLAYER;
        this.lives = 3;
        this.keysFound = 0;
        this.scores = 0;
        this.onTarget = false;
        this.toGetKey = false;
        this.toGetPoint = false;
        this.toloseLive = false;
    }
    /**
     * Updates the object state by checking the PathField object that is passed as the parameter.
     * If the object is on the target or it needs to get a key or point, it sets the corresponding boolean flags to true.
     * If the object encounters a ghost, it sets the toloseLive boolean flag to true.
     *
     * @param var1 PathField object that is passed as the parameter for updating the object state.
     */
    @Override
    public void update(Observable var1)
    {
        PathField pf = (PathField)var1;
        int size = pf.getAll().size();
        if(size < 2)
            return;
        onTarget = false;
        for(MazeObjectClass obj : pf.getAll())
        {
            switch(obj.getObjectType())
            {
                case GHOST:
                {
                    toloseLive = true;
                    break;
                }
                case KEY:
                {
                    if(logger != null)
                        logger.logKeyGot();
                    toGetKey = true;
                    break;
                }
                case TARGET:
                {
                    onTarget = true;
                    break;
                }
                case POINT:
                {
                    if(logger != null)
                        logger.logPointGot();
                    toGetPoint = true;
                    break;
                }
                default:
                {
                    break;
                }
            }
        }
    }


    /**
     * Moves the object in the specified direction if it is possible.
     * If the object needs to get a key, it calls the getKey() method.
     * If the object needs to get a point, it calls the getPoint() method.
     * If the object needs to lose a life, it calls the loseLife() method.
     *
     * @param dir Field.Direction object that represents the direction of the move.
     * @return True if the move is possible, false otherwise.
     */
    @Override
    public boolean move(Field.Direction dir)
    {
        boolean ret = super.move(dir);
        if(!ret)
            return false;
        if(toGetKey)
        {
            getKey();
            toGetKey = false;
        }
        if(toGetPoint)
        {
            getPoint();
            toGetPoint = false;
        }
        if(toloseLive)
        {
            loseLife();
            toloseLive = false;
        }
        return true;
    }
    /**
     * Decreases the number of lives of the player by one.
     * Notifies the visual observer about the change in the number of lives.
     * If the logger is set, logs the loss of a life.
     */
     public void loseLife()
     {
         this.lives--;
         if (lives < 0)
         {
             this.lives = 0;
         }
         else
         {
             this.notifyVisual();
             if(logger != null)
                 logger.logLiveLose();
         }
     }
    /**
     * Returns the number of lives of the player.
     * @return an integer representing the number of lives of the player
     */
    public int getLives()
     {
         return this.lives;
     }
    /**
     * Sets the number of lives for the player and notifies the visual representation of the change.
     * @param lives the new number of lives for the player
     */
    public void setLives(int lives)
    {
        this.lives = lives;
        notifyVisual();
    }
    /**
     * Returns the number of keys found by the player.
     * @return The number of keys found by the player.
     */
    public int getKeysCount()
    {
        return this.keysFound;
    }
    /**
     * Returns the current score of the player.
     * @return The current score of the player.
     */
    public int getScore()
    {
        return this.scores;
    }
    /**
     * Sets the score of the player.
     * @param scores The new score of the player.
     */
    public void setScore(int scores)
    {
        this.scores = scores;
    }
    /**
     * Sets the number of keys found by the player.
     * @param count The new number of keys found by the player.
     */
    public void setKeysCount(int count)
    {
        keysFound = count;
    }
    /**
     * Gets a key in the maze game by looping through all the MazeObjectClass objects in the game,
     * and if it finds an object of type KEY, it removes it from the game, increments the keysFound count,
     * and calls the notifyVisual method to update the game's visual representation.
     */
    public void getKey()
    {
        for(MazeObjectClass obj : this.getParent().getAll())
        {
            if(obj.getObjectType() == MazeObjectType.KEY)
            {
                this.getParent().remove(obj);
                this.keysFound++;
                notifyVisual();
                break;
            }
        }
    }
    /**
     * Collects a point in the maze by removing it from the parent and incrementing the score.
     * Notifies visual elements of the change.
     */
    public void getPoint()
    {
        for(MazeObjectClass obj : this.getParent().getAll())
        {
            if(obj.getObjectType() == MazeObjectType.POINT)
            {
                this.getParent().remove(obj);
                this.scores++;
                notifyVisual();
                break;
            }
        }
    }
    /**
     * Returns whether the player is currently on a target object.
     *
     * @return true if the player is on a target object, false otherwise.
     */
    public boolean isOnTarget()
    {
        return this.onTarget;
    }
    /**
     * Sets the logger for this player to the given GameLogger object.
     *
     * @param logger the GameLogger object to set as the logger for this player.
     */
    public void setLogger(GameLogger logger)
    {
        this.logger = logger;
    }
}