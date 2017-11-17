package inn2power.bll;

import be.Company;
import inn2power.dal.CSVReaderRegion;
import inn2power.dal.DataAccess;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
     * @return @returns the list, which contains the selected country.
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
        RegionFiltering regionFilter = new RegionFiltering(filters);

        Iterator<ICompanyFilter> i = filters.iterator();
        while (i.hasNext())
        {
            ICompanyFilter filter = i.next();

            if (filter.equals(regionFilter))
            {
                i.remove();
            }
        }

        CSVReaderRegion csv;
        ObservableList<String> regions = FXCollections.observableArrayList();

        if (checkBoxFilters[0] == true)
        {
            csv = new CSVReaderRegion("AfricaCountryList");
            ObservableList<String> countries = csv.getAllCountries();
            addCountry(countries, regions);
        }
        if (checkBoxFilters[1] == true)
        {
            // ASIA
            csv = new CSVReaderRegion("AsiaCountryList");
            ObservableList<String> countries = csv.getAllCountries();
            addCountry(countries, regions);
        }
        if (checkBoxFilters[2] == true)
        {
            // EUROPE
            csv = new CSVReaderRegion("EuropeCountryList");
            ObservableList<String> countries = csv.getAllCountries();
            addCountry(countries, regions);
        }
        if (checkBoxFilters[3] == true)
        {
            // NORTH AMERICA
            csv = new CSVReaderRegion("NAmericaCountryList");
            ObservableList<String> countries = csv.getAllCountries();
            addCountry(countries, regions);
        }
        if (checkBoxFilters[4] == true)
        {
            // OCEANIA
            csv = new CSVReaderRegion("OceaniaCountryList");
            ObservableList<String> countries = csv.getAllCountries();
            addCountry(countries, regions);
        }
        if (checkBoxFilters[5] == true)
        {
            // SOUTH AMERICA
            csv = new CSVReaderRegion("SAmericaCountryList");
            ObservableList<String> countries = csv.getAllCountries();
            addCountry(countries, regions);
        }

        filters.add(new RegionFiltering(regions));

        List<Company> filteredList = filteredList();

        return filteredList;
    }

    private void addCountry(ObservableList<String> countries,
                            ObservableList<String> regions)
    {
        countries.forEach((country) ->
        {
            regions.add(country);
        });
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
        CountryFiltering cm = new CountryFiltering(country);

        Iterator<ICompanyFilter> i = filters.iterator();
        while (i.hasNext())
        {

            ICompanyFilter filter = i.next();
            if (filter.equals(cm))
            {
                i.remove();
            }
        }

        filters.add(cm);
    }

    /**
     * sets the required small buissness filter
     *
     * @param SME
     *
     * @return list of companies
     */
    public List<Company> addSMEFilter(int SME)
    {
        CompanySMEFilter sme = new CompanySMEFilter(true);

        Iterator<ICompanyFilter> i = filters.iterator();
        while (i.hasNext())
        {
            ICompanyFilter filter = i.next();
            if (filter.equals(sme))
            {
                i.remove();
            }
        }

        if (SME == 1)
        {
            filters.add(sme);
        }
        else if (SME == 0)
        {
            filters.add(new CompanySMEFilter(false));
        }
        else if (SME == -1)
        {
            filters.add(sme);
        }

        List<Company> filteredList = filteredList();

        return filteredList;
    }
}
