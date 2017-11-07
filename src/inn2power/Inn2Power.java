package inn2power;

import be.*;
import dal.*;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author Axl
 */
public class Inn2Power
{

    /**
     * @param args the command line arguments
     *
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException
    {
        getAllCompaniesExample();
    }

    /**
     * An example on how to get the name of all companies individually
     *
     * @throws IOException
     */
    private static void getAllCompaniesExample() throws IOException
    {
        CompanyDAO cDAO = new CompanyDAO();

        List<Company> companies = cDAO.getAllCompanies();

        companies.forEach((company) ->
        {
            System.out.println(company.getName());
        });
    }

}
