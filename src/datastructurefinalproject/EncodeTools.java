
package datastructurefinalproject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 *
 * @author mohammadi
 */
public class EncodeTools 
{
    Tools tools = new Tools();
    Scanner scanner;
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
            tools.errorMessage(null, "Error occured ", fileNotFoundException.getMessage());
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
    
    Map<Character,String> mp = new HashMap<Character,String>();  
    public Map<Character,String> getHuffmanCodePerCharacter(HuffmanNode root, String s)
    {
        if(root.left == null && root.right == null && root.character !='~')
        {
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
}
