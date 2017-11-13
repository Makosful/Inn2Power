package inn2power.dal;

import be.*;
import bll.Inn2PowerException;
import dal.*;
import java.io.IOException;
import java.util.List;

/**
 * This class will be used to access all data from the CSV files through the
 * Inn2Power_Backend libirary
 *
 * @author Axl
 */
public class DataAccess
{

    CompanyDAO cDAO;
    RelationDAO rDAO;

    public DataAccess() throws IOException
    {
        cDAO = new CompanyDAO();
        rDAO = new RelationDAO();
    }

    public List<Company> getAllCompanies()
    {
        return cDAO.getAllCompanies();
    }

    /**
     * Get's a specific company by their assigned ID
     *
     * @param id The ID of the Company
     *
     * @return The Company with a matching ID. Null if the Company does not
     *         exist
     *
     * @throws Inn2PowerException
     */
    public Company getCompanyById(int id) throws Inn2PowerException
    {
        return cDAO.getCompanyById(id);
    }

    /**
     * Gets an array of String containing all the business roles
     *
     * @return All the business roles listed in an array
     */
    public String[] getCompanyBusinessRoles()
    {
        return cDAO.getCompanyBuisnessRoles();
    }

    /**
     * Gets an array of Strings with all the supply chain categories
     *
     * @return A String array with all the supply chain categories
     */
    public String[] getSupplyChainCategories()
    {
        return cDAO.getSupplyChainCategories();
    }

    /**
     * Gets a list of all relations currently registered
     *
     * @return A list of all relations
     */
    public List<Relation> getAllRelations()
    {
        return rDAO.getAllRelations();
    }
    
}
