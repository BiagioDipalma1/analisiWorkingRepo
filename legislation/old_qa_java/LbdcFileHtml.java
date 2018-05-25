package gov.nysenate.openleg.qa;

import gov.nysenate.openleg.model.Action;
import gov.nysenate.openleg.model.Bill;
import gov.nysenate.openleg.model.Person;
import gov.nysenate.openleg.qa.model.FieldName;
import gov.nysenate.openleg.qa.model.LbdcFile;
import gov.nysenate.openleg.qa.model.LbdcFile.AssociatedFields;
import gov.nysenate.openleg.qa.model.NonMatchingField;
import gov.nysenate.openleg.qa.model.ProblemBill;
import gov.nysenate.openleg.search.SearchEngine;
import gov.nysenate.openleg.util.SessionYear; 

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Date;
import java.time.temporal.ChronoUnit;

/**
 * PJDCC - Summary for class responsabilities.
 *
 * @author 
 * @since 
 * @version 
 */

@AssociatedFields({	FieldName.SPONSOR,
    FieldName.COSPONSORS,
    FieldName.ACTIONS,
    FieldName.TITLE,
    FieldName.SUMMARY,
    FieldName.LAW_SECTION })

public class LbdcFileHtml extends LbdcFile {
    Pattern billP = Pattern.compile(
            "<a .+?>(.+?)</a>" + 										
                    "(.+?)<br>\\s*" + 											
                    "(.+?)<br>\\s*" + 											
                    "<b>\\s*Primary Law:\\s*</b>(.+?)<br>\\s*" + 						
                    "(?:<b>SUMM \\: </b>)?(BILL SUMMARY NOT FOUND|.+?)<br>\\s*" + 	
            "(?:(Criminal Sanction Impact.)(?: <br>))?"); 				
    Pattern actionP = Pattern.compile("(<b>(?:&nbsp;)+</b>)?+(\\d{2}/\\d{2}/\\d{2}) (.+?)<br>");
    Pattern sponsorP = Pattern.compile("([\\w\\- ']+?)(?: CO\\: (.+))");

   LocalDate date1 = LocalDate.now();
/** Comments about this class */
    public LbdcFileHtml(File file) {
        super(file);
    }
/** Comments about this class */
    @Override
    public ArrayList<ProblemBill> getProblemBills(FieldName[] fieldNames) {
        ArrayList<ProblemBill> ret = new ArrayList<ProblemBill>();

        Bill lbdcBill = null;

        open();
        
        Bill d=nextBill();

        while((lbdcBill = d) != null) {
            Bill luceneBill = SearchEngine.getInstance().getBill(lbdcBill.getSenateBillNo() + "-" + SessionYear.getSessionYear());

            if(luceneBill == null) {
                //TODO we don't have it
                continue;
            }
            else {
                ProblemBill problemBill = new ProblemBill(luceneBill.getSenateBillNo(), luceneBill.getModified());
                problemBill.setLastReported(time);

                if(valid(lbdcBill.getSponsor(), luceneBill.getSponsor())) {
                    if(!cln(lbdcBill.getSponsor().getFullname()).equalsIgnoreCase(
                            cln(luceneBill.getSponsor().getFullname().replaceAll("\\s*\\(MS\\)", "")))) {

                        problemBill.addNonMatchingField(
                                new NonMatchingField(
                                        FieldName.SPONSOR,
                                        luceneBill.getSponsor().getFullname(),
                                        lbdcBill.getSponsor().getFullname()));
                    }
                }

                doCollectionField(problemBill, FieldName.ACTIONS, luceneBill.getActions(), lbdcBill.getActions());
                doCollectionField(problemBill, FieldName.COSPONSORS, luceneBill.getCoSponsors(), lbdcBill.getCoSponsors());
                doStringField(problemBill, FieldName.SUMMARY, luceneBill.getSummary(), lbdcBill.getSummary(), ".*?");
                doStringField(problemBill, FieldName.TITLE, luceneBill.getTitle(), lbdcBill.getTitle(), null);
                doStringField(problemBill, FieldName.LAW_SECTION, luceneBill.getLawSection(), lbdcBill.getLawSection(), null);

                if(problemBill.getNonMatchingFields() != null && problemBill.getNonMatchingFields().size() != 0)
                    ret.add(problemBill);
            }
        }

        close();

        return ret;
    }

