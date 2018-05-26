package gov.nysenate.openleg.model;

// Richiede commento

/**
 * PJDCC - Summary for class responsabilities.
 *
 * @author 
 * @since 
 * @version 
 */
public class Person {
    private String position = "";

    private String fullname = "";

    private String id = "";

    private String branch = "";

    private String contactInfo = "";

    private String guid = "";
/** Comments about this class */
    public Person () {

    }
/** Comments about this class */
    public Person (String fullname) {
        this.fullname = fullname;
    }
/** Comments about this class */
    public Person (String fullname, String position) {
        this.fullname = fullname;
        this.position = position;

        this.id = fullname + '-' + position;
    }
/** Comments about this class */
    public String getPosition() {
        return position;
    }
/** Comments about this class */
    public String getFullname() {
        return fullname;
    }
/** Comments about this class */
    public String getId() {
        return id;
    }
/** Comments about this class */
    public String getBranch() {
        return branch;
    }
/** Comments about this class */
    public String getContactInfo() {
        return contactInfo;
    }
/** Comments about this class */
    public String getGuid() {
        return guid;
    }
/** Comments about this class */
    public void setPosition(String position) {
        this.position = position;
    }
/** Comments about this class */
    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
/** Comments about this class */
    public void setId(String id) {
        this.id = id;
    }
/** Comments about this class */
    public void setBranch(String branch) {
        this.branch = branch;
    }
/** Comments about this class */
    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }
/** Comments about this class */
    public void setGuid(String guid) {
        this.guid = guid;
    }
/** Comments about this class */
    @Override
    public boolean equals(Object obj) {
        if(obj != null && obj instanceof Person)
        {
            Person person = (Person)obj;
            return this.fullname.equals(person.getFullname());
        }
        return false;
    }
/** Comments about this class */
    @Override
    public String toString() {
        return this.fullname;
    }
}
