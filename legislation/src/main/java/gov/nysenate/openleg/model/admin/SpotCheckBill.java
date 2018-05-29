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
    /**
       * Comments about this field
       */
    public int year;
    /**
       * Comments about this field
       */
    public int pages;
/**
       * Comments about this field
       */
    public String id;
    /**
       * Comments about this field
       */
    public String law;
    /**
       * Comments about this field
       */
    public String title;
    /**
       * Comments about this field
       */
    public String sponsor;
    /**
       * Comments about this field
       */
    public String summary;
    /**
       * Comments about this field
       */
    public String sameas;
    /**
       * Comments about this field
       */
     private boolean currentAmendment;
    /**
       * Comments about this field
       */
    public ArrayList<String> actions;
    /**
       * Comments about this field
       */
    public ArrayList<String> cosponsors;
    /**
       * Comments about this field
       */
    public ArrayList<String> multisponsors;
    /**
       * Comments about this field
       */
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
    public String setTitle(String str)
    {
        this.title = str;
        return title;
    }
/** Comments about this class */
    public String getSummary()
    {
        return summary;
    }
/** Comments about this class */
    public String setSummary(String strSummary)
    {
        this.summary = strSummary;
        return summary;
    }
/** Comments about this class */
    public String getSponsor()
    {
        return sponsor;
    }
/** Comments about this class */
    public String setSponsor(String strSponsor)
    {
        this.sponsor = strSponsor;
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