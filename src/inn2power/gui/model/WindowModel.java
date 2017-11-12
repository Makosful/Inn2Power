package inn2power.gui.model;

import inn2power.bll.Search;
import be.Company;
import inn2power.bll.BllManager;
import inn2power.bll.Filtering;
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
    private ObservableList<Company> companies = FXCollections.observableArrayList();
   
    public WindowModel()
    {
          try
        {
            data = new DataAccess();
            search = new Search();
            filtering = new Filtering();
        }
        catch (IOException ex)
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

        companies.addAll(data.getAllCompanies());
        
        return companies;
    }
    
    /**
     * Gets the companies that contains the searchresult and updates the tableview with the observablelist
     * @param searchText 
     */
    public void Search(String searchText)
    {
        companies.clear();
        List<Company> result = search.getSearchResult(data.getAllCompanies(), searchText);
        companies.addAll(result);
    }
    
    
     /**
     * Sends the checkbox values as parameters to the filter,
     * gets the filtered list of companies back which are added to the observable list 
     * @param national
     * @param bordering
     * @param continent
     * @param semiInternational
     * @param international
     * @throws IOException 
     */
    public void filterBox(boolean national, boolean bordering, boolean continent, boolean semiInternational, boolean international) throws IOException
    {

        
        List<Company> filteredList;
        filteredList = filtering.addFilters(national, bordering, continent, semiInternational, international);
        companies.clear();
        companies.addAll(filteredList);
    }
    
    
}
