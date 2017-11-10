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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author B
 */
public class MainWindowController implements Initializable
{

    //<editor-fold defaultstate="collapsed" desc="FXML Variables">
    @FXML
    private TableView<Company> tableView;
    @FXML
    private TableColumn<Company, String> tcName;
    @FXML
    private TableColumn<Company, String> tcAddress;
    @FXML
    private TableColumn<Company, String> tcId;
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
    private TableColumn<?, ?> tcCountry;
    @FXML
    private TableColumn<?, ?> tcWebsite;
    @FXML
    private TableColumn<?, ?> tcCoorcinate;
    @FXML
    private Label lblStartId;
    @FXML
    private TableColumn<?, ?> tcIsSME;
    @FXML
    private Label lblTargetId;
    @FXML
    private ComboBox<String> comboBoxCountries;
    @FXML
    private Label lblStartName;
    @FXML
    private Label lblTargetName;
    @FXML
    private Label lblStartCountry;
    @FXML
    private Label lblTargetCountry;
    @FXML
    private Label lblStartAdress;
    @FXML
    private Label lblTargetAdress;
    @FXML
    private Hyperlink linkStartURL;
    @FXML
    private Hyperlink linkTargetURL;
    @FXML
    private Label lblStartCoords;
    @FXML
    private Label lblTargetCoords;
    @FXML
    private Label lblStartSME;
    @FXML
    private Label lblTargetSME;
    @FXML
    private SplitPane splitPane;
    @FXML
    private TextField txtSearch;
    @FXML
    private AnchorPane apLeft;
    //</editor-fold>

    private ObservableList<String> countries;
    private BllManager bm;

    /**
     * Constructor
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        bm = new BllManager();

        
        boolean[] CheckBoxes = new boolean[5];
        CheckBox[] boxes = {regionNational, regionBordering, regionContinent, regionSemiInternational, regionInternational};
        for(int i = 0; i < boxes.length; i++){
            boxes[i].selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) {
                    
                    for(int q = 0;q < boxes.length; q++){
                      CheckBoxes[q] = boxes[q].selectedProperty().getValue();
                    }

                   // try {
                     //   updateTable(bm.filterBox(CheckBoxes[0], CheckBoxes[1], CheckBoxes[2], CheckBoxes[3], CheckBoxes[4]));
                   // } catch (IOException ex) {
                   //     Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
                   // }
                }
            });
        }
        
        
        
        tableView.setRowFactory(tv ->
        {
            TableRow<Company> row = new TableRow<>();
            row.setOnMouseClicked(event ->
            {
                if (event.getClickCount() == 2 && (!row.isEmpty()))
                {

                    try
                    {
                        Company rowData = row.getItem();
                        Stage primeStage = (Stage) tableView.getScene().getWindow();
                        FXMLLoader fxLoader = new FXMLLoader(this.getClass().getResource("/inn2power/gui/view/CompanyWindow.fxml"));
                        Parent root = fxLoader.load();

                        CompanyWindowController controller = fxLoader.getController();
                        controller.loadCompany(rowData);
                        Stage stage = new Stage();
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        //The Cascading Style Sheet (Initially to manipulate lblLink to a visual hyperlink)
                        scene.getStylesheets().add("inn2power/css/cssstylesheet.css");
                        stage.initOwner(primeStage);
                        stage.initModality(Modality.WINDOW_MODAL);
                        stage.show();
                    }
                    catch (IOException ex)
                    {
                        Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            return row;
        });

        try
        {
            updateTable(countries);
        }
        catch (Exception e)
        {
        }

        try
        {
            countries = bm.countryNameList();
            tableView.setItems(bm.getAllCompaniesExample());
            tableView.getSortOrder().add(tcName);
            tableView.getSortOrder().add(tcAddress);
            tableView.getSortOrder().add(tcId);

            splitPane.setResizableWithParent(apLeft, false);
            autoTextChange();
        }
        catch (IOException ex)
        {
            Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
        comboBoxCountries.setItems(countries.sorted());
    }

    /**
     * Adds an observer to the search bar, allowing the app to search the
     * database on the fly
     */
    private void autoTextChange()
    {
        txtSearch.textProperty().addListener(new ChangeListener<String>()
        {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldText, String newText)
            {
                updateTable(bm.getSearchResult(txtSearch.getText()));
            }
        });
    }

    /**
     * Updates the table with the search results
     *
     * @param companies
     */
    private void updateTable(ObservableList companies)
    {
        tcName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tcAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
        tcId.setCellValueFactory(new PropertyValueFactory<>("Id"));

        tableView.setItems(companies);

    }

    /**
     * Sets the currently selected company as the starting company
     *
     * @param event
     */
    @FXML
    private void setStartCompany(ActionEvent event)
    {
        try
        {
            Company comp = tableView.getSelectionModel().getSelectedItem();
            lblStartId.setText(comp.getId() + "");
            lblStartName.setText(comp.getName());
            lblStartCountry.setText(comp.getCountry());
            lblStartAdress.setText(comp.getAddress());
            //lblStartUrl.setText(comp.getWebsite()); // Feel free to delete.Used toset the now removed label
            lblStartCoords.setText(comp.getLat() + "" + comp.getLng());
            lblStartSME.setText(comp.getIsSME() + "");
        }
        catch (NullPointerException e)
        {
            System.out.println("No company selected.");
        }
    }

    /**
     * Sets the currently selected company as the targetcompany
     *
     * @param event
     */
    @FXML
    private void setTargetCompany(ActionEvent event)
    {
        try
        {
            Company comp = tableView.getSelectionModel().getSelectedItem();
            lblTargetId.setText(comp.getId() + "");
            lblTargetName.setText(comp.getName());
            lblTargetCountry.setText(comp.getCountry());
            lblTargetAdress.setText(comp.getAddress());
            //lblTargetUrl.setText(comp.getWebsite()); // Usedto set the label. Feelfree to remove
            lblTargetCoords.setText(comp.getLat() + "" + comp.getLng());
            lblTargetSME.setText(comp.getIsSME() + "");
        }
        catch (NullPointerException e)
        {
            System.out.println("No company selected.");
        }
    }

    /**
     * Clears the current starting company
     *
     * @param event
     */
    @FXML
    private void clearStartCompany(ActionEvent event)
    {
        lblStartId.setText("0");
        lblStartName.setText("No company selected");
        lblStartCountry.setText("");
        lblStartAdress.setText("");
        // Spot reserved for setting the Start URL
        lblStartCoords.setText("(0.0; 0.0)");
        lblStartSME.setText("Yes");
    }

    /**
     * Clears the current target company
     *
     * @param event
     */
    @FXML
    private void clearTargetCompany(ActionEvent event)
    {
        lblTargetId.setText("0");
        lblTargetName.setText("No company selected");
        lblTargetCountry.setText("");
        lblTargetAdress.setText("");
        // Spot reserved for setting the Target URL
        lblTargetCoords.setText("(0.0; 0.0)");
        lblTargetSME.setText("Yes");
    }
}
