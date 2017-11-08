/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inn2powertool;

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
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private TableView<Company> tableCompany;
    @FXML
    private TableColumn<Company, String> columnName;
    @FXML
    private TableColumn<Company, String> columnAdress;
    @FXML
    private TableColumn<Company, String> columnId;
      
    @FXML
    private TextField textFieldNameInput;
    
    
    private ObservableList<Company> search = FXCollections.observableArrayList();
    
    
    BllManager ip = new BllManager();
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
            
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnAdress.setCellValueFactory(new PropertyValueFactory<>("Address"));
        columnId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        
        
    
        try {
            tableCompany.setItems(ip.getAllCompaniesExample());
            tableCompany.getSortOrder().add(columnName);
            tableCompany.getSortOrder().add(columnAdress);
            tableCompany.getSortOrder().add(columnId);
            
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }  
    
    
    @FXML
    private void btnSearchName(ActionEvent event) throws IOException 
    {
               
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnAdress.setCellValueFactory(new PropertyValueFactory<>("Address"));
        columnId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        
        tableCompany.setItems(ip.getSearchResult(textFieldNameInput.getText()));
        
        RelationDAO r = new RelationDAO();
      
        for(Relation re : r.getAllRelations()){
            if(re.getSource().getName().equals(tableCompany.getItems().get(0).getName())){
                System.out.println(re.getSource().getName());
            };
        }
    }
    
}
