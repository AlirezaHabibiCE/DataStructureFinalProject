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
 
    
    public Object[] getCharacterFromBinaryString(String strBin)
    {

        String[] splited8CharacterStringArray = new String[10000000];
        String remainString = "";
        int till = 0;
        
        for(int i = 1; i < strBin.length(); i++)
        {
            if(i % 8 == 0)
            {
                splited8CharacterStringArray[i / 8 - 1] = strBin.substring(till, i);
                till = i;
            }
        }
        

        
        remainString = strBin.substring(till, strBin.length());
        Object[] t = new Object[3];
        char[] characterArray = new char[splited8CharacterStringArray.length];
        int whileCounter = 0;
        while(splited8CharacterStringArray[whileCounter] != null)
        {
            int tempInteger = 0;
            String tempString = splited8CharacterStringArray[whileCounter];
            for(int i = 7; i >= 0; i--)
            {
                tempInteger += Integer.parseInt(tempString.charAt(i) + "") * Math.pow(2, 7 - i);
            }
            char tempCharacter = (char)tempInteger;
            
            characterArray[whileCounter] = tempCharacter;
            whileCounter++;
        }
        t[0] = characterArray;
        t[1] = remainString;
        
        return t;
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
    
    public char[] getTreeLetter(String data)
    {
        char[] characterArray = data.toCharArray();
        int countOfSpace = 0;
        int indexOfChar = 0;
        char[] charArray = new char[characterArray.length];
        for(int i = 0; i < characterArray.length; i++)
        {
            if(i+2 < characterArray.length)
            {
                if(characterArray[i] == ' ' && characterArray[i+1] == ' ' && characterArray[i+2] == ' ')
                {
                    charArray[indexOfChar++] = characterArray[i+1];
                    i += 4;
                    continue;
                }
            }
            if(characterArray[i] == ' ')
            {
                
                countOfSpace++;
            }
            
            if(countOfSpace % 2 == 0)
            {
                
                charArray[indexOfChar] = characterArray[i];
                indexOfChar++;
            }
            
            
            
        }
        return charArray;
    }
    
    public Object[] getTreeLetterFrequency(String data)
    {
        char[] arrayData = data.toCharArray();
        int till=0;
        int head=1;
        int fla = 0;
        char[] letterTemp = new char[256];
        int[] letterFreq = new int[256];
        int counterLetter = 0;
        int counterFrq = 0;
        
        while(head < arrayData.length)
        {
              if(till == 0)
              {
                letterTemp[counterLetter] = arrayData[0];
                counterLetter++;
                till++;
                head++;
                fla = 1;
              }
              
              if(arrayData[head] != ' ' && head+1 == arrayData.length)
              {
                  String temp = "";
                  for(int j = till +1; j < head+1;j++)
                  {
                      temp += arrayData[j];
                  }
                  letterFreq[counterFrq++] = Integer.parseInt(temp);
                  till = head;
                  head++;
                  fla = 0;
              }else if(arrayData[head]==' ' && till+1 == head)
              {
                  letterTemp[counterLetter++] = arrayData[till];
                  till = head + 1;
                  head += 2;
                  fla = 1;
              }else if(arrayData[head] == ' ')
              {
                  if(fla == 0)
                  {
                      letterTemp[counterLetter++] = arrayData[till+1];
                      till = head;
                      head++;
                      fla = 1;
                  }else
                  {
                      String temp = "";
                      for(int j = till + 1; j < head; j++)
                      {
                          temp += arrayData[j];
                      }
                      letterFreq[counterFrq++] = Integer.parseInt(temp);
                      till = head;
                      head++;
                      fla = 0;
                  }
              }else
              {
                  head++;
              }
        }
        int counter = 0;
        for(int c : letterFreq)
        {
            if(c != 0)
                counter++;
        }
        Object[] tempOb = new Object[3];
        tempOb[0] = letterTemp;
        tempOb[1] = letterFreq;
        tempOb[2] = counter;
        return tempOb;
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
    while (index < arr.length) 
    {
        HuffmanNode node = root;
        while (node != null) 
        {
            if (node.left == null && node.right == null)
            {
                rst += node.character;

                break;//break inner while
            } else 
            {
                char c = arr[index];
                if (c == '0')
                {
                    node = node.left;
                } else 
                {
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
            
            inOrder(tree.right);
            System.out.print(" | " + tree.data);
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
    
    public String readHuffmanCodeFromCmpFile(String path)
    {
        Object[] tempo = new Object[2];
        char[] tempB = null;
        String tempS = "";
        String huffmancode = "";
        try 
        {
            FileInputStream f = new FileInputStream(new File(path));
            ObjectInputStream oi = new ObjectInputStream(f);
            oi.readUTF();
            oi.readChar();
            String assumedCharacter = oi.readUTF();
            oi.readChar();
            String remainHuffmanCode = oi.readUTF();
            oi.close();
            String tempBinaryPart1 = "";
            
            for(char c : assumedCharacter.toCharArray())
            {
                String result = String.format("%8s", Integer.toBinaryString(c)).replace(' ', '0');
                tempBinaryPart1 += result;
            }
            
            huffmancode = tempBinaryPart1 + remainHuffmanCode;
            
            
        }catch (FileNotFoundException ex) 
        {
            
        }catch(IOException iOException)
        {
            
        }
        
        return huffmancode;
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
    
    
    public void writeCmpFile(String path,int[] arrayCharFrequency,char[] characterArray,String remainHuffmanCode)
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
           String charString = "";
           for(char c : characterArray)
           {
               if(c != '\0')
                charString += c;
           }
           os.writeUTF(tempCh);
           os.writeChar('\n');
           os.writeUTF(charString);
           os.writeChar('\n');
           os.writeUTF(remainHuffmanCode);
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
