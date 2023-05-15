/**
 *  Class provides general methods for putting and removing objects from a field
 *
 * @author xdomra00 xdomra00@stud.fit.vutbr.cz
 * @author xgarip00 xgarip00@stud.fit.vutbr.cz
 */

package xdomra00.src.logic.game;

import xdomra00.src.logic.common.*;
import xdomra00.src.visual.common.*;

import javax.swing.*;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;

/**
 * The BFS class provides an implementation of the breadth-first search algorithm to find the shortest path between two FieldClass instances in the game maze.
 * It uses the FieldClass.nextField method to explore the neighboring fields and the canMove method of MazeObject interface to determine if a move can be made.
 * It maintains a search tree of BFSNode objects representing the visited fields and the shortest path to the current node from the starting node.
 * The find method returns the list of Field.Direction instances representing the path to the goal node.
 * @see FieldClass
 * @see MazeObject
 * @see Field
 * @see CommonField
 */
public abstract class FieldClass implements Field, CommonField
{
    /**
     * The row and column coordinate of the cell.
     */
    protected final int row, col;
    /**
     * The type of the cell, indicating whether it is a wall or a path.
     */
    protected FieldType type;
    //MazeObjectClass mazeObj;
    /**
     * The maze object associated with this cell.
     * It is not clear if we can put more objects
     */
    Maze maze;
    /**
     * A set of observers that are registered to receive notifications when the state of
     * the cell changes.
     */
    private Set<Observable.Observer> observers;
    //It is not clear if we can put more objects
    /**
     * A list of objects that are currently present in the cell.
     */
    protected List<MazeObjectClass> mazeObjs;
    /**
     * Constructs a new FieldClass object with the given row and column coordinates.
     * Initializes an empty list of maze objects, a null maze, and an empty set of observers.
     *
     * @param row The row coordinate of the field
     * @param col The column coordinate of the field
     */
    protected FieldClass(int row, int col)
    {
        this.mazeObjs = new ArrayList<>();
        //this.mazeObj = null;
        this.maze = null;
        this.row = row;
        this.col = col;
        this.observers = new HashSet<>();
    }

//    public int getRow() { return this.row; }
//    public int getCol() { return this.col; }
    /**
     * Returns the type of the field.
     *
     * @return The type of the field
     */
    public FieldType getFieldType()
    {
        return type;
    }
    /**
     * Returns the row coordinate of the field.
     *
     * @return The row coordinate of the field
     */
    public int getRow()
    {
        return row;
    }
    /**
     * Returns the column coordinate of the field.
     *
     * @return The column coordinate of the field
     */
    public int getCol()
    {
        return col;
    }
    /**
     * Compares the specified object with this FieldClass object for equality.
     * Returns true if the object is a FieldClass object with the same row and column coordinates and field type as this object.
     *
     * @param obj The object to compare to this FieldClass object
     * @return True if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj)
    {
        if(obj.getClass() != this.getClass())
            return false;
        FieldClass field = (FieldClass)obj;
        return this.col == field.col && this.row == field.row && this.type == field.type;
    }

    //Field functions
    /**
     * Returns whether a maze object can move onto the field.
     * This method is not supported by FieldClass and will always throw an UnsupportedOperationException.
     *
     * @return Always throws an UnsupportedOperationException
     */
    @Override
    public boolean canMove()
    {
        throw new UnsupportedOperationException();
    }
    /**
     * Returns the top-most maze object on the field, or null if there are no maze objects on the field.
     *
     * @return The top-most maze object on the field, or null if there are no maze objects on the field
     */
    @Override
    public MazeObjectClass get()
    {
        if(this.mazeObjs.size() == 0)
            return null;
        return this.mazeObjs.get(this.mazeObjs.size() - 1);
        //return this.mazeObj;
    }
    /**
     * Returns a list of all MazeObjectClass objects in this field.
     * If the list is empty, returns null.
     * @return a list of all MazeObjectClass objects in this field, or null if the list is empty.
     */
    public List<MazeObjectClass> getAll()
    {
        if(this.mazeObjs.size() == 0)
            return null;
        return this.mazeObjs;
    }

