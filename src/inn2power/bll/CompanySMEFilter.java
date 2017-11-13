package inn2power.bll;

import be.Company;
import inn2power.bll.ICompanyFilter;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author B
 */
public class CompanySMEFilter implements ICompanyFilter {
    private final boolean showSME;
    
    public CompanySMEFilter(boolean showSME){
        this.showSME = showSME;
    }
    
    @Override
    public boolean meetCriteria(Company company) {
        
        // checks if its a big or small company depends on parameter isSME
        if(showSME == true){
            boolean isItSme = false;
            if(company.getIsSME() == 1){
                isItSme = true;
            }
            return isItSme;
        }else {
            boolean isItSme = false;
            if(company.getIsSME() == 0){
                isItSme = true;
            }
            return isItSme;
        }
           
    }
    
    @Override   
    public boolean equals(Object object2) {
        return object2 instanceof CompanySMEFilter;
    }
    
}
