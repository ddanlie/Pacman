/**
 *  The GamePanel class represents the main panel of the game that extends JPanel
 *  and implements OpenableFrame and KeyListener interfaces. It contains the logic,
 *  game presenter, game objects, and graphical user interface components.
 * @author xdomra00 xdomra00@stud.fit.vutbr.cz
 * @author xgarip00 xgarip00@stud.fit.vutbr.cz
 */
package xdomra00.src.frames;


import xdomra00.src.custom.CustomDefaultJbutton;
import xdomra00.src.custom.CustomJpanelBackground;
import xdomra00.src.logic.common.Field;
import xdomra00.src.logic.common.MazeObject;
import xdomra00.src.logic.game.*;
import xdomra00.src.visual.GamePresenter;
import xdomra00.src.visual.view.FieldView;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * The GamePanel class represents the main panel of the game that extends JPanel
 * and implements OpenableFrame and KeyListener interfaces. It contains the logic,
 * game presenter, game objects, and graphical user interface components.
 */
public class GamePanel extends JPanel implements OpenableFrame, KeyListener
{
    //logic
    /**
     * A reference to the GamePresenter that controls the game logic and interacts with the view.
     */
    private GamePresenter gp;
    /**
     * An AtomicBoolean used to signal if the PacmanObject is currently not moving.
     */
    private AtomicBoolean pacmanIsNotMoving = new AtomicBoolean(true);
    /**
     * An AtomicBoolean used to signal if the game is currently searching for a path for a ghost.
     */
    private AtomicBoolean findsPath = new AtomicBoolean(false);
    /**
     * An AtomicBoolean used to signal if the game has ended.
     */
    private AtomicBoolean endGameFlag = new AtomicBoolean(false);
    /**
     * A MazeClass object representing the chosen map.
     */
    private MazeClass chosenMap;
    /**
     * A PacmanObject representing the player's character.
     */
    private PacmanObject pacmanOnMap;
    /**
     * A TargetObject representing the object that the player must reach to win.
     */
    private TargetObject target;
    /**
     * A List of GhostObject objects representing the game's enemies.
     */
    private List<GhostObject> ghosts;
    /**
     * A GameLogger object used to log game events.
     */
    private GameLogger logger;
    /**
     * An int representing the number of keys required to win the game.
     */
    private int keysGoal = 0;
    /**
     *  An int representing the number of steps a ghost takes before it moves.
     */
    private int ghostMoveEvery = 20;
    /**
     * A boolean indicating if the game has been won.
     */
    private boolean win = false;

    //logger

