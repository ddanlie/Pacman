/**
 *  Class reads from file and creates new game maze
 *
 * @author xdomra00 xdomra00@stud.fit.vutbr.cz
 * @author xgarip00 xgarip00@stud.fit.vutbr.cz
 */

package xdomra00.src.logic.game;
import xdomra00.src.logic.common.*;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 *  Class reads from file and creates new game maze
 */
public class MazeConfigure
{
    private MazeClass configuredMaze;
    private int row_index, rows, cols;
    private final static int wall_padding = 1;
    private final static int padding_side_mult = wall_padding*2;

    /**
     * Constructs a new instance of the MazeConfigure class with the specified parameters.
     */
    public MazeConfigure()
    {
    }

    /**
     * creates maze from a given filename
     * file format: first string: rows(int) cols(int)
     *              next this and other(rows) strings: cols objects which are one of the maze object type chars/field type chars
     *
     * @param filename - file to read from
     * @return configured maze if all is ok or null if something went wrong
     */
    public MazeClass createMaze(String filename)
    {
        this.row_index = 0;
        this.configuredMaze = null;

        BufferedReader fileReader;
        String sizeData;
        try
        {
            fileReader = new BufferedReader(new FileReader(filename));
            sizeData = fileReader.readLine();
        }
        catch (Exception e)
        {
            return null;
        }

        //get rows, cols
        String[] d  = sizeData.split(" ");
        if(d.length != 2)
            return null;
        this.rows = Integer.parseInt(d[0]);
        this.cols = Integer.parseInt(d[1]);

        this.configuredMaze = new MazeClass(
                this.rows+this.padding_side_mult, this.cols+this.padding_side_mult);

        for(int i = 0; i < this.rows; i++)
        {
            String line;
            try
            {
                line = fileReader.readLine();
            }
            catch(Exception e)
            {
                return null;
            }
            if(!processLine(line))
                return null;
        }

        if(!stopReading())
            return null;
        if(this.row_index != this.configuredMaze.numRows()-this.padding_side_mult)
            return null;

        return this.configuredMaze;
    }
    /**
     * Checks if the reading of the configuration file should stop.
     * The reading should stop if the configuredMaze is null, or if the row index
     * is not equal to the number of rows specified in the configuration file.
     * @return true if the reading should stop, false otherwise
     */
    private boolean stopReading()
    {
        if(this.configuredMaze == null)
            return false;
        if(this.row_index != this.rows)
            return false;

        return true;
    }
    /**
     * Process a single line of input string and add it to the configured maze.
     * @param line the line of input to be processed
     * @return true if the line was successfully processed and added to the maze, false otherwise
     */
    private boolean processLine(String line)
    {
        if(line == null)
            return false;
        if (this.configuredMaze == null)
            return false; 
        if(this.cols != line.length())
            return false;
        
        //column padding
        char colpad = Field.FieldType.WALL.getType();
        StringBuilder lineBuilder = new StringBuilder(line);
        for(int i = 0; i < this.wall_padding; i++)
            lineBuilder.insert(0, colpad).append(colpad);
        line = lineBuilder.toString();

        boolean error = false;
        if(this.row_index == 0)
        {
            //row padding
            String padding_line = new String(new char[this.configuredMaze.numCols()])
                    .replace('\0', Field.FieldType.WALL.getType());
            for(int i = 0; i < this.wall_padding; i++)
                if(this.configuredMaze.putLine(padding_line, 0+i) == false)
                    error = true;
            if(this.configuredMaze.putLine(line, 0+this.wall_padding) == false)
                error = true;
        }
        if(this.row_index == this.rows-1)
        {
            //row padding
            String padding_line = new String(new char[this.configuredMaze.numCols()])
                    .replace('\0', Field.FieldType.WALL.getType());
            if(this.configuredMaze.putLine(line, this.row_index+this.wall_padding) == false)
                error = true;
            for(int i = 0; i < this.wall_padding; i++)
                if(this.configuredMaze.putLine(padding_line, this.row_index+i+this.wall_padding+1) == false)
                    error = true;
        }
        else
        {
            if(this.configuredMaze.putLine(line, this.row_index+this.wall_padding) == false)
                error = true;
        }
        if(error)
        {
            this.configuredMaze = null;
            return false;
        }

        this.row_index++;
        return true;
    }

    /**
     *@param  maze - what maze to take from
     *@return rows count oof a maze without padding
     */
    public static int getOriginalRows(MazeClass maze)
    {
        return maze.numRows()-padding_side_mult;
    }


    /**
     * Returns the number of original columns in the given maze, i.e., the number of columns before padding was added.
     * @param maze the maze to get the original number of columns from
     * @return the number of original columns in the maze
     */
    public static int getOriginalCols(MazeClass maze)
    {
        return maze.numCols()-padding_side_mult;
    }
    /**
     * Returns the FieldClass object from the original maze (before padding) at the specified row and column indices.
     * @param maze the original maze (before padding)
     * @param i the row index of the desired field
     * @param j the column index of the desired field
     * @return the FieldClass object from the original maze at the specified row and column indices
     */
    public static FieldClass getOriginalField(MazeClass maze, int i, int j)
    {
        return maze.getField(i+wall_padding, j+wall_padding);
    }
}