package inn2power.dal;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This class is used to read custom CSV files
 *
 * @author Axl
 */
public class CSVReader
{

    /**
     * Reads the list of countries and returns an ObservableList containing all
     * of them
     *
     * @return All the countries
     */
    public ObservableList<String> getAllCountries()
    {
        ObservableList<String> data = FXCollections.observableArrayList();

        try (BufferedReader CSVFile = new BufferedReader(
                new FileReader("EuropeCountryList.csv")))
        {
            String dataRow = CSVFile.readLine();
            dataRow = CSVFile.readLine(); // Skip the header

            while (dataRow != null)
            {
                data.add(dataRow);
                dataRow = CSVFile.readLine();
            }
            return data;
        }
        catch (IOException e)
        {
            System.out.println("Could not read file.");
            System.out.println(e);
            return null;
        }
    }
}
