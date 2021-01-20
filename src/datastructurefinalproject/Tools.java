package datastructurefinalproject;

import java.awt.Toolkit;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;
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
    Scanner scanner;
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
    
    public String readWholeTextFromFile(File file)
    {
        String temp = "";
        try
        {
            scanner = new Scanner(file);
            while(scanner.hasNextLine())
            {
                temp += scanner.nextLine()+"\n";
            }
        }catch(FileNotFoundException fileNotFoundException)
        {
            errorMessage(null, "Error occured ", fileNotFoundException.getMessage());
        }
        return temp;
    }
 
    
    
    
    public char[] getCharacterArrayFromText(String text)
    {
        return text.toCharArray();
    }
    
    public int[] getLetterFrequency(char[] charArray)
    {
        int[] tempArray = new int[256];
        
        for(char c : charArray)
        {
            tempArray[c]++;
        }
        
        return tempArray;
    }
    
    
    public HuffmanNode createTree(int length, char[] charArray, int[] charArrayFrequency)
    {
        PriorityQueue<HuffmanNode> priorityQueue = new PriorityQueue<>(length,new MyComparator());
        
        for(int i = 0; i < length; i++)
        {
            HuffmanNode newHuffmanNode = new HuffmanNode();
            newHuffmanNode.character = charArray[i];
            newHuffmanNode.data = charArrayFrequency[charArray[i]];
            newHuffmanNode.left = newHuffmanNode.right = null;
            priorityQueue.add(newHuffmanNode);
            
        }
        
        HuffmanNode root = null;
        
        while(priorityQueue.size() > 1)
        {
            HuffmanNode left = priorityQueue.peek();
            priorityQueue.poll();
            
            HuffmanNode right = priorityQueue.peek();
            priorityQueue.poll();
            
            HuffmanNode fnewHuffmanNode = new HuffmanNode();
            
            fnewHuffmanNode.data = left.data + right.data;
            fnewHuffmanNode.character = '~';
            
            fnewHuffmanNode.left = left;
            fnewHuffmanNode.right = right;
            
            root = fnewHuffmanNode;
            
            priorityQueue.add(fnewHuffmanNode);
            
        }
        return root;
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
