package ES;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Hello world!
 */
public class App {
    private App() {
    }

    /**
     * Says hello to the world.
     * 
     * @param args The arguments of the program.
     * @throws IOException
     */
    public static void main(final String[] args) throws IOException {
        ESOperations.makeConnection();
        //a) Implement application Steps: 1 through 7 described in the article.
        //student
        System.out.println("Inserting a new Person with name Haoxuan...");
        Person person = new Person();
        person.setName("Haoxuan");
        person = ESOperations.insertPerson(person);
        System.out.println("Person inserted --> " + person);
        System.out.println("--------------------------------------------------------");
        System.out.println("Changing name to `SuHaoxuan`...");
        person.setName("SuHaoxuan");
        ESOperations.updatePersonById(person.getPersonId(), person);
        System.out.println("Person updated  --> " + person);
        System.out.println("--------------------------------------------------------");
        System.out.println("Getting SuHaoxuan...");
        Person personFromDB = ESOperations.getPersonById(person.getPersonId());
        System.out.println("Person from DB  --> " + personFromDB);
        System.out.println("--------------------------------------------------------");
        System.out.println("Deleting SuHaoxuan...");
        ESOperations.deletePersonById(personFromDB.getPersonId());
        System.out.println("Person Deleted");
        System.out.println("--------------------------------------------------------");
        //b) Once (a) is working, add the following index “school” to Elasticsearch database.
        //school
        School sch1= new School();
        sch1.setName("Saint Paul School");
        sch1.setDescription("ICSE Afiliation");
        sch1.setStreet("Dawarka");
        sch1.setCity("Delhi");
        sch1.setState("Delhi");
        sch1.setZip("110075");
        sch1.setLocation("28.5733056, 77.0122136");
        sch1.setFees("5000");
        sch1.setTags("Good Faculty, Great Sports");
        sch1.setRating("4.5");
        sch1.setId("10");
        School sch2= new School();
        sch2.setName("Crescent School");
        sch2.setDescription("State Board Affiliation");
        sch2.setStreet("Tonk Road");
        sch2.setCity("Jaipur");
        sch2.setState("RJ");
        sch2.setZip("176114");
        sch2.setLocation("26.8535922,75.7923988");
        sch2.setFees("2500");
        sch2.setTags("Well equipped labs");
        sch2.setRating("4.5");
        sch2.setId("16");

        //create school index
        sch1=ESOperations.createSchoolIndex(sch1);
        sch2=ESOperations.createSchoolIndex(sch2);
        System.out.println("create School 1 and School 2..");
        //c) Modify your program in (a) to ALSO read data “school” described below, and have your program to report the data.
        //get scholl info
        sch1=ESOperations.getSchoolById(sch1.getId());
        sch2=ESOperations.getSchoolById(sch2.getId());

        ObjectMapper Obj = new ObjectMapper(); 
        String sch1Str = Obj.writeValueAsString(sch1); 
        String sch2Str = Obj.writeValueAsString(sch2); 
        System.out.println("Getting School 1: "+sch1Str);
        System.out.println("------------------------------------------------");
        System.out.println("Getting School 2: "+sch2Str);

        ESOperations.closeConnection();
    }
}
