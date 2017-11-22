/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inn2power.dal;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Hussain
 */
public class Countries
{    
        
    /**
     * Filereaders, regions.
     * @throws FileNotFoundException 
     */
    public Countries() throws FileNotFoundException
    {
        
    }
    /**
     * Looping through each file, adding them to arrayList.
     * @param fileReader
     * @return
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public List<String> getAllCountries(FileReader fileReader) throws FileNotFoundException, IOException 
    {
        List<String> countries =  new ArrayList();
        String line;
        try (BufferedReader br = new BufferedReader(fileReader)) {
            while ((line = br.readLine()) != null) {
                countries.add(line);
            }
        }
        return countries;
    }
}
