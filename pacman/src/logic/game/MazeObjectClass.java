/**
 *  Class provides general methods for all object which can be put on free field
 *
 * @author xdomra00 xdomra00@stud.fit.vutbr.cz
 * @author xgarip00 xgarip00@stud.fit.vutbr.cz
 */

package xdomra00.src.logic.game;
import xdomra00.src.logic.common.*;
import xdomra00.src.visual.common.*;

import java.io.File;
import java.util.HashSet;
import java.util.Set;
/**
 * MazeObjectClass is an abstract class that provides general methods for all objects that can be put on a free field
 * and implements the MazeObject, CommonMazeObject, and Observable.Observer interfaces. It contains methods for checking
 * if an object can move in a given direction, moving an object to a given direction if it can, and notifying observers
 * of any changes. It also contains methods for setting and getting the object's type and parent field, and for comparing
 * objects for equality.
 * This class is meant to be extended by other classes that represent specific types of maze objects, such as Pacman and
 * Ghost.
 * @see MazeObject
 * @see CommonMazeObject
 * @see Observable.Observer
 */
public abstract class MazeObjectClass implements MazeObject, CommonMazeObject, Observable.Observer
{
    /**
     * The parent field of the maze object.
     */
    protected FieldClass parent;
    /**
     * The type of the maze object.
     */
    protected MazeObjectType type;
    /**
     * The current moving direction of the maze object.
     */
    protected Field.Direction movingDirection;
    /**
     *  // A set of observers that are notified about changes in the maze object.
     */
    private Set<Observer> observers;
    /**
     * Constructs a new MazeObjectClass object.
     * Initializes the observers set to a new HashSet and sets the parent to null.
     */
    protected MazeObjectClass()
    {
        this.observers = new HashSet<>();
        this.parent = null;
        movingDirection = Field.Direction.R;
    }
    /**
     * Sets the type of this object.
     * @param type the new type of this object
     */
    public void setType(MazeObjectType type)
    {
        this.type = type;
    }
    /**
     * Gets the type of this object.
     * @return the type of this object
     */
    public MazeObjectType getObjectType()
    {
        return this.type;
    }
    /**
     * Compares this object to another object for equality.
     * Two MazeObjectClass objects are considered equal if they have the same type.
     * @param obj the object to compare to
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj)
    {
        if(obj.getClass() != this.getClass())
            return false;
        MazeObjectClass mazeObj = (MazeObjectClass)obj;
        return this.type == mazeObj.type;
    }
    /**
     * Sets the parent of this object.
     * @param parent the new parent field of this object
     */
    public void setParent(Field parent)
    {
        this.parent = (FieldClass)parent;
    }
    /**
     * Gets the parent of this object.
     * @return the parent field of this object
     */
    public FieldClass getParent() { return this.parent; }
    /**
     * Returns the current moving direction of the player.
     * @return the moving direction of the player
     */
    public Field.Direction getMovingDirection()
    {
        return this.movingDirection;
    }


    //CommonMazeObject functions

    /**
     * Checks if object can be moved to a given direction
     *
     * @param dir - direction to check if object can move there
     * @return true if can, false if not
     */
    @Override
    public boolean canMove(Field.Direction dir)
    {
        Field f = this.parent.nextField(dir);
        if(f != null)
            return f.canMove();
        return false;
    }


    /**
     * Moves object can be moved to a given direction if can
     *
     * @param dir - direction to check if object can move there
     * @return true if moved, false if cant move
     */
    @Override
    public boolean move(Field.Direction dir)
    {
        if(!canMove(dir))
            return false;
        Field toPut = this.parent.nextField(dir);
        if(!this.parent.remove(this))
            return false;
        this.parent = (FieldClass)toPut;
        this.movingDirection = dir;
        return this.parent.put(this);
    }

    /**
     * Moves the MazeObjectClass to the next Field in the given direction if possible, quietly (without making any noise).
     * @param dir the direction in which to move the MazeObjectClass
     * @return true if the MazeObjectClass was successfully moved, false otherwise
     */
    public boolean quietMove(Field.Direction dir)
    {
        if(!canMove(dir))
            return false;
        Field toPut = this.parent.nextField(dir);
        if(!this.parent.remove(this))
            return false;
        this.parent = (FieldClass)toPut;
        this.movingDirection = dir;
        return this.parent.quietPut(this);
    }
    /**
     * Sets the moving direction of the object to the given direction.
     * @param d the direction to set the moving direction to
     */
    public void setMovingDirection(Field.Direction d)
    {
        this.movingDirection = d;
        notifyVisual();
    }
    /**
     * Updates the object according to the state change of its observable. This method
     * is required to implement the Observer interface, but is not currently used by this
     * class.
     * @param var1 the observable object that triggered the update
     */
    @Override
    public void update(Observable var1)
    {

    }


    //Observer functions
    /**
     * Adds an observer to the set of observers for the object.
     * @param o the observer to add
     */
    @Override
    public void addObserver(Observable.Observer o)
    {
        if (o != null)
        {
            observers.add(o);
        }
    }
    /**
     * Removes the specified observer from the list of observers to be notified of changes.
     * @param o the observer to be removed
     */
    @Override
    public void removeObserver(Observable.Observer o)
    {
        observers.remove(o);
    }
    /**
     * Notifies observers that the state of the object has changed.
     */
    @Override
    public void notifyObservers()
    {
        for (Observer o : observers)
        {
            o.update(this);
        }
    }
    /**
     * Notifies all non-MazeObjectClass observers that the state of the object has changed.
     * Observers that are not MazeObjectClass will have their update() method called with this as the argument.
     */
    public void notifyVisual()
    {
        for (Observer o : observers)
        {
            if(!(o instanceof MazeObjectClass))
                o.update(this);
        }
    }
}
