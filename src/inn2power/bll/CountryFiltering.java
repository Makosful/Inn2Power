package inn2power.bll;

import be.Company;

/**
 *
 * @author Axl
 */
public class CountryFiltering implements ICompanyFilter
{

    private final String country;

    public CountryFiltering(String country)
    {
        this.country = country;
    }

    @Override
    public boolean meetCriteria(Company company)
    {
        return company.getCountry().equalsIgnoreCase(country);
    }

    @Override
    public boolean equals(Object object2)
    {
        return object2 instanceof CountryFiltering;
    }

}
