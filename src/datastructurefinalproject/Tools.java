package datastructurefinalproject;

import java.awt.Toolkit;
import javax.swing.JFrame;

/**
 *
 * @author mohammadi
 */
public class Tools 
{
    public void setCenter(JFrame jframe)
    {
            Toolkit tool = Toolkit.getDefaultToolkit();
            int width = jframe.getWidth();
            int height = jframe.getHeight();
            int screenWidth = tool.getScreenSize().width;
            int screenHeight = tool.getScreenSize().height;
            int x = (screenWidth - width)/2;
            int y = (screenHeight-height)/2;
            jframe.setLocation(x, y);
    }
}
