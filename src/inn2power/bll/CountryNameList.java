package inn2power.bll;

import inn2power.dal.CSVReaderCountries;
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

    ObservableList<String> countries;

        public CountryNameList(String region) throws IOException
        {
        CSVReaderRegion csv = new CSVReaderRegion(region);
        countries = csv.getAllCountries();
        }
        
    // Ordered list of countries, returns only every country once.
    public ObservableList<String> removeDuplicates() throws IOException
    {
        ObservableList<String> noDublicatesList = FXCollections.observableArrayList();
        countries.stream().filter((country) -> (!noDublicatesList.contains(country)
                && !country.contains("\""))).forEachOrdered((country) ->
        {
            noDublicatesList.add(country);
        });
        return noDublicatesList;
    }

    public ObservableList<String> getCountries()
    {
        return countries;
    }
}
