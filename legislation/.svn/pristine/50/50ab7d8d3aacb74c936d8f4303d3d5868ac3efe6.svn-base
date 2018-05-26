package gov.nysenate.openleg.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * PJDCC - Summary for class responsabilities.
 *
 * @author 
 * @since 
 * @version 
 */
public class Supplemental {

    protected String id;

    protected Date calendarDate;

    protected Date releaseDateTime;

    protected List<Section> sections;

    protected List<Sequence> sequences;

    protected String supplementalId;

    protected Calendar calendar;
/** Comments about this class */
    public void addSequence(Sequence sequence) {
        if(sequences == null) {
            sequences = new ArrayList<Sequence>();
        }
        else {
            sequences.remove(sequence);
        }

        sequences.add(sequence);
    }
/** Comments about this class */
    public List<Sequence> getSequences() {
        return sequences;
    }
/** Comments about this class */
    public void setSequences(List<Sequence> sequences) {
        this.sequences = sequences;
    }
/** Comments about this class */
    public Date getCalendarDate() {
        return calendarDate;
    }
/** Comments about this class */
    public void setCalendarDate(Date calendarDate) {
        this.calendarDate = calendarDate;
    }
/** Comments about this class */
    public Date getReleaseDateTime() {
        return releaseDateTime;
    }
/** Comments about this class */
    public void setReleaseDateTime(Date releaseDateTime) {
        this.releaseDateTime = releaseDateTime;
    }
/** Comments about this class */
    public String getSupplementalId() {
        return supplementalId;
    }
/** Comments about this class */
    public void setSupplementalId(String supplementalId) {
        this.supplementalId = supplementalId;
    }
/** Comments about this class */
    public List<Section> getSections() {
        return sections;
    }
/** Comments about this class */
    public void setSections(List<Section> sections) {
        this.sections = sections;
    }
/** Comments about this class */
    public Calendar getCalendar() {
        return calendar;
    }
/** Comments about this class */
    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }
/** Comments about this class */
    public String getId() {
        return id;
    }
/** Comments about this class */
    public void setId(String id) {
        this.id = id;
    }
/** Comments about this class */
    @Override
    public class OVERRIDE{
    public boolean equals(Object obj) {

        if (obj != null && obj instanceof Supplemental)
        {
            if ( ((Supplemental)obj).getId().equals(this.getId()))
                return true;
        }

        return false;
    }
    public int hashCode() {
        return value;
    }}
}