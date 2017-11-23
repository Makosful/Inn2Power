package inn2power.gui.controller;

import be.Company;
import inn2power.gui.model.WindowModel;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author Axl
 */
public class DeleteCompanyConfirmationController implements Initializable
{

    private Company company;
    private WindowModel wm;

    @FXML
    private Label lblCompany;

    void setContextCompany(Company company, WindowModel wm)
    {
        this.company = company;
        this.wm = wm;

        lblCompany.setText(this.company.getName().trim());
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
    }

    private void close()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @FXML
    private void deleteNo(ActionEvent event)
    {
        this.close();
    }

    @FXML
    private void deleteYes(ActionEvent event)
    {
        try
        {
            wm.removeCompany(this.company);
        }
        catch (SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
    }

}
