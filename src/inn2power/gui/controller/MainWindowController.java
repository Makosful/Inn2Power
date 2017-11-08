package inn2power.gui.controller;

import be.Company;
import inn2power.bll.BllManager;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author B
 */
public class MainWindowController implements Initializable
{

    @FXML
    private TableView<Company> tableView;
    @FXML
    private TableColumn<Company, String> tcName;
    @FXML
    private TableColumn<Company, String> tcAddress;
    @FXML
    private TableColumn<Company, String> tcId;

    @FXML
    private TextField txtSearch;

    /*
     * @FXML private Button btnSearch;
     */
    private ObservableList<Company> search = FXCollections.observableArrayList();

    BllManager bm = new BllManager();

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        tcName.setCellValueFactory(new PropertyValueFactory<>("Name:"));
        tcAddress.setCellValueFactory(new PropertyValueFactory<>("Address:"));
        tcId.setCellValueFactory(new PropertyValueFactory<>("ID:"));

        try
        {
            tableView.setItems(bm.getAllCompaniesExample());
            tableView.getSortOrder().add(tcName);
            tableView.getSortOrder().add(tcAddress);
            tableView.getSortOrder().add(tcId);

        }
        catch (IOException ex)
        {
            Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void btnSearchName(ActionEvent event) throws IOException
    {
        tcName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tcAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
        tcId.setCellValueFactory(new PropertyValueFactory<>("Id"));

        tableView.setItems(bm.getSearchResult(txtSearch.getText()));

        //tableView.setItems(ip.getSearchResult(txtSearch.getText()));
    }
}
