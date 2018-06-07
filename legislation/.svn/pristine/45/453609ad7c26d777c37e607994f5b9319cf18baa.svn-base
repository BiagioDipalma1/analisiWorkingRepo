package gov.nysenate.openleg.api.servlets;

import gov.nysenate.openleg.api.AbstractApiRequest.ApiRequestException;
import gov.nysenate.openleg.api.ApiHelper;
import gov.nysenate.openleg.converter.Api1JsonConverter;
import gov.nysenate.openleg.converter.Api1XmlConverter;
import gov.nysenate.openleg.converter.pdf.BillTextPDFConverter;
import gov.nysenate.openleg.model.BaseObject;
import gov.nysenate.openleg.model.Bill;
import gov.nysenate.openleg.model.SenateResponse;
import gov.nysenate.openleg.util.Application;
import gov.nysenate.openleg.util.RequestUtils;
import gov.nysenate.openleg.util.SessionYear;
import gov.nysenate.openleg.util.TextFormatter;

import java.io.IOException; 
import java.io.PrintWriter;
import java.util.regex.Matcher; 
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.pdfbox.exceptions.COSVisitorException;

// Richiede commento

/**
 * PJDCC - Summary for class responsabilities.
 *
 * @author 
 * @since 
 * @version 
 */
@SuppressWarnings("serial")
public class ApiServlet1 extends HttpServlet
{
    /**
       * Comments about this field
       */
    public static int MAX_PAGE_SIZE = 1000;
    /**
       * Comments about this field
       */
    public static int DEFAULT_PAGE_SIZE = 20;

    
    /**
       * Comments about this field
       */
    public final static Pattern documentPattern = Pattern.compile("(?:/api)?(?:/1.0)?/(json|xml|jsonp|html-print|lrs-print|html|pdf)/(bill|calendar|meeting|transcript)/(.*)$", Pattern.CASE_INSENSITIVE);
    /**
       * Comments about this field
       */
    public final static Pattern searchPattern = Pattern.compile("(?:/api)?(?:/1.0)?/(csv|atom|rss|json|xml|jsonp)/(search|votes|bills|meetings|actions|calendars|transcripts|sponsor)(?:/(.*?[a-z].*?))?(?:/([0-9]+))?(?:/([0-9]+))?/?$", Pattern.CASE_INSENSITIVE);

/** Comments about this class */    
    private String editPath (HttpServletRequest request){
        
        String path = request.getServletPath()+(request.getPathInfo() != null ? request.getPathInfo() : "");
        return path;
        }
    /** Comments about this class */
    private String editTerm(String type, String term, String uriTerm){
        
        if (type.equals("sponsor")) {
            term = "sponsor:"+uriTerm+" AND otype:bill";
            String filter = RequestUtils.getSearchString(request, "");
            if (!filter.isEmpty()) {
                term += " AND "+filter;
            }
        }
        else {
            term = "otype:"+type.substring(0, type.length()-1)+(term.isEmpty() ? "" : " AND "+term);
        }
        return term;
    }
    /** Comments about this class */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int pageIdx = 1;
        int pageSize = DEFAULT_PAGE_SIZE;
        boolean sortOrder = false;
        String uri = request.getRequestURI();
        String path = editPath(request);
        String sort = request.getParameter("sort");
        String pageIdxParam = request.getParameter("pageIdx");
        String pageSizeParam = request.getParameter("pageSize");
        String sortOrderParam = request.getParameter("sortOrder");
        Logger logger = Logger.getLogger(ApiServlet1.class);

