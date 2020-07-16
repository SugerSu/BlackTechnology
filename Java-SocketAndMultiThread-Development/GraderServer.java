import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.Executors;

public class GraderServer {
    public static  List<String> contents = new ArrayList<>();
    public static void main(String[] args) {
        String path = "";// "D:/7215 Multi-Thread/Programs/Grades.txt";
        File dic = new File("");
        // System.out.println(dic.getAbsolutePath());
        path = dic.getAbsolutePath() + "\\FinalGrade.txt";
       
        try (var listener = new ServerSocket(59898)) {
            System.out.println("GraderServer is running");
            // maintain a thread pool
            var pool = Executors.newFixedThreadPool(10);
            while (true) {
                pool.execute(new Grader(listener.accept(), contents,path));
            }
        } catch (IOException io) {
            io.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

// ADD
// UPDATE
class Grader implements Runnable {
    private Socket socket;
    Map<String, String> records;
    Map<String, String> socres;
    //private String path = "D:/7215 Multi-Thread/Programs/Grades.txt";
    List<String> contents;
    String Id;
    String firstName;
    String lastName;
    Scanner input;
    PrintWriter output;
    String path;

    Grader(Socket socket, List<String> contents, String path) {
        this.socket = socket;
        this.contents = contents;
        this.path=path;
    }

    @Override
    public void run() {
        System.out.println("Connected: " + socket);
        try {
            // read data from client
             input = new Scanner(socket.getInputStream());
             output = new PrintWriter(socket.getOutputStream(), true);
            while (input.hasNextLine()) {

                String inputScores = input.nextLine();
                String[] items = inputScores.split(",");
                this.Id = items[2];
                
                // operation choice
                String scores;
                if (inputScores.startsWith("UPDATE")) {
                    //System.out.println("update Score: " + inputScores);
                    scores = updateScore(inputScores);
                    //System.out.println("update Score is: " + scores);
                } else {
                    scores = inputScores;
                    deleteDuplicatedScore(scores);
                    System.out.println("Add Scores :" + scores);
                }

                // calculate score
                String grade = calculateScore(scores);
                //System.out.println("The final score is: " + grade);
                // writeFile
                String content = scores + ",FinalGrade," + grade;
                contents.add(content);
                writeFile(contents);
                // System.out.println("Finish writting file!");
                // output response
                output.println(grade);
                // System.out.println("Receive Scores: "+sb.toString());
                //socket.close();
                output.println("FINISH");

            }

        } catch (Exception e) {

        } finally {

            input.close();
            
            try {
                socket.close();
            } catch (IOException e) {
            }
            System.out.println("Closed: " + socket);
        }

    }
    public void deleteDuplicatedScore(String scores){
        try {
            synchronized (contents) {
                int index = -1;
                // update score
                for (int i = 0; i < contents.size(); i++) {
                    String[] oldItem = contents.get(i).split(",");
                    if (oldItem[2].equals(this.Id)) {
                        index = i;
                        break;
                    }
                }  
                if(index !=-1)       contents.remove(index);     
                
                
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String updateScore(String scores) throws Exception {

        synchronized (contents) {
            int index = -1;
            // update score
            for (int i = 0; i < contents.size(); i++) {
                String[] oldItem = contents.get(i).split(",");
                if (oldItem[2].equals(this.Id)) {
                    String[] newItem = scores.split(",");
                    if (newItem[8].equals("NULL")) {
                        newItem[8] = oldItem[8];
                    }
                    if (newItem[10].equals("NULL")) {
                        newItem[10] = oldItem[10];
                    }
                    if (newItem[12].equals("NULL")) {
                        newItem[12] = oldItem[12];
                    }
                    if (newItem[14].equals("NULL")) {
                        newItem[14] = oldItem[14];
                    }
                    StringBuilder sb = new StringBuilder();
                    sb.append("UPDATED").append(",");
                    sb.append("ID,").append(newItem[2]).append(",");
                    sb.append("FirstName,").append(newItem[4]).append(",");
                    sb.append("LastName,").append(newItem[6]).append(",");
                    sb.append("homework,").append(newItem[8]).append(",");
                    sb.append("quiz,").append(newItem[10]).append(",");
                    sb.append("midterm,").append(newItem[12]).append(",");
                    sb.append("finalExam,").append(newItem[14]);

                    scores = sb.toString();

                    index = i;
                    break;
                }
            }
            if(index != -1) contents.remove(index);
            
            
            
        }

        return scores;
    }

    public String calculateScore(String scores) throws Exception {
        String[] items = scores.split(",");
        this.Id = items[2];
        this.firstName = items[4];
        this.lastName = items[6];

        int homework = Integer.parseInt(items[8]);
        int quiz = Integer.parseInt(items[10]);
        int midTerm = Integer.parseInt(items[12]);
        int finalExam = Integer.parseInt(items[14]);
        // System.out.println("After Pasrse:
        // "+Id+","+firstName+","+lastName+","+homework+","+midTerm+","+quiz+","+finalExam);

        return calculateScore(homework, midTerm, quiz, finalExam);
    }

    public String calculateScore(int homework, int midTerm, int quiz, int finalExam) throws Exception {
        double totoalScore = 0.2 * homework + 0.3 * midTerm + 0.3 * quiz + 0.2 * finalExam;
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

    public void writeFile(List<String> contents) {
        synchronized (contents) {
            try {
                System.out.println(Thread.currentThread().getName());
                FileWriter fr = new FileWriter(path, false);
                for (String content : contents) {
                    fr.write(content);
                    fr.write("\n");
                }
                fr.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
