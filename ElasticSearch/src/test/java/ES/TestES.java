package ES;

import static org.junit.Assert.*;
import org.junit.*;


public class TestES {
    @BeforeClass
    public static void setUpBeforeClass() throws Exception{
        System.out.println("BeforeClass");
    }
    @Before
    public void setUp() throws Exception{
        System.out.println("Before");
    }
    @Test
    public void testFindMax(){
        System.out.println("TestES.testFindMax()");
        assertEquals(4,Calculation.findMax(new int[]{1,4,3,2}));
        assertEquals(-1,Calculation.findMax(new int[]{-12,-1,-3,-4,-2}));
    }
    @Test
    public void testCube() {
        System.out.println("TestES.testCube()");
        assertEquals(27, Calculation.cube(3));
    }
    @Test
    public void testReverseWord() {
        System.out.println("TestES.testReverseWord()");
        assertEquals("ym eman si nahk ",Calculation.reverseWord("my name is khan"));

    }
    @After  
    public void tearDown() throws Exception {  
        System.out.println("after");  
    }  
  
    @AfterClass  
    public static void tearDownAfterClass() throws Exception {  
        System.out.println("after class");  
    }  
}