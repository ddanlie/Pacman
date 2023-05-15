/**
 *   Class implementing this interface can be observed
 *
 * @author xdomra00 xdomra00@stud.fit.vutbr.cz
 * @author xgarip00 xgarip00@stud.fit.vutbr.cz
 */

package xdomra00.src.visual.common;
/**
 * Class implementing this interface can be observed
 */
public interface Observable
{
    /**
     * Adds an observer to the list of observers for this observable object.
     * @param var1 the observer to be added
     */
    void addObserver(Observer var1);
    /**
     * Removes an observer from the list of observers for this observable object.
     * @param var1 the observer to be removed
     */
    void removeObserver(Observer var1);
    /**
     * Notifies all observers of this observable object that the object has changed.
     */
    void notifyObservers();
    /**
     * This interface represents an object that can observe changes in an Observable object.
     */
    public interface Observer {
        /**
         * This method is called when an observed object has changed.
         * @param var1 the object that has changed
         */
        void update(Observable var1);
    }
}
