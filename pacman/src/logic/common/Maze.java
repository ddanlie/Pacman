/** 
* Interface representing a maze
* This interface provides methods for getting a field at a specific position
* and getting the number of rows and columns in the maze.
* The Maze interface is implemented by classes that represent different types of mazes.
* For example, there could be a class for rectangular mazes and a class for hexagonal mazes.
* @author xdomra00 xdomra00@stud.fit.vutbr.cz
* @author xgarip00 xgarip00@stud.fit.vutbr.cz
*/
package xdomra00.src.logic.common;

/**

* The Maze interface represents a maze.
* It provides methods for getting a field at a specific position,
* getting the number of columns and rows in the maze.
*/
public interface Maze
{
    /**
    * Returns the field at the specified position in the maze.
    * @param row the row of the field
    * @param col the column of the field
    * @return the field at the specified position
    */
    Field getField(int row, int col);
    /**
    * Returns the number of columns in the maze.
    * @return the number of columns in the maze
    */
    int numCols();
    /**
    * Returns the number of rows in the maze.
    * @return the number of rows in the maze
    */
    int numRows();
}