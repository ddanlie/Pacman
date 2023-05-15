/**
 * A custom implementation of JButton that can be painted.
 * It extends the JButton class and adds custom styling and sound effects.
 * @author xdomra00 xdomra00@stud.fit.vutbr.cz
 * @author xgarip00 xgarip00@stud.fit.vutbr.cz
 */
package xdomra00.src.custom;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import javax.sound.sampled.*;
/**
 * A custom implementation of JButton that can be painted.
 * It extends the JButton class and adds custom styling and sound effects.
 */
public class CustomDefaultJbutton extends JButton
{
    /**
     * Creates a new CustomDefaultJbutton with the specified text.
     * @param text the text to be displayed on the button
     */
    public CustomDefaultJbutton(String text)
    {
        super(text);
        defaultConstructor();
    }
    /**
     * Creates a new CustomDefaultJbutton with the specified icon.
     * @param icon the icon to be displayed on the button
     */
    public CustomDefaultJbutton(ImageIcon icon)
    {
        super(icon);
        defaultConstructor();
    }
    /**
     * Initializes the default properties of the button, including size, font, colors, border, and sound effects.
     */
    private void defaultConstructor()
    {
        setContentAreaFilled(false);
        this.setPreferredSize(new Dimension(200, 80));
        this.setFont(new Font("Arial",  Font.BOLD, 35));
        this.setFocusPainted(false);
        this.setBorderPainted(true);
        this.setForeground(new Color(255, 210, 57));
        this.setBackground(new Color(12, 26, 79));
        this.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));

        File clickSound = new File("../lib/sounds/button_click.wav");

        this.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseEntered(MouseEvent e)
            {
                CustomDefaultJbutton.this.setBorderPainted(false);
                try
                {
                    Clip clip = AudioSystem.getClip();
                    clip.open(AudioSystem.getAudioInputStream(clickSound.getAbsoluteFile()));
                    clip.start();
                }
                catch (Exception ex) {}
            }
            @Override
            public void mouseExited(MouseEvent e)
            {
                CustomDefaultJbutton.this.setBorderPainted(true);
            }
        });
    }
}
