/**
 *  Class is acting like a free field in game logic
 *
 * @author xdomra00 xdomra00@stud.fit.vutbr.cz
 * @author xgarip00 xgarip00@stud.fit.vutbr.cz
 */

package xdomra00.src.logic.game;
/**
 * This class represents a free field in the game's logic.
 * It inherits from the FieldClass and sets its type to "FREE".
 * This class allows the player to move on it.
 @see FieldClass
 */
public class PathField extends FieldClass
{
    /**
     * Creates a new instance of PathField with the given coordinates.
     * Sets the type of the field to "FREE".
     * @param row the row number of the field
     * @param col the column number of the field
     */
    public PathField(int row, int col)
    {
        super(row, col);
        this.type = FieldType.FREE;
    }
    /**
     * Checks whether the player can move on this field.
     * @return true if the player can move on the field, false otherwise.
     */
    public boolean canMove() { return true; }

}