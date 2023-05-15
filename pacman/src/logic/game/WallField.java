/**
 *  Class is acting like a wall in game logic
 *
 * @author xdomra00 xdomra00@stud.fit.vutbr.cz
 * @author xgarip00 xgarip00@stud.fit.vutbr.cz
 */

package xdomra00.src.logic.game;

import xdomra00.src.logic.common.*;
/**
 *  Class is acting like a wall in game logic
 *  @see FieldClass
 * @see MazeObject
 * @see FieldType
 * @see UnsupportedOperationException
 * @see xdomra00.src.logic.game
 * @author xdomra00
 */
public class WallField extends FieldClass
{

    /**
     * This is a constructor method for the WallField class, which initializes a new instance of a wall field with the specified row and column coordinates.
     * @param row the row coordinate of the wall field
     * @param col the column coordinate of the wall field
     */
    public WallField(int row, int col)
    {
        super(row, col);
        this.type = FieldType.WALL;
    }
    /**
     * Checks if a player or object can move through this field.
     * Since this field is a wall, it always returns false.
     * @return false, as the field is a wall and cannot be passed through
     */
    public boolean canMove() { return false; }
    /**
     * This method is not supported for WallField since it represents a wall.
     * @throws UnsupportedOperationException Always thrown for WallField.
     */
    @Override
    public Field nextField(Field.Direction dirs)
    {
        throw new UnsupportedOperationException();
    }
    /**
     * This method is not supported for WallField since it represents a wall.
     * @throws UnsupportedOperationException Always thrown for WallField.
     */
    @Override
    public boolean put(MazeObject object)
    {
        throw new UnsupportedOperationException();
    }
    /**
     * This method is not supported for WallField since it represents a wall.
     * @throws UnsupportedOperationException Always thrown for WallField.
     */
    @Override
    public boolean remove(MazeObject object)
    {
        throw new UnsupportedOperationException();
    }
}

