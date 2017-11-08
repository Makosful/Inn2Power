package inn2power.gui.controller;

import be.Company;
import inn2power.bll.BllManager;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

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
    private TableColumn<?, ?> tcCountry;
    @FXML
    private TableColumn<?, ?> tcWebsite;
    @FXML
    private TableColumn<?, ?> tcCoorcinate;
    @FXML
    private TableColumn<?, ?> tcIsSME;
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
    private SplitPane splitPane;
    @FXML
    private AnchorPane apLeft;

    BllManager bm = new BllManager();

   
    

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {

        boolean[] CheckBoxes = new boolean[5];
        CheckBox[] boxes = {regionNational, regionBordering, regionContinent, regionSemiInternational, regionInternational};
        for(int i = 0; i < boxes.length; i++){
            boxes[i].selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) {
                    
                    for(int q = 0;q < boxes.length; q++){
                      CheckBoxes[q] = boxes[q].selectedProperty().getValue();
                    }

                    try {
                        updateTable(bm.filterBox(CheckBoxes[0], CheckBoxes[1], CheckBoxes[2], CheckBoxes[3], CheckBoxes[4]));
                    } catch (IOException ex) {
                        Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    
                }
            });
        }
        

        try {
            updateTable(bm.getAllCompaniesExample());
        } catch (IOException ex) {
            Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }

        tableView.getSortOrder().add(tcName);
        tableView.getSortOrder().add(tcAddress);
        tableView.getSortOrder().add(tcId);

        // Locks the splitPane's devider to the left anchorPane, where the
        // search functionality is
        splitPane.setResizableWithParent(apLeft, Boolean.FALSE);

        AutoTextChange();
     
    }

 
    private void AutoTextChange() {
        txtSearch.textProperty().addListener(new ChangeListener<String>()
        {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                String oldText, String newText) {
                try {
                    btnSearchName();
                } catch (IOException ex) {
                    Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
                }
                //System.out.println("The text changed from: " + oldText + " to: " + newText);
            }
        });
    }

    @FXML
    private void btnSearchName() throws IOException
    {



        updateTable(bm.getSearchResult(txtSearch.getText()));
    }

    
    private void updateTable(ObservableList companies){
    
        tcName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tcAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
        tcId.setCellValueFactory(new PropertyValueFactory<>("Id"));
    
        tableView.setItems(companies);

    }


}