    /**
     * Check if the collection of maze objects is empty.
     * @return true if the collection of maze objects is empty, false otherwise.
     */
    @Override
    public boolean isEmpty()
    {
        return this.mazeObjs.size() == 0;
        //return this.mazeObj == null;
    }
    /**
     * Returns the adjacent Field in the specified direction.
     * @param dir the Direction of the adjacent Field
     * @return the adjacent Field in the specified direction, or null if the adjacent Field does not exist or this Field is not part of a Maze
     */
    @Override
    public Field nextField(Field.Direction dir)
    {
        if(this.maze == null)
            return null;
        switch (dir) {
            case R: return maze.getField(this.row, this.col + 1);
            case L: return maze.getField(this.row, this.col - 1);
            case D: return maze.getField(this.row + 1, this.col);
            case U: return maze.getField(this.row - 1, this.col);
        }
        return null;
    }
    /**
     * Adds a maze object to the current field.
     * @param object the maze object to be added
     * @return true if the maze object was successfully added, false otherwise
     */
    @Override
    public boolean put(MazeObject object)
    {
        this.mazeObjs.add((MazeObjectClass)object);
        this.mazeObjs.get(this.mazeObjs.size()-1).setParent(this);
        this.addObserver(this.mazeObjs.get(this.mazeObjs.size()-1));
        this.notifyObservers();

        return true;
    }
    /**
     * Adds a MazeObject to the MazeObjectClass's list of MazeObjects without notifying all observers.
     * Instead, it only notifies observers that are not instances of MazeObjectClass.
     * @param object the MazeObject to add to the list
     * @return true if the object is successfully added, false otherwise
     */
    public boolean quietPut(MazeObject object)
    {
        this.mazeObjs.add((MazeObjectClass)object);
        this.mazeObjs.get(this.mazeObjs.size()-1).setParent(this);
        this.addObserver(this.mazeObjs.get(this.mazeObjs.size()-1));
        notifyVisual();

        return true;
    }
    /**
     * Removes the specified maze object from the field's list of objects and notifies observers.
     * @param object the maze object to be removed
     * @return true if the object was successfully removed, false otherwise
     */
    @Override
    public boolean remove(MazeObject object)
    {
        if(this.mazeObjs.size() == 0)
        {
            return false;
        }
        int index = this.mazeObjs.indexOf(object);
        if(index >= 0)
        {
            this.removeObserver(this.mazeObjs.get(index));
            this.mazeObjs.remove(index);
            this.notifyObservers();
            return true;
        }
        return false;
    }
    /**
     * Removes a maze object from this field's list of maze objects quietly without notifying any observers except for the visual representation of the maze.
     * If the maze object is not found in the list, returns false.
     * @param object The maze object to remove
     * @return True if the maze object was successfully removed, false otherwise
     */
    public boolean quietRemove(MazeObject object)
    {
        if(this.mazeObjs.size() == 0)
        {
            return false;
        }
        int index = this.mazeObjs.indexOf(object);
        if(index >= 0)
        {
            this.removeObserver(this.mazeObjs.get(index));
            this.mazeObjs.remove(index);
            this.notifyVisual();
            return true;
        }
        return false;
    }
    /**
     * Sets the maze object for this field.
     * @param maze The maze object to set.
     */
    @Override
    public void setMaze(Maze maze)
    {
        this.maze = maze;
    }

    //CommonField functions

    //Observer functions
    /**
     * Adds an observer to the list of observers for this field.
     * @param o The observer to add.
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
     * Removes an observer from the list of observers for this field.
     * @param o The observer to remove.
     */
    @Override
    public void removeObserver(Observable.Observer o)
    {
        observers.remove(o);
    }
    /**
     * Notifies all the observers of this field that a change has occurred.
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
     * Notifies all the visual observers of this field that a change has occurred.
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
