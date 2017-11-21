/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inn2power.gui.model;

import bll.Inn2PowerException;
import inn2power.bll.BllManager;
import java.io.IOException;
import java.sql.SQLException;

/**
 *
 * @author Hussain
 */
public class CreateCompanyModel
{
    BllManager bm;
    
    public CreateCompanyModel() throws IOException, Inn2PowerException
    {
        bm = new BllManager();
    }
    
    public void createCompany(String name, String address, String country, String website, String supplyChainCat, String businessRole, double lat, double lng, int sme) throws SQLException
    {
        bm.createCompany(name, address, country, website, supplyChainCat, businessRole, lat, lng, sme);
    }
}
