package gov.nysenate.openleg.qa;

import gov.nysenate.openleg.model.Action;
import gov.nysenate.openleg.model.Bill;
import gov.nysenate.openleg.model.Person;
import gov.nysenate.openleg.util.EasyReader;
import gov.nysenate.openleg.util.SessionYear;

import java.io.BufferedReader;
import java.io.File; 
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.text.DateFormat;


import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;


import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.Month;

import java.time.temporal.ChronoUnit;

import org.apache.log4j.Logger;
/** Comments about this class */
class Informations {
    
            String status;
            String summary;
            String text;
            String memo;
      
            Informations(){
                status = null;
                summary = null;
                text = null;
                memo = null;
            }
      
}
// Richiede commento

/**
 * PJDCC - Summary for class responsabilities.
 *
 * @author 
 * @since 
 * @version 
 */
public class LBDConnect {
    private static Logger logger = Logger.getLogger(LBDConnect.class);

    private static final int PORT = 80;
    private static final String TEMP_FILE_NAME = "lbdc.bill.temp";

    private static final String KEY_URL = "http://public.leginfo.state.ny.us/menugetf.cgi";
    private static final String BASE_URL = "http://public.leginfo.state.ny.us";
    private static final String APPLICATION = "/bstfrmef.cgi?";
    private static final String QUERY_TYPE = "QUERYTYPE=BILLNO";
    private static final String SESSION_YEAR = "&SESSYR=";
    private static final String QUERY_DATA = "&QUERYDATA=";
    private static final String QQ_DATA = "&QQDATA=";
    private static final String GET_SEL = "&GETSEL=";
    private static final String LST = "&LST=";
    private static final String BROWSER = "&BROWSER=Netscape";
    private static final String TOKEN = "&TOKEN=";
    private static final String SELECT = "&SELECT=TEXT++&SELECT=STATUS++&SELECT=SPMEMO++&SELECT=SUMMARY++&SELECT=HISTORY";

    private static final String COMMON_QUERY = "COMMONQUERY=";
    private static final String COMMON_QUERY_COMMITTEE = "VM:\\PROD\\COMLIST2.MAIN";

