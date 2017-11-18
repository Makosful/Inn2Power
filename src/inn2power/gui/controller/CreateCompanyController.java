/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inn2power.gui.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Hussain
 */
public class CreateCompanyController implements Initializable
{

    @FXML
    private ComboBox<String> comboBoxCountry;
    @FXML
    private ComboBox<String> comboBoxSME;
    @FXML
    private TextField txtAddress;
    @FXML
    private TextField txtBusinessRole;
    @FXML
    private TextField txtCompanyName;
    @FXML
    private TextField txtLat;
    @FXML
    private TextField txtLng;
    @FXML
    private TextField txtSupplyChainCategory;
    @FXML
    private TextField txtWebsite;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        initializeComboBoxSME();
    }    
    
    private void  initializeComboBoxSME()
    {
        comboBoxSME.setVisibleRowCount(2);
        comboBoxSME.setItems(FXCollections.observableArrayList("Yes", "No"));
    }
}
