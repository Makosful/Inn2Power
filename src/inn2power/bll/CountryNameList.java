package inn2power.bll;

import inn2power.dal.CSVReaderRegion;
import java.io.IOException;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Hussain
 */
public class CountryNameList
{

    ObservableList<String> countries;

    /**
     * Creates a new list of countries based of the region specified
     *
     * @param region The region from which to load the countries from
     * @throws IOException
     */
    public CountryNameList(String region) throws IOException
    {
        CSVReaderRegion csv = new CSVReaderRegion(region);
        countries = csv.getAllCountries();
    }

    /**
     * Creates a new list of countries using an existing list
     *
     * @param list The list of countries
     */
    public CountryNameList(List<String> list)
    {
        this.countries = FXCollections.observableArrayList(list);
    }

    // Ordered list of countries, returns only every country once.
    public ObservableList<String> allCountriesCorrect() throws IOException
    {
        ObservableList<String> noDublicatesList = FXCollections.observableArrayList();
        countries.stream().filter((country) -> (!noDublicatesList.contains(country)
                && !country.contains("\""))).forEachOrdered((country) ->
        {
            noDublicatesList.add(country);
        });
        return noDublicatesList;
    }

    /**
     * Gets all the countries without modifying the list in any way. If the list
     * has been modified before calling this, then those modifications will
     * apply
     *
     * @return Returns the raw list of countries
     */
    public ObservableList<String> getCountries()
    {
        return countries;
    }
}
