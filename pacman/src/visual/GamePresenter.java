/**
 *  Class is holding all views to show in game
 *
 * @author xdomra00 xdomra00@stud.fit.vutbr.cz
 * @author xgarip00 xgarip00@stud.fit.vutbr.cz
 */




package xdomra00.src.visual;

import xdomra00.src.visual.common.CommonMaze;
import xdomra00.src.visual.common.CommonPacman;
import xdomra00.src.visual.view.FieldView;
import xdomra00.src.visual.view.GameStatusView;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
/**
 * The GamePresenter class represents a JPanel used to display the game.
 * It extends JPanel and sets the background color to black.
 * It also holds a CommonMaze object representing the maze used in the game.
 * @see CommonPacman
 * @see CommonMaze
 * @see FieldView
 * @see GameStatusView
 */
public class GamePresenter extends JPanel
{
    /**
     * is a final variable of type CommonMaze. It likely represents the maze object that is being used in some part of the code. Since there is no context provided, it's difficult to provide a more specific comment.
     */
    private final CommonMaze maze;
    /**
     * Constructs a new GamePresenter object with the specified CommonMaze.
     * The background color of the JPanel is set to black.
     *
     * @param maze the CommonMaze object representing the maze used in the game.
     */
    public GamePresenter(CommonMaze maze)
    {
        super();
        setBackground(Color.BLACK);
        this.maze = maze;
    }


    /**
     * Method is used for auto-resize
     */
    public void setProportions()
    {
        Container parent = getParent();
        if(parent == null)
            return;
        int min = Math.min(parent.getWidth(), parent.getHeight());
        ((JPanel)this.getComponents()[0]).setPreferredSize(new Dimension(min, min));
    }


    /**
     * Adds all views
     */
    public void open()
    {
        int rows = this.maze.numRows();
        int cols = this.maze.numCols();

        JPanel border = new JPanel(new BorderLayout());
        JPanel grid = new JPanel(new GridLayout(rows, cols));
        grid.setBackground(Color.BLACK);
        grid.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
        for(int i = 0; i < rows; i++)
        {
            for(int j = 0; j < cols; j++)
            {
                try
                {
                    FieldView field = new FieldView(this.maze.getField(i, j));
                    grid.add(field);
                }
                catch(Exception e)
                {
                    System.out.println("Error adding FieldView");
                }
            }
        }
        border.add(grid, BorderLayout.CENTER);
        border.add(new GameStatusView((CommonPacman)this.maze.getPacman()), BorderLayout.NORTH);
        this.add(border);

        this.addComponentListener(new ComponentAdapter()
        {
            public void componentResized(ComponentEvent event)
            {
                setProportions();
            }
        });
        setProportions();
    }


    /**
     * @return all field  views from grid-layout panel
     */
    public List<FieldView> getFiledViews()
    {
        List<FieldView> fws = new ArrayList<>();
        for(Component c : ((JPanel)((JPanel)getComponents()[0]).getComponents()[0]).getComponents())
        {
            fws.add((FieldView)c);
        }
        return fws;
    }
}