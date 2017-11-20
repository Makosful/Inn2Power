package inn2power.bll;

import be.Company;
import be.Relation;
import inn2power.bll.exception.inn2powerexception;
import inn2power.dal.DataAccess;
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
    

    public BllManager() throws IOException, inn2powerexception
    {
        this.companyNames = FXCollections.observableArrayList();
        data = new DataAccess();
        companies = data.getAllCompanies();
    }

    public List<Relation> getAllRelations()
    {
        return data.getAllRelations();
    }

    public List<Relation> getRelationNetwork(Company source, int depth)
    {
        return data.getRelationNetwork(source, depth);
    }
    
    public List<Company> getAllCompanies() throws inn2powerexception
    {
        return data.getAllCompanies();
    }
    
    public void setNewCompany(String name, String address, String country, String website, String supplyChainCat, String businessRole, String lat, String lng, String isSME) throws SQLException
    {
        
        data.createNewCompany(name, address, country, website, supplyChainCat, businessRole, Double.parseDouble(lat), Double.parseDouble(lng), Integer.parseInt(isSME));
        
    }
}
