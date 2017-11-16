package inn2power.bll;

import be.Company;

/**
 *
 * @author B
 */
public class CompanySMEFilter implements ICompanyFilter
{

    private final int showSME;

    public CompanySMEFilter(int showSME)
    {
        this.showSME = showSME;
    }

    @Override
    public boolean meetCriteria(Company company)
    {

        // checks if its a big or small company depends on parameter isSME
        if (showSME == 1)
        {
            boolean isItSme = false;
            if (company.getIsSME() == 1)
            {
                isItSme = true;
            }
            return isItSme;
        } else if(showSME == 0)
        {
            boolean isItSme = false;
            if (company.getIsSME() == 0)
            {
                isItSme = true;
            }
            return isItSme;
        }else
        {
            boolean isItSme = false;
            if (company.getIsSME() == -1)
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



}
