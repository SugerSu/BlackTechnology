import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class StudentHM9 {
    public static int nextID = 0;
    String name;
    int id;
    int midterm;
    int project;
    int finalExam;

    public StudentHM9() {
        nextID++;
        this.id = nextID;
        this.name = "Student" + this.id;

    }

    @Override
    public String toString() {
        return "name" + "," + name + "," + "id" + "," + id + "," + "midterm" + "," + midterm + "," + "project" + ","
                + project + "," + "finalExam" + "," + finalExam;
    }
}

class StudentThreadHM9 implements Runnable {
    StudentHM9 st;

    public StudentThreadHM9() {
        st = new StudentHM9();
    }

    @Override
    public void run() {

        try {
            Methods.generateGrades(st);
            Thread.sleep(1000);
            Methods.writeFile(st.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

class GraderThread implements Runnable {
    List<String> scores;

    public GraderThread() {
        scores = new ArrayList<>();
    }

    @Override
    public void run() {

        try {
            scores = Methods.readFile();
            scores = Methods.gradeScores(scores);
            for (String sco : scores) {
                Methods.writeFile(sco.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

class Methods {
    public static String path = "D:\\7215 Multi-Thread\\HomeWork9\\Grades.txt";

    private static Lock lock = new ReentrantLock();

    // generate scores
    public static void generateGrades(StudentHM9 st) {
        st.midterm = GetGrades();
        st.finalExam = GetGrades();
        st.project = GetGrades();

    }

    // write scores to file
    public static void writeFile(String content) {
        try {
            lock.lock();
            FileWriter fr = new FileWriter(path, true);
            fr.write(content);
            fr.write("\n");
            fr.close();

        } catch (Exception e) {

        } finally {
            lock.unlock();
        }
    }

    // write scores to file
    public static void writeFile(String content, boolean isAppend) {
        try {
            lock.lock();
            FileWriter fr = new FileWriter(path, isAppend);
            fr.write(content);
            fr.write("\n");
            fr.close();

        } catch (Exception e) {

        } finally {
            lock.unlock();
        }
    }

    // read score from the file
    public static List<String> readFile() {
        List<String> res = new ArrayList<>();
        BufferedReader br;
        try {
            lock.lock();
            FileReader fl = new FileReader(path);
            br = new BufferedReader(fl);
            String s = br.readLine();
            while (s != null) {
                res.add(s);
                s = br.readLine();
            }
            return res;
        } catch (Exception e) {

        } finally {

            lock.unlock();

        }
        return new ArrayList<>();

    }

    // grader letter grades
    public static List<String> gradeScores(List<String> scores) {
        List<String> res = new ArrayList<>();
        // read socre from list
        for (String score : scores) {

            String[] s = score.split(",");
            int midTerm = Integer.parseInt(s[5]);
            int project = Integer.parseInt(s[7]);
            int finalExam = Integer.parseInt(s[9]);
            String finalGrades = calculateGrade(midTerm, project, finalExam);
            score += ", FinalGrades," + finalGrades;
            res.add(score);
        }

        return res;
    }

    public static int GetGrades() {
        Random ran = new Random();
        return ran.nextInt(50) + 50;
    }

    public static String calculateGrade(int midTerm, int project, int finalExam) {
        double totoalScore = 0.3 * midTerm + 0.3 * project + 0.4 * finalExam;
        String score;

        if (totoalScore > 90)
            score = "A";
        else if (totoalScore > 80)
            score = "B";
        else if (totoalScore > 70)
            score = "C";
        else if (totoalScore > 60)
            score = "D";
        else
            score = "E";

        return score;
    }

}

class GradesTestDriver {
    public static void main(String[] args) throws InterruptedException {

        // test average time
        // long time = 0;
        // for (int j = 0; j < 10; j++) {
        //     long start = System.currentTimeMillis();
        //     Thread[] threads = new Thread[150];
        //     for (int i = 0; i < threads.length; i++) {
        //         threads[i] = new Thread(new StudentThreadHM9());
        //     }
        //     for (int i = 0; i < threads.length; i++) {
        //         threads[i].start();
        //     }
        //     for (int i = 0; i < threads.length; i++) {
        //         threads[i].join();
        //     }
        //     long end = System.currentTimeMillis();
        //     long timedif = end - start;
        //     System.out.println("Creating Threads used: " + timedif + "ms");// 1055-1008ms
        //     time += timedif;
        // }
        // System.out.println("ave:" + time / 10);// 1048ms
        long start=System.currentTimeMillis();
        Thread[] threads = new Thread[150];
        for (int i = 0; i < threads.length; i++) {
        threads[i] = new Thread(new StudentThreadHM9());
        }
        for (int i = 0; i < threads.length; i++) {
        threads[i].start();
        }
        for (int i = 0; i < threads.length; i++) {
        threads[i].join();
        }
        long end=System.currentTimeMillis();
        long timedif=end-start;
        System.out.println("Creating Threads used: "+timedif+"ms");
        Thread.sleep(30000);

        GraderThread grader = new GraderThread();
        Thread grad = new Thread(grader);
        grad.start();
        grad.join();
    }
}
