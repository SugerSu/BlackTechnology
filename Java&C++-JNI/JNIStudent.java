class JNISt{

     String name;
     int id;
     int midterm;
     int project;
     int finalexam;
     public JNISt(){
        name=new String("");
        id=0;
        midterm=0;
        project=0;
        finalexam=0;
     }

}
public class JNIStudent {
    static{
        System.loadLibrary("clib4");
    }

    public native static JNISt[] getStudent();

    public static void main(String[] args) {
        //HM12-Q3B
        JNISt[] st = getStudent();
        for (JNISt record : st) {
            System.out.println();
            System.out.println("Name:" + record.name);
            System.out.println("ID:" + record.id);
            System.out.println("Midterm:" + record.midterm);
            System.out.println("Project:" + record.project);
            System.out.println("Final Exam:" + record.finalexam);


        }
        
    }
}