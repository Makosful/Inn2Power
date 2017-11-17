package inn2power.bll;

import be.Company;
import java.util.List;

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
        return regions.contains(company.getCountry());
    }
}
