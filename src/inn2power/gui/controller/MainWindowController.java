package inn2power.gui.controller;

//<editor-fold defaultstate="collapsed" desc="Imports">
import be.Company;
import inn2power.bll.BllManager;
import inn2power.gui.model.WindowModel;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
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
import javafx.scene.control.Control;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
//</editor-fold>

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
    private TableColumn<Company, String> tcCountry;
    @FXML
    private TableColumn<Company, String> tcWebsite;
    @FXML
    private TableColumn<Company, String> tcCoorcinate;
    @FXML
    private TableColumn<Company, String> tcIsSME;
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
    private Label lblStartId;
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
    private Label lblStartCoords;
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
    private WindowModel wm;
    private String sourceWebsite;
    private String targetWebsite;
    
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
        wm = new WindowModel();
                
        checkBoxes();

        tableView.setRowFactory(tv ->
        {
            TableRow<Company> row = new TableRow<>();
            row.setOnMouseClicked(event ->
            {
                if (event.getClickCount() == 2 && (!row.isEmpty()))
                {
                    openRowInWindow(row);
                }
            });
            return row;
        });


        try
        {
            
            countries = wm.countryNameList();
            
            tcName.setCellValueFactory(new PropertyValueFactory<>("name"));
            tcAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
            tcId.setCellValueFactory(new PropertyValueFactory<>("Id"));
            tcWebsite.setCellValueFactory(new PropertyValueFactory<>("Website"));
            
            
            tableView.setItems(wm.getAllCompanies());
            tableView.getSortOrder().add(tcName);
            tableView.getSortOrder().add(tcAddress);
            tableView.getSortOrder().add(tcId);
            tableView.getSortOrder().add(tcWebsite);

            splitPane.setResizableWithParent(apLeft, false);
            autoTextChange();
        }
        catch (IOException ex)
        {
            Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
        comboBoxCountries();
    }
    /**
     * Adds the countries to the combobox.
     */
    public void comboBoxCountries()
    {
        comboBoxCountries.setItems(countries.sorted());
    }
    
     /**
     * Listener for the checkboxes, 
     * sends the checkbox values as parameters to the filter.
     */
    public void checkBoxes()
    {
        boolean[] CheckBoxes = new boolean[5];
        CheckBox[] boxes =
        {
            regionNational, 
            regionBordering, 
            regionContinent, 
            regionSemiInternational, 
            regionInternational
        };
        for (int i = 0; i < boxes.length; i++)
        {
            boxes[i].selectedProperty().addListener(new ChangeListener<Boolean>()
            {
                public void changed(ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val)
                {
                    for (int q = 0; q < boxes.length; q++)
                    {
                        CheckBoxes[q] = boxes[q].selectedProperty().getValue();
                    }

                    try {
                        wm.filterBox(CheckBoxes[0],
                                     CheckBoxes[1],
                                     CheckBoxes[2],
                                     CheckBoxes[3],
                                     CheckBoxes[4]);
                    } catch (IOException ex) {
                        Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
        }
    }
    
    
     /* 
     * Opens the company, from the clicked row in tableview, in a new window.
     */
    private void openRowInWindow(TableRow<Company> row)
    {
        try
        {
            Company rowData = row.getItem();
            Stage primeStage;
            primeStage = (Stage) tableView.getScene().getWindow();
            FXMLLoader fxLoader;
            fxLoader = new FXMLLoader(this.getClass().getResource("/inn2power/gui/view/CompanyWindow.fxml"));
            Parent root = fxLoader.load();

            CompanyWindowController controller;
            controller = fxLoader.getController();
            controller.loadCompany(rowData);
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            scene.getStylesheets().add("inn2power/css/cssstylesheet.css");
            stage.initOwner(primeStage);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.show();
        }
        catch (IOException ex)
        {
            System.out.println("Could not read StyleSheet");
        }
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
                wm.Search(txtSearch.getText());
            }
        });
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
            linkStartURL.setText("Visit Website");
            //lblStartCoords.setText(comp.getLat() + "" + comp.getLng());
            //lblStartSME.setText(comp.getIsSME() + "");
            sourceWebsite = comp.getWebsite();            
        }
        catch (NullPointerException e)
        {
            System.out.println("No company selected.");
        }
    }

    /**
     * Sets the currently selected company as the target company
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
            linkTargetURL.setText("Visit Website");
            //lblTargetCoords.setText(comp.getLat() + "" + comp.getLng());
            //lblTargetSME.setText(comp.getIsSME() + "");
            targetWebsite = comp.getWebsite();
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

    @FXML
    private void visitWebsite(MouseEvent event) {
        System.out.println(sourceWebsite);
        try {
            try {
                if(((Control)event.getSource()).getId().equals("linkStartURL"))
                {
                    Desktop.getDesktop().browse(new URI(sourceWebsite));
                }
                else
                {
                    Desktop.getDesktop().browse(new URI(targetWebsite));
                }
            } catch (IOException ex) {
                Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (URISyntaxException ex) {
            Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handleCountrySearch(ActionEvent event) throws IOException
    {
        String selectedItem = comboBoxCountries.getSelectionModel().getSelectedItem();
        wm.addCountryFilter(selectedItem);

        checkBoxes();
    }
}
