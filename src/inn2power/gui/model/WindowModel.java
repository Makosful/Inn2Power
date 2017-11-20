package inn2power.gui.model;

import be.Company;
import be.Relation;
import inn2power.bll.BllManager;
import inn2power.bll.CountryNameList;
import inn2power.bll.Filtering;
import inn2power.bll.Search;
import inn2power.dal.DataAccess;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Axl
 */
public class WindowModel
{


    private BllManager bll;
    private Search search;
    private Filtering filtering;
    private ObservableList<Company> CompanyObsArrayList = FXCollections.observableArrayList();

    public WindowModel() throws SQLException
    {
        try
        {
          
            bll = new BllManager();
            search = new Search();
            filtering = new Filtering();
        } catch (IOException ex)
        {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Gets a list containing all the companies
     *
     * @return observablelist with companies
     * @throws IOException
     */
    public ObservableList<Company> getAllCompanies() throws IOException, SQLException
    {
        CompanyObsArrayList.addAll(bll.getAllCompanies());
        return CompanyObsArrayList;
    }

    /**
     * Gets the companies that contains the searchresult and updates the
     * tableview with the observablelist
     *
     * @param searchText
     */
    public void Search(String searchText) throws SQLException
    {
        CompanyObsArrayList.clear();
        List<Company> result = search.getSearchResult(bll.getAllCompanies(), searchText);
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

    /**
     * Gets the entire list of relations
     *
     * @return
     */
    public List<Relation> getAllRelations()
    {
        return bll.getAllRelations();
    }

    public List<Relation> getRelationNetwork(Company source, int depth)
    {
        return bll.getRelationNetwork(source, depth);
    }
    
    public void createNewCompany(String name, String country, String address, String website, String supplyChain, String businessRole, String lat, String lng, String isSME) throws SQLException
    {
        bll.setNewCompany(name, country, address, website, supplyChain, businessRole, lat, lng, isSME);
    }
}
