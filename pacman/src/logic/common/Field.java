/**
* Interface representing a field in a maze
* This interface provides methods for determining whether the field can be moved on,
* getting the object on the field, determining whether the field is empty,
* getting the next field in a specified direction, placing an object on the field,
* removing an object from the field, setting the maze that the field is in,
* and checking if the field is equal to another object.
* @author xdomra00 xdomra00@stud.fit.vutbr.cz
* @author xgarip00 xgarip00@stud.fit.vutbr.cz
*/
package xdomra00.src.logic.common;

/**
 Interface representing a field in a maze
 * This interface provides methods for determining whether the field can be moved on,
 * getting the object on the field, determining whether the field is empty,
 * getting the next field in a specified direction, placing an object on the field,
 * removing an object from the field, setting the maze that the field is in,
 * and checking if the field is equal to another object.
 @see Field
 */
public interface Field
{
    /**
    * The Direction enum represents the possible directions in which a field can be moved.
    * Each direction is associated with a type (character).
    */
    /**
     * The Direction enum represents the possible directions in which a field can be moved.
     * Each direction is associated with a type (character).
     */
    enum Direction
    {
        /**
         The "down" direction, represented by the character 'D'.
         */
        D('D'),
        /**
         The "left" direction, represented by the character 'L'.
         */
        L('L'),
        /**
         The "right" direction, represented by the character 'R'.
         */
        R('R'),
        /**
         The "up" direction, represented by the character 'U'.
         */
        U('U');

        private final char type;
        /**
         * Constructs a Direction enum with a given type (character).
         * The type is converted to uppercase.
         * @param type the character representation of the direction
         */
        Direction(char type)
        {
            this.type = Character.toUpperCase(type);
        }
        /**
         * Returns the type (character) associated with the direction.
         * @return the character representation of the direction
         */
        public char getType() { return type; }

    }
    /**
    * The FieldType enum represents the possible types of a field.
    * Each type is associated with a type (character).
    */
    enum FieldType
    {
        /**
         Represents a wall in the maze.
         */
        WALL('X'),
        /**
         Represents free space in the maze.
         */
        FREE('.');
        private final char type;
        /**
         * Constructs a FieldType enum with a given type (character).
         * The type is converted to uppercase.
         * @param type the character representation of the field type
         */
        FieldType(char type)
        {
            this.type = Character.toUpperCase(type);
        }
        /**
         * Returns the type (character) associated with the field type.
         * @return the character representation of the field type
         */
        public char getType() { return type; }

    }

    /**
    * Returns true if it's possible to move in at least one direction from the field.
    * @return true if the field is movable
    */
    boolean canMove();
    /**
    * Returns the MazeObject located on the field.
    * @return the MazeObject located on the field
    */
    MazeObject get();
    /**
    * Returns true if the field is empty.
    * @return true if the field is empty
    */
    boolean isEmpty();
    /**
    * Returns the field in a specified direction.
    * @param dirs the direction in which to get the next field
    * @return the next field in the specified direction
    */
    Field nextField(Field.Direction dirs);
    /**
    * Adds a MazeObject to the field if it's empty.
    * @param object the MazeObject to add
    * @return true if the MazeObject was successfully added
    */
    boolean put(MazeObject object);
    /**
    * Removes a MazeObject from the field if it's present.
    * @param object the MazeObject to remove
    * @return true if the MazeObject was successfully removed
    */
    boolean remove(MazeObject object);
    /**
    * Sets the Maze that the field belongs to.
    * @param maze the Maze to set as the owner of the field
    */
    void setMaze(Maze maze);
    /**
    * Returns true if the specified object is equal to the field.
    * @param obj the object to compare
    * @return true if the specified object is equal to the field
    */
    boolean equals(Object obj);
}