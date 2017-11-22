package inn2power.dal;

import be.*;
import bll.RelationalLogic;
import dal.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * This class will be used to access all data from the CSV files through the
 * Inn2Power_Backend library
 *
 * @author Axl
 */
public class DataAccess
{

    CompanyDAO cDAO;
    OurCompanyDAO ocDAO;
    RelationDAO rDAO;
    RelationalLogic logic;

    public DataAccess() throws IOException
    {
        ocDAO = new OurCompanyDAO();
        cDAO = new CompanyDAO();
        rDAO = new RelationDAO();
    }

    /**
     * Gets a list of all companies
     *
     * @return
     *
     * @throws bll.Inn2PowerException
     */
    public List<Company> getAllCompanies() throws bll.Inn2PowerException
    {
        try
        {
            return ocDAO.getAllCompanies();
        }
        catch (SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    /**
     * Gets a specific company by their assigned ID
     *
     * @param id The ID of the Company
     *
     * @return The Company with a matching ID. Null if the Company does not
     *         exist
     *
     * @throws bll.Inn2PowerException
     */
    public Company getCompanyById(int id) throws bll.Inn2PowerException
    {
        return cDAO.getCompanyById(id);
    }

    /**
     * Gets an array of String containing all the business roles
     *
     * @return All the business roles listed in an array
     */
    public String[] getCompanyBuisnessRoles()
    {
        return cDAO.getCompanyBusinessRoles();
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

    public List<Relation> getRelationNetwork(Company source, int depth)
    {
        return logic.getRelationalNetwork(source, depth);
    }

}
