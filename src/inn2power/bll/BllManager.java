package inn2power.bll;

import be.Company;
import be.Relation;
import bll.Inn2PowerException;
import inn2power.dal.DataAccess;
import inn2power.dal.OurCompanyDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author B
 */
public class BllManager
{

    private final ObservableList<Company> companyNames;
    private final DataAccess data;
    private final List<Company> companies;
    private OurCompanyDAO ourCompanyDAO;

    public BllManager() throws IOException, Inn2PowerException
    {
        this.companyNames = FXCollections.observableArrayList();
        data = new DataAccess();
        companies = data.getAllCompanies();
        ourCompanyDAO = new OurCompanyDAO();
    }

    /**
     * Gets all companies
     *
     * @return list of all companies
     *
     * @throws bll.Inn2PowerException
     */
    public List<Company> getAllCompanies() throws Inn2PowerException
    {
        return data.getAllCompanies();
    }

    public List<Relation> getAllRelations()
    {
        return data.getAllRelations();
    }

    public List<Relation> getRelationNetwork(Company source, int depth)
    {
        return data.getRelationNetwork(source, depth);
    }

    public void createCompany(String name, String address, String country, 
                              String website, String supplyChainCat, 
                              String businessRole, double lat, double lng,
                              int sme) throws SQLException
    {
        ourCompanyDAO.createCompany(name, address, country, website, 
                                    supplyChainCat, businessRole, lat, lng, sme);
    }
}
