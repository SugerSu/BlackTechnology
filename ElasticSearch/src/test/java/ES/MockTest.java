package ES;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;



interface  ICoffeeMachine{
    public  boolean  makeCoffee(int size);
}
interface  IContainer extends ICoffeeMachine{
    public boolean getPortion(int size);
    
}
 
class CoffeeMachine implements ICoffeeMachine {
    public CoffeeMachine(IContainer container, IContainer container2){        
    }

    @Override
    public boolean makeCoffee(int size) {
        // TODO Auto-generated method stub
        return false;

    }

   
    
}

@RunWith(MockitoJUnitRunner.class)
class CoffeeMachineTest {
     
    ICoffeeMachine coffeeMachine;
     
    @Mock
    IContainer coffeeContainer;
    @Mock
    IContainer waterContainer;
 
    @Before
    public void setUp() {
        coffeeMachine = new CoffeeMachine(coffeeContainer, waterContainer);
    }
 
    @After
    public void tearDown() {
        coffeeContainer = null;
        waterContainer = null;
        coffeeMachine = null;       
    }
 
    @Test
    public void testMakeCoffe() {
         
        when(coffeeContainer.getPortion(Portion.LARGE)).thenReturn(true);
        when(waterContainer.getPortion(Portion.LARGE)).thenReturn(true);
         
        assertTrue(coffeeMachine.makeCoffee(Portion.LARGE));
    }
     
    @Test
    public void testNotEnoughException() {
         
        when(coffeeContainer.getPortion(Portion.SMALL)).thenReturn(false);
        when(waterContainer.getPortion(Portion.SMALL)).thenReturn(true);
 
        assertFalse(coffeeMachine.makeCoffee(Portion.SMALL));
         
    }
 
}

class Portion{
    public static int LARGE=0;
    public static int MEDIUM=1;
    public static int SMALL=2;

}
public class MockTest {
    
}