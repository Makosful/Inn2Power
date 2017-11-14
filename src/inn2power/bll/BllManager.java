package inn2power.bll;

import be.Company;
import inn2power.dal.DataAccess;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author B
 */
public class BllManager
{

    private ObservableList<Company> companyNames = FXCollections.observableArrayList();
    private DataAccess data;
    private final List<Company> companies;

    public BllManager()
    {
        try
        {
            data = new DataAccess();
        } catch (IOException ex)
        {
            Logger.getLogger(BllManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        companies = data.getAllCompanies();

    }

}
