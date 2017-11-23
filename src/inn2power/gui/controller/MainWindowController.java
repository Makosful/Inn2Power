package inn2power.gui.controller;

//<editor-fold defaultstate="collapsed" desc="Imports">
import be.Company;
import bll.Inn2PowerException;
import inn2power.bll.SteggerOverflowException;
import inn2power.gui.model.WindowModel;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
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

//<editor-fold defaultstate="collapsed" desc="FXML elements & Variables">
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
    private TableColumn<Company, String> tcSME;
    @FXML
    private Label lblStartId;
    @FXML
    private Label lblTargetId;
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
    private Label lblStartSME;
    @FXML
    private Label lblTargetSME;
    @FXML
    private TextField txtSearch;
    @FXML
    private AnchorPane apLeft;
    @FXML
    private CheckBox regionAfrica;
    @FXML
    private CheckBox regionAsia;
    @FXML
    private CheckBox regionEurope;
    @FXML
    private CheckBox regionNAmerica;
    @FXML
    private CheckBox regionOceania;
    @FXML
    private CheckBox regionSAmerica;
    @FXML
    private ToggleGroup SME;
    @FXML
    private RadioButton isSME;
    @FXML
    private RadioButton isNotSME;
    @FXML
    private RadioButton isBothSME;
    @FXML
    private RadioButton SMENotDeclared;
    @FXML
    private RadioButton noSMEFilter;
    @FXML
    private SplitPane splitPane;
    @FXML
    private ComboBox<String> comboBoxCountries;

    private Label lblStartCoords;
    private Label lblTargetCoords;
    private ObservableList<String> fileCountries;
    private ObservableList<String> combCountries;
    private WindowModel wm;
    private String sourceWebsite;
    private String targetWebsite;
