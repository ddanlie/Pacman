/**
 * Main file launching the whole application
 *
 * @author xdomra00 xdomra00@stud.fit.vutbr.cz
 * @author xgarip00 xgarip00@stud.fit.vutbr.cz
 */


package xdomra00.src;

import xdomra00.src.frames.*;
import javax.swing.*;
/**
 * The Main class is the entry point of the application.
 * It launches the main frame on the Event Dispatch Thread using SwingUtilities.invokeLater().
 * The Main frame contains the main menu and is responsible for the overall application flow.
 * The class has only one method, main(), which is the starting point of the application.
 * When invoked, it creates a new instance of the MainFrame and launches it.
 * @see javax.swing.SwingUtilities#invokeLater(Runnable)
 * @see xdomra00.src.frames.MainFrame
 * @version 1.0
 * @since 2023-05-01
 */
public class Main
{
    /**
     * The entry point of the application. Launches the MainFrame on the Swing Event Dispatch Thread.
     * @param args command-line arguments (unused)
     */
    public static void main(String ... args)
    {
       SwingUtilities.invokeLater(new Runnable()
       {
           @Override
           public void run()
           {
               new MainFrame();
           }
       });
    }
}
