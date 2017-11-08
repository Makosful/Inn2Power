package inn2power.bll;

import be.Company;

/**
 * Main filter for the search
 *
 * @author Axl
 */
public interface ICompanyFilter
{
    public boolean meetCriteria(Company company);
}
