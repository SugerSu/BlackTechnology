package ES;

import com.fasterxml.jackson.databind.annotation.JsonAppend.Prop;

/**
 * School
 */
public class School {

    private String name;
    private String id;
    private String street;
    private String city;
    private String state;
    private String zip;
    private String location;
    private String fees;
    private String tags;
    private String rating;
    private String description;
    

    public School() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String Id) {
        this.id = Id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreet() {
        return this.street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLocatoin() {
        return this.location;
    }

    public void setLocation(String locatoin) {
        this.location = locatoin;
    }

    public String getZip() {
        return this.zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getFees() {
        return this.fees;
    }

    public void setFees(String fees) {
        this.fees = fees;
    }

    public String getTags() {
        return this.tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getRating() {
        return this.rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    

    
}