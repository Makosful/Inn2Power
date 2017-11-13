package inn2power.bll;

import inn2power.dal.CSVReaderRegion;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Hussain
 */
public class CountryNameList
{

    CSVReaderRegion csvrc;

    public CountryNameList(String region) throws IOException
    {
        this.csvrc = new CSVReaderRegion(region);
    }

    // Ordered list of countries, returns only every country once.
    public ObservableList<String> removeDublicates()
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
