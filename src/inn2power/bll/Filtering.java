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
    
    public List<Company> addFilters(boolean[] checkBoxFilters) throws IOException
    {
        for (int i = 0; i < checkBoxFilters.length; i++)
        {
//            if (checkBoxFilters[i] == true)
//            {
//                filters.add(new CountryFiltering("Brazil"));
//            }
            
            if(checkBoxFilters[0] == true)
            {
                // AFRICA
            }
            else if(checkBoxFilters[1] == true)
            {
               // ASIA
            }
            else if(checkBoxFilters[2] == true)
            {
                // EUROPE
            }
            else if(checkBoxFilters[3] == true)
            {
                // NORTH AMERICA
            }
            else if(checkBoxFilters[4] == true)
            {
                // OCEANIA
            }
            else if(checkBoxFilters[5] == true)
            {
                // SOUTH AMERICA
            }
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

}
