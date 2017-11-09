/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inn2power.bll;

import inn2power.dal.CSVReaderCountries;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Hussain
 */
public class CountryNameList 
{
    CSVReaderCountries csvrc = new CSVReaderCountries();
    
           // Ordered list of countries, returns only every country once.
        public ObservableList<String> allCountriesCorrect() throws IOException
        {
            ObservableList<String> countriesData = FXCollections.observableArrayList();
            for (String string : csvrc.getAllCountries())
            {
                if (!countriesData.contains(string) && !string.contains("\""))
                {
                    countriesData.add(string);
                }
            }
            return countriesData;
        }
}
