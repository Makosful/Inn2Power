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

    public void countrySearch() throws IOException
    {
        List<Company> allCompanies = new DataAccess().getAllCompanies();

        List<ICompanyFilter> filters = new ArrayList();
        filters.add(new CountryFiltering("Phillipines"));

        List<Company> filteredList;
        filteredList = companyFiltering(allCompanies, filters);

        for (Company company : filteredList)
        {
            System.out.println(company.getName());
        }
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

}
