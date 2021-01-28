/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
}
