/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inn2powertool.bll;

import be.Company;
import dal.CompanyDAO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author B
 */
public class BllManager {
    private ObservableList<Company> companyNames = FXCollections.observableArrayList();
    private CompanyDAO cDAO;
    private List<Company> companies; 
    
    public BllManager() {
        try {
            cDAO = new CompanyDAO();
        } catch (IOException ex) {
            Logger.getLogger(BllManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        companies = cDAO.getAllCompanies();
    }
    
    public ObservableList getAllCompaniesExample() throws IOException
    {

        companies.forEach((company) ->
        {
            companyNames.add(company);
        });
        return companyNames;
    }
    
    
    public ObservableList getSearchResult(String searchText) throws IOException
    {
       
        companyNames.clear();
        companies.forEach((company) ->
        {
            if((company.getName()).contains(searchText))
            {
                companyNames.add(company);
               
            }
                       
            
        });
        return companyNames;
    }
}
