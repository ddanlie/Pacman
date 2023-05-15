/**
 *  Class is representing target object on the screen
 *
 * @author xdomra00 xdomra00@stud.fit.vutbr.cz
 * @author xgarip00 xgarip00@stud.fit.vutbr.cz
 */


package xdomra00.src.visual.view;

import xdomra00.src.logic.game.TargetObject;
import xdomra00.src.visual.common.CommonMazeObject;
import xdomra00.src.visual.common.Observable;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
/**
 *  Class is representing target object on the screen
 */
public class TargetView implements ComponentView, Observable.Observer
{
    private CommonMazeObject model;
    private FieldView parent;

    private Image opened = null;
    private Image closed = null;
    /**
     * This constructor creates a new instance of the TargetView class with the specified parent FieldView
     * and CommonMazeObject model. It initializes the instance variables and sets this object as an observer
     * of the model. It also attempts to load the target sprites from the specified image files.
     * @param parent the parent FieldView to which this TargetView belongs
     * @param m the CommonMazeObject model associated with this TargetView
     */
    public TargetView(FieldView parent, CommonMazeObject m)
    {
        this.model = m;
        this.model.addObserver(this);
        this.parent = parent;
        try
        {
            opened = ImageIO.read(new File("../lib/sprites/opened_target_sprite.png"));
            closed = ImageIO.read(new File("../lib/sprites/closed_target_sprite.png"));
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * This method paints the target component on the screen.
     * @param g The graphics object that will be used for painting.
     */
    public void paintComponent(Graphics g)
    {
        Rectangle bounds = this.parent.getBounds();
        int w = (int)bounds.getWidth();
        int h = (int)bounds.getHeight();


        if(((TargetObject)model).isAccessible() && opened != null)
        {
            g.drawImage(opened.getScaledInstance(w, h, Image.SCALE_SMOOTH), 0, 0, (int)w, (int)h, null, null);
        }
        else if(!((TargetObject)model).isAccessible() && closed != null)
        {
            g.drawImage(closed.getScaledInstance(w, h, Image.SCALE_SMOOTH), 0, 0, (int)w, (int)h, null, null);
        }
        else
        {
            g.drawString("Target", (int)w/2, (int)h/2);
        }
    }
    /**
     * This method is called whenever the observed object is changed.
     * An application calls an Observable object's
     * <code>notifyObservers</code> method to have all the object's
     * observers notified of the change.
     * @param var1 the observable object.
     * @throws NullPointerException if the argument is <code>null</code>.
     */
    @Override
    public void update(Observable var1)
    {
        paintComponent(this.parent.getGraphics());
    }
}
