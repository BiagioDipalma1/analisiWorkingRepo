 package gov.nysenate.openleg.api.servlets;

import gov.nysenate.openleg.api.AbstractApiRequest;
import gov.nysenate.openleg.api.AbstractApiRequest.ApiRequestException;
import gov.nysenate.openleg.api.KeyValueViewRequest;
import gov.nysenate.openleg.api.KeyValueViewRequest.KeyValueView;
import gov.nysenate.openleg.api.MultiViewRequest;
import gov.nysenate.openleg.api.MultiViewRequest.MultiView;
import gov.nysenate.openleg.api.SearchRequest;
import gov.nysenate.openleg.api.SearchRequest.SearchView;
import gov.nysenate.openleg.api.SingleViewRequest;
import gov.nysenate.openleg.api.SingleViewRequest.SingleView;
import gov.nysenate.openleg.util.OpenLegConstants;
import gov.nysenate.openleg.util.TextFormatter;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.commons.lang3.text.StrSubstitutor;

// Richiede commento

/**
 * PJDCC - Summary for class responsabilities.
 *
 * @author 
 * @since 
 * @version 
 */
@SuppressWarnings("serial")

public class WebServlet extends HttpServlet implements OpenLegConstants {

    /**
       * Comments about this class
       */
    public static final int SINGLE_FORMAT = 1;
    /**
       * Comments about this field
       */
    public static final int SINGLE_TYPE = 2;
    /**
       * Comments about this field
       */
    public static final int SINGLE_ID = 3;
/**
       * Comments about this field
       */
    public static final int MULTI_FORMAT = 1;
    /**
       * Comments about this field
       */
    public static final int MULTI_TYPE = 2;
    /**
       * Comments about this field
       */
    public static final int MULTI_PAGE_NUMBER = 3;
    /**
       * Comments about this field
       */
    public static final int MULTI_PAGE_SIZE = 4;
/**
       * Comments about this field
       */
    public static final int KEY_VALUE_FORMAT = 1;
    /**
       * Comments about this field
       */
    public static final int KEY_VALUE_KEY = 2;
    /**
       * Comments about this field
       */
    public static final int KEY_VALUE_VALUE = 3;
    /**
       * Comments about this field
       */
    public static final int KEY_VALUE_PAGE_NUMBER = 4;
    /**
       * Comments about this field
       */
    public static final int KEY_VALUE_PAGE_SIZE = 5;
/**
       * Comments about this field
       */
    public static final int SEARCH_FORMAT = 1;
    /**
       * Comments about this field
       */
    public static final int SEARCH_KEY = 2;
    /**
       * Comments about this field
       */
    public static final int SEARCH_VALUE = 3;
    /**
       * Comments about this field
       */
    public static final int SEARCH_PAGE_NUMBER = 4;
    /**
       * Comments about this field
       */
    public static final int SEARCH_PAGE_SIZE = 5;

    /*
     * Used to match the start of a single, multi or key value view..
     * 		${context_path}/[view type]
     * 		${context_path}/api/[view type]
     * 		${context_path}/api/1.0/[view type]
     */
    
     /**
       * Comments about this field
       */
    public static final String BASE_START = "^(?i)${context_path}/(?:(?:api/)(?:(?<=api/)1\\.0/)?(?:(";

    /*
     * Ends base start, surrounds possible formats associated with a view
     */
     /**
       * Comments about this field
       */
    public static final String BASE_MIDDLE = ")/))?(";
/** Comments about this class */
    public static final String BASE_END = "$";

    /*
     * multi views and key value views have an optional
     * paging mechanism.. can end with
     * 		../[page]
     * 		../[page]/[page size]
     */
     /**
       * Comments about this field
       */
    public static final String PAGING = "(?:(\\d+)/?+)?(?:(\\d+)/?)?";

    /*
     * Captures ID from single view
     */
     /**
       * Comments about this field
       */
    public static final String SINGLE_END = ")/(.+)";
/** Comments about this class */
    public static final String MULTI_END = ")/?";

