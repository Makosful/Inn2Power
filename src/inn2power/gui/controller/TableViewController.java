/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inn2power.gui.controller;

import be.Company;
import be.Relation;
import dal.RelationDAO;

import inn2powertool.bll.BllManager;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author B
 */
public class TableViewController implements Initializable {
    
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
    @FXML
    private Button btnSearch;
    */

    private ObservableList<Company> search = FXCollections.observableArrayList();
    
    BllManager ip = new BllManager();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
            
        tcName.setCellValueFactory(new PropertyValueFactory<>("Name:"));
        tcAddress.setCellValueFactory(new PropertyValueFactory<>("Address:"));
        tcId.setCellValueFactory(new PropertyValueFactory<>("ID:"));
        
        try {
            tableView.setItems(ip.getAllCompaniesExample());
            tableView.getSortOrder().add(tcName);
            tableView.getSortOrder().add(tcAddress);
            tableView.getSortOrder().add(tcId);
            
        } catch (IOException ex) {
            Logger.getLogger(TableViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }  
    
    @FXML
    private void btnSearchName(ActionEvent event) throws IOException 
    {
        tcName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tcAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
        tcId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        
        tableView.setItems(ip.getSearchResult(txtSearch.getText()));
        
        RelationDAO r = new RelationDAO();
      
        for(Relation re : r.getAllRelations()){
            if(re.getSource().getName().equals(tableView.getItems().get(0).getName())){
                System.out.println(re.getSource().getName());
            };
        }
    }
    
}
