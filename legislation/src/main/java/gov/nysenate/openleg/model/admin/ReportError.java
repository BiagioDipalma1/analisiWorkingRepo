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
public class ReportError
{
    public enum FIELD {BILL_SUMMARY, BILL_TITLE, BILL_ACTION, BILL_SPONSOR, BILL_COSPONSOR, BILL_TEXT_PAGE, BILL_AMENDMENT};

    private int id;
    private String oid;
    private String field;

    private Date openedAt;
    private Date closedAt;
    private Collection<ReportObservation> observations = new ArrayList<ReportObservation>();
/** Comments about this class */
    public ReportError() {}

    pub/** Comments about this class */lic ReportError(int id, String oid, String field, Date openedAt, Date closedAt) {
        this.id = id;
        this.oid = oid;
        this.field = field;
        this.openedAt = openedAt;
        this.closedAt = closedAt;
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
    public String getOid()
    {
        return oid;
    }
    /** Comments about this class */
    public void setOid(String oid)
    {
        this.oid = oid;
    }
/** Comments about this class */
    public String getField()
    {
        return field;
    }
    /** Comments about this class */
    public void setField(String field)
    {
        this.field = field;
    }
/** Comments about this class */
    public Date getOpenedAt()
    {
        return openedAt;
    }
    /** Comments about this class */
    public void setOpenedAt(Date openedAt)
    {
        this.openedAt = openedAt;
    }
/** Comments about this class */
    public Date getClosedAt()
    {
        return closedAt;
    }
    /** Comments about this class */
    public void setClosedAt(Date closedAt)
    {
        this.closedAt = closedAt;
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
}
