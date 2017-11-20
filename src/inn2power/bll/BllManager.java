package inn2power.bll;

import be.Company;
import be.Relation;
import inn2power.dal.DataAccess;
import java.io.IOException;
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

    public BllManager() throws IOException
    {
        this.companyNames = FXCollections.observableArrayList();
        data = new DataAccess();
        companies = data.getAllCompanies();
    }

    /**
     * Gets all companies
     * @return list of all companies
     */
    public List<Company> getAllCompanies()
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
}
