/**
 *   Class implementing this interface provides methods for pacman viewer
 *
 * @author xdomra00 xdomra00@stud.fit.vutbr.cz
 * @author xgarip00 xgarip00@stud.fit.vutbr.cz
 */

package xdomra00.src.visual.common;
/**
 * This interface provides methods for pacman viewer
 */
public interface CommonPacman extends CommonMazeObject
{
    /**
     * Returns the number of lives of a player.
     * @return the number of lives of a player
     */
    int getLives();
}
