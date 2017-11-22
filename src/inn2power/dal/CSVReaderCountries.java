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
public class CSVReaderCountries
{
    

    /**
     * Returns all countries from the companies file, and weird other text,
     * basically not ordered list, and  same countries multiple times. 
     * @return
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public ObservableList<String> getAllCountries() throws FileNotFoundException, IOException
    {
        ObservableList<String> data = FXCollections.observableArrayList();

        try (BufferedReader CSVFile = new BufferedReader(new FileReader("companies.csv")))
        {
            CSVFile.readLine();// Skip header
            String dataRow = CSVFile.readLine();
            while (dataRow != null)
            {
                String[] dataArray = dataRow.split(",");
                data.add(dataArray[2]);

                dataRow = CSVFile.readLine(); // Read next line of data.
            }
            return data;
        }
    }

}