    //interface
    /**
     * ExecutorService objects used to execute tasks in separate threads.
     */
    private ExecutorService  threadExecutor, threadExecutor2;
    /**
     * A JPanel representing the game's main panel.
     */
    private  JPanel ingamePanel = new JPanel(new BorderLayout());
    /**
     * A CustomJpanelBackground object representing the panel displayed at the end of the game.
     */
    private CustomJpanelBackground  endgamePanel = new CustomJpanelBackground();
    /**
     * A JLabel representing the player's score.
     */
    JLabel scoreLabel = new JLabel("Score: 0");
    /**
     * A CustomDefaultJbutton object representing the button used to access the game's menu.
     */
    CustomDefaultJbutton menuButton = new CustomDefaultJbutton("MENU");
    /**
     * Constructs a new GamePanel object, sets double buffering, and layout.
     * It also initializes the graphical user interface components, including
     * ingamePanel and endgamePanel with their corresponding components.
     */
    public GamePanel()
    {
        super();
        setDoubleBuffered(true);
        setLayout(new CardLayout());


        endgamePanel.setLayout(new BorderLayout());
        JPanel boxPanel = new JPanel();
        boxPanel.setLayout(new BoxLayout(boxPanel, BoxLayout.Y_AXIS));

        menuButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuButton.setMaximumSize(menuButton.getPreferredSize());
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setFont(new Font("Arial",  Font.BOLD, 35));
        boxPanel.setOpaque(false);
        boxPanel.add(Box.createVerticalGlue());
        boxPanel.add(scoreLabel);
        boxPanel.add(Box.createVerticalStrut(250));
        boxPanel.add(menuButton);
        boxPanel.add(Box.createVerticalStrut(100));
        endgamePanel.add(boxPanel, BorderLayout.SOUTH);

        this.add(ingamePanel, "ingame");
        this.add(endgamePanel, "endgame");

    }
    /**
     * Opens the game panel and initializes the game presenter, logger, Pacman and ghost objects, and target object.
     * Also sets up mouse listeners for the game field views.
     */
    @Override
    public void open()
    {
        threadExecutor = Executors.newFixedThreadPool(2);
        chosenMap = ((MainFrame)getTopLevelAncestor()).getPickMapPanel().getChosenMap();
        GamePresenter gp = null;
        if(chosenMap != null)
        {
            gp = new GamePresenter(chosenMap);
            ingamePanel.add(gp, BorderLayout.CENTER);
            gp.open();
        }
        else
            ingamePanel.add(new Label("EMPTY"), BorderLayout.CENTER);

        pacmanOnMap = chosenMap.getPacman();
        ghosts = chosenMap.getGhosts();
        target = chosenMap.getTarget();
        logger = new GameLogger(chosenMap);
        pacmanOnMap.setLogger(logger);

        if(gp != null)
        {
            for(FieldView fw : gp.getFiledViews())
            {
                fw.addMouseListener(new MouseAdapter()
                {
                    @Override
                    public void mouseEntered(MouseEvent e)
                    {
                        super.mouseEntered(e);
                        SwingUtilities.invokeLater(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                ((JPanel)e.getComponent()).setBorder(BorderFactory.createLineBorder(Color.WHITE, 4));
                            }
                        });

                    }
                    @Override
                    public void mouseExited(MouseEvent e)
                    {
                        super.mouseExited(e);
                        SwingUtilities.invokeLater(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                ((JPanel)e.getComponent()).setBorder(BorderFactory.createEmptyBorder());
                            }
                        });

                    }
                    @Override
                    public void mouseClicked(MouseEvent e)
                    {
                        super.mouseClicked(e);
                        threadExecutor.submit(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                pacmanGoTo(fw.getModel());
                            }
                        });

                    }
                });
            }
        }



        threadExecutor.submit(new Runnable()
        {
            @Override
            public void run()
            {
                startGame();
            }
        });

        ((CardLayout)this.getLayout()).show(this, "ingame");
    }
    /**
     * Starts the game loop and continuously updates the game state until the game is won or lost.
     * Also manages the movement of ghosts and the win/lose conditions.
     */
    private void startGame()
    {
        keysGoal = chosenMap.getKeysCount();
        threadExecutor2 = Executors.newFixedThreadPool(ghosts.size()+1);

        endGameFlag.set(false);
        int moveCounter = 0;
        //move ghosts
        for(GhostObject g : ghosts)
        {
            threadExecutor2.submit(new Runnable()
            {
                @Override
                public void run()
                {
                    ghostFindPath(g);
                }
            });
        }
        while(!endGameFlag.get())
        {
            moveCounter++;
            if(moveCounter > 1000)
                moveCounter = 0;
            try
            {
                Thread.sleep(100);
            }
            catch(Exception e) {}

            //lose condition
            if(pacmanOnMap.getLives() == 0)
            {
                this.win = false;
                endGameFlag.set(true);
            }

            //win/open target condition
            if(pacmanOnMap.getKeysCount() >= keysGoal)
            {
                target.makeAccessible(true);
                if(pacmanOnMap.isOnTarget())
                {
                    this.win = true;
                    endGameFlag.set(true);
                }
            }
            else
            {
                target.makeAccessible(false);
            }

        }

        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                GamePanel.this.endGame();
            }
        });
    }
    /**
     * Ends the game and shows the endgamePanel with the appropriate background image and score.
     * Shuts down the threadExecutor and threadExecutor2, and awaits their termination.
     * @throws InterruptedException if the thread is interrupted while awaiting termination
     */
    private void endGame()
    {
        logger.logGameEnd(win);
        try
        {
            threadExecutor.shutdown();
            threadExecutor2.shutdown();
            if(!threadExecutor.awaitTermination(10, TimeUnit.SECONDS) ||
                    !threadExecutor2.awaitTermination(10, TimeUnit.SECONDS))
                throw new InterruptedException();

        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }

        String file;
        this.scoreLabel.setText(String.format("Score: %d", pacmanOnMap.getScore()*pacmanOnMap.getLives()));
        if(win)
        {
            file = "../lib/backgrounds/win_background.jpg";
        }
        else
        {
            file = "../lib/backgrounds/lose_background.jpg";
        }

        Image background = null;
        try
        {
            background = ImageIO.read(new File(file));
        }
        catch(Exception e)
        {
            background = null;
        }
        this.endgamePanel.setBackgroundImage(background);

        ((CardLayout)this.getLayout()).show(this, "endgame");

        ingamePanel.removeAll();
    }
    /**
     Moves Pacman to the specified location on the game field.
     @param field the game field location to move Pacman to
     */
    private void pacmanGoTo(FieldClass field)
    {
        //BFS
        try
        {
            try
            {
                while(findsPath.get())
                {
                    Thread.sleep(10);
                }
                findsPath.set(true);
            }
            catch(Exception e)
            {return;}
            findsPath.set(true);
            BFS pathSearch = new BFS(pacmanOnMap.getParent(), field);
            List<Field.Direction> path = null;
            path = pathSearch.find();
            if(path == null)
            {
                findsPath.set(false);
                return;
            }
            if(!pacmanIsNotMoving.get())
            {
                findsPath.set(false);
                return;
            }
            pacmanIsNotMoving.set(false);
            for(Field.Direction d : path)
            {
                //            if(!pacmanOnMap.canMove(Field.Direction.U))
                //                continue;
                if(endGameFlag.get())
                {
                    pacmanIsNotMoving.set(true);
                    findsPath.set(false);
                    return;
                }
                logger.logMove(pacmanOnMap, d);
                pacmanOnMap.move(d);
            }
            pacmanIsNotMoving.set(true);

        }
        catch(Exception e)
        {
            e.printStackTrace();
            pacmanIsNotMoving.set(true);
        }
        findsPath.set(false);
    }

    @Override
    public void keyTyped(KeyEvent e)
    {

    }
    /**
     * Invoked when a key is pressed. Moves the Pacman object in the desired direction if the key corresponds to one of the
     * arrow keys and the Pacman object can move in that direction. Logs the move and updates the game state accordingly.
     * @param e the KeyEvent object containing information about the key that was pressed
     */
    @Override
    public void keyPressed(KeyEvent e)
    {
        try
        {
            threadExecutor.submit(new Runnable()
            {
                @Override
                public void run()
                {
                    if(pacmanOnMap == null)
                        return;
                    if(!pacmanIsNotMoving.get())
                        return;
                    pacmanIsNotMoving.set(false);
                    try
                    {
                        switch(e.getKeyCode())
                        {
                            case(KeyEvent.VK_W):
                            {
                                if(pacmanOnMap.canMove(Field.Direction.U))
                                {
                                    logger.logMove(pacmanOnMap, Field.Direction.U);
                                    pacmanOnMap.move(Field.Direction.U);
                                }
                                break;
                            }
                            case(KeyEvent.VK_S):
                            {
                                if(pacmanOnMap.canMove(Field.Direction.D))
                                {
                                    logger.logMove(pacmanOnMap, Field.Direction.D);
                                    pacmanOnMap.move(Field.Direction.D);
                                }
                                break;
                            }
                            case(KeyEvent.VK_A):
                            {
                                if(pacmanOnMap.canMove(Field.Direction.L))
                                {
                                    logger.logMove(pacmanOnMap, Field.Direction.L);
                                    pacmanOnMap.move(Field.Direction.L);
                                }
                                break;
                            }
                            case(KeyEvent.VK_D):
                            {
                                if(pacmanOnMap.canMove(Field.Direction.R))
                                {
                                    logger.logMove(pacmanOnMap, Field.Direction.R);
                                    pacmanOnMap.move(Field.Direction.R);
                                }
                                break;
                            }
                        }
                    }
                    catch(Exception e)
                    {
                        pacmanIsNotMoving.set(true);
                    }
                    pacmanIsNotMoving.set(true);

                }
            });
        }
        catch(Exception ex)
        {
        }
    }

    @Override
    public void keyReleased(KeyEvent e)
    {

    }
    /**
     * Returns the JButton instance that represents the menu button.
     * @return the JButton instance representing the menu button.
     */
    public JButton getMenuButton()
    {
        return menuButton;
    }

    /**
     * Represents a cell in the maze.
     */
    private class mazeCell
    {
        /**
         * The field object contained in this maze cell.
         */
        public FieldClass field;
        /**
         * The maze cell to the left of this one.
         */
        public mazeCell left;
        /**
         * The maze cell to the right of this one.
         */
        public mazeCell right;
        /**
         * The maze cell at the top of this one.
         */
        public mazeCell top;
        /**
         * The maze cell at the bottom of this one.
         */
        public mazeCell down;
        /**
         * The weight of this cell used in maze generation algorithms.
         */
        public int weight = Integer.MAX_VALUE;
        /**
         * The cost of this cell used in maze generation algorithms.
         */
        public int cost = 1;
    }

    /**
     * Calculates the Manhattan distance between two MazeObjectClass objects.
     * @param o1 The first MazeObjectClass object.
     * @param o2 The second MazeObjectClass object.
     * @return The Manhattan distance between the two objects.
     */
    public int distance(MazeObjectClass o1, MazeObjectClass o2)
    {
        return Math.abs(o1.getParent().getRow() - o2.getParent().getRow()) + Math.abs(o1.getParent().getCol() - o2.getParent().getCol());
    }
    /**
     * Causes the specified ghost to find a path and move in a random direction.
     * If the game has ended, no action is taken.
     * @param ghost the GhostObject to move
     */
    public void ghostFindPath(GhostObject ghost)
    {
        while(!endGameFlag.get())
        {
            if(distance(ghost, pacmanOnMap) < 10)
            {
                try
                {
                    BFS pathSearch = new BFS(ghost.getParent(), pacmanOnMap.getParent());
                    while(findsPath.get()) {Thread.sleep(10);}
                    findsPath.set(true);
                    List<Field.Direction> path = pathSearch.find();
                    findsPath.set(false);
                    for(int i = 0; i < (Math.min(path.size(), 10)); i++)
                    {
                        try
                        {
                            while(findsPath.get()) {Thread.sleep(10);}
                            findsPath.set(true);
                            logger.logMove(ghost, path.get(i));
                            ghost.move(path.get(i));
                            findsPath.set(false);
                            Thread.sleep(200);
                        }
                        catch(Exception e)
                        {
                            findsPath.set(false);
                        }
                    }

                } catch(Exception e)
                {
                    findsPath.set(false);
                    e.printStackTrace();
                }
            }
            else
            {
                Field.Direction d = Field.Direction.values()[new Random().nextInt(Field.Direction.values().length)];
                while(!ghost.canMove(d))
                    d = Field.Direction.values()[new Random().nextInt(Field.Direction.values().length)];

                try
                {
                    while(findsPath.get()) {Thread.sleep(10);}
                    findsPath.set(true);
                    logger.logMove(ghost, d);
                    ghost.move(d);
                    findsPath.set(false);
                    Thread.sleep(new Random().nextInt(200, 500));
                } catch(Exception e)
                {
                    findsPath.set(false);
                }

            }

        }
        findsPath.set(false);
    }

}