    /*
     * Captures value for Key Value view
     */
     /**
       * Comments about this field
       */
    public static final String KEY_VALUE_END = ")/(.*?)/?+";

    /*
     * Captures value for Search view
     */
     /**
       * Comments about this field
       */
    public static final String SEARCH_END = ")(?:/)?(?:(.+?)/?+)?";

    
     /**
       * Comments about this field
       */
    public Pattern MULTI_PATTERN;
     /**
       * Comments about this field
       */
    public Pattern KEY_VALUE_PATTERN;
     /**
       * Comments about this field
       */
    public Pattern SEARCH_PATTERN;
     /**
       * Comments about this field
       */

    private final Logger logger = Logger.getLogger(WebServlet.class);

    /*
     * Generates patterns based on views listed
     * in SingleView, MultiView and KeyValueView enums
     */
     /**
       * Comments about this field
       */
    @Override
    public void  init() throws ServletException {
        
    
        String singleViews = new Join<SingleView>() {
            @Override
            public String value(SingleView t) {
                return t.view;
            }
        }.join(SingleView.values(), "|");

        String singleFormats = new Join<String>() {
            @Override
            public String value(String t) {
                return t;
            }
        }.join(AbstractApiRequest.getUniqueFormats(SingleView.values()), "|");

        String multiViews = new Join<MultiView>() {
            @Override
            public String value(MultiView t) {
                return t.view;
            }
        }.join(MultiView.values(), "|");

        String multiFormats = new Join<String>() {
            @Override
            public String value(String t) {
                return t;
            }
        }.join(AbstractApiRequest.getUniqueFormats(MultiView.values()), "|");

        String keyValueViews = new Join<KeyValueView>() {
            @Override
            public String value(KeyValueView t) {
                return t.view;
            }
        }.join(KeyValueView.values(), "|");

        String keyValueFormats = new Join<String>() {
            @Override
            public String value(String t) {
                return t;
            }
        }.join(AbstractApiRequest.getUniqueFormats(KeyValueView.values()), "|");

        String searchViews = new Join<SearchView>() {
            @Override
            public String value(SearchView t) {
                return t.view;
            }
        }.join(SearchView.values(), "|");

        String searchFormats = new Join<String>() {
            @Override
            public String value(String t) {
                return t;
            }
        }.join(AbstractApiRequest.getUniqueFormats(SearchView.values()), "|");

        // Formats the base api regex to include the context path
        Map<String, String> subMap = new HashMap<>();
        subMap.put("context_path", this.getServletContext().getContextPath());
        String formattedBaseStart = StrSubstitutor.replace(BASE_START, subMap);
        
        Pattern SINGLE_PATTERN;
        
        SINGLE_PATTERN = Pattern.compile(
                TextFormatter.append(

                        formattedBaseStart, singleFormats, BASE_MIDDLE, singleViews, SINGLE_END, BASE_END)
        );

        MULTI_PATTERN = Pattern.compile(
                TextFormatter.append(
                        formattedBaseStart, multiFormats, BASE_MIDDLE, multiViews, MULTI_END, PAGING, BASE_END)
        );

        KEY_VALUE_PATTERN = Pattern.compile(
                TextFormatter.append(
                        formattedBaseStart, keyValueFormats, BASE_MIDDLE, keyValueViews, KEY_VALUE_END, PAGING, BASE_END)
        );

        SEARCH_PATTERN = Pattern.compile(
                TextFormatter.append(
                        formattedBaseStart, searchFormats, BASE_MIDDLE, searchViews, SEARCH_END, PAGING, BASE_END)
        );
    }
/** Comments about this class */
    private boolean controlM(Pattern patt, AbstractApiRequest apiRequest, Matcher m, String uri){
        
        boolean flag = false;
        
        if(apiRequest == null && (m = patt.matcher(uri)) != null && m.find()){
            flag = true;
        }
        
        return flag;
    }
    /**
     * Tries to match request URI to patterns generated above, if successful creates
     * applicable ApiRequest extension and routes request
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response , 
            String formattedBaseStart , String searchFormats,String singleFormats, String singleViews, String uri,  )
            throws ServletException, IOException 
    {
        Matcher m = null;
       
        AbstractApiRequest apiRequest = null;

        String request = request.getParameter(multiFormats, multiViews, MULTI_END, PAGING, BASE_END ); 
        
        /*
         *	/legislation/(api/(1.0/)?[format]/)?[type]/[id]
         *
         *		ex. /legislation/api/html/bill/s1234-2011
         */
        Pattern SINGLE_PATTERN;
        
