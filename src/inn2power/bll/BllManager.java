package inn2power.bll;

import be.Company;
import inn2power.dal.DataAccess;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author B
 */
public class BllManager
{

    private ObservableList<Company> companyNames = FXCollections.observableArrayList();
    private DataAccess data;
    private List<Company> companies;
    Filtering filtering;

    public BllManager()
    {
        try
        {
            data = new DataAccess();
        }
        catch (IOException ex)
        {
            Logger.getLogger(BllManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        companies = data.getAllCompanies();

        filtering = new Filtering();
    }
    
    
    public ObservableList<Company> filteredList()
    {
        companyNames.clear();
        
        for (Company company : filtering.filteredList())
        {
            companyNames.add(company);
        }

        return companyNames;
    }

    
    
    public ObservableList<String> countryNameList() throws IOException
    {
        CountryNameList cnl = new CountryNameList();
        return cnl.allCountriesCorrect();
    }

    public void addCountryFilter(String country)
    {
        filtering.addCountryFilter(country);
    }
    
    

    /**
     * ends the checkbox values as parameters to the filter,
     * gets the filtered list of companies back which are added to the observable list 
     * @param national
     * @param bordering
     * @param continent
     * @param semiInternational
     * @param international
     * @return the ovservablelist with companies
     * @throws IOException 
     */
    public ObservableList<Company> filterBox(boolean national, boolean bordering, boolean continent, boolean semiInternational, boolean international) throws IOException
    {

        companyNames.clear();
        
        List<Company> filteredList;
        
        filteredList = filtering.addFilters(national, bordering, continent, semiInternational, international);

        for (Company company : filteredList)
        {
            companyNames.add(company);
        }

        return companyNames;
    }

}
