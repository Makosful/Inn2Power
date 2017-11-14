package inn2power.bll;

import be.Company;

/**
 *
 * @author B
 */
public class CompanySMEFilter implements ICompanyFilter
{

    private final boolean showSME;

    public CompanySMEFilter(boolean showSME)
    {
        this.showSME = showSME;
    }

    @Override
    public boolean meetCriteria(Company company)
    {

        // checks if its a big or small company depends on parameter isSME
        if (showSME == true)
        {
            boolean isItSme = false;
            if (company.getIsSME() == 1)
            {
                isItSme = true;
            }
            return isItSme;
        } else
        {
            boolean isItSme = false;
            if (company.getIsSME() == 0)
            {
                isItSme = true;
            }
            return isItSme;
        }

    }

    @Override
    public boolean equals(Object object2)
    {
        return object2 instanceof CompanySMEFilter;
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 53 * hash + (this.showSME ? 1 : 0);
        return hash;
    }

}
