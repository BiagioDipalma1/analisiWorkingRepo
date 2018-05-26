package gov.nysenate.openleg.model.admin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
/**
 * PJDCC - Summary for class responsabilities.
 *
 * @author 
 * @since 
 * @version 
 */
public class Report
{
    private int id;
    private Date time;
    private Collection<ReportObservation> observations = new ArrayList<ReportObservation>();
    private Collection<ReportError> openErrors = new ArrayList<ReportError>();
    private Collection<ReportObservation> closedObservations = new ArrayList<ReportObservation>();
    private Collection<ReportError> closedErrors = new ArrayList<ReportError>();
    private Collection<ReportError> newErrors = new ArrayList<ReportError>();
/** Comments about this class */
    public Report() {}
/** Comments about this class */
    public Report(int id, Date time) {
        this.id = id;
        this.time = time;
    }
/** Comments about this class */
    public int getId()
    {
        return id;
    }
/** Comments about this class */
    public void setId(int id)
    {
        this.id = id;
    }
/** Comments about this class */
    public Date getTime()
    {
        return time;
    }
/** Comments about this class */
    public void setTime(Date time)
    {
        this.time = time;
    }
/** Comments about this class */
    public String toString()
    {
        return getId()+": "+getTime();
    }
/** Comments about this class */
    public Collection<ReportObservation> getObservations()
    {
        return observations;
    }
/** Comments about this class */
    public void setObservations(Collection<ReportObservation> observations)
    {
        this.observations = observations;
    }
/** Comments about this class */
    public void addObservation(ReportObservation observation)
    {
        this.observations.add(observation);
    }
/** Comments about this class */
    public Collection<ReportError> getOpenErrors()
    {
        return openErrors;
    }
/** Comments about this class */
    public void setOpenErrors(Collection<ReportError> openErrors)
    {
        this.openErrors = openErrors;
    }
/** Comments about this class */
    public Collection<ReportError> getClosedErrors()
    {
        return closedErrors;
    }
/** Comments about this class */
    public void setClosedErrors(Collection<ReportError> closedErrors)
    {
        this.closedErrors = closedErrors;
    }
/** Comments about this class */
    public Collection<ReportError> getNewErrors()
    {
        return newErrors;
    }
/** Comments about this class */
    public void setNewErrors(Collection<ReportError> newErrors)
    {
        this.newErrors = newErrors;
    }
/** Comments about this class */
    public Collection<ReportObservation> getClosedObservations() {
        return closedObservations;
    }
/** Comments about this class */
    public void setClosedObservations(Collection<ReportObservation> closedObservations) {
        this.closedObservations = closedObservations;
    }
}