        try {
            
            pageIdx = parsePage(pageIdxParam, pageIdx, pageSize);
            pageSize = parsePage(pageSizeParam, pageIdx, pageSize);
           
            sortOrder = setSortOrder(sortOrderParam);

            Matcher searchMatcher = searchPattern.matcher(path);
            Matcher documentMatcher = documentPattern.matcher(path);
            if (searchMatcher.find()) {
                String format = searchMatcher.group(1);
                String type = searchMatcher.group(2);
                String uriTerm = searchMatcher.group(3);
                String pagePart = searchMatcher.group(4);
                String sizePart = searchMatcher.group(5);
                String term = RequestUtils.getSearchString(request, uriTerm);

                pageIdx = parsePagePart(pagePart, pageIdx, pageSize);
                pageIdx = parsePagePart(sizePart, pageIdx, pageSize);
                

                
                    term= editTerm(type, term, uriTerm);
          
                doSearch(request, response, format, type, term, pageIdx, pageSize, sort, sortOrder);
            }
            else if (documentMatcher.find()) {
                String format = documentMatcher.group(1);
                String otype = documentMatcher.group(2);
                String oid = documentMatcher.group(3);
                doSingleView(request, response, format, otype, oid);
            }
            else {
                throw new ApiRequestException("Invalid request: "+uri);
            }
        }
        catch (ApiRequestException e) {
            logger.error(e.getMessage(), e);
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
/** Comments about this class */
    private String editTerm(String term) throws ApiRequestException{
      
      if (!type.equals("search")) {
        // If we aren't requesting a specific document or time period, only show current documents
        if(!term.contains("year:") && !term.contains("when:") && !term.contains("oid:")) {
            term += " AND year:"+SessionYear.getSessionYear();
        }

        // Only show inactive documents when they search by oid, but don't repeat yourself
        if (!term.contains("oid:") && !term.contains("active:")) {
            term += " AND active:true";
        }
        }
        else if (term.isEmpty()) {
                    throw new ApiRequestException("A search term is required.");
                }
        return term;
    }
    /** Comments about this class */
    private HttpServletResponse jsonpFormat(String callback, HttpServletResponse response, SenateResponse sr) throws ApiRequestException{
        
        if (callback != null && !callback.equals("")) {
            PrintWriter out = response.getWriter();
            response.setContentType("application/javascript");
            out.write(callback+"("+new Api1JsonConverter().toString(sr)+");");
        }
        else {
            throw new ApiRequestException("callback parameter required for jsonp queries.");
        }
        
        return response;
        
    }
    /** Comments about this class */
    private HttpServletResponse jsonpFormat(String callback, HttpServletResponse response, BaseObject object) throws ApiRequestException{
        
        
        if (callback != null && callback.eqauls("")) {
            PrintWriter out = response.getWriter();
            response.setContentType("application/javascript");
            out.write(callback+"("+new Api1JsonConverter().toString(object)+");");
        }
        else {
            throw new ApiRequestException("callback parameter required for jsonp queries.");
        }
        
        return response;
        
    }
    /** Comments about this class */
    private void doSearch(HttpServletRequest request, HttpServletResponse response, String format,  String term, int pageNumber, int pageSize, String sort, boolean sortOrder) throws ApiRequestException, IOException
    {
        // Verify that all bills in the query are in the proper format
        term = Bill.formatBillNo(term);

        term = editTerm(term);
        Logger logger = Logger.getLogger(ApiServlet1.class);
        

        try {
            int start = (pageNumber-1) * pageSize;
            SenateResponse sr = Application.getLucene().search(term, start, pageSize, sort, sortOrder);
            ApiHelper.buildSearchResultList(sr);

            if (format.equals("json")) {
                response.setContentType("application/json");
                new Api1JsonConverter().write(sr, response.getOutputStream());
            }
            else if (format.equals("jsonp")) {
                String callback = request.getParameter("callback");
                
                response = jsonpFormat(callback, response, sr);
            }
            else if (format.equals("xml")) {
                response.setContentType("application/xml");
                new Api1XmlConverter().write(sr, response.getOutputStream());
            }
            else if (format.equals("rss")) {
                request.setAttribute("term", term);
                request.setAttribute("results", sr);
                request.setAttribute("pageSize", pageSize);
                request.setAttribute("pageIdx", pageNumber);
                response.setContentType("application/rss+xml");
                request.getSession().getServletContext().getRequestDispatcher("/views/search-rss.jsp").forward(request, response);
            }
            else if (format.equals("atom")) {
                request.setAttribute("term", term);
                request.setAttribute("results", sr);
                request.setAttribute("pageSize", pageSize);
                request.setAttribute("pageIdx", pageNumber);
                response.setContentType("application/atom+xml");
                request.getSession().getServletContext().getRequestDispatcher("/views/search-atom.jsp").forward(request, response);
            }
            else if (format.equals("csv")) {
                request.setAttribute("term", term);
                request.setAttribute("results", sr);
                request.setAttribute("pageSize", pageSize);
                request.setAttribute("pageIdx", pageNumber);
                response.setContentType("text/plain");
                request.getSession().getServletContext().getRequestDispatcher("/views/search-csv.jsp").forward(request, response);
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ApiRequestException("internal server error.");
        }
    }
/** Comments about this class */
    private void doSingleView(HttpServletRequest request, HttpServletResponse response, String format, String type, String id) throws ApiRequestException, IOException, ServletException
    {
        BaseObject object = (BaseObject)Application.getLucene().getSenateObject(id, type);
        Logger logger = Logger.getLogger(ApiServlet1.class);

        if(object == null) {
            throw new ApiRequestException(TextFormatter.append("couldn't find id: ", id, " of type: ", type));
        }
        else {
            if (format.equals("lrs-print")) {
                request.setAttribute("bill", object);
                request.getSession().getServletContext().getRequestDispatcher("/views/bill-lrs-print.jsp").forward(request, response);
            }
            else if (format.equals("pdf")) {
                response.setContentType("application/pdf");
                try {
                    BillTextPDFConverter.write(object, response.getOutputStream());
                } catch (COSVisitorException e) {
                    logger.error(e.getMessage(), e);
                   System.out.println("Something was wrong");
                }
            }
            else if(format.equals("html") || format.equals("html-print")) {
                // TODO: Send a 301 response instead.
                response.sendRedirect(request.getContextPath()+"/"+type+"/"+id );
            }
            else{
                structuredData(format, response, object, request);
            }
        }
        
        
    }
    /** Comments about this class */
    private void structuredData(String format, HttpServletRequest response, BaseObject object, HttpServletRequest request){
            
            if (format.equals("json")) {
                response.setContentType("application/json");
                new Api1JsonConverter().write(object, response.getOutputStream());
            }
            else if (format.equals("jsonp")) {
                String callback = request.getParameter("callback");
                response = jsonpFormat(callback, response, object);
            }
            else if (format.equals("xml")) {
                response.setContentType("application/xml");
                new Api1XmlConverter().write(object, response.getOutputStream());
            }
        }
}
