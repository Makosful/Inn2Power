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

    /*
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
     * @return @throws IOException
     */
    public ObservableList<String> countryNameList() throws IOException
    {
        CountryNameList cnl = new CountryNameList("EuropeCountryList");
        ObservableList<String> list = cnl.getCountries();
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

}
