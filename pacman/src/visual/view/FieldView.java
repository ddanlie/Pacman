/**
 *  Class is representing field on the screen
 *
 * @author xdomra00 xdomra00@stud.fit.vutbr.cz
 * @author xgarip00 xgarip00@stud.fit.vutbr.cz
 */


package xdomra00.src.visual.view;

import xdomra00.src.custom.CustomJpanelBackground;
import xdomra00.src.frames.MainFrame;
import xdomra00.src.logic.common.Field;
import xdomra00.src.logic.common.MazeObject;
import xdomra00.src.logic.game.FieldClass;
import xdomra00.src.visual.common.CommonField;
import xdomra00.src.visual.common.CommonGhost;
import xdomra00.src.visual.common.CommonMazeObject;
import xdomra00.src.visual.common.Observable;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;
/**
 * This class represents a single field on the screen
 * and implements the Observable.Observer interface for observing changes
 * in its corresponding model.
 * It manages a list of ComponentView objects that are painted on the field.
 * It has a method for painting itself and a private update method for updating
 * its list of objects based on changes in its model.
 * It also loads an image for wall objects when instantiated.
 * @see Observable.Observer
 * @see ComponentView
 * @see CommonField
 * @see FieldClass
 * @see CommonGhost
 * @see CommonMazeObject
 * @see PacmanView
 * @see GhostView
 * @see KeyView
 * @see TargetView
 * @see PointView
 */
public class FieldView extends CustomJpanelBackground implements Observable.Observer
{
    /**
     * A final instance of the CommonField class that represents the field model in the game.
     */
    private final CommonField model;
    /**
     * A list of ComponentView instances that represent the objects present in the field model.
     */
    private final List<ComponentView> objects;
    private static Image wallImg = null;
    /**
     *  Constructor for creating a new FieldView object.
     *  Initializes its model and list of ComponentViews, and registers itself
     *  as an observer of its model.
     *  It also loads an image for wall objects when instantiated.
     *
     *  @param model the CommonField object to be used as the model for this view
     */
    public FieldView(CommonField model)
    {
        super();
        this.model = model;
        this.objects = new ArrayList();
        this.privUpdate();
        model.addObserver(this);
        if(wallImg == null)
        {
            try
            {
                wallImg = ImageIO.read(new File("../lib/sprites/wall_sprite.png"));
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }

    }
    /**
     *  Overrides the paintComponent method of its parent class to paint all
     *  of its ComponentViews.
     *
     *  @param g the Graphics object to be used for painting
     */
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        this.objects.forEach((v) -> {
            v.paintComponent(g);
        });
    }
    /**
     *  Private update method for updating its list of ComponentViews based on
     *  changes in its model.
     *  It clears the current list of objects and adds new ones based on the
     *  type of the model's field.
     */
    private void privUpdate()
    {
        this.objects.clear();
        Field.FieldType t = this.model.getFieldType();
        switch(t)
        {
            case FREE:
            {
                this.setBackground(Color.BLACK);//TODO: paste picture
                if (!this.model.isEmpty())
                {
                    CommonMazeObject o = this.model.get();
                    ComponentView v = null;
                    switch(o.getObjectType())
                    {
                        case PLAYER:
                        {
                            v = new PacmanView(this, this.model.get());
                            break;
                        }
                        case GHOST:
                        {
                            v = new GhostView(this, (CommonGhost)this.model.get());
                            break;
                        }
                        case KEY:
                        {
                            v = new KeyView(this, this.model.get());
                            break;
                        }
                        case TARGET:
                        {
                            v = new TargetView(this, this.model.get());
                            break;
                        }
                        case POINT:
                        {
                            v = new PointView(this, this.model.get());
                            break;
                        }
                    }
                    this.objects.add(v);//TODO: add more than one object
                }
                else
                {
                    this.objects.clear();//TODO: paste picture
                }
                break;
            }
            case WALL:
            {
                this.setBackgroundImage(wallImg);
                break;
            }
        }

        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }
    /**
     * This method is called by the Observable object that this FieldView instance is observing.
     * It updates the view of the field asynchronously using SwingUtilities.invokeLater() method to avoid blocking the UI thread.
     * @param field the Observable object that this FieldView instance is observing.
     */
    public final void update(Observable field)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                FieldView.this.privUpdate();
            }
        });

    }
    /**
     * Returns the FieldClass object that this FieldView is associated with.
     * @return the FieldClass object that this FieldView is associated with.
     */
    public FieldClass getModel()
    {
        return (FieldClass)model;
    }

}
