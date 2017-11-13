package inn2power.bll;

import be.Company;
import inn2power.dal.DataAccess;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Hussain
 */
public class Filtering
{

    DataAccess data;
    List<ICompanyFilter> filters = new ArrayList();
    
    public Filtering()
    {
        try
        {
            data = new DataAccess();
            
        }
        catch (IOException e)
        {
        }
    }
    /**
     * 
     * @returns the list, which contains the selected country. 
     */
    public List<Company> filteredList()
    {
        
        List<Company> allCompanies = data.getAllCompanies();

        List<Company> filteredList;
        filteredList = companyFiltering(allCompanies, filters);

        return filteredList;
    }
    
    public List<Company> addFilters(boolean national, boolean bordering, boolean continent, boolean semiInternational, boolean international) throws IOException
    {
                
        
        if(national == true){
             filters.add(new CountryFiltering("Brazil"));
        }
        
        // filters.add(new CompanySMEFilter(true));
        List<Company> filteredList = filteredList();

        return filteredList;
    }
    
    public List<Company> companyFiltering(List<Company> allCompanies, List<ICompanyFilter> filters)
    {
        // ArrayList which countries are added to, according to if there is a
        // match. (Country match)
        List<Company> filteredList = new ArrayList();
        // Going through all companies
        for (Company company : allCompanies)
        {
            boolean isThereAMatch = true;

            for (ICompanyFilter filter : filters)
            {
                if (!filter.meetCriteria(company))
                {
                    isThereAMatch = false;
                    break;
                }
            }
            if (isThereAMatch)
            {
                filteredList.add(company);
            }
        }
        return filteredList;
    }

    // Adds country to filter.
    public void addCountryFilter(String country)
    {
        filters.add(new CountryFiltering(country));
    }
    
    public void clearFilter()
    {
        filters.clear();
    }

}