        SINGLE_PATTERN = Pattern.compile(
                TextFormatter.append(
                        formattedBaseStart, singleFormats, BASE_MIDDLE, singleViews, SINGLE_END, BASE_END)
        );
        
        if(m = singlePatternWork();

        }

        /*
         *	/legislation/(api/(1.0/)?)?[type](/[page number](/[page size]))?
         *
         *		ex.  legislation/bills/1/20 (first page, 20 bills a page)
         */
        if(controlM(MULTI_PATTERN, apiRequest, m, uri)) {
            apiRequest = new MultiViewRequest(	request,
                    response,
                    m.group(MULTI_FORMAT),
                    m.group(MULTI_TYPE),
                    m.group(MULTI_PAGE_NUMBER),
                    m.group(MULTI_PAGE_SIZE));
        }

        /*
         *	/legislation/(api/(1.0/)?)?[key]/[value](/[page number](/[page size]))?
         *
         *		ex. /legislation/api/committee/finance
         */

        if(controlM(KEY_VALUE_PATTERN, apiRequest, m, uri)) {
            logger.info(TextFormatter.append("Key value request: ", uri));
            apiRequest = new KeyValueViewRequest(	request,
                    response,
    
                   
                    
                    m.group(KEY_VALUE_FORMAT),
                    m.group(KEY_VALUE_KEY),
                    m.group(KEY_VALUE_VALUE),
                    m.group(KEY_VALUE_PAGE_NUMBER),
                    m.group(KEY_VALUE_PAGE_SIZE));
                    
            String request = request.getParameter();
           
        }
        

        if(controlM(SEARCH_PATTERN, apiRequest, m, uri)) {
            apiRequest = new SearchRequest(		request,
                    response,
                    m.group(SEARCH_FORMAT),
                    m.group(SEARCH_KEY),
                    m.group(SEARCH_VALUE),
                    m.group(SEARCH_PAGE_NUMBER),
                    m.group(SEARCH_PAGE_SIZE));
        }

        try {
            if(apiRequest == null)
                throw new ApiRequestException(TextFormatter.append("Failed to route request: ", uri));

            apiRequest.execute();
        }
        catch(ApiRequestException e) {
            logger.error(e);
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            
            
        }
    }
    public static String neutralizeMessage(String message) {
  // ensure no CRLF injection into logs for forging records
  String clean = message.replace( '\n', '_' ).replace( '\r', '_' );
  if ( ESAPI.securityConfiguration().getLogEncodingRequired() ) {
      clean = ESAPI.encoder().encodeForHTML(clean);
      if (!message.equals(clean)) {
          clean += " (Encoded)";
      }
  }
  return clean;
}

    /*
     * Used here to easily join lists of values
     */
    static abstract class Join<T> {
        public abstract String value(T t);

        public String join(Iterable<T> iterable, String on) {
            StringBuffer buf = new StringBuffer();

            Iterator<T> iter = iterable.iterator();

            if(iter.hasNext())
                buf.append(value(iter.next()));

            while(iter.hasNext()) {
                buf.append(on);
                buf.append(value(iter.next()));
            }

            return buf.toString();
        }

        public String join(T[] array, String on) {
            return join(Arrays.asList(array), on);
        }
    }
}
