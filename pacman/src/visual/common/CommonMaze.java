/**
 *   Class implementing this interface provides methods for maze viewer
 *
 * @author xdomra00 xdomra00@stud.fit.vutbr.cz
 * @author xgarip00 xgarip00@stud.fit.vutbr.cz
 */

package xdomra00.src.visual.common;

import java.util.List;
/**
 * This interface provides methods for maze viewer
 */
public interface CommonMaze
{
    /**
     * Returns the CommonField object at the specified row and column in the maze.
     * @param var1 the row of the field to retrieve
     * @param var2 the column of the field to retrieve
     * @return the CommonField object at the specified row and column
     */
    CommonField getField(int var1, int var2);
    /**
     * Returns the number of rows in the maze.
     * @return the number of rows in the maze
     */
    int numRows();
    /**
     * Returns the number of columns in the maze.
     * @return the number of columns in the maze
     */
    int numCols();
    /**
     * Returns the CommonMazeObject object representing the Pacman character in the maze.
     * @return the CommonMazeObject object representing Pacman
     */
    CommonMazeObject getPacman();
}
