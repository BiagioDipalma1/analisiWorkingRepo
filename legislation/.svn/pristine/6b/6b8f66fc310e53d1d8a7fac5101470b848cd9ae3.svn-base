package gov.nysenate.openleg.model.admin;

import java.util.ArrayList;
/**
 * PJDCC - Summary for class responsabilities.
 *
 * @author 
 * @since 
 * @version 
 */
public class SpotCheckBill {
    public int year;
    public int pages;

    public String id;
    public String law;
    public String title;
    public String sponsor;
    public String summary;
    public String sameas;

    private boolean currentAmendment;

    public ArrayList<String> actions;
    public ArrayList<String> cosponsors;
    public ArrayList<String> multisponsors;
    public ArrayList<String> amendments;
/** Comments about this class */
    public SpotCheckBill() {
        pages = year = 0;
        sameas = id = sponsor = title = summary = law = "";
        currentAmendment = false;
        cosponsors = new ArrayList<String>();
        multisponsors = new ArrayList<String>();
        actions = new ArrayList<String>();
        amendments = new ArrayList<String>();
    }

/** Comments about this class */
    public String getTitle()
    {
        return title;
    }
/** Comments about this class */
    public String setTitle(String title)
    {
        this.title = title;
        return title;
    }
/** Comments about this class */
    public String getSummary()
    {
        return summary;
    }
/** Comments about this class */
    public String setSummary(String summary)
    {
        this.summary = summary;
        return summary;
    }
/** Comments about this class */
    public String getSponsor()
    {
        return sponsor;
    }
/** Comments about this class */
    public String setSponsor(String sponsor)
    {
        this.sponsor = sponsor;
        return sponsor;
    }
/** Comments about this class */
    public boolean isCurrentAmendment() {
        return currentAmendment;
    }
/** Comments about this class */
    public void setCurrentAmendment(boolean currentAmendment) {
        this.currentAmendment = currentAmendment;
    }
/** Comments about this class */
    public ArrayList<String> getCosponsors()
    {
        return cosponsors;
    }
/** Comments about this class */
    public void setCosponsors(ArrayList<String> cosponsors)
    {
        this.cosponsors = cosponsors;
    }
/** Comments about this class */
    public ArrayList<String> getActions()
    {
        return actions;
    }
/** Comments about this class */
    public void setActions(ArrayList<String> actions)
    {
        this.actions = actions;
    }
/** Comments about this class */
    public ArrayList<String> getAmendments() {
        return amendments;
    }
/** Comments about this class */
    public void setMultisponsors(ArrayList<String> multisponsors) {
        this.multisponsors = multisponsors;
    }
/** Comments about this class */
    public void setAmendments(ArrayList<String> amendments) {
        this.amendments = amendments;
    }
}