    /**
     * parsing data from the LBDC html dump
     * @return next Bill if available from the parsed HTML
     */
    private Bill nextBill() {
        String in = null;
        StringBuffer buffer = null;
        boolean readToggle = false;
        String h = er.readLine();
        while((in = h) != null) {
            //if in matches the beginning of a new bill element
            if(in.matches("(</td></tr>)?(<tr align=\"left\"|\\Q <script>document.getElementById(\"SRCHCNT\")\\E).*$")) {

                if(buffer != null) {
                    er.reset();

                    return this.getBillFromHtml(buffer.toString());
                }

                if(in.contains("<script>document.getElementById(\"SRCHCNT\")")) {
                    //this signifies the end of last bill in the table
                    break;
                }
                else {
                    readToggle = true;
                    buffer = new StringBuffer().append(in);
                    buffer.append(" ");
                }
            }
            else {
                if(readToggle) {
                    buffer.append(in);
                    buffer.append(" ");
                }
            }

            er.mark(65535);
        }

        return null;
    }

    
  /** Comments about this class */
    private Bill editBill(Matcher m, Bill bill){
        
       bill.setSummary(m.group(5).equals("BILL SUMMARY NOT FOUND") ? null : m.group(5).trim()); 
       return bill;
    }
    /** Comments about this class */
    private Bill getBillFromHtml(String text) {
    	synchronized(dateFormat) {
        Bill bill = null;

        text = text.replaceAll("(</?(tr|td).+?>|<table.+?/table>)", "");
        Matcher m = billP.matcher((text));

        if(m.find()) {
            bill = new Bill();

            bill.setSenateBillNo(m.group(1).trim());
            bill.setTitle(m.group(3).trim());
            bill.setLawSection(m.group(4).trim());
            bill = editBill(m, bill);

            String sponsorString = m.group(2).trim();

            text = text.substring(m.end());

            m.usePattern(sponsorP).reset(sponsorString);
            if(m.find()) {

                bill.setSponsor(new Person(m.group(1)));

                if(m.group(2) != null) {
                    String[] coSpons = sponsorString.trim().split(", ");

                    ArrayList<Person> cosponsors = new ArrayList<Person>();
                    for(String coSpon:coSpons) {
                        cosponsors.add(new Person(coSpon));
                    }

                    bill.setCoSponsors(cosponsors);
                }
            }

            m.usePattern(actionP).reset(text);

            ArrayList<Action> billEvents = new ArrayList<Action>();
            
            boolean f= m.find();
            while(f) {
                if(m.group(1) != null) {
                    continue;
                }

                try {
                    billEvents.add(new Action(bill.getSenateBillNo(), sdf.parse(m.group(2)), m.group(3)));
                } catch (ParseException e) {
                   System.out.println("Something was wrong");
                }
            }

            bill.setActions(billEvents);
        }

        return bill;
    	}
    }
    
/** Comments about this class */
    private String cln(String str) {
        return str.trim().replaceAll("  "," ").replaceAll("&apos;", "'");
    }
/** Comments about this class */
    private boolean valid(Object o1, Object o2) {
        if(o1 != null && o2 != null)
            return true;
        return false;
    }
/** Comments about this class */
    private void doCollectionField(ProblemBill problemBill, FieldName fieldName, Collection<?> openCol, Collection<?> lbdcCol) {
        if(valid(openCol, lbdcCol)) {
            if(lbdcCol.size() - openCol.size() > 0) {
                problemBill.addNonMatchingField(
                        new NonMatchingField(
                                fieldName,
                                openCol.size()+"",
                                lbdcCol.size()+""));
            }
        }
    }
/** Comments about this class */
    private void doStringField(ProblemBill problemBill, FieldName fieldName, String openField, String lbdcField, String pattern) {
        if(valid(openField,lbdcField)) {
            lbdcField = cln(lbdcField);
            openField = cln(openField);
            if(!lbdcField.matches((pattern == null ? "" : pattern) + Pattern.quote(openField))) {
                problemBill.addNonMatchingField(
                        new NonMatchingField(
                                fieldName,
                                openField,
                                lbdcField));
            }
        }
    }
}