//</editor-fold>

    /**
     * Constructor
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        try
        {
            wm = new WindowModel();
        }
        catch (Inn2PowerException ex)
        {
            Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }

        try
        {
            combCountries = wm.getTableCountries();
            setTableContent();
            setSortOrder();
        }
        catch (IOException ex)
        {
            System.out.println(ex.getMessage());
        }
        catch (Inn2PowerException ex)
        {
            Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }

        setCheckBoxes();
        smeFilterListener();
        setOpenCompanyWindow();
        setAutoTextChange();
        setColumns();
        setLockedSplitPane(true);

        // NOTE TO SELF: ADD LATER. IMPORTANT
        comboBoxCountries();

    }

    /**
     * Sets the locking on the split pane to the left pane
     *
     * @param lock Should it be locked
     */
    private void setLockedSplitPane(boolean lock)
    {
        if (lock)
        {
            SplitPane.setResizableWithParent(apLeft, false);
        }
        else
        {
            SplitPane.setResizableWithParent(apLeft, true);
        }
    }

    /**
     * Filles the table with all companies
     *
     * @throws IOException
     */
    private void setTableContent() throws IOException, Inn2PowerException
    {
        // Fills the table with the cmpanies
        tableView.setItems(wm.getAllCompanies());
    }

    /**
     * Sets the order of which the rows should be sorted by, by default
     */
    private void setSortOrder()
    {
        tableView.getSortOrder().add(tcName);
        tableView.getSortOrder().add(tcId);
    }

    /**
     * Defines the collumns used
     */
    private void setColumns()
    {
        tcName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tcAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
        tcId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        tcWebsite.setCellValueFactory(new PropertyValueFactory<>("Website"));
        tcCountry.setCellValueFactory(new PropertyValueFactory<>("Country"));
        // Potential add (Swap integers to YES / NO / UNKNOWN
        tcSME.setCellValueFactory(new PropertyValueFactory<>("SME"));
    }

    /**
     * Creates the functionality for double clicking on a company in the list to
     * get a window with detailed information about the company
     */
    private void setOpenCompanyWindow()
    {
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
    }

    /**
     * Adds the countries to the combobox.
     */
    public void comboBoxCountries()
    {
        comboBoxCountries.setItems(combCountries.sorted());
    }

    /**
     * Updates the comboBox based on the regional filters that's been sat
     *
     * @param boxes The checkboxes handling the regional filters
     */
    private void updateComboBox(boolean[] boxes) throws IOException
    {

        ObservableList checkList = FXCollections.observableArrayList();

        try
        {
            if (boxes[0])
            {
                ObservableList<String> listCountries
                                       = wm.getListCountries("AfricaCountryList");
                for (int i = 0; i < listCountries.size(); i++)
                {
                    checkList.add(listCountries.get(i));
                }
            }
            if (boxes[1])
            {
                ObservableList<String> listCountries
                                       = wm.getListCountries("AsiaCountryList");
                for (int i = 0; i < listCountries.size(); i++)
                {
                    checkList.add(listCountries.get(i));
                }
            }
            if (boxes[2])
            {
                ObservableList<String> listCountries
                                       = wm.getListCountries("EuropeCountryList");
                for (int i = 0; i < listCountries.size(); i++)
                {
                    checkList.add(listCountries.get(i));
                }
            }
            if (boxes[3])
            {
                ObservableList<String> listCountries
                                       = wm.getListCountries("NAmericaCountryList");
                for (int i = 0; i < listCountries.size(); i++)
                {
                    checkList.add(listCountries.get(i));
                }
            }
            if (boxes[4])
            {
                ObservableList<String> listCountries
                                       = wm.getListCountries("OceaniaCountryList");
                for (int i = 0; i < listCountries.size(); i++)
                {
                    checkList.add(listCountries.get(i));
                }
            }
            if (boxes[5])
            {
                ObservableList<String> listCountries
                                       = wm.getListCountries("SAmericaCountryList");
                for (int i = 0; i < listCountries.size(); i++)
                {
                    checkList.add(listCountries.get(i));
                }
            }
        }
        catch (IOException ex)
        {
            System.out.println(ex.getMessage());
        }

        ObservableList<String> updatedList = wm.updateList(combCountries,
                                                           checkList);
        comboBoxCountries.setItems(updatedList.sorted());
    }

    /**
     * Listener for the checkboxes, sends the checkbox values as parameters to
     * the filter.
     */
    public void setCheckBoxes()
    {
        boolean[] CheckBoxes = new boolean[6];
        CheckBox[] boxes =
        {
            regionAfrica,
            regionAsia,
            regionEurope,
            regionNAmerica,
            regionOceania,
            regionSAmerica
        };

        for (CheckBox box : boxes)
        {
            box.selectedProperty().addListener((ObservableValue<? extends Boolean> ov,
                                                Boolean old_val,
                                                Boolean new_val) ->
            {
                for (int q = 0; q < boxes.length; q++)
                {
                    CheckBoxes[q] = boxes[q].selectedProperty().getValue();
                }

                try
                {
                    wm.filterBox(CheckBoxes);
                    System.out.println("Succesfully added checkbox array list");
                    updateComboBox(CheckBoxes);
                }
                catch (IOException ex)
                {
                    System.out.println(ex.getMessage());
                }
                catch (Inn2PowerException ex)
                {
                    Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }
    }

    /**
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
    private void setAutoTextChange()
    {
        txtSearch.textProperty().addListener((ObservableValue<? extends String> observable, String oldText, String newText) ->
        {
            try
            {
                wm.Search(txtSearch.getText());
            }
            catch (Inn2PowerException ex)
            {
                Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
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
            sourceWebsite = comp.getWebsite();
        }
        catch (SteggerOverflowException e)
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
        lblStartCoords.setText("");
        lblStartSME.setText("");
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
        lblTargetCoords.setText("");
        lblTargetSME.setText("");
    }

    /**
     * Opens the link in the browser
     *
     * @param event
     */
    @FXML
    private void visitWebsite(MouseEvent event)
    {
        System.out.println(sourceWebsite);
        try
        {
            try
            {
                if (((Control) event.getSource()).getId().equals("linkStartURL"))
                {
                    Desktop.getDesktop().browse(new URI(sourceWebsite));
                }
                else
                {
                    Desktop.getDesktop().browse(new URI(targetWebsite));
                }
            }
            catch (IOException ex)
            {
                Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        catch (URISyntaxException ex)
        {
            Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * The search functionality
     *
     * @param event
     *
     * @throws IOException
     */
    @FXML
    private void handleCountrySearch(ActionEvent event) throws IOException, Inn2PowerException
    {
        String selectedItem = comboBoxCountries.getSelectionModel().getSelectedItem();
        wm.addCountryFilter(selectedItem);

        //setCheckBoxes();
    }

    /**
     * handles the click on sme radiobutton
     */
    private void smeFilterListener()
    {
        SME.selectedToggleProperty().addListener(new ChangeListener<Toggle>()
        {

            //add a listener on SME togglegroup.
            @Override
            public void changed(ObservableValue<? extends Toggle> ov, Toggle oldToggle, Toggle newToggle)
            {

                //Gets the RadioButton clicked by the user by typecasting the toggle.
                RadioButton newRb = (RadioButton) newToggle;

                switch (newRb.getId())
                {
                    case "isSME":
                    {
                        try
                        {
                            wm.setSMEFilter(1);
                        }
                        catch (Inn2PowerException ex)
                        {
                            Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    break;
                    case "isNotSME":
                    {
                        try
                        {
                            wm.setSMEFilter(0);
                        }
                        catch (Inn2PowerException ex)
                        {
                            Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    break;
                    case "SMENotDeclared":
                    {
                        try
                        {
                            wm.setSMEFilter(-1);
                        }
                        catch (Inn2PowerException ex)
                        {
                            Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    break;
                    case "isBothSME":
                    {
                        try
                        {
                            wm.setSMEFilter(3);
                        }
                        catch (Inn2PowerException ex)
                        {
                            Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    break;
                    case "noSMEFilter":
                    {
                        try
                        {
                            wm.setSMEFilter(2);
                        }
                        catch (Inn2PowerException ex)
                        {
                            Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    break;
                }

            }
        });
    }

    /**
     * Window, to register company.
     *
     * @param event
     *
     * @throws IOException
     */
    @FXML
    private void openCompanyRegister(ActionEvent event) throws IOException
    {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/inn2power/gui/view/CreateCompany.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.WINDOW_MODAL);

            CreateCompanyController controller;
            controller = fxmlLoader.getController();

            stage.setScene(new Scene(root));
            stage.showAndWait();
        }
        catch (Exception e)
        {
            
        }
    }

    @FXML
    private void removeCompany(ActionEvent event) throws SQLException
    {

        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/inn2power/gui/view/DeleteCompanyConfirmation.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.WINDOW_MODAL);

            DeleteCompanyConfirmationController controller;
            controller = fxmlLoader.getController();

            Company selectedCompany
                    = tableView.getSelectionModel().getSelectedItem();

            controller.setContextCompany(selectedCompany, wm);

            stage.setScene(new Scene(root));
            stage.showAndWait();

        }
        catch (IOException ex)
        {
            System.out.println(ex.getMessage());
        }

    }
}
