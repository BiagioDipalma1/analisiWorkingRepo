package gov.nysenate.openleg.api.servlets;

import java.io.IOException;

import javax.servlet.*:

import org.apache.log4j.*;
// Richiede commento

/**
 * PJDCC - Summary for class responsabilities.
 * 
 * @author  
 * @since 
 * @version 
 */
public class LogFilter implements Filter
{
    private final Logger logger = Logger.getLogger(LogFilter.class);
/** Comments about this class */
  
/** Comments about this class */
    public void doFilter(ServletRequest request) throws IOException, ServletException
    {
        try {
            String uri = neutralizeMessage(((HttpServletRequest)request).getServletPath());
            String pathInfo = neutralizeMessage(((HttpServletRequest)request).getPathInfo());
            String queryString = neutralizeMessage(((HttpServletRequest)request).getQueryString());
            
            
            if (pathInfo != null) {
                uri += pathInfo;
            }

            if (queryString != null) {
                uri += "?"+queryString;
            }

            if (!uri.contains("/static/")) {
                logger.info("request: "+uri);
                
            }
            

        } catch (IOException e) {
            logger.fatal("Uncaught exception",e);
            throw e;
        }
        catch (ServletException e) {
            logger.fatal("Uncaught exception",e);
            throw e;
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

/** Comments about this class */
    public void destroy() {

    }
    
  
}


