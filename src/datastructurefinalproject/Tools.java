package datastructurefinalproject;

import java.awt.Toolkit;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
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
 
    
//    public Object[][] getCharacterFromBinaryString(String strBin)
//    {
//        String[] temp = new String[256];
//        for()
//    }
    
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
    
    public char[] getTreeLetter(String data)
    {
        char[] tempC = data.toCharArray();
        String tempS = "";
        
        for(int i = 0; i < tempC.length; i += 4)
        {
            tempS += tempC[i];
        }
        
        return tempS.toCharArray();
    }
    
    public int[] getTreeLetterFrequency(String data)
    {
        char[] tempC = data.toCharArray();
        String tempS = "";
        
        for(int i = 2; i < tempC.length; i += 4)
        {
            tempS += tempC[i];
        }
        
        int[] tempI = new int[256];
        char[] tempCharArray = tempS.toCharArray();
        
        for(int i = 0; i < tempCharArray.length; i++)
        {
            tempI[i] = Integer.parseInt(tempCharArray[i]+"");
        }
        
        return tempI;
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
    
    
    public String decode(String S ,HuffmanNode root) {
    if (root == null) {
        return "";
    }
    char[] arr = S.toCharArray();
    int index = 0;
    String rst = "";
    while (index < arr.length) {
        HuffmanNode node = root;
        while (node != null) {
            if (node.left == null && node.right == null) {
                rst += node.character;
                break;//break inner while
            } else {
                char c = arr[index];
                if (c == '0') {
                    node = node.left;
                } else {
                    node = node.right;
                }
                index++;
            }
        }
    }
    return rst;
}
    
    
    public HuffmanNode createTreeFromCmpFileData(int length, char[] charArray, int[] charArrayFrequency)
    {
        PriorityQueue<HuffmanNode> priorityQueue = new PriorityQueue<>(length,new MyComparator());
        
        for(int i = 0; i < length; i++)
        {
            HuffmanNode newHuffmanNode = new HuffmanNode();
            newHuffmanNode.character = charArray[i];
            newHuffmanNode.data = charArrayFrequency[i];
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
    
    public void inOrder(HuffmanNode tree)
    {
        if(tree != null)
        {
            inOrder(tree.left);
            System.out.print(" | " + tree.data);
            inOrder(tree.right);
        }
    }
    
    Map<Character,String> mp = new HashMap<Character,String>();  
    public Map<Character,String> getHuffmanCodePerCharacter(HuffmanNode root, String s)
    {
        if(root.left == null && root.right == null && root.character !='~')
        {
//            System.out.println(root.character + " : " + s);
            mp.put(root.character,s);
            return mp; 
        }
            getHuffmanCodePerCharacter(root.left, s + "0");
            getHuffmanCodePerCharacter(root.right, s + "1");
        return mp;
   
     }
    
    public String getHuffmanCodeAllCharacter(char[] charArray, Map<Character,String> map)
    {
        String temp = "";
        
        for(char c : charArray)
        {
            temp += map.get(c);
        }
        
        return temp;
    }
    
    public String readTreeFromCmpFile(String path)
    {
        String temp = "";
        try 
        {
            FileInputStream f = new FileInputStream(new File(path));
            ObjectInputStream oi = new ObjectInputStream(f);
            temp = oi.readUTF();
            oi.close();
            
        }catch (FileNotFoundException ex) 
        {
            
        }catch(IOException iOException)
        {
            
        }
        return temp;
    }
    
    public byte[] readHuffmanCodeFromCmpFile(String path)
    {
        byte[] tempB = null;
        try 
        {
            FileInputStream f = new FileInputStream(new File(path));
            ObjectInputStream oi = new ObjectInputStream(f);
            oi.readUTF();
            oi.readChar();
            tempB = oi.readAllBytes();
            oi.close();
            
        }catch (FileNotFoundException ex) 
        {
            
        }catch(IOException iOException)
        {
            
        }
        return tempB;
    }
    
    public void writeTextFile(String path,String text)
    {
        try 
        {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path));
            bufferedWriter.write(text);
            bufferedWriter.close();
        } catch (IOException ex) 
        {
            
        }
    }
    
    
    public void writeCmpFile(String path,int[] arrayCharFrequency,byte[] binaryData)
    {
        try
        {
       
           FileOutputStream f = new FileOutputStream(new File(path));
           ObjectOutputStream os = new ObjectOutputStream(f);
           String tempCh = "";
           for(int i = 0; i < 256; i++)
           {
              if(arrayCharFrequency[i] != 0)
              {
                  tempCh += (char)i + " " + arrayCharFrequency[i] + " ";
              }
           }
           os.writeUTF(tempCh);
           os.writeChar('\n');
           os.write(binaryData);

           os.close();

           
            
        }catch(FileNotFoundException fileNotFoundException)
        {
            
        }catch(IOException iOException)
        {
            
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
