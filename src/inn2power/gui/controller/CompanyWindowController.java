/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inn2power.gui.controller;

import be.Company;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author B
 */
public class CompanyWindowController implements Initializable {
    @FXML
    private Label lblName;
    @FXML
    private Label lblAddress;
    @FXML
    private Label lblCountry;
    @FXML
    private Label lblLink;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    public void loadCompany(Company company){
            lblName.setText(company.getName());
            lblAddress.setText(company.getAddress());
            lblCountry.setText(company.getCountry());
            lblLink.setText(company.getWebsite());
    }
    
    public void goToWebsite() throws URISyntaxException, IOException{
        Desktop.getDesktop().browse(new URI(this.lblLink.getText()));
    }   
}
