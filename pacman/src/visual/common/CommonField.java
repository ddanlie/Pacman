/**
 *   Class implementing this interface provides methods for field viewer
 *
 * @author xdomra00 xdomra00@stud.fit.vutbr.cz
 * @author xgarip00 xgarip00@stud.fit.vutbr.cz
 */

package xdomra00.src.visual.common;

import xdomra00.src.logic.common.Field;
/**
 * This interface provides methods for field viewer
 * @see Field
 */
public interface CommonField extends Observable
{
    /**
     * Returns the type of the field.
     * @return the type of the field
     */
    Field.FieldType getFieldType();
    /**
     * Checks if the field is empty.
     * @return true if the field is empty, false otherwise
     */
    boolean isEmpty();
    /**
     * Returns the maze object at the field.
     * @return the maze object at the field
     */
    CommonMazeObject get();
    /**
     * Checks if a player can move to the field.
     * @return true if a player can move to the field, false otherwise
     */
    boolean canMove();

}