    private final String key;
/** Comments about this class */
    public static LBDConnect getInstance() {
        String key = getInstanceKey();

        logger.info("Creating instance with key: " + key);

        if(key == null)
            return null;
        else
            return new LBDConnect(key);
    }
/** Comments about this class */
    private LBDConnect(String key) {
        this.key = key;
    }
/** Comments about this class */
    public String getKey() {
        return key;
    }
/** Comments about this class */
    private String constructUrlForCommittees() {
        return APPLICATION + COMMON_QUERY + COMMON_QUERY_COMMITTEE
                + TOKEN + this.key;
    }
/** Comments about this class */
    public ArrayList<String> getSenateCommittees() throws IOException{
        ArrayList<String> committees = new ArrayList<String>();

        File file = new File(TEMP_FILE_NAME);

        
        writeDataFromLbdc(constructUrlForCommittees());
        

        EasyReader er = new EasyReader(file).open();

        boolean senateCom = false;
        boolean senateComId = false;

        for(String in:er) {
            if(senateCom && senateComId) {
                committees.add(in.replaceAll("<.*>", ""));

                senateCom = false;
                senateComId = false;
            }

            if(in.matches(Pattern.quote("<a href=\"/nyslbdc1/statdoc.cgi?VM:\\PROD\\COMS"))) {
                senateCom = true;
            }

            if(senateCom && in.matches("\\d+\\.MAIN\">")) {
                senateComId = true;
            }
        }

        if(file.exists())
            file.delete();

        return committees;
    }
/** Comments about this class */
    private String constructUrlBill(String billNumber, String year) {
        return APPLICATION + QUERY_TYPE + SESSION_YEAR + year
                + QUERY_DATA + billNumber + QQ_DATA
                + billNumber + GET_SEL + LST + BROWSER
                + TOKEN + this.key + SELECT;
    }
/** Comments about this class */
    public Bill getBillFromLbdc(String bill) {
        if(bill.indexOf("0") == -1)
            return getBillFromLbdc(bill, SessionYear.getSessionYear()+"");
        else {
            String[] strings = bill.split("-");
            return getBillFromLbdc(strings[0], strings[1]);
        }
    }
/** Comments about this class */
    private Informations controlIn(String in, Informations info){
      
        if(in.contains("<B>STATUS:</B>")) {
                    info.status = "";
                }
                else if(in.contains("<B>SUMMARY:</B>")) {
                    info.summary = "";
                }
                else if(in.contains("<B>BILL TEXT:</B>")) {
                    info.text = "";
                }
                else if(in.contains("<B>SPONSORS MEMO:</B>")) {
                    info.memo = "";
                    
                }
        return info;
    
    }
    /** Comments about this class */
    private Informations controlInfo (String in, Informations info){
        
        if(info.memo != null) {
                    info.memo += in;
                }
                else if(info.text != null) {
                    info.text += in;
                }
                else if(info.summary != null) {
                    info.summary += in;
                }
                else if(info.status != null) {
                    info.status += in;
                }
        return info;
        
    }
    /** Comments about this class */
    private Bill controlStatus (Bill bill, Informations info, String billNumber, String year){
        
        if(parseStatus(info.status, bill)
                    && parseSummary(info.summary, bill)
                    && parseText(info.text, bill)
                    && parseMemo(info.memo, bill)) {
                //success
                logger.info("Successfully retreieved bill: " + bill.getSenateBillNo());
            }
            else {
                logger.info("Failed to retreieve bill: " + billNumber + "-" + year);
                bill = null;
            }

       return bill;
       
    }
    /** Comments about this class */
    @SuppressWarnings("empty-statement")
    public Bill getBillFromLbdc(String billNumber, String year) {
        Bill bill = new Bill();
        File file = new File(TEMP_FILE_NAME);
        try {
            writeDataFromLbdc(constructUrlBill(billNumber, year));

            Informations info;
            //skip past http header
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                //skip past http header
                boolean a= br.readLine().equals("Content-type: text/html");
                while(!a);
                br.readLine();
                String in = null;
                info = new Informations();
                
                String  c= br.readLine();
                while(in == c &&  in != null) {
                    
                    info = controlIn(in, info);
                    
                    info = controlInfo(in, info);
                    
                }
            }

            bill.setSenateBillNo(bill + "-" + year);

            bill = controlStatus(bill, info, billNumber, year);
            

        } catch (IOException e) {
            logger.error(e);
 
        }
        finally {
         try{
         bill.close();
         }catch(Exception e){}
            System.out.println("Exception IO");
        }

        return bill;
    }
/** Comments about this class */
    private boolean parseStatus(String status, Bill bill) {
        if(status == null)
            return false;

        status = status.replaceAll("(?i)<br>","\n")
                .replaceAll("&nbsp;"," ")
                .replaceAll("(?i)(</?tr.*?>)", "\n\t$1")
                .replaceAll("(?i)<td>(.*?)</td>","\n\t\t$1")
                .replaceAll("(?i)</?(meta|html|table|a|th|body|hr|tr|td|font|b|strong).*?>","")
                .replaceAll("(\t|\n|\\s){2,}","\n")
                .replaceAll("No Same as","No Same as\nNo Same as Sponsor");

        if(status.contains("Bill Status Information Not Found"))
            return false;

        String strings[] = status.split("\n");

        bill.setSponsor(new Person(strings[2]));

        String sameAsBillNo = strings[3].replaceAll("(Same as| |\\-)","");
        bill.setSameAs(sameAsBillNo);

        bill.setLawSection(strings[5]);

        String title = strings[6].replace("TITLE....","");
        bill.setTitle(title);

        LocalDate date1 = LocalDate.now();
        Action be = new Action();
        for(int i = 7; i < strings.length;) {
            if(strings[i].matches("\\d{2}/\\d{2}/\\d{2}")) {
                
                try {

                    be.setDate(sdf.parse(strings[i]));
                    i++;
                    be.setText(strings[i]);

                    bill.addAction(be);
                } catch (ParseException e) {
                    logger.error(e);
                }
                i++;
            }
            else {
                if(strings[i].charAt(0) == sameAsBillNo.charAt(0)) {
                    i += 2;
                    boolean d= strings[i].matches("\\d{2}/\\d{2}/\\d{2}");
                    while(d) {
                        i += 2;
                    }
                    i += 2;
                }
                else {
                    break;
                }
            }
        }
        return true;
    }
