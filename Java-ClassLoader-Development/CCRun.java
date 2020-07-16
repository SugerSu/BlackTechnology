import java.io.DataInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

class CCLoader extends ClassLoader {

    /**
     * This constructor is used to set the parent ClassLoader
     */
    public CCLoader(ClassLoader parent) {
        super(parent);
    }

    /**
     * Loads the class from the file system. The class file should be located in the
     * file system. The name should be relative to get the file location
     *
     * @param name 
     * Fully Classified name of the class, for example,
     *             com.journaldev.Foo
     */
    private Class getClass(String name) throws ClassNotFoundException {
        String file = name.replace('.', File.separatorChar) + ".class";
        byte[] b = null;
        try {
            // This loads the byte code data from the file
            b = loadClassFileData(file);
            // defineClass is inherited from the ClassLoader class
            // that converts byte array into a Class. defineClass is Final
            // so we cannot override it
            Class c = defineClass(name, b, 0, b.length);
            resolveClass(c);
            return c;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Every request for a class passes through this method. If the class is in
     * com.journaldev package, we will use this classloader or else delegate the
     * request to parent classloader.
     *
     *
     * @param name Full class name
     */
    @Override
    public Class loadClass(String name) throws ClassNotFoundException {
        System.out.println("Loading Class '" + name + "'");
        if (name.startsWith("CC")) {
            System.out.println("Loading Class using CCLoader");
            return getClass(name);
        }
        return super.loadClass(name);
    }

    /**
     * Reads the file (.class) into a byte array. The file should be accessible as a
     * resource and make sure that it's not in Classpath to avoid any confusion.
     *
     * @param name File name
     * @return Byte array read from the file
     * @throws IOException if any exception comes in reading the file
     */
    private byte[] loadClassFileData(String name) throws IOException {
        InputStream stream = getClass().getClassLoader().getResourceAsStream(name);
        int size = stream.available();
        byte buff[] = new byte[size];
        DataInputStream in = new DataInputStream(stream);
        in.readFully(buff);
        in.close();
        return buff;
    }
}

// This is our test class with the main function where we are creating an object
// of our ClassLoader and load sample classes using its loadClass method.

// After loading the Class, we are using Java Reflection API to invoke its
// methods.

public class CCRun {

    public CCRun(){

    }
    public static void main(String args[]) throws Exception {
        String a = CCRun.class.getName();
        System.out.println(a);
        String progClass = "Bar";
        
        String progClass2 = "Student";
        String progArgs[] = new String[args.length];
        System.arraycopy(args, 1, progArgs, 0, progArgs.length);

        CCLoader ccl = new CCLoader(CCRun.class.getClassLoader());

        Class clas = ccl.loadClass(progClass);
        
        // initialize student class
        String path="";//"D:/7215 Multi-Thread/Programs/Grades.txt";
        File dic= new File("");
        path=dic.getAbsolutePath()+"/Grades.txt";
        GenerateGrade grades = new GenerateGrade ();
        Object lock= new Object();
        //GenerateGrade grades, Object lock,String path
        Object student =clas.getConstructor(GenerateGrade.class,Object.class,String.class).newInstance(grades,lock,path);
        clas.getMethod("run").invoke(student);

        
        // Invoke the main method
        Method main = clas.getMethod("main", String[].class);
        main.invoke(null,  new Object[]{new String[]{}});

        Method ccPrint = clas.getMethod("ccPrint",);
        
        ccPrint.invoke(null);
        //Invoke Bar
        Object instance = clas.getConstructor(String.class,String.class).newInstance("Bar","HAHA");
        clas.getMethod("printCL",String.class).invoke(instance,"aaa");
        

        //Invoke Foo
        Method fooPrintCL = clas.getMethod("printCL",int.class);
        fooPrintCL.invoke(null,1);
        Method fooMain = clas.getMethod("main", String[].class);
        fooMain.invoke(null, new Object[]{new String[]{"Su","Hao"}});
        Method barConstructor = clas.getMethod("printCL",null);
        barConstructor.invoke(null, new Object[0]);
    }

}

class Foo {
    static public void main(String args[]) throws Exception {
        //System.out.println("Foo Constructor >>> " + args[0] + " " + args[1]);
        System.out.println("Foo Constructor >>> " +args.length);
        Bar bar = new Bar(args[0], args[1]);
        bar.printCL();
    }

    public static void printCL(int a) {
        System.out.println("Foo.printCL()"+a);
        System.out.println("Foo ClassLoader: " + Foo.class.getClassLoader());
    }

    public static void printCL() {
        System.out.println("Foo.printCL()");
        System.out.println("Foo ClassLoader: " + Foo.class.getClassLoader());
    }
}
 class Bar {
 
    public Bar(String a, String b) {
        System.out.println("Bar Constructor >>> " + a + " " + b);
    }
 
    public void printCL() {
        System.out.println("Bar ClassLoader: "+Bar.class.getClassLoader());
    }
    public void printCL(String a) {
        System.out.println("Bar ClassLoader: "+ a +Bar.class.getClassLoader());
    }
}

class Student implements Runnable {
    public static int nextId = 1;

    private String name;
    private int id;
    private int midterm;
    private int project;
    private int finalexam;
    private String content;
    private Object lock;
    private String path;

    public Student(GenerateGrade grades, Object lock,String path) {
        this.lock = lock;
        this.id = nextId++;
        this.name = "Student" + this.id;
        grades.GetGrades(this);
        this.path=path;

    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMidterm() {
        return this.midterm;
    }

    public void setMidterm(int midterm) {
        this.midterm = midterm;
    }

    public int getProject() {
        return this.project;
    }

    public void setProject(int project) {
        this.project = project;
    }

    public int getFinalexam() {
        return this.finalexam;
    }

    public void setFinalexam(int finalexam) {
        this.finalexam = finalexam;
    }

    @Override
    public void run() {

        synchronized (lock) {
            
            System.out.println("Student.run() start!!!!");
            content = this.getName() + "," + this.getId() + "," + Thread.currentThread().getId() + ","
                    + this.getMidterm() + "," + this.getProject() + "," + this.getFinalexam();

            try {
                System.out.println(Thread.currentThread().getName());
                FileWriter fr = new FileWriter(path, true);
                fr.write(this.content);
                fr.write("\n");
                fr.close();

            System.out.println("Student.run() finished!!!");
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
}

class GenerateGrade {

    void GetGrades(Student student) {
        try {
            Random ran = new Random();
            int score = ran.nextInt(50) + 50;
            student.setMidterm(score);
            score = ran.nextInt(50) + 50;
            student.setProject(score);
            score = ran.nextInt(50) + 50;
            student.setFinalexam(score);
            Thread.sleep(1000 * 1);
        } catch (Exception e) {
            System.out.print(e.getStackTrace());
        }

    }
}
