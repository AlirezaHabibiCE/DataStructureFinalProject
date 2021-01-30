package datastructurefinalproject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.PriorityQueue;

/**
 *
 * @author mohammadi
 */
public class DecodeTools 
{
    Tools tools = new Tools();
    // read whole first line of tree data from cmp file
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
            tools.errorMessage(null, ex.getMessage() , "File Not Found Error!");
        }catch(IOException iOException)
        {
            tools.errorMessage(null, iOException.getMessage() , "File Input Output Error!");
        }
        return temp;
    }
    
    public Object[] getTreeLetterAndFrequency(String data)
    {
        char[] arrayData = data.toCharArray();
        int till=0; // first pivote of letter or freq
        int head=1; // second pivote of letter or freq
        int fla = 0; // flag for checking of insertion in to letter or freq array
        char[] letterTemp = new char[256];
        int[] letterFreq = new int[256];
        int counterLetter = 0;
        int counterFrq = 0;
        
        while(head < arrayData.length)
        {
              if(till == 0) // checking for first element
              {
                letterTemp[counterLetter] = arrayData[0];
                counterLetter++;
                till++;
                head++;
                fla = 1;
              }
              
              if(arrayData[head] != ' ' && head+1 == arrayData.length) // checking for last element
              {
                  String temp = "";
                  for(int j = till +1; j < head+1;j++) // getting last element freq
                  {
                      temp += arrayData[j];
                  }
                  letterFreq[counterFrq++] = Integer.parseInt(temp);
                  till = head;
                  head++;
                  fla = 0;
              }else if(arrayData[head]==' ' && till+1 == head) // checking for space character
              {
                  letterTemp[counterLetter++] = arrayData[till];
                  till = head + 1;
                  head += 2;
                  fla = 1;
              }else if(arrayData[head] == ' ') // getting letter and freq in general case
              {
                  if(fla == 0) // getting letter
                  {
                      letterTemp[counterLetter++] = arrayData[till+1];
                      till = head;
                      head++;
                      fla = 1;
                  }else // getttin freq
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
              }else // incrementing head when head is not space
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
    
    // creatting huffman tree from data getted from cmp file
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
                String result = String.format("%8s", Integer.toBinaryString(c)).replace(' ', '0'); // prevent from lossing first zeros
                tempBinaryPart1 += result;
            }
            
            huffmancode = tempBinaryPart1 + remainHuffmanCode;
            
            
        }catch (FileNotFoundException ex) 
        {
            tools.errorMessage(null, ex.getMessage() , "File Not Found Error!");
        }catch(IOException iOException)
        {
            tools.errorMessage(null, iOException.getMessage() , "File Input Output Error!");
        }
        
        return huffmancode;
    }
     
     public String decode(String huffmanCode ,HuffmanNode root) 
     {
        if (root == null) 
        {
            return "";
        }
        
        char[] arr = huffmanCode.toCharArray();
        int index = 0;
        String rst = ""; // original text after decode
        while (index < arr.length) 
        {
            HuffmanNode node = root;
            while (node != null) // repeat untill reaching end of character array
            {
                if (node.left == null && node.right == null)
                {
                    rst += node.character;
                    break;
                }else 
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
     
    public void writeTextFile(String path,String text)
    {
        try 
        {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path));
            bufferedWriter.write(text);
            bufferedWriter.close();
        } catch (IOException ex) 
        {
            tools.errorMessage(null, ex.getMessage() , "File Input Output Error!");
        }
    }
}
