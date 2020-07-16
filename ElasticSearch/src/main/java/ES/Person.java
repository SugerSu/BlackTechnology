package ES;



public class Person {
 
    private String personId;
    private String name;
 
    //standard getters and setters
    public Person(){
           
    }
    public Person(String ID, String Name){
        this.setPersonId(ID);
        this.setName(Name);
        
    }
    public void setPersonId(String Id){
        this.personId=Id;
    }
    public String getPersonId(){
        return this.personId;
    }
    public void setName(String Name){
        this.name=Name;
    }
    public String getName(){
        return this.name;
    }
 
    @Override
    public String toString() {
        return String.format("Person{personId='%s', name='%s'}", 
            personId, name);
    }
}
