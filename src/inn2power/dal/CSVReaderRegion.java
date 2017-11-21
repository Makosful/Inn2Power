package inn2power.dal;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Hussain
 */
public class CSVReaderRegion
{

    ObservableList<String> countries;

    /**
     * Loads and stores a list of countries from a region
     *
     * @param region The region which's country to load
     * @throws FileNotFoundException
     * @throws IOException
     */
    public CSVReaderRegion(String region) throws FileNotFoundException, IOException
    {
        countries = FXCollections.observableArrayList();

        try (BufferedReader CSVFile = new BufferedReader(new FileReader("./regions/" + region + ".csv")))
        {
            
            String country = CSVFile.readLine();
            while (country != null)
            {
                countries.add(country); // Adds the county to the list
                country = CSVFile.readLine();     // Moves to the next line
            }
        }
    }

    /**
     * Gets the full list of countries
     *
     * @return Returns an observable list of Strigs containing all the countries
     * from the specified region
     */
    public ObservableList<String> getAllCountries()
    {
        return countries;
    }
}
