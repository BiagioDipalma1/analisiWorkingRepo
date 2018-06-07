package gov.nysenate.openleg.api;

import gov.nysenate.openleg.api.QueryBuilder.*;
import gov.nysenate.openleg.model.*;
 
import gov.nysenate.openleg.util.*;


import java.text.*;  
import java.util.*;

import java.time.*; 
 


import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
// Richiede commento

/**
 * PJDCC - Summary for class responsabilities.
 *
 * @author 
 * @since 
 * @version 
 */
public class ApiHelper implements OpenLegConstants {
    private final static DateFormat DATE_FORMAT_MED = SimpleDateFormat.getDateInstance(SimpleDateFormat.MEDIUM);
    LocalDate date1 = LocalDate.now();
      

    private static Logger logger = Logger.getLogger(ApiHelper.class);

    private static ObjectMapper mapper = new ObjectMapper();
/** Comments about this class */
    public static ObjectMapper getMapper() {
        if(mapper == null) {
            mapper = new ObjectMapper();
            mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        }

        return mapper;
    }
/** Comments about this class */
    private boolean controlSr(SenateResponse sr){
        boolean flag =false;
        
        if(sr.getResults() == null || sr.getResults().isEmpty()){
            flag = true;
        }
        
        return flag;
    }
    /** Comments about this class */
    private String editTitle( String title, Bill bill){

        if (bill.getTitle() != null)
            title += bill.getTitle();
        else
            title += "(no title)";
        
        return title;
    }
    /** Comments about this class */
    private HashMap<String, String> editSponsors (HashMap<String, String> fields, Bill bill){
        
        if (bill.getSponsor() != null)
            fields.put("sponsor", bill.getSponsor().getFullname());
        else
            fields.put("sponsor", null);
        
        return fields;
    }
    /** Comments about this class */
    private HashMap<String, String> editCalendar(HashMap<String, String> fields, Calendar calendar){
        
        if (calendar.getType() == null)
            fields.put("type", "");
        else if (calendar.getType().equals("active"))
            fields.put("type", "Active List");
        else if (calendar.getType().equals("floor"))
            fields.put("type", "Floor Calendar");
        else
            fields.put("type", calendar.getType());
        
        return fields;

    }
    /** Comments about this class */
    private String editTitle(String title, Transcript transcript){
        
         if (transcript.getTimeStamp() != null)
            title = date1.atTime(LocalTime.MIN).format(transcript.getTimeStamp());
        else
            title = "Transcript - " + transcript.getLocation();
         
         
         return title;
    }
    /** Comments about this class */
    private String suppCase(String summary, HashMap<String, String> fields, Supplemental supp){
        
        synchronized(dateFormat) {
            
        if (supp.getCalendarDate() != null) {
        fields.put("date", dateFormat.format(supp.getCalendarDate()));

        summary = "";

        if (supp.getSections() != null) {
            Iterator<Section> itSections = supp.getSections()
                    .iterator();
            while (itSections.hasNext()) {
                Section section = itSections.next();

                summary += section.getName() + ": ";
                summary += section.getCalendarEntries().size() + " items;";
            }
        }
    } else if (supp.getSequences() != null && supp.getSequences().size() > 0) {

        fields.put("date", dateFormat.format(supp.getSequences().get(0).getActCalDate()));

        int total = 0;
        for(Sequence seq:supp.getSequences()) {
            total += seq.getCalendarEntries().size();
        }
        summary = total + " item(s)";

    }
        
        return summary;
    }}
    /** Comments about this class */
    public static ArrayList<Result> buildSearchResultList(SenateResponse sr) {

        ArrayList<Result> resultList = new ArrayList<Result>();

        if (controlSr(sr)){
            return resultList;
        }
        try {    
        for (Result result : sr.getResults()) {
            
                String type = result.getOtype();
                String jsonData = result.getData();

                if (jsonData == null)
                    continue;

                jsonData = unwrapJson(jsonData);

                ApiType apiType = getApiType(type);
                Class<? extends BaseObject> clazz = apiType.clazz();

                BaseObject resultObj = null;
                
                    resultObj = mapper.readValue(jsonData, clazz);
                    result.setObject(resultObj);
                 throw new LoggeException();

                if (resultObj == null)
                    continue;

                resultObj.setModifiedDate(new Date(result.getLastModified()));
                resultObj.setActive(result.isActive());

                result = editResult(resultObj, type, result);
                
             
            
            }
        }
        catch ( LoggeException e) {
                    logger.error("error binding:" + clazz.getName(), e);
                }
        
             catch (Exception e) {
                logger.error(TextFormatter.append(
                        "problem parsing result: ", result.getOtype(), "-", result.getOid()),
                        e);
            }
       
        

        return sr.getResults();
    }
    /** Comments about this class */
    private HashMap<String, String> editVote(HashMap<String,String> fields, Vote vote){
        
        if (vote.getVoteType() == Vote.VOTE_TYPE_COMMITTEE)
            fields.put("type", "Committee Vote");
        else if (vote.getVoteType() == Vote.VOTE_TYPE_FLOOR)
            fields.put("type", "Floor Vote");

        
        return fields;
    }
    /** Comments about this class */
    private Result editResult(BaseObject resultObj, String type,Result result ){
        
        synchronized (dateFormat) {
        
        String title = "";
        String summary = "";

        HashMap<String, String> fields = new HashMap<String, String>();
        fields.put("type", type);
        
        if (type.equals("bill")) {
            Bill bill = (Bill) resultObj;

            title = editTitle(type, title, bill);

            fields = editSponsors(fields, bill);

            fields.put("othersponsors", StringUtils.join(bill.getOtherSponsors(), ", "));
            summary = bill.getSummary();

            fields.put("committee", bill.getCurrentCommittee());
            fields.put("billno", bill.getBillId());
            fields.put("summary", bill.getSummary());
            fields.put("year", bill.getSession() + "");
        } else if (type.equals("calendar")) {
            Calendar calendar = (Calendar) resultObj;

            title = Integer.toString(calendar.getNo());

            fields = editCalendar(fields, calendar);

            Supplemental supp = calendar.getSupplementals().get(0);

            summary = suppCase(summary, fields, supp);

        } else if (type.equals("transcript")) {
            Transcript transcript = (Transcript) resultObj;

            title = editTitle(title, transcript);

            summary = TextFormatter.append(transcript.getType(), ": ", transcript.getLocation());

            fields.put("location", transcript.getLocation());

        } else if (type.equals("meeting")) {
            Meeting meeting = (Meeting) resultObj;
            title = TextFormatter.append(meeting.getCommitteeName(), " (",
                    date1.atTime(LocalTime.MIN).format(meeting.getMeetingDateTime()), ")");

            fields.put("location", meeting.getLocation());
            fields.put("chair", meeting.getCommitteeChair());
            fields.put("committee", meeting.getCommitteeName());

        } else if (type.equals("action")) {
            Action billEvent = (Action) resultObj;
            String billId = billEvent.getBill().getBillId();

            title = billEvent.getText();

            fields.put("date", dateFormat.format(billEvent
                    .getDate()));
            fields.put("billno", billId);
        } else if (type.equals("vote")) {
            Vote vote = (Vote) resultObj;

            fields = editVote(fields, vote);

            HashMap<String, String> resultFields = result.getFields();
            fields.put("sponsor", resultFields.get("sponsor"));
            fields.put("billno", resultFields.get("billno"));
            fields.put("othersponsors", resultFields.get("othersponsors"));

            if (vote.getVoteType() == Vote.VOTE_TYPE_COMMITTEE)
                fields.put("committee", vote.getDescription());

            title +=  DATE_FORMAT_CUSTOM.format(vote.getVoteDate());
        }

        result.setTitle(title);
        result.setSummary(summary);
        result.setFields(fields);
        
        
        return result;
    } }
/** Comments about this class */
    public static String dateReplace(String term) {
        Pattern  p = Pattern.compile("(\\d{1,2}[-]?){2}(\\d{2,4})T\\d{2}-\\d{2}");
        Matcher m = p.matcher(term);

        LocalDate sdf= LocalDate.now();
        boolean t= m.find();
        try {
        while(t) {
            String d = term.substring(m.start(),m.end());

            Date date = null; 
            
                date = sdf.parse(d);
                term = term.substring(0, m.start()) + date.getTime() + term.substring(m.end());
            

            m.reset(term);

        }
        }
        catch (java.text.ParseException e) {
                logger.warn(e);
            }

        return term;
    }
/** Comments about this class */
    public static String unwrapJson(String jsonData) {
        jsonData = jsonData.substring(jsonData.indexOf(":")+1);
        jsonData = jsonData.substring(0,jsonData.lastIndexOf("}"));
        return jsonData;
    }
/** Comments about this class */
    public static ApiType getApiType(String type) {
        for(ApiType apiType:ApiType.values()) {
            if(apiType.type().equalsIgnoreCase(type)) {
                return apiType;
            }
        }
        return null;
    }
/** Comments about this class */
    public static String buildBillWildCardQuery(String billType, String billWildcard, String sessionYear) {
        return TextFormatter.append(billType,":((",
                billWildcard, "-", sessionYear,
                " OR [", billWildcard, "A-", sessionYear,
                " TO ", billWildcard, "Z-", sessionYear,
                "]) AND ", billWildcard, "*-", sessionYear, ")");
    }
/** Comments about this class */
    public static String formatDate(String term, String command) {
        LocalDateTime date = null;

        if(term.matches("(\\d{1,2}[-/]?){2}(\\d{2,4})?")) {
            term = term.replace("/","-");

            java.util.Calendar c = java.util.Calendar.getInstance();
            if(term.matches("\\d{1,2}-\\d{1,2}"))
                term = term + "-" + c.get(java.util.Calendar.YEAR);
            if(term.matches("\\d{1,2}-\\d{1,2}-\\d{2}")) {

                String yr = term.split("-")[2];

                term = term.replaceFirst("-\\d{2}$","");

                term = term + "-" + Integer.toString(c.get(java.util.Calendar.YEAR)).substring(0,2) + yr;
            }
        }

        try {
            LocalDate date1 = LocalDate.now();
            date = date1.atTime(LocalTime.MIN).parse(term);
        }
        catch (java.text.ParseException e) {
            logger.error(e);
        }

       LocalDate sdf= LocalDate.now();

        QueryBuilder queryBuilder  = QueryBuilder.build();

        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTime(date);
        cal.set(java.util.Calendar.HOUR, 23);
        cal.set(java.util.Calendar.MINUTE, 59);
        cal.set(java.util.Calendar.SECOND, 59);

        try {
            queryBuilder.otype(command).and().range("when", sdf.format(date), sdf.format(cal.getTime()));
        } catch (QueryBuilderException e) {
            logger.error(e);
        }

        return queryBuilder.query();
    }
}
