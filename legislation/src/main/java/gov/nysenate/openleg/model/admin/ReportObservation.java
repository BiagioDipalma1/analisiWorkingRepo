package gov.nysenate.openleg.model.admin;
/**
 * PJDCC - Summary for class responsabilities.
 *
 * @author 
 * @since 
 * @version 
 */
public class ReportObservation
{
    private int id;
    private String oid;
    private String field;

    private int errorId;
    private ReportError error;
    private int reportId;
    private Report report;
    private String actualValue;
    private String observedValue;
/** Comments about this class */
    public ReportObservation() {

    }
/** Comments about this class */
    public ReportObservation(int reportId, String oid, String field, String actualValue, String observedValue) {
        this.reportId = reportId;
        this.oid = oid;
        this.field = field;
        this.actualValue = actualValue;
        this.observedValue = observedValue;
    }
/** Comments about this class */
    public int getId()
    {
        return id;
    }
    /** Comments about this class */
    public void setId(int errorId)
    {
        this.id = errorId;
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
    public int getReportId()
    {
        return reportId;
    }
    /** Comments about this class */
    public void setReportId(int reportId)
    {
        this.reportId = reportId;
    }
/** Comments about this class */
    public String getActualValue()
    {
        return actualValue;
    }
    /** Comments about this class */
    public void setActualValue(String actualValue)
    {
        this.actualValue = actualValue;
    }
/** Comments about this class */
    public String getObservedValue()
    {
        return observedValue;
    }
    /** Comments about this class */
    public void setObservedValue(String observedValue)
    {
        this.observedValue = observedValue;
    }
/** Comments about this class */
    public int getErrorId()
    {
        return errorId;
    }
/** Comments about this class */
    public void setErrorId(int errorId)
    {
        this.errorId = errorId;
    }
/** Comments about this class */
    public ReportError getError()
    {
        return error;
    }
/** Comments about this class */
    public void setError(ReportError error)
    {
        this.error = error;
    }
/** Comments about this class */
    public Report getReport()
    {
        return report;
    }
/** Comments about this class */
    public void setReport(Report report)
    {
        this.report = report;
    }
}
