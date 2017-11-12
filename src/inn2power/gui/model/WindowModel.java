package inn2power.gui.model;

import inn2power.bll.BllManager;
import inn2power.dal.DataAccess;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Axl
 */
public class WindowModel
{

    private DataAccess data;
   
   
    public WindowModel()
    {
          try
        {
            data = new DataAccess();
        }
        catch (IOException ex)
        {
            Logger.getLogger(BllManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
