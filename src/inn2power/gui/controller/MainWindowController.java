package inn2power.gui.controller;

import be.Company;
import inn2power.bll.BllManager;
import inn2power.dal.DataAccess;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;

import javafx.scene.Scene;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 *
 * @author B
 */
public class MainWindowController implements Initializable {

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

    BllManager bm = new BllManager();
    private CheckBox[] boxes = new CheckBox[5];
    

    public void initialize(URL url, ResourceBundle rb)
    {
        /**
        CheckBox[] boxes = {regionNational, regionBordering, regionContinent, regionSemiInternational, regionInternational};
        for(int i = 0; i < boxes.length; i++){
            boxes[i].selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> ov,
                Boolean old_val, Boolean new_val) {
                    if(new_val == true){
                        for(int q = 0;q < boxes.length; q++){
                            
                        }
                    }
                }
            });
        }
        
        **/
        
        tcName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        tcAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
        tcId.setCellValueFactory(new PropertyValueFactory<>("Id"));


        AutoTextChange();

        tcName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        tcAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
        tcId.setCellValueFactory(new PropertyValueFactory<>("Id"));


        try {
            tableView.setItems(bm.getAllCompaniesExample());
            System.out.println(bm.getAllCompaniesExample().size());
            tableView.getSortOrder().add(tcName);
            tableView.getSortOrder().add(tcAddress);
            tableView.getSortOrder().add(tcId);
        } catch (IOException ex) {
            Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void checkbox(){}



    private void AutoTextChange() {
        txtSearch.textProperty().addListener(new ChangeListener<String>()
        {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldText, String newText) {
                UpdatedSearch();
                System.out.println("The text changed from: " + oldText + " to: " + newText);
            }
        });
    }


    @FXML
    private void btnSearchName() throws IOException {
        tcName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tcAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
        tcId.setCellValueFactory(new PropertyValueFactory<>("Id"));

        UpdatedSearch();
    }

    private void UpdatedSearch() {
        tableView.setItems(bm.getSearchResult(txtSearch.getText()));
    }
}
