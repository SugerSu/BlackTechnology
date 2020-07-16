package ES;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

interface INEURegister {
    boolean register(int NUID);
}

interface INEUUpdate{
    boolean update(int NUID);
}
interface INEUDelete{
    boolean delete(int NUID);
}


interface INEUwebService extends INEURegister, INEUUpdate,INEUDelete{
    boolean Verify(int NUID,int PW);
}

class NEUwebService implements INEUwebService{

    @Override
    public boolean delete(int NUID) {      
        return false;
    }

    @Override
    public boolean update(int NUID) {     
        return false;
    }

    @Override
    public boolean register(int NUID) {
        return false;
    }

    @Override
    public boolean Verify(int NUID, int PW) {
        return false;
    }

}
class Student{
    public static int WrongID=0;
    public static int RightID=1;
    public static int WrongPassWord;
    public static int RightPassword;
}
@RunWith(MockitoJUnitRunner.class)
public class MokitoTestApi {
    @Mock
    INEUwebService webService;

    @Before
    public void setUp() {

        webService= mock(NEUwebService.class);
    }

    @After
    public void tearDown() {
        webService = null;
           
    }
 
    @Test
    public void testNEUWevService() {

        when(webService.Verify(Student.RightID,Student.RightPassword)).thenReturn(true);
        assertTrue(webService.register(Student.RightID));
        assertTrue(webService.update(Student.RightID));
        assertTrue(webService.delete(Student.RightID));

        when(webService.Verify(Student.RightID,Student.WrongPassWord)).thenReturn(false);
        assertFalse(webService.register(Student.RightID));
        assertFalse(webService.update(Student.RightID));
        assertFalse(webService.delete(Student.RightID));

        when(webService.Verify(Student.WrongID,Student.RightPassword)).thenReturn(false);
        assertFalse(webService.register(Student.WrongID));
        assertFalse(webService.update(Student.WrongID));
        assertFalse(webService.delete(Student.WrongID));

        
        

        
    }
     
}