/** Comments about this class */
    private boolean parseSummary(String summary, Bill bill) {
        if(summary == null)
            return false;

        summary = summary.replaceAll("(?i)<br>","\n")
                .replaceAll("(?i)</?(hr|b).*?>","")
                .replaceAll("(\t|\n|\\s){2,}","\n");

        if(summary.contains("Bill Summary Information Not Available"))
            return false;

        String[] strings = summary.split("\n");

        if(strings.length == 3) {
            bill.setSummary(strings[2]);
        }
        else {
            bill.setSummary(strings[3]);
        }



        return true;
    }
/** Comments about this class */
    private boolean parseText(String text, Bill bill) {
        if(text == null)
            return false;

        text = text.replaceAll("(?i)<br>","\n")
                .replaceAll("(?i)</?(hr|b|html|head|style|title|basefont|font|pre|u|!\\-\\-).*?>","");

        String[] strings = text.split("\n");

        bill.setFulltext(strings[1]);

        return true;
    }
/** Comments about this class */
    private boolean parseMemo(String memo, Bill bill) {
        if(memo == null)
            return false;

        /* Since it's actually possible that a bill won't have a memo
         * there's no sense in checking for it here.  Could create false
         * negatives */

        memo = memo.replaceAll("&nbsp;", "").replaceAll("(?i)<br>","\n")
                .replaceAll("&nbsp","")
                .replaceAll("(?i)</?(hr|b|html|head|style|title|basefont|font|pre|u|center|!\\-\\-).*?>","");

        if(memo.contains("Memo Text Not Found"))
            bill.setMemo(null);
        else {
            String[] strings = memo.split("\n");

            bill.setMemo(strings[5]);
        }

        return true;
    }
/** Comments about this class */
    private static String getInstanceKey() {
        String key = null;

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new URL(KEY_URL).openStream()));

            Pattern tokenPattern = Pattern.compile("<FRAME NAME=\"TOP\" src=\"frmload.cgi\\?TOP-(\\d+)\">");
            Matcher tokenMatcher = null;

            String in = null;
            
            String s=br.readLine();
            while((in = s) != null) {

                tokenMatcher = tokenPattern.matcher(in);

                if(tokenMatcher.find()) {
                    key = tokenMatcher.group(1);
                }
            }
            br.close();
        }
        catch (IOException ioe) {
            logger.error(ioe);
        }

        return key;
    }
/** Comments about this class */
    private SocketChannel getSocketChannel(String url, String file) throws IOException {
        URL connectUrl = new URL(url);
        String host = connectUrl.getHost();

        SocketAddress remote = new InetSocketAddress(host, PORT);
        SocketChannel channel = SocketChannel.open(remote);

        String request = "GET " + file + " HTTP/1.1\r\n" + "User-Agent: HTTPGrab\r\n" +
                "Accept: text/*\r\nConnection: close\r\nHost: " + host + "\r\n\r\n";

        logger.info("Request: " + request);

        ByteBuffer header = ByteBuffer.wrap(request.getBytes("US-ASCII"));
        channel.write(header);

        return channel;
    }
/** Comments about this class */
    private void writeDataFromLbdc(String uri) throws IOException {
        logger.info("Reading " + uri + " from LBDC");
        try (SocketChannel channel = getSocketChannel(BASE_URL, uri); 
                FileOutputStream out = new FileOutputStream(TEMP_FILE_NAME); 
                FileChannel local = out.getChannel()) {
            
            ByteBuffer buffer = ByteBuffer.allocate(131072);
            int s=channel.read(buffer);
            while(s != -1) {
                buffer.flip();
                local.write(ByteBuffer.wrap(
                        Charset.forName("ISO-8859-1")
                                .decode(buffer).toString().getBytes()));
                buffer.clear();
            }
           local.close ();
           out.close();
           channel.close();
        }
       
    }
}
