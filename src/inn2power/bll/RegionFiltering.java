package inn2power.bll;

import be.Company;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Axl
 */
public class RegionFiltering implements ICompanyFilter
{

    private final List regions;

    public RegionFiltering(List regions)
    {
        this.regions = regions;
    }

    @Override
    public boolean meetCriteria(Company company)
    {
        if (regions.isEmpty())
        {
            return true;
        }

        return regions.contains(company.getCountry().trim());
    }

    @Override
    public boolean equals(Object obj)
    {
        return obj instanceof RegionFiltering;
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 43 * hash + Objects.hashCode(this.regions);
        return hash;
    }
}
