package gov.nysenate.openleg.model;

import java.text.SimpleDateFormat;
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
public class Vote extends BaseObject {

    public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

    private int voteType;

    private Date voteDate;

    public String oid;

    private List<String> ayes;

    private List<String> nays;

    private List<String> abstains;

    private List<String> absent;

    private List<String> excused;

    private Bill bill;

    private List<String> ayeswr;

    private String sequenceNumber;

    private String description = "";

    public final static int VOTE_TYPE_FLOOR = 1;

    public final static int VOTE_TYPE_COMMITTEE = 2;
/** Comments about this class */
    public int count()
    {
        return ayes.size()+nays.size()+abstains.size()+excused.size();
    }
/** Comments about this class */
    public Vote()
    {
        super();
        ayes = new ArrayList<String>();
        ayeswr = new ArrayList<String>();
        nays = new ArrayList<String>();
        abstains = new ArrayList<String>();
        excused = new ArrayList<String>();
        absent = new ArrayList<String>();
    }
/** Comments about this class */
    public synchronized Vote(String billId, Date date, int type, String sequenceNumber)
    {
        this();
        this.voteDate = date;
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTime(voteDate);
        this.setYear(cal.get(java.util.Calendar.YEAR));
        this.setSession(this.getYear() % 2 == 0 ? this.getYear() -1 : this.getYear());
        this.voteType = type;
        this.setSequenceNumber(sequenceNumber);
        this.oid = billId+'-'+dateFormat.format(voteDate)+'-'+String.valueOf(voteType)+'-'+sequenceNumber;
    }
/** Comments about this class */
    public Vote(Bill bill, Date date, int type, String sequenceNumber)
    {
        this(bill.getBillId(), date, type, sequenceNumber);
        this.bill = bill;
    }
/** Comments about this class */
    public int getVoteType()
    {
        return voteType;
    }

    /**
     * The object type of the bill.
     */
    public String getOtype()
    {
        return "vote";
    }
/** Comments about this class */
    public String getOid()
    {
        return this.oid;
    }
/** Comments about this class */
    public void setOid(String oid)
    {
        this.oid = oid;
    }
/** Comments about this class */
    public Date getVoteDate()
    {
        return voteDate;
    }
/** Comments about this class */
    public List<String> getAyes()
    {
        return ayes;
    }
/** Comments about this class */
    public List<String> getNays()
    {
        return nays;
    }
/** Comments about this class */
    public List<String> getAbstains()
    {
        return abstains;
    }
/** Comments about this class */
    public List<String> getAbsent()
    {
        return absent;
    }
/** Comments about this class */
    public List<String> getExcused()
    {
        return excused;
    }
/** Comments about this class */
    public Bill getBill()
    {
        return bill;
    }
/** Comments about this class */
    public List<String> getAyeswr()
    {
        return ayeswr;
    }
/** Comments about this class */
    public String getDescription()
    {
        return description;
    }
/** Comments about this class */
    public void setVoteType(int voteType)
    {
        this.voteType = voteType;
    }
/** Comments about this class */
    public void setVoteDate(Date voteDate)
    {
        this.voteDate = voteDate;
    }
/** Comments about this class */
    public void setAyes(List<String> ayes)
    {
        this.ayes = ayes;
    }
/** Comments about this class */
    public void setNays(List<String> nays)
    {
        this.nays = nays;
    }
/** Comments about this class */
    public void setAbstains(List<String> abstains)
    {
        this.abstains = abstains;
    }
/** Comments about this class */
    public void setAbsent(List<String> absent)
    {
        this.absent = absent;
    }
/** Comments about this class */
    public void setExcused(List<String> excused)
    {
        this.excused = excused;
    }
/** Comments about this class */
    public void setBill(Bill bill)
    {
        this.bill = bill;
    }
/** Comments about this class */
    public void setAyeswr(List<String> ayeswr)
    {
        this.ayeswr = ayeswr;
    }
/** Comments about this class */
    public void setDescription(String description)
    {
        this.description = description;
    }
/** Comments about this class */
    public void addAye(Person person)
    {
        ayes.add(person.getFullname());
    }
/** Comments about this class */
    public void addAyeWR(Person person)
    {
        ayeswr.add(person.getFullname());
    }
/** Comments about this class */
    public void addNay(Person person)
    {
        nays.add(person.getFullname());
    }
/** Comments about this class */
    public void addAbstain(Person person)
    {
        abstains.add(person.getFullname());
    }
/** Comments about this class */
    public void addAbsent(Person person)
    {
        absent.add(person.getFullname());
    }
/** Comments about this class */
    public void addExcused(Person person)
    {
        excused.add(person.getFullname());
    }
/** Comments about this class */
    @Override
    public class OVERRIDE{
    public boolean equals(Object obj)

    {
        if(obj != null && obj instanceof Vote) {
            Vote vote = (Vote)obj;
            return this.oid.equals(vote.getOid());
        }
        return false;
    }
       public int hashCode() {
        return value;
    }}
/** Comments about this class */
    @Override
    public String toString() {
        return this.getOid();
    }
/** Comments about this class */
    public String getSequenceNumber()
    {
        return sequenceNumber;
    }
/** Comments about this class */
    private void setSequenceNumber(String sequenceNumber)
    {
        this.sequenceNumber = sequenceNumber;
    }
}
