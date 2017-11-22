/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inn2power.gui.controller;

import bll.Inn2PowerException;
import inn2power.gui.model.CreateCompanyModel;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
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
    private Label lblError;
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
    
    private CreateCompanyModel ccModel;
    


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
                try
        {
            ccModel = new CreateCompanyModel();
        }
        catch (IOException ex)
        {
            Logger.getLogger(CreateCompanyController.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (Inn2PowerException ex)
        {
            Logger.getLogger(CreateCompanyController.class.getName()).log(Level.SEVERE, null, ex);
        }
                
        initializeComboBoxSME();
        try
        {
            initializeComboBoxCountry();
        }
        catch (IOException ex)
        {
            Logger.getLogger(CreateCompanyController.class.getName()).log(Level.SEVERE, null, ex);
        }
        

    }
    /**
     * Initializing the SME ComboBox, setting the two options.
     */
    private void initializeComboBoxSME()
    {
        comboBoxSME.setVisibleRowCount(2);
        comboBoxSME.setItems(FXCollections.observableArrayList("Yes", "No"));
    }

    /**
     * Initializing the ComboBox, Country.
     */
    private void initializeComboBoxCountry() throws IOException
    {
       ObservableList<String> allCountries = FXCollections.observableList(ccModel.allCountries());
       comboBoxCountry.setItems(allCountries.sorted());
    }

    /**
     * Creating the company.
     *
     * @param event
     *
     * @throws SQLException
     */
    @FXML
    private void createCompany(ActionEvent event) throws SQLException
    {

        if (registerCompanyEmpty() == false)
        {
            if (!isDouble(txtLat.getText()) || !isDouble(txtLng.getText()))
            {
                lblError.setText("Lng or Lat contains characters");
                return;
            }
            String country = comboBoxCountry.getSelectionModel().getSelectedItem();

            double lat = Double.parseDouble(txtLat.getText());
            double lng = Double.parseDouble(txtLng.getText());

            ccModel.createCompany(txtCompanyName.getText(), txtAddress.getText(), country, txtWebsite.getText(), txtSupplyChainCategory.getText(), txtBusinessRole.getText(),
                                  lat, lng, getSME());
            lblError.setStyle("-fx-text-fill: black");
            lblError.setText("Your company was created");
        }
    }

    /**
     * check if the txtfields/comboboxes are empty, returns boolean.
     *
     * @return
     */
    private boolean registerCompanyEmpty()
    {

        List<String> txtInput = new ArrayList();
        txtInput.add(txtCompanyName.getText());
        txtInput.add(txtAddress.getText());
        txtInput.add(txtWebsite.getText());
        txtInput.add(txtSupplyChainCategory.getText());
        txtInput.add(txtBusinessRole.getText());
        txtInput.add(txtLat.getText());
        txtInput.add(txtLng.getText());
        txtInput.add(comboBoxCountry.getSelectionModel().getSelectedItem());
        txtInput.add(comboBoxSME.getSelectionModel().getSelectedItem());

        return ccModel.registerCompanyEmpty(txtInput, lblError);
    }

    /**
     * Returns whether SME is 0 or 1, based on the answer yes, or no.
     *
     * @return
     */
    private int getSME()
    {
        String smeNumber = comboBoxSME.getSelectionModel().getSelectedItem();
        return ccModel.getSME(smeNumber);
    }

    /**
     * Checks if lat or lng contains characters.
     *
     * @param lat
     *
     * @return
     */
    private boolean isDouble(String lat)
    {
        return ccModel.isDouble(lat);
    }
    
           
}
