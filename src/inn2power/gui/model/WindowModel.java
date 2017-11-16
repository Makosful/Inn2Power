package inn2power.gui.model;

import be.Company;
import inn2power.bll.BllManager;
import inn2power.bll.CountryNameList;
import inn2power.bll.Filtering;
import inn2power.bll.Search;
import inn2power.dal.DataAccess;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Axl
 */
public class WindowModel
{

    private DataAccess data;
    private Search search;
    private Filtering filtering;
    private ObservableList<Company> CompanyObsArrayList = FXCollections.observableArrayList();

    public WindowModel()
    {
        try
        {
            data = new DataAccess();
            search = new Search();
            filtering = new Filtering();
        } catch (IOException ex)
        {
            Logger.getLogger(BllManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Gets a list containing all the companies
     *
     * @return observablelist with companies
     * @throws IOException
     */
    public ObservableList<Company> getAllCompanies() throws IOException
    {
        CompanyObsArrayList.addAll(data.getAllCompanies());
        return CompanyObsArrayList;
    }

    /**
     * Gets the companies that contains the searchresult and updates the
     * tableview with the observablelist
     *
     * @param searchText
     */
    public void Search(String searchText)
    {
        CompanyObsArrayList.clear();
        List<Company> result = search.getSearchResult(data.getAllCompanies(), searchText);
        CompanyObsArrayList.addAll(result);
    }

    /**
     * Sends the checkbox values as parameters to the filter, gets the filtered
     * list of companies back which are added to the observable list
     *
     * @param regionCheckboxes
     * @throws IOException
     */
    public void filterBox(boolean[] regionCheckboxes) throws IOException
    {
        List<Company> filteredList;

        filteredList = filtering.addFilters(regionCheckboxes);
        CompanyObsArrayList.clear();
        CompanyObsArrayList.addAll(filteredList);
    }

    /**
     *
     * @param region
     * @return @throws IOException
     */
    public ObservableList<String> getListCountries(String region) throws IOException
    {
        CountryNameList cnl = new CountryNameList(region);
        ObservableList<String> list = cnl.allCountriesCorrect();
        return list;
    }

    /**
     *
     * @param country
     */
    public void addCountryFilter(String country)
    {

        filtering.addCountryFilter(country);
        CompanyObsArrayList.clear();
        CompanyObsArrayList.addAll(filtering.filteredList());
    }

    /**
     * calls the smefilter method in filters and adds the filter then sets the
     * reurned list to the observablearraylist
     *
     * @param SME
     */
    public void setSMEFilter(int SME)
    {
        List<Company> filteredList;
        filteredList = filtering.addSMEFilter(SME);
        CompanyObsArrayList.clear();
        CompanyObsArrayList.addAll(filteredList);
    }

    /**
     * Gets the countries from all the companies registered
     *
     * @return Returns and observable list containing all the countries in which
     * a registered company is located
     * @throws IOException
     */
    public ObservableList<String> getTableCountries() throws IOException
    {
        // Creates the return list
        ObservableList<String> list = FXCollections.observableArrayList();

        // Gets the countries from all the companies
        ObservableList<Company> allCompanies = getAllCompanies();
        for (int i = 0; i < allCompanies.size(); i++)
        {
            list.add(allCompanies.get(i).getCountry());
        }

        CountryNameList cnl = new CountryNameList(list);

        list = cnl.allCountriesCorrect();

        return list;
    }

    /**
     * Updates the comboboxCountries' list
     *
     * @param table A List of all the companies' cuntries
     * @param regions A List with all the countries from the selected regions
     * @return Returns a List containing only the countries where a registered
     * company is from inside the selected regions
     */
    public ObservableList<String> updateList(ObservableList<String> table,
            ObservableList<String> regions)
    {
        ObservableList<String> newList = FXCollections.observableArrayList();

        for (int i = 0; i < table.size(); i++)
        {
            if (regions.isEmpty())
            {
                return table;
            }

            if (regions.contains(table.get(i)))
            {
                newList.add(table.get(i));
            }
        }

        return newList;
    }

}
