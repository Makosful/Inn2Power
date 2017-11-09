package inn2power.gui.controller;

import be.Company;
import inn2power.bll.BllManager;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
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

    BllManager ip = new BllManager();
    @FXML
    private CheckBox regionNational;
    @FXML
    private CheckBox regionBordering;
    @FXML
    private CheckBox regionContinent;
    @FXML
    private CheckBox regionSemiInternational;
    @FXML
    private CheckBox regionInternational;
    @FXML
    private Button txt;
    @FXML
    private TableColumn<?, ?> tcCountry;
    @FXML
    private TableColumn<?, ?> tcWebsite;
    @FXML
    private TableColumn<?, ?> tcCoorcinate;
    @FXML
    private TableColumn<?, ?> tcIsSME;
    @FXML
    private ComboBox<String> comboBoxCountries;
    
    ObservableList<String> countries;

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        tcName.setCellValueFactory(new PropertyValueFactory<>("Name:"));
        tcAddress.setCellValueFactory(new PropertyValueFactory<>("Address:"));
        tcId.setCellValueFactory(new PropertyValueFactory<>("ID:"));
                          
        try {
            countries = ip.countryNameList();
        } catch (IOException ex) {
            Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
        comboBoxCountries.setItems(countries.sorted());

        try
        {
            tableView.setItems(ip.getAllCompaniesExample());
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
        
        
        tableView.setItems(ip.getSearchResult(txtSearch.getText()));
        
        //tableView.setItems(ip.getSearchResult(txtSearch.getText()));
    }

}
