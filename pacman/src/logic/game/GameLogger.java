/**
 *  Class records and reads information about game logic events
 *
 * @author xdomra00 xdomra00@stud.fit.vutbr.cz
 * @author xgarip00 xgarip00@stud.fit.vutbr.cz
 */

package xdomra00.src.logic.game;

import xdomra00.src.logic.common.Field;
import xdomra00.src.logic.common.Maze;
import xdomra00.src.logic.common.MazeObject;

import java.io.*;
/**
 * Class records and reads information about game logic events
 * @see Maze
 * @see MazeObject
 * @see Field
 */
public class GameLogger
{
    private MazeClass map;
    private String logFilename = "../data/log/log.txt";
    private int currentLine;

    private boolean unlogReady = false;
    private long filePosition;

    /**
     * Copies map to a log file
     *
     * @param  map - map to record
     */
    public GameLogger(MazeClass map)
    {
        this.map = map;
        try(FileWriter writer = new FileWriter(logFilename, false))
        {
            writer.write(MazeConfigure.getOriginalRows(map) + " " + MazeConfigure.getOriginalCols(map) + "\n");
            FieldClass f;
            for (int i = 0; i < MazeConfigure.getOriginalRows(map); i++)
            {
                for (int j = 0; j < MazeConfigure.getOriginalCols(map); j++)
                {
                    f = MazeConfigure.getOriginalField(map, i, j);
                    if(f.getFieldType() == Field.FieldType.FREE)
                    {
                        if(f.get() != null)
                            writer.write(f.get().getObjectType().getType());
                        else
                            writer.write(f.getFieldType().getType());
                    }
                    else
                    {
                        writer.write(f.getFieldType().getType());
                    }

                }
                writer.write("\n");
            }
            writer.write("\n");
        }
        catch (IOException e)
        {
            System.err.println("Error writing to " + logFilename + ": " + e.getMessage());
        }
    }

    /**
     * Preparing to read from log file
     */
    public GameLogger()
    {
        try(RandomAccessFile reader = new RandomAccessFile(logFilename, "r"))
        {
            while(!reader.readLine().equals(""))
            {

            }
            filePosition = reader.getFilePointer();
        }
        catch(Exception e){e.printStackTrace();}
        currentLine = 1;
        unlogReady = true;
    }

    /**
     * Checks if object can be moved to a given direction
     *
     * @param  direction - read next/previous line
     * @return next/previous string or "" if out of bounds(bounds are defined in a constructor)
     */

    public String unlog(boolean direction)
    {
        if(!unlogReady)
            return "";
        if(direction)//read forward
        {
            String line = null;
            long tmpPosition = 0;
            try(RandomAccessFile reader = new RandomAccessFile(logFilename, "r"))
            {
                reader.seek(filePosition);
                line = reader.readLine();
                tmpPosition = reader.getFilePointer();
            }
            catch(Exception e){e.printStackTrace();}

            if(line != null)
            {
                currentLine++;
                filePosition = tmpPosition;
                return line;
            }
            else
                return "";
        }
        else//read previous line
        {
            currentLine--;
            if(currentLine < 1)
            {
                currentLine = 1;
                return "";
            }

            try(RandomAccessFile reader = new RandomAccessFile(logFilename, "r");)
            {
                reader.seek(filePosition);
                reader.seek(reader.getFilePointer()-2);//2: line feed + symbol to read
                while(reader.read() != '\n')//skip read line
                {
                    reader.seek(reader.getFilePointer()-2);//unread current and go to next symbol;
                }
                reader.seek(reader.getFilePointer()-2);
                while(reader.read() != '\n')//skip line to read
                {
                    reader.seek(reader.getFilePointer()-2);//unread current and go to next symbol;
                }
                String line = reader.readLine();
                filePosition = reader.getFilePointer();
                return line;
            }
            catch(Exception e){e.printStackTrace();}

        }
        return "";
    }

    /**
     * Record object moved
     *
     * @param  obj - what object moved
     * @param direction - in what direction
     */
    public void logMove(MazeObjectClass obj, Field.Direction direction)
    {
        if(map == null)
            return;
        try(FileWriter writer = new FileWriter(logFilename, true))
        {
            MazeObject.MazeObjectType t = obj.getObjectType();
            if(t == MazeObject.MazeObjectType.GHOST)
            {
                writer.write("M G " + ((GhostObject)obj).getId() + " " + direction.getType() + "\n");
            }
            else if(t == MazeObject.MazeObjectType.PLAYER)
            {
                writer.write("M P " + direction.getType() + "\n");
            }
        }
        catch (IOException e)
        {
            System.err.println("Error writing to " + logFilename + ": " + e.getMessage());
        }
    }
    /**
     * Logs a key being obtained by the player by writing "K" to the log file.
     * If the map is null, nothing is logged.
     */
    public void logKeyGot()
    {
        if(map == null)
            return;
        try(FileWriter writer = new FileWriter(logFilename, true))
        {
            writer.write("K\n");
        }
        catch (IOException e)
        {
            System.err.println("Error writing to " + logFilename + ": " + e.getMessage());
        }
    }
    /**
     * Logs a point being obtained by the player by writing "P" to the log file.
     * If the map is null, nothing is logged.
     */
    public void logPointGot()
    {
        if(map == null)
            return;
        try(FileWriter writer = new FileWriter(logFilename, true))
        {
            writer.write("P\n");
        }
        catch (IOException e)
        {
            System.err.println("Error writing to " + logFilename + ": " + e.getMessage());
        }
    }
    /**
     * Logs the player losing a life by writing "L" to the log file.
     * If the map is null, nothing is logged.
     */
    public void logLiveLose()
    {
        if(map == null)
            return;
        try(FileWriter writer = new FileWriter(logFilename, true))
        {
            writer.write("L\n");
        }
        catch (IOException e)
        {
            System.err.println("Error writing to " + logFilename + ": " + e.getMessage());
        }
    }
    /**
     * Logs the end of a game by writing "T" to the log file if the player won,
     * or "F" if the player lost.
     * @param win true if the player won the game, false if the player lost
     */
    public void logGameEnd(boolean win)
    {
        try(FileWriter writer = new FileWriter(logFilename, true))
        {
            if(win)
                writer.write("T\n");
            else
                writer.write("F\n");
        }
        catch (IOException e)
        {
            System.err.println("Error writing to " + logFilename + ": " + e.getMessage());
        }

    }
}
