package datastructurefinalproject;

import java.awt.Toolkit;
import java.util.Comparator;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author mohammadi
 */

class HuffmanNode
{
    
    int data;
    char character;
    HuffmanNode left,right;
   
}

class MyComparator implements Comparator<HuffmanNode>
{
    @Override
    public int compare(HuffmanNode x, HuffmanNode y)
    {
       return x.data - y.data; 
    }
}

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
    
    public void inOrder(HuffmanNode tree)
    {
        if(tree != null)
        {
            inOrder(tree.left);
            
            inOrder(tree.right);
            System.out.print(" | " + tree.data);
        }
    }
  
    
    // ------------------------- Messeges -----------------------------------
    
    public void successMessage(JFrame jFrame,String message)
    {
        JOptionPane.showMessageDialog(jFrame, message,"Successful Message",JOptionPane.INFORMATION_MESSAGE);
    }
    public void wrongMessage(JFrame jFrame,String message,String title)
    {
        JOptionPane.showMessageDialog(jFrame, message,title,JOptionPane.WARNING_MESSAGE);
    }
    public void errorMessage(JFrame jFrame,String message,String title)
    {
        JOptionPane.showMessageDialog(jFrame, message,title,JOptionPane.ERROR_MESSAGE);
    }
     public void errorMessage(JFrame jFrame,Exception e ,String message,String title)
    {
        JOptionPane.showMessageDialog(jFrame, message+" Error : "+e.getMessage(),title,JOptionPane.ERROR_MESSAGE);
    }
}
