/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inn2power.gui.controller;

import inn2power.dal.CompanyDAO;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
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
    
    private CompanyDAO cDAO;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        initializeComboBoxSME();
        initializeComboBoxCountry();
        try
        {
            cDAO = new CompanyDAO();
        }
        catch (IOException ex)
        {
            System.out.println("Error. Cannot load CDAO");
        }
    }
    
    private void  initializeComboBoxSME()
    {
        comboBoxSME.setVisibleRowCount(2);
        comboBoxSME.setItems(FXCollections.observableArrayList("Yes", "No"));
    }
    
    private void initializeComboBoxCountry()
    {
        comboBoxCountry.setItems(FXCollections.observableArrayList("Denmark"));
    }
    
    @FXML
    private void createCompany(ActionEvent event) throws SQLException
    {
        if (checkIfFormIsFilled() == false)
        {
            String country = comboBoxCountry.getSelectionModel().getSelectedItem();
        
            double lat = Double.parseDouble(txtLat.getText());
            double lng = Double.parseDouble(txtLng.getText());

        cDAO.createCompany(txtCompanyName.getText(), txtAddress.getText(), country , txtWebsite.getText(), txtSupplyChainCategory.getText(), txtBusinessRole.getText(),
                                                                                                                                    lat, lng, getSME());
        }
    }
    
    /**
     * check if the txtfields/comboboxes are empty, returns boolean.
     * @return 
     */
    private boolean checkIfFormIsFilled()
    {
        Boolean valueEmpty = false;
        
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
        
        for (int i = 0;i<txtInput.size();i++)
        {
            if(txtInput.get(i).isEmpty())
            {
                valueEmpty = true;
                lblError.setText("Error: You need to fill the form out");
                break;
            }
        }
        return valueEmpty;
    }
    
    private int getSME()
    {
        int sme = 0;
            String smeNumber = comboBoxSME.getSelectionModel().getSelectedItem();
    
            if(smeNumber.equalsIgnoreCase("Yes"))
            {
                sme = 0;
            }
            if(smeNumber.equalsIgnoreCase("No"))
            {
                sme = 1;
            }
            return sme;
    }
           
}
