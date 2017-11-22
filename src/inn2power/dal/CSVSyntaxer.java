/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inn2power.dal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author Storm
 */
public class CSVSyntaxer
{   
     public static void changeFile() throws FileNotFoundException, IOException
    {
        String InputFile = "companies.csv";
        
        String OutputFile = "companies_fix.csv";
        
         try (BufferedWriter output = new BufferedWriter(new FileWriter(OutputFile)))
         {
             Scanner input = new Scanner(new BufferedReader(new FileReader(new File(InputFile))));
             input.nextLine();
             while (input.hasNext())
             {
                 String line = input.nextLine();
                 String[] split = line.split(",");
                 String out;
                 if(split.length > 9){
                     if(split[1].startsWith("\"")){
                         split[1] = split[1].substring(1);
                     }
                     if(split[2].endsWith("\"")){
                         split[2] = split[2].substring(0, split[2].length()-1);
                     }
                     out = split[0]+","+split[1]+split[2]+","+split[3]+","+split[4]+","+split[5]+","+split[6]+","+split[7]+","+split[8];
                 }
                 else{
                     out = split[0]+","+split[1]+","+split[2]+","+split[3]+","+split[4]+","+split[5]+","+split[6]+","+split[7];
                 }
                 
                 output.write(out);
                 output.newLine();
             }
         }
    }
     public static void main(String[] args) throws IOException
    {
        changeFile();
    }
}
