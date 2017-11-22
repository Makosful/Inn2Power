package inn2power.bll;

import be.Company;
import bll.Inn2PowerException;
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
    public List<Company> filteredList() throws Inn2PowerException
    {
        List<Company> allCompanies = data.getAllCompanies();

        List<Company> filteredList;
        filteredList = companyFiltering(allCompanies, filters);

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

    /**
     * Adds country to filter.
     *
     * @param country
     */
    public void addCountryFilter(String country)

    {
        CountryFiltering cm = new CountryFiltering(country);
        removeFilter(cm);
        filters.add(cm);
    }

    /**
     * sets the required small buissness filter
     *
     * @param SME
     *
     * @return list of companies
     */
    public List<Company> addSMEFilter(int SME) throws Inn2PowerException
    {

        CompanySMEFilter sme = new CompanySMEFilter(1);
        removeFilter(sme);

        if (SME == 1)
        {
            filters.add(new CompanySMEFilter(SME));
        }
        else if (SME == 0)
        {
            filters.add(new CompanySMEFilter(SME));
        }
        else if (SME == -1)
        {
            filters.add(new CompanySMEFilter(SME));
        }
        else if (SME == 3)
        {
            filters.add(new CompanySMEFilter(SME));
        }

        List<Company> filteredList = filteredList();

        return filteredList;
    }

    /**
     * removes the objects which are instances of regionFilter, then if the
     * given checkbox value is true, it adds the filter corresponding to the
     * checkbox
     *
     * @param checkBoxFilters - with booleans, if checked then true
     *
     * @return the filtered list
     *
     * @throws IOException
     */
    public List<Company> addFilters(boolean[] checkBoxFilters) throws IOException, Inn2PowerException
    {

        RegionFiltering regionFilter = new RegionFiltering(filters);
        removeFilter(regionFilter);

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

        if (regions.isEmpty())
        {

            CountryFiltering cm = new CountryFiltering("country");
            removeFilter(cm);
        }

        List<Company> filteredList = filteredList();

        return filteredList;
    }

    /**
     * Adds countries to the list regions
     *
     * @param countries
     * @param regions
     */
    private void addCountry(ObservableList<String> countries,
                            ObservableList<String> regions)
    {
        countries.forEach((country) ->
        {
            regions.add(country);
        });
    }

    /**
     * Remove specific filter, uses the equals method to remove all instances of
     * a filter object
     *
     * @param specificFilter
     */
    public void removeFilter(ICompanyFilter specificFilter)
    {
        Iterator<ICompanyFilter> i = filters.iterator();
        while (i.hasNext())
        {
            ICompanyFilter filter = i.next();

            if (filter.equals(specificFilter))
            {
                i.remove();
            }
        }

    }
}
