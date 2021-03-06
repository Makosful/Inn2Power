/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inn2power.dal;

import be.Company;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author pgn
 */
public class OurCompanyDAO
{

    private DataBaseConnector dbConnector;

    public OurCompanyDAO() throws IOException
    {
        dbConnector = new DataBaseConnector();
    }

    public Company createCompany(String name, String address, String country, String website, String supplyChainCat, String businessRole, double lat, double lng, int isSME) throws SQLServerException, SQLException
    {
        try (Connection con = dbConnector.getConnection())
        {
            String sql = "INSERT INTO Company VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";

            PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, name);
            statement.setString(2, country);
            statement.setString(3, address);
            statement.setString(4, website);
            statement.setString(5, supplyChainCat);
            statement.setString(6, businessRole);
            statement.setDouble(7, lat);
            statement.setDouble(8, lng);
            statement.setInt(9, isSME);

            if (statement.executeUpdate() == 1)
            {
                //Good
                ResultSet rs = statement.getGeneratedKeys();
                rs.next();
                int id = rs.getInt(1);
                Company c = new Company(id, name, country, address, website, supplyChainCat, businessRole, lat, lng, isSME);
                return c;
            }
            throw new RuntimeException("Can't create company");
        }
    }

    /**
     * Gets a list of companies with given name.
     *
     * @param name The name to search for. I sure hope no one would send
     *             malicious data this way...
     *
     * @return The List of companies that match our search.
     */
    public List<Company> getCompaniesInAnSqlInjectionInsecureWay(String name) throws SQLException
    {
        try (Connection con = dbConnector.getConnection())
        {
            Statement st = con.createStatement();
            String sql = "SELECT * FROM Company WHERE Name = '" + name + "';";
            st.execute(sql);
            ResultSet rs = st.getResultSet();
            List<Company> allCompanies = new ArrayList<>();
            while (rs.next())
            {
                allCompanies.add(getCompanyFromResultSetRow(rs));
            }
            return allCompanies;
        }
    }

    public List<Company> getAllCompanies() throws SQLException
    {
        try (Connection con = dbConnector.getConnection()) //I create a connection as a resource using my DatabaseConnector object:
        {
            String sql = "SELECT * FROM Company"; // I prepare my SQL

            Statement st = con.createStatement(); //I create a statement object
            ResultSet rs = st.executeQuery(sql); //I execute my SQL and receive a ResultSet

            List<Company> allCompanies = new ArrayList<>(); // I Prepare a list for holding my returned companies
            while (rs.next()) //While there are companies (rows) in the result set:
            {
                Company company = getCompanyFromResultSetRow(rs);
                allCompanies.add(company);
            }
            //I return all the found companies:
            return allCompanies;
        }
        //The connection to the database i automatically closed by the "try with resources"..
        //The connection to the database i automatically closed by the "try with resources"..
    }

    /**
     * Extracts a single company from the ResultSet at the current row
     *
     * @param rs The result set to work with
     *
     * @return The Company represented at the current row
     *
     * @throws SQLException
     */
    private Company getCompanyFromResultSetRow(ResultSet rs) throws SQLException
    {
        //I extract the data from the current row in the resultset:
        int id = rs.getInt("Id");
        String name = rs.getString("Name").trim();
        String country = rs.getString("Country").trim();
        String address = rs.getString("Address").trim();
        String website = rs.getString("Website").trim();
        String supply = rs.getString("SupplyChainCat").trim();
        String business = rs.getString("BusinessRole").trim();
        double lat = rs.getDouble("Lat");
        double lng = rs.getDouble("Lng");
        int isSME = rs.getInt("IsSME");
        //I create the company object and add it to my list of results:
        Company company = new Company(id, name, country, address, website, supply, business, lat, lng, isSME);
        return company;
    }

    public void removeCompany(Company company) throws SQLServerException, SQLException
    {
        try (Connection con = dbConnector.getConnection())
        {
            Statement statement = con.createStatement();
            statement.execute("DELETE FROM Company WHERE id =" + company.getId());
        }
    }
    
    
    /**
     * Sends query to db that selects the companies that contains the searchtext
     * @param searchText
     * @return List company
     * @throws SQLServerException
     * @throws SQLException 
     */
    public List<Company> getSearchResult(String searchText) throws SQLServerException, SQLException
    {
        if(!searchText.equals("")){
            try (Connection con = dbConnector.getConnection())
            {
                String sql;
                ResultSet rs;
                if(isNumeric(searchText))
                {
                    sql = "SELECT * FROM Company WHERE id = ?";
                    PreparedStatement preparedStatement = con.prepareStatement(sql);
                    preparedStatement.setInt(1, Integer.parseInt(searchText));
                    rs = preparedStatement.executeQuery();
                }
                else
                {
                    sql = "SELECT * FROM Company WHERE name LIKE ? OR address LIKE ?";
                    PreparedStatement preparedStatement = con.prepareStatement(sql);
                    preparedStatement.setString(1, "%"+searchText+"%");
                    preparedStatement.setString(2, "%"+searchText+"%");
                    rs = preparedStatement.executeQuery();
                }
               

                List<Company> allCompanies = new ArrayList<>(); 


                while (rs.next()) 
                {
                    Company company = getCompanyFromResultSetRow(rs);
                    allCompanies.add(company);
                }
                 
                    
                return allCompanies;
                

            }
        }else{
        
            return getAllCompanies();
        }
        
    }
    
    /**
     * Checks if a given string is numeric 
     * @param str
     * @return 
     */
    public static boolean isNumeric(String str)  
    {  
      try  
      {  
        double d = Double.parseDouble(str);  
      }  
      catch(NumberFormatException nfe)  
      {  
        return false;  
      }  
      return true;  
    }

}
