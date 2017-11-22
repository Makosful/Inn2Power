/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inn2power.gui.model;

import be.Company;
import bll.Inn2PowerException;
import inn2power.bll.BllManager;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Label;

/**
 *
 * @author Hussain
 */
public class CreateCompanyModel
{
    // FileReaders, regions.
    FileReader fileReaderAfrica;
    FileReader fileReaderEurope;
    FileReader fileReaderSA;
    FileReader fileReaderOceania;
    FileReader fileReaderNA;
    FileReader fileReaderAsia;
    
    
    BllManager bm;
    
    public CreateCompanyModel() throws IOException, Inn2PowerException
    {
        bm = new BllManager();
        
        fileReaderAfrica = new FileReader("C:\\Users\\Hussain\\Documents\\NetBeansProjects\\testGit\\Inn2Power\\Inn2Power\\regions\\AfricaCountryList.csv");
        fileReaderEurope = new FileReader("C:\\Users\\Hussain\\Documents\\NetBeansProjects\\testGit\\Inn2Power\\Inn2Power\\regions\\EuropeCountryList.csv");
        fileReaderSA = new FileReader("C:\\Users\\Hussain\\Documents\\NetBeansProjects\\testGit\\Inn2Power\\Inn2Power\\regions\\SAmericaCountryList.csv");
        fileReaderOceania = new FileReader("C:\\Users\\Hussain\\Documents\\NetBeansProjects\\testGit\\Inn2Power\\Inn2Power\\regions\\OceaniaCountryList.csv");
        fileReaderNA = new FileReader("C:\\Users\\Hussain\\Documents\\NetBeansProjects\\testGit\\Inn2Power\\Inn2Power\\regions\\NAmericaCountryList.csv");
        fileReaderAsia = new FileReader("C:\\Users\\Hussain\\Documents\\NetBeansProjects\\testGit\\Inn2Power\\Inn2Power\\regions\\AsiaCountryList.csv");
    }
    
    public void createCompany(String name, String address, String country, String website, String supplyChainCat, String businessRole, double lat, double lng, int sme) throws SQLException
    {
        bm.createCompany(name, address, country, website, supplyChainCat, businessRole, lat, lng, sme);
    }
    
    /**
     *  Checks what number to send, 1 or 0, based on users answer.
     * 
     * @param smeNumber
     * @return 
     */
    public int getSME(String smeNumber)
    {
        int sme = 0;

        if (smeNumber.equalsIgnoreCase("Yes"))
        {
            sme = 0;
        }
        if (smeNumber.equalsIgnoreCase("No"))
        {
            sme = 1;
        }
        return sme;
    }
    /**
     * Checks if the string contains characters or not. 
     * @param lat
     * @return 
     */
    public boolean isDouble(String lat) 
    {
        try
        {
            Double.parseDouble(lat);
            return true;
        }
        catch (NumberFormatException e)
        {
            return false;
        }
    
    }
    
    
     /**
     * Goes through every txtField / ComboBox, and checks if one of them are empty.
     * Returns a boolean.
     * @param txtInput
     * @param lblError
     * @return 
     */
    public boolean registerCompanyEmpty(List<String> txtInput, Label lblError)
    {
        Boolean valueEmpty = false;

        for (int i = 0; i < txtInput.size(); i++)
        {
            if (txtInput.get(i).isEmpty())
            {
                valueEmpty = true;
                lblError.setStyle("-fx-text-fill: red");
                lblError.setText("Error: You need to fill the form out");
                break;
            }
        }
        return valueEmpty;
    }
    /**
     * Returning all countries.
     * @return
     * @throws IOException 
     */
    public List<String> allCountries() throws IOException, IOException
    {
        List<String> allCountries = new ArrayList();
        allCountries.addAll(bm.getAllCountries(fileReaderSA));
        allCountries.addAll(bm.getAllCountries(fileReaderNA));
        allCountries.addAll(bm.getAllCountries(fileReaderOceania));
        allCountries.addAll(bm.getAllCountries(fileReaderEurope));
        allCountries.addAll(bm.getAllCountries(fileReaderAfrica));
        allCountries.addAll(bm.getAllCountries(fileReaderAsia));

        return allCountries;
    }
    
}
