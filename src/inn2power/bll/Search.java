package inn2power.bll;

import be.Company;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author B
 */
public class Search
{

    /**
     * Selecte the companies that contains the searched text and returns them as
     * a list -- IS NOT USED ANYMORE!
     *
     * @param companies
     * @param searchText
     * @return companies as an observablelist
     */
    public List<Company> getSearchResult(List<Company> companies, String searchText)
    {
        List<Company> searchResult = new ArrayList<>();

        companies.forEach((company) ->
        {
            if ((company.getName().toLowerCase()).contains(searchText.toLowerCase()) || (company.getAddress().toLowerCase().contains(searchText.toLowerCase())))
            {
                searchResult.add(company);
            }
        });

        return searchResult;
    }

}
