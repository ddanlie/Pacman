/**
 * Class is a game maze where fields are put
 *
 * @author xdomra00 xdomra00@stud.fit.vutbr.cz
 * @author xgarip00 xgarip00@stud.fit.vutbr.cz
 */

package xdomra00.src.logic.game;
import xdomra00.src.logic.common.*;

import xdomra00.src.visual.common.*;
import java.util.List;
import java.util.ArrayList;
/**
 *  Class is a game maze where fields are put
 */
public class MazeClass implements Maze, CommonMaze
{
    private final FieldClass[][] map;
    private final int rows, cols;

    /**
     * Creates a new MazeClass object with the given number of rows and columns.
     * @param rows the number of rows in the maze
     * @param cols the number of columns in the maze
     */
    MazeClass(int rows, int cols)
    {
        this.map = new FieldClass[rows][cols];
        this.rows = rows;
        this.cols = cols;
        GhostObject.reinitIds();
    }

    /**
     * used by maze configure to fill the maze
     * @param  line - line of chars to put (check maze configure input file specification)
     * @param  row - row count
     * @return true if put, false if fail
     */
    public boolean putLine(String line, int row)
    {
        if(line.length() != this.cols)
            return false;
        if(row >= this.rows)
            return false;

        int col_index = 0;

        boolean found = false;
        for(char map_content : line.toCharArray())
        {
            //search for fields
            for(Field.FieldType f : Field.FieldType.values())
            {
                if(map_content != f.getType())
                    continue;
                if(map_content == Field.FieldType.WALL.getType())
                {
                    this.map[row][col_index] = new WallField(row, col_index);
                    this.map[row][col_index].setMaze(this);
                } else if(map_content == Field.FieldType.FREE.getType())
                {
                    this.map[row][col_index] = new PathField(row, col_index);
                    this.map[row][col_index].setMaze(this);
                }
                found = true;
            }

            //search for maze objects
            for(MazeObject.MazeObjectType m : MazeObject.MazeObjectType.values())
            {
                if(map_content != m.getType())
                    continue;
                MazeObject.MazeObjectType type = MazeObject.MazeObjectType.toEnum(map_content);
                if(map_content == MazeObject.MazeObjectType.PLAYER.getType())
                {
                    this.map[row][col_index] = new PathField(row, col_index);
                    this.map[row][col_index].setMaze(this);
                    this.map[row][col_index].put(new PacmanObject());
                } else if(map_content == MazeObject.MazeObjectType.GHOST.getType())
                {
                    this.map[row][col_index] = new PathField(row, col_index);
                    this.map[row][col_index].setMaze(this);
                    this.map[row][col_index].put(new GhostObject());
                } else if(map_content == MazeObject.MazeObjectType.KEY.getType())
                {
                    this.map[row][col_index] = new PathField(row, col_index);
                    this.map[row][col_index].setMaze(this);
                    this.map[row][col_index].put(new KeyObject());
                } else if(map_content == MazeObject.MazeObjectType.TARGET.getType())
                {
                    this.map[row][col_index] = new PathField(row, col_index);
                    this.map[row][col_index].setMaze(this);
                    this.map[row][col_index].put(new TargetObject());
                } else if(map_content == MazeObject.MazeObjectType.POINT.getType())
                {
                    this.map[row][col_index] = new PathField(row, col_index);
                    this.map[row][col_index].setMaze(this);
                    this.map[row][col_index].put(new PointObject());
                }
                found = true;
            }
            if(found == false)
                return false;
            col_index++;
        }
        return true;
    }

    //Maze functions
    /**
     * Retrieves the field at the specified position in the maze.
     * @param row The row of the field to retrieve.
     * @param col The column of the field to retrieve.
     * @return The FieldClass object at the specified position, or null if the position is out of bounds.
     */
    @Override
    public FieldClass getField(int row, int col)
    {
        if(row > this.rows - 1 || col > this.cols - 1 || row < 0 || col < 0)
            return null;
        return this.map[row][col];
    }

    /**
     * Returns the number of columns in the maze.
     * @return The number of columns in the maze.
     */
    @Override
    public int numCols()
    {
        return this.cols;
    }
    /**
     * Returns the number of rows in the maze.
     * @return The number of rows in the maze.
     */
    @Override
    public int numRows()
    {
        return this.rows;
    }

    /**
     * Returns a list of all the GhostObjects in the maze.
     * @return a list of all the GhostObjects in the maze.
     */
    public List<GhostObject> getGhosts()
    {
        List<GhostObject> ghostsList = new ArrayList<>();
        for(int i = 0; i < this.numRows(); i++)
        {
            for(int j = 0; j < this.numCols(); j++)
            {
                MazeObjectClass obj = this.map[i][j].get();
                if(obj != null && obj.getObjectType() == MazeObject.MazeObjectType.GHOST)
                {
                    ghostsList.add((GhostObject) obj);
                }
            }
        }
        return ghostsList;
    }
    /**
     * Returns the count of all keys present in the maze.
     * @return the count of keys present in the maze.
     */
    public int getKeysCount()
    {
        int counter = 0;
        for(int i = 0; i < this.numRows(); i++)
        {
            for(int j = 0; j < this.numCols(); j++)
            {
                MazeObjectClass obj = this.map[i][j].get();
                if(obj != null && obj.getObjectType() == MazeObject.MazeObjectType.KEY)
                {
                    counter++;
                }
            }
        }
        return counter;
    }
    /**
     * Returns the PacmanObject in the maze, if it exists.
     * @return the PacmanObject in the maze, or null if it doesn't exist.
     */
    public PacmanObject getPacman()
    {
        for(int i = 0; i < this.numRows(); i++)
        {
            for(int j = 0; j < this.numCols(); j++)
            {
                MazeObjectClass obj = this.map[i][j].get();
                if(obj != null && obj.getObjectType() == MazeObject.MazeObjectType.PLAYER)
                {
                    return (PacmanObject) obj;
                }
            }
        }
        return null;
    }
    /**
     * Returns the target object in the maze, or null if no target exists.
     * @return The target object in the maze, or null if no target exists.
     */
    public TargetObject getTarget()
    {
        for(int i = 0; i < this.numRows(); i++)
        {
            for(int j = 0; j < this.numCols(); j++)
            {
                MazeObjectClass obj = this.map[i][j].get();
                if(obj != null && obj.getObjectType() == MazeObject.MazeObjectType.TARGET)
                {
                    return (TargetObject) obj;
                }
            }
        }
        return null;
    }

}
