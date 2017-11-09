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
    }

    public ObservableList getAllCompaniesExample() throws IOException
    {

        companies.forEach((company) ->
        {
            companyNames.add(company);
        });
        return companyNames;
    }

    public ObservableList getSearchResult(String searchText)
    {
        companyNames.clear();
        companies.forEach((company) ->
        {
            if ((company.getName().toLowerCase()).contains(searchText.toLowerCase()))
            {
                companyNames.add(company);
            }
        });
        return companyNames;
    }
    
    public void countrySearch() throws IOException
    {
        Filtering filtering = new Filtering();
        
        filtering.countrySearch();
    }
    
    public ObservableList<String> countryNameList() throws IOException
    {
        CountryNameList cnl = new CountryNameList();
        return cnl.allCountriesCorrect();
    }
}
