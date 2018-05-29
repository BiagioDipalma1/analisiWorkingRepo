 package gov.nysenate.openleg.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a weekly senate meetings agenda.
 *
 * @author GraylinKim
 */
public class Agenda extends BaseObject
{
    /**
     * The agenda's unique object id.
     */ 
    private String oid;

    /**
     * The agenda's calendar number. Starts at 1 at the beginning of each calendar year.
     */
    private int number;

    /**
     * The list of addendum to the agenda.
     */
    private List<Addendum> addendums;

    /**
     * JavaBean constructor
     */
    public Agenda()
    {
        super();
        addendums = new ArrayList<Addendum>();
    }



    /**
     * The object type of the agenda.
     */
    public String getOtype()
    {
        return "agenda";
    }

    /**
     * @return - This object's unique object id.
     */
    public String getOid()
    {
        return this.oid;
    }


    /**
     * @return - The agenda number.
     */
    public int getNumber()
    {
        return number;
    }


    /**
     * @return - The list of addendum.
     */
    public List<Addendum> getAddendums()
    {
        return addendums;
    }

    /**
     * @param addendums - The new list of addendum.
     */
    public void setAddendums(List<Addendum> addendums)
    {
        this.addendums = addendums;
    }

    /**
     * @param meeting - The meeting to remove from the meetings list.
     */
    public void removeCommitteeMeeting(Meeting meeting)
    {
        for(Addendum addendum:this.getAddendums()) {
            addendum.removeMeeting(meeting);
        }
    }

  

       /**
        * valore intero
        */
        public int value;
    /**
     * 
     * @param o
     * @return 
     */
        public boolean equals (Object o) {
          if (o instanceof Agenda){ 
             return true; }
         else {
           return false; 
         }
        }
      @Override
    public int hashCode() {
        return value;
    }
}
