/**
 * The PickMapPanel class represents the panel that allows the player to choose a map to play on.
 * It extends JPanel and implements the OpenableFrame interface.
 * The panel contains buttons to navigate between maps and a "PLAY" button to start the game.
 * The chosen map is displayed in the center of the panel.
 * @author xdomra00 xdomra00@stud.fit.vutbr.cz
 * @author xgarip00 xgarip00@stud.fit.vutbr.cz
 */
package xdomra00.src.frames;

import xdomra00.src.custom.CustomDefaultJbutton;
import xdomra00.src.custom.CustomJpanelBackground;
import xdomra00.src.logic.common.Field;
import xdomra00.src.logic.game.*;
import xdomra00.src.visual.GamePresenter;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
/**
 * The PickMapPanel class represents the panel that allows the player to choose a map to play on.
 * It extends JPanel and implements the OpenableFrame interface.
 * The panel contains buttons to navigate between maps and a "PLAY" button to start the game.
 * The chosen map is displayed in the center of the panel.
 * @see Field
 * @see GamePresenter
 * @see CustomDefaultJbutton
 * @see CustomJpanelBackground
 */
public class ReplayPanel extends CustomJpanelBackground implements OpenableFrame
{
    /**
     * A button that represents a menu in the game.
     */
    private CustomDefaultJbutton menuButton;
    /**
     * An AtomicBoolean variable that indicates whether the game is in "auto" or "step" mode.
     */
    private AtomicBoolean mode = new AtomicBoolean(true);
    /**
     * An AtomicBoolean variable that indicates whether the game is in "forward" or "backward" direction mode.
     */
    private AtomicBoolean directionMode = new AtomicBoolean(true);
    /**
     * A MazeClass object that represents the current maze in the game.
     */
    private MazeClass currentMaze = null;
    /**
     * A PacmanObject object that represents the Pacman character in the game.
     */
    private PacmanObject pacman;
    /**
     * A List of GhostObject objects that represents the ghosts in the game.
     */
    private List<GhostObject> ghosts;
    /**
     * A MazeConfigure object that reads and configures the maze from the file system.
     */
    private MazeConfigure mapReader = null;
    /**
     *  A String that represents the path of the log file.
     */
    private final String logFilename = "../data/log/log.txt";
    /**
     *  A boolean variable that indicates whether the auto-mode should sleep between moves.
     */
    private boolean autoThreadSleep = false;
    /**
     * A GameLogger object that logs the game events.
     */
    private GameLogger logger;
    /**
     * An ExecutorService that manages the auto-mode thread.
     */
    private ExecutorService autoModeExecutor;
    /**
     * A JPanel that represents the arrow keys panel in the game.
     */
    private  JPanel arrowPanel;
    /**
     * A JPanel that represents the settings panel in the game.
     */
    private  JPanel settingsPanel;
    /**
     * A JPanel used as a placeholder in the game.
     */
    private JPanel emptyPanel;
    /**
     * A CustomDefaultJbutton that represents the button used to switch between "auto" and "step" mode.
     */
    private CustomDefaultJbutton modeButton;
    /**
     * Creates a new ReplayPanel instance.
     * Initializes the panel by setting the background image, layout, and creating
     * the navigation buttons and settings panel.
     */
    public ReplayPanel()
    {
        super();
        try
        {
            setBackgroundImage(ImageIO.read(new File("../lib/backgrounds/replay_background.png")));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        setLayout(new BorderLayout());
        arrowPanel = new JPanel();
        arrowPanel.setOpaque(false);
        settingsPanel = new JPanel();
        settingsPanel.setOpaque(false);
        settingsPanel.setLayout(new BoxLayout(settingsPanel, BoxLayout.Y_AXIS));
        settingsPanel.setPreferredSize(new Dimension(250, 0));
        emptyPanel = new JPanel();
        emptyPanel.setOpaque(false);
        emptyPanel.setPreferredSize(new Dimension(250, 0));


        CustomDefaultJbutton backStepButton = new CustomDefaultJbutton(new ImageIcon(new ImageIcon("../lib/icons/button_prev_icon.png").getImage().getScaledInstance(120, 80, Image.SCALE_SMOOTH)));
        backStepButton.setOpaque(false);
        backStepButton.setBorder(BorderFactory.createEmptyBorder());
        backStepButton.addActionListener(e ->
        {
            step(false);
        });

        menuButton = new CustomDefaultJbutton("MENU");

        CustomDefaultJbutton forwardStepButton = new CustomDefaultJbutton(new ImageIcon(new ImageIcon("../lib/icons/button_next_icon.png").getImage().getScaledInstance(120, 80, Image.SCALE_SMOOTH)));
        forwardStepButton.setOpaque(false);
        forwardStepButton.setBorder(BorderFactory.createEmptyBorder());
        forwardStepButton.addActionListener(e ->
        {
            step(true);
        });

        CustomDefaultJbutton beginButton = new CustomDefaultJbutton("BEGIN");
        beginButton.addActionListener(new ActionListener()
        {
            /**
             * Action listener for the "BEGIN" button. Sets the replay to the beginning.
             * @param e the action event
             */
            @Override
            public void actionPerformed(ActionEvent e)
            {
                to(false);
            }
        });

        CustomDefaultJbutton endButton = new CustomDefaultJbutton("END");
        endButton.addActionListener(new ActionListener()
        {
            /**
             * Action listener for the "END" button. Sets the replay to the end.
             * @param e the action event
             */
            @Override
            public void actionPerformed(ActionEvent e)
            {
                to(true);
            }
        });


        JLabel modeLabel = new JLabel("MODE:");
        modeLabel.setForeground(Color.GRAY);
        modeLabel.setFont(new Font("Arial",  Font.BOLD, 35));
        modeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        modeButton = new CustomDefaultJbutton("STEP");
        modeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        modeButton.setMaximumSize(modeButton.getPreferredSize());
        menuButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuButton.setMaximumSize(menuButton.getPreferredSize());

        menuButton.addActionListener(e ->
        {
            close();
        });

        modeButton.addActionListener(new ActionListener()
        {
            /**
             * Action listener for the "STEP" and "AUTO" buttons. Sets mods of replaying.
             * @param e the action event
             */
            @Override
            public void actionPerformed(ActionEvent e)
            {
                changeMode();
                boolean m = mode.get();
                if(m)
                    modeButton.setText("STEP");
                else
                    modeButton.setText("AUTO");
            }
        });


        settingsPanel.add(Box.createVerticalGlue());
        settingsPanel.add(modeLabel);
        settingsPanel.add(modeButton);
        settingsPanel.add(Box.createVerticalGlue());
        settingsPanel.add(menuButton);
        settingsPanel.add(Box.createVerticalGlue());

        arrowPanel.add(beginButton);
        arrowPanel.add(backStepButton);
        arrowPanel.add(Box.createHorizontalGlue());
        arrowPanel.add(forwardStepButton);
        arrowPanel.add(endButton);

        this.add(arrowPanel, BorderLayout.SOUTH);
        this.add(settingsPanel, BorderLayout.WEST);
        this.add(emptyPanel, BorderLayout.EAST);



    }
    /**
     * Opens the ReplayPanel and initializes the required objects and components.
     * If the currentMaze is null, it displays an empty label, otherwise it creates a GamePresenter object
     * and adds it to the ReplayPanel.
     */
    @Override
    public void open()
    {
        logger = new GameLogger();
        autoModeExecutor = Executors.newFixedThreadPool(1);

        if(mapReader == null)
            mapReader = new MazeConfigure();

        currentMaze = mapReader.createMaze(logFilename);

        if(currentMaze == null)
        {
            this.add(new JLabel("Empty"), BorderLayout.CENTER);
        }
        else
        {
            this.removeAll();
            this.add(arrowPanel, BorderLayout.SOUTH);
            this.add(settingsPanel, BorderLayout.WEST);
            this.add(emptyPanel, BorderLayout.EAST);
            GamePresenter result = new GamePresenter(currentMaze);
            result.setOpaque(false);
            JPanel wrapper = new JPanel(new GridLayout(1,1));
            wrapper.setOpaque(false);
            wrapper.add(result);
            this.add(wrapper, BorderLayout.CENTER);
            result.open();
            this.validate();
        }

        pacman = currentMaze.getPacman();
        ghosts = currentMaze.getGhosts();
    }
    /**
     * Closes the replay panel and stops any ongoing auto mode execution if it is active.
     */
    public void close()
    {
        mode.set(true);
        directionMode.set(true);
        modeButton.setText("STEP");
        try {
            autoModeExecutor.shutdown();
            if(!autoModeExecutor.awaitTermination(10, TimeUnit.SECONDS))
                throw new InterruptedException();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    /**
     * Changes the mode of the game to either step-by-step mode or automatic mode.
     * If the current mode is step-by-step, it changes to automatic mode, and vice versa.
     * If changing to automatic mode, it shuts down the current executor and creates a new one.
     */
    private void changeMode()
    {
        boolean b = mode.get();
        if(b)
        {
            mode.set(false);
        }
        else
        {
            mode.set(true);
            try {
                autoModeExecutor.shutdown();
                if(!autoModeExecutor.awaitTermination(10, TimeUnit.SECONDS))
                    throw new InterruptedException();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            autoModeExecutor = Executors.newFixedThreadPool(1);
        }
    }

    /**
    * Performs a single step in the game either forward or backward based on the direction flag.
    * @param direction true to step forward, false to step backward
    */
    private void step(boolean direction)
    {
        directionMode.set(direction);
        boolean b = mode.get();
        if(b)
        {
            if(direction)
                stepForward();
            else
                stepBackward();
        }
        else
        {
            try
            {
                autoModeExecutor.submit(new Runnable()
                {
                    /**
                     * Runs the game in auto-mode by continuously stepping forward or backward
                     * based on the direction flag until the mode is switched to manual.
                     */
                    @Override
                    public void run()
                    {
                        while(!mode.get())
                        {
                            try
                            {
                                Thread.sleep(100);
                            } catch(Exception e)
                            {
                                e.printStackTrace();
                            }
                            if(directionMode.get())
                                stepForward();
                            else
                                stepBackward();
                        }

                    }
                });
            }
            catch(Exception e) {}
        }
    }
    /**
     * Executes a single step forward in the game by reading the next action from the logger and executing it.
     * If the action is invalid or empty, it returns false.
     * If the action is a move, it updates the position of the Pac-Man or the ghost accordingly.
     * If the action is a loss of life, it decreases the number of lives of the Pac-Man.
     * If the action is getting a key or a point, it updates the score of the Pac-Man.
     * @return true if the step is successfully executed, false otherwise.
     */
    private boolean stepForward()
    {
        String action = logger.unlog(true);
        String[] tokens = action.split(" ");
        if(tokens[0].equals("F") || tokens[0].equals("T") || tokens[0].equals(""))
        {
            logger.unlog(false);
            return false;
        }
        if(tokens[0].equals("M"))
        {
            if(tokens[1].equals("P"))
                pacman.quietMove(Field.Direction.valueOf(tokens[2]));
            if(tokens[1].equals("G"))
            {
                for(GhostObject g: ghosts)
                {
                    if(g.getId() == Integer.parseInt(tokens[2]))
                    {
                        g.quietMove(Field.Direction.valueOf(tokens[3]));
                        break;
                    }
                }
            }
        }
        else
        if(tokens[0].equals("L"))
        {
            pacman.setLives(pacman.getLives()-1);
        }
        else
        if(tokens[0].equals("K"))
        {
            pacman.getKey();
        }
        else
        if(tokens[0].equals("P"))
        {
            pacman.getPoint();
        }
        return true;
    }
    /**
     * Finds the opposite direction of a given direction.
     * @param d the direction for which to find the opposite direction
     * @return the opposite direction of the given direction
     */
    private Field.Direction findOppositeDirection(String d)
    {
        Field.Direction move = null;
        switch(Field.Direction.valueOf(d))
        {
            case D:
            {
                move = Field.Direction.U;
                break;
            }
            case U:
            {
                move = Field.Direction.D;
                break;
            }
            case R:
            {
                move = Field.Direction.L;
                break;
            }
            case L:
            {
                move = Field.Direction.R;
                break;
            }
        }
        return move;
    }
    /**
     * Performs a single step in the backward direction, by undoing the last action taken in the game.
     * This method reads the last executed action from the logger and executes it in reverse.
     * If the last action was a movement, the opposite direction is executed instead.
     * If the last action was a life loss, it is undone by increasing the number of lives of the Pac-Man.
     * If the last action was a key pickup, it is undone by decreasing the number of keys of the Pac-Man
     * and placing the key object back in the corresponding field.
     * If the last action was a point pickup, it is undone by decreasing the score of the Pac-Man
     * and placing the point object back in the corresponding field.
     * @return true if there are still steps to undo, false if all steps have been undone.
     */
    private boolean stepBackward()
    {
        //                line1           line1 (read)             line1                    line1 (read)  |     next back step:  we need ->      line1
        //we need ->      line2     1 -> |line2             2 ->   line2 (read)      3 ->  |line2         |                      we are here -> |line2
        //we are here -> |line3           line3                   |line3                    line3         |                                      line3
        logger.unlog(false);//back to beginning of current line (previous line is read)
        String action = logger.unlog(true);//read current line
        String eof = logger.unlog(false);//back to beginning of current line (previous line is read) (next time stepBack called previous line becomes current)

        String[] tokens = action.split(" ");

        if(tokens[0].equals("M"))
        {
            if(tokens[1].equals("P"))
            {
                pacman.quietMove(findOppositeDirection(tokens[2]));
                pacman.setMovingDirection(Field.Direction.valueOf(tokens[2]));
            }

            if(tokens[1].equals("G"))
            {
                for(GhostObject g: ghosts)
                {
                    if(g.getId() == Integer.parseInt(tokens[2]))
                    {
                        g.quietMove(findOppositeDirection(tokens[3]));
                        break;
                    }
                }
            }
        }
        else
        if(tokens[0].equals("L"))
        {
            pacman.setLives(pacman.getLives()+1);
        }
        else
        if(tokens[0].equals("K"))
        {
            pacman.setKeysCount(pacman.getKeysCount()-1);
            pacman.getParent().quietPut(new KeyObject());
            pacman.getParent().remove(pacman);
            pacman.getParent().quietPut(pacman);

        }
        else
        if(tokens[0].equals("P"))
        {
            pacman.setScore(pacman.getScore()-1);
            pacman.getParent().quietPut(new PointObject());
            pacman.getParent().remove(pacman);
            pacman.getParent().quietPut(pacman);

        }
        return !eof.equals("");
    }
    /**
     * Changes the mode of the game to manual and steps through the game either forward or backward, depending on the boolean parameter.
     * @param direction a boolean indicating whether to step forward (true) or backward (false)
     */
    public void to(boolean direction)
    {
        mode.set(false);
        changeMode();
        modeButton.setText("STEP");
        directionMode.set(direction);
        if(direction)
        {
            while(stepForward()){}
        }
        else
        {
            while(stepBackward()){}
        }
    }
    /**
     * Returns the menu button of the game screen.
     * @return the menu button
     */
    public JButton getMenuButton()
    {
        return menuButton;
    }

}
