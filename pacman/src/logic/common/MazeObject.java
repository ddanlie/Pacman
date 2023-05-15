/**
* Interface representing objects in a maze
* This interface provides methods for moving the object in a specified direction,
* checking if the object can move in a certain direction, and checking if two objects are equal.
* It also defines an enumeration of MazeObjectTypes, which represents the different types of objects
* that can be present in a maze.
* @author xdomra00 xdomra00@stud.fit.vutbr.cz
* @author xgarip00 xgarip00@stud.fit.vutbr.cz
*/
package xdomra00.src.logic.common;

import java.util.HashMap;
/**
 * Interface representing objects in a maze
 * This interface provides methods for moving the object in a specified direction,
 * checking if the object can move in a certain direction, and checking if two objects are equal.
 * It also defines an enumeration of MazeObjectTypes, which represents the different types of objects
 * that can be present in a maze.
 */
public interface MazeObject
{
    /**
    *  Enumeration representing the different types of objects that can be present in a maze
    * The enumeration contains the types TARGET, GHOST, KEY, PLAYER, and POINT, and each type is associated with a
    * unique character, which is used to represent the object in the maze.
    * The enumeration also provides a method toEnum() that maps a character to the corresponding MazeObjectType.
    */
    public enum MazeObjectType
    {
        /**
         * A target object in the maze that the player needs to reach in order to complete the game.
         */
        TARGET('T'),
        /**
         * A ghost object in the maze that the player needs to avoid, otherwise they will lose the game.
         */
        GHOST('G'),
        /**
         * A key object in the maze that the player needs to collect in order to unlock certain doors or gates.
         */
        KEY('K'),
        /**
         * The player object that represents the player's avatar in the maze game.
         */
        PLAYER('S'),
        /**
         * A point object in the maze that the player can collect to increase their score.
         */
        POINT('.');

        private final char type;
        /**
        *  HashMap for mapping characters to MazeObjectTypes
        * The HashMap is used by the toEnum() method to map a character to the corresponding MazeObjectType.
        */
        private static final HashMap<Character, MazeObjectType> keymap =
                new HashMap<>()
                {{
                    put('T', MazeObjectType.TARGET);
                    put('G', MazeObjectType.GHOST);
                    put('K', MazeObjectType.KEY);
                    put('S', MazeObjectType.PLAYER);
                    put('.',  MazeObjectType.POINT);
                }};

        /**
        *  Constructor for the MazeObjectType enumeration
        * The constructor initializes the type attribute with the character associated with the enumeration value.
        * @param type The character associated with the enumeration value
        */
        MazeObjectType(char type)
        {
            this.type = Character.toUpperCase(type);
        }
        /**
        *  Getter method for the type attribute of a MazeObjectType
        * The method returns the character associated with the enumeration value.
        * @return The character associated with the enumeration value
        */
        public char getType()
        {
            return type;
        }
        /**
        *  Method for mapping a character to the corresponding MazeObjectType
        * The method returns the MazeObjectType associated with the specified character, or null if the character
        * does not correspond to any MazeObjectType.
        * @param type The character to map to a MazeObjectType
        * @return The MazeObjectType associated with the specified character, or null if the character
        * does not correspond to any MazeObjectType.
        */
        public static MazeObjectType toEnum(char type)
        {
            return keymap.get(Character.toUpperCase(type));
        }

    }
    /**
    *  Method for checking if the object can move in a certain direction
    * The method returns true if the object can move in the specified direction, and false otherwise.
    * @param dir The direction in which to check if the object can move
    * @return True if the object can move in the specified direction, false otherwise
    */
    boolean canMove(Field.Direction dir);
    /**
    *  Method for moving the object in a specified direction
    * The method returns true if the object was successfully moved in the specified direction, and false otherwise.
    * @param dir The direction in which to move the object
    * @return True if the object was successfully moved in the specified direction, false otherwise
    */
    boolean move(Field.Direction dir);
    /**
    * Indicates whether some other object is "equal to" this one.
    * @param obj the reference object with which to compare.
    * @return true if this object is the same as the obj argument; false otherwise.
    */
    boolean equals(Object obj);
}