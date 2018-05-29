 package gov.nysenate.openleg.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TreeMap;
// Richiede commento

/**
 * PJDCC - Summary for class responsabilities.
 *
 * @author 
 * @since 
 * @version 
 */
public class PublicHearing extends BaseObject
{
    /**
       * Comments about this field
       */
    public ArrayList<String> committees;
    /**
       * Comments about this field
       */
    public String title;
    /**
       * Comments about this field
       */
    public String location;
    /**
       * Comments about this field
       */
    public Date timeStamp;
/**
       * Comments about this field
       */
    public ArrayList<Person> presidingSenators;
    /**
       * Comments about this field
       */
    public ArrayList<Person> presentSenators;
    /**
       * Comments about this field
       */
    public ArrayList<Person> presentAssemblyPersons;
    /**
       * Comments about this field
       */
    public ArrayList<Person> speakers;
/**
       * Comments about this field
       */
    public TreeMap<Integer, String> pages;
/** Comments about this class */
    public PublicHearing() {
        committees = new ArrayList<String>();
        presidingSenators = new ArrayList<Person>();
        presentSenators = new ArrayList<Person>();
        presentAssemblyPersons = new ArrayList<Person>();
        speakers = new ArrayList<Person>();

        pages = new TreeMap<Integer, String>();
    }

    /**
     * The object type of the hearing.
     */
    public String getOtype()
    {
        return "publichearing";
    }
/** Comments about this class */
    public String getOid()
    {
        return "";
    }
/** Comments about this class */
    public ArrayList<String> getCommittees() {
        return committees;
    }
    /** Comments about this class */
    public String getTitle() {
        return title;
    }
    /** Comments about this class */
    public String getLocation() {
        return location;
    }
    /** Comments about this class */
    public Date getTimeStamp() {
        return timeStamp;
    }
    /** Comments about this class */
    public ArrayList<Person> getPresidingSenators() {
        return presidingSenators;
    }
    /** Comments about this class */
    public ArrayList<Person> getPresentSenators() {
        return presentSenators;
    }
    /** Comments about this class */
    public ArrayList<Person> getPresentAssemblyPersons() {
        return presentAssemblyPersons;
    }
    /** Comments about this class */
    public ArrayList<Person> getSpeakers() {
        return speakers;
    }
    /** Comments about this class */
    public TreeMap<Integer, String> getPages() {
        return pages;
    }
    /** Comments about this class */
    public void setCommittees(ArrayList<String> committees) {
        this.committees = committees;
    }
    /** Comments about this class */
    public void setTitle(String title) {
        this.title = title;
    }
    /** Comments about this class */
    public void setLocation(String location) {
        this.location = location;
    }
    /** Comments about this class */
    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }
    /** Comments about this class */
    public void setPresidingSenators(ArrayList<Person> presidingSenators) {
        this.presidingSenators = presidingSenators;
    }
    /** Comments about this class */
    public void setPresentSenators(ArrayList<Person> presentSenators) {
        this.presentSenators = presentSenators;
    }
    /** Comments about this class */
    public void setPresentAssemblyPersons(ArrayList<Person> presentAssemblyPersons) {
        this.presentAssemblyPersons = presentAssemblyPersons;
    }
    /** Comments about this class */
    public void setSpeakers(ArrayList<Person> speakers) {
        this.speakers = speakers;
    }
    /** Comments about this class */
    public void setPages(TreeMap<Integer, String> pages) {
        this.pages = pages;
    }
/** Comments about this class */
    public void addPerson(Person person, ArrayList<Person> persons) {
        persons.add(person);
    }
/** Comments about this class */
    public void addPage(int pageNumber, String pageText) {
        pages.put(pageNumber, pageText);
    }
/** Comments about this class */
    public int getYear() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getTimeStamp());
        return cal.get(Calendar.YEAR);
    }
/** Comments about this class */
    public static class Person {
        String name;
        String title;
        String committee;
        String organization;

        Integer page;
        Integer questions;
/** Comments about this class */
        public Person() {

        }
/** Comments about this class */
        public Person(String name, String title, String committee, String organization) {
            this.name = name;
            this.title = title;
            this.committee = committee;
            this.organization = organization;
        }
/** Comments about this class */
        public String getName() {
            return name;
        }
/** Comments about this class */
        public String getTitle() {
            return title;
        }
/** Comments about this class */
        public String getCommittee() {
            return committee;
        }
/** Comments about this class */
        public String getOrganization() {
            return organization;
        }
/** Comments about this class */
        public Integer getPage() {
            return page;
        }
/** Comments about this class */
        public Integer getQuestions() {
            return questions;
        }
/** Comments about this class */
        public void setName(String name) {
            this.name = name;
        }
/** Comments about this class */
        public void setTitle(String title) {
            this.title = title;
        }
/** Comments about this class */
        public void setCommittee(String committee) {
            this.committee = committee;
        }
/** Comments about this class */
        public void setOrganization(String organization) {
            this.organization = organization;
        }
/** Comments about this class */
        public void setPage(int page) {
            this.page = page;
        }
/** Comments about this class */
        public void setQuestions(int questions) {
            this.questions = questions;
        }
    }
}
