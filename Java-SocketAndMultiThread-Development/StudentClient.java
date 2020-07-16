import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;

public class StudentClient {

    public static void main(String[] args) {
        try {
            Thread[] threads = new Thread[50];
            for (int i = 0; i < 50; i++) {
                threads[i] = new Thread(new studentHM5(i + ""));

            }
            for (int i = 0; i < 50; i++) {
                threads[i].start();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

class studentHM5 implements Runnable {
    static String namelib = "abcdefghijklmnopqrstuvwxyz";
    Socket socket;
    String id;
    String firstName;
    String lastName;
    Scanner in;
    PrintWriter out;

    public studentHM5(String id) {
        this.id = id;
        generateStudentInfo();
    }

    @Override
    public void run() {

        try (var socket = new Socket("127.0.0.1", 59898)) {
            System.out.println(this.id + " Connet to the server!");
            in = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream(), true);
            String scores = generateGrades();
            // write score
            out.println(scores);
            System.out.println("Add Score: "+scores);
            while (in.hasNextLine()) {
                String response=in.nextLine();
                if(response.equals("FINISH")){
                    break;
                }else{
                    System.out.println(this.id + " first Score is : " +response);
                }
                
            }
            //wait 2 seconds
            Thread.sleep(2000);
            // update score
            System.out.println(this.id + " Update score!");
            String scores2 = updateGrades();
            System.out.println("Update Score: "+scores2);
            out.println(scores2);
            while (in.hasNextLine()) {
                String response=in.nextLine();
                if(response.equals("FINISH")){
                    break;
                }else{
                    System.out.println(this.id + " Update Score is : " +response);
                }
              
            }
            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            in.close();
        }

    }

    public String generateGrades() {
        StringBuilder sb = new StringBuilder();
        Random ran = new Random();
        sb.append("ADD").append(",");
        sb.append("ID,").append(id).append(",");
        sb.append("FirstName,").append(firstName).append(",");
        sb.append("LastName,").append(lastName).append(",");
        sb.append("homework,").append(ran.nextInt(30) + 70).append(",");
        sb.append("quiz,").append(ran.nextInt(30) + 70).append(",");
        sb.append("midterm,").append(ran.nextInt(30) + 70).append(",");
        sb.append("finalExam,").append(ran.nextInt(30) + 70);
        return sb.toString();
    }

    public String updateGrades() {
        StringBuilder sb = new StringBuilder();
        Random ran = new Random();
        sb.append("UPDATE").append(",");
        sb.append("ID,").append(id).append(",");
        sb.append("FirstName,").append(firstName).append(",");
        sb.append("LastName,").append(lastName).append(",");
        sb.append("homework,").append(ran.nextInt(30) + 70 > 90 ? ran.nextInt(30) + 70 : "NULL").append(",");
        sb.append("quiz,").append(ran.nextInt(30) + 70 > 85 ? ran.nextInt(30) + 70 : "NULL").append(",");
        sb.append("midterm,").append(ran.nextInt(30) + 70 > 80 ? ran.nextInt(30) + 70 : "NULL").append(",");
        sb.append("finalExam,").append(ran.nextInt(30) + 70 > 75 ? ran.nextInt(30) + 70 : "NULL");
        return sb.toString();
    }

    public void generateStudentInfo() {
        Random ran = new Random();

        StringBuilder name = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            name.append(namelib.charAt(ran.nextInt(26)));
        }
        this.firstName = name.toString().substring(0, 4);
        this.lastName = name.toString().substring(4);

    }

}
