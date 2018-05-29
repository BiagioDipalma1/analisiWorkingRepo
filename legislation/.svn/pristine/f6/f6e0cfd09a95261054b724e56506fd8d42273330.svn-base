 package gov.nysenate.openleg.api;

import gov.nysenate.openleg.util.SessionYear;
import gov.nysenate.openleg.util.TextFormatter;

import org.apache.log4j.Logger;

// Richiede commento

/**
 * PJDCC - Summary for class responsabilities.
 *
 * @author 
 * @since 
 * @version 
 */
public class QueryBuilder {
    /**
       * Comments about this class
       */
    public static Logger logger = Logger.getLogger(QueryBuilder.class);
/**
       * Comments about this field
       */
    public static final String OTYPE  = "otype";
    /**
       * Comments about this field
       */
    public static final String OID  = "oid";
    /**
       * Comments about this field
       */
    public static final String YEAR = "year";
    /**
       * Comments about this field
       */
    public static final String WHEN = "when";
    /**
       * Comments about this field
       */
    public static final String ACTIVE = "active";
/**
       * Comments about this field
       */
    public static final String SEPARATOR = ":";
    /**
       * Comments about this field
       */
    public static final String TO = "TO";
    /**
       * Comments about this field
       */
    public static final String AND = "AND";
    /**
       * Comments about this field
       */
    public static final String OR = "OR";
    /**
       * Comments about this field
       */
    public static final String NOT = "NOT";
    /**
       * Comments about this field
       */
    protected StringBuffer query;
    /**
       * Comments about this field
       */
    protected boolean operatorToggle = true;
/** Comments about this class */
    private QueryBuilder() {
        query = new StringBuffer();
    }
/** Comments about this class */
    private QueryBuilder(String key, String value) throws QueryBuilderException {
        this();
        keyValue(key, value);
    }
/** Comments about this class */
    public QueryBuilder(QueryBuilder queryBuilder) {
        this.query = new StringBuffer(queryBuilder.query);
        this.operatorToggle = queryBuilder.operatorToggle;
    }

/** Comments about this class */
    public QueryBuilder otype(String getOtype) throws QueryBuilderException {
        return keyValue(OTYPE, getOtype);
    }
/** Comments about this class */
    public QueryBuilder oid(String getOid) throws QueryBuilderException {
        return keyValue(OID, getOid);
    }
/** Comments about this class */
    public QueryBuilder oid(String getOid, boolean quote) throws QueryBuilderException {
        return keyValue(OID, quote ? TextFormatter.append("\"", getOid, "\"") : getOid);
    }
/** Comments about this class */
    public QueryBuilder active() throws QueryBuilderException {
        return keyValue(ACTIVE,"true");
    }
/** Comments about this class */
    public QueryBuilder inactive() throws QueryBuilderException {
        return keyValue(ACTIVE,"false");
    }
/** Comments about this class */
    public QueryBuilder inSession(int year) throws QueryBuilderException {
        int session = SessionYear.getSessionYear(year);
        condition();
        return append(YEAR, SEPARATOR, "[", Integer.toString(session), " TO ", Integer.toString(session+1), "] ");
    }
/** Comments about this class */
    public QueryBuilder current() throws QueryBuilderException {
        return inSession(SessionYear.getSessionYear());
    }
/** Comments about this class */
    public QueryBuilder relatedBills(String key, String billNo) throws QueryBuilderException {
        String year = null;
        String billNoRaw = null;

        if(billNo.contains("-")) {
            String tuple[] = billNo.split("\\-");
            billNo = tuple[0];
            year = tuple[1];
        }
        else {
            year = Integer.toString(SessionYear.getSessionYear());
        }

        billNoRaw = billNo.replaceAll("[a-zA-Z]$", "");

        return keyValue(key,TextFormatter.append(
                "((",billNoRaw,"-",year," ",OR," ",range(
                        TextFormatter.append(billNoRaw,"A-",year),
                        TextFormatter.append(billNoRaw,"Z-",year)),
                        ") ",AND," ",billNoRaw,"*-",year,")"));
    }
/** Comments about this class */
    public QueryBuilder range(String key, String from, String to) throws QueryBuilderException {
        return keyValue(key, range(from, to));
    }
/** Comments about this class */
    private String range(String from, String to) throws QueryBuilderException {
        return TextFormatter.append("[",from," ",TO," ",to,"]");
    }
/** Comments about this class */
    private QueryBuilder keyValue(String key, String value) throws QueryBuilderException {
        condition();
        return append(key,SEPARATOR, value);
    }
/** Comments about this class */
    public QueryBuilder keyValue(String key, String value, String wrapper) throws QueryBuilderException {
        condition();
        return append(key,SEPARATOR, wrapper, value, wrapper);
    }
/** Comments about this class */
    public QueryBuilder keyValue(String key, String value, String before, String after) throws QueryBuilderException {
        condition();
        return append(key,SEPARATOR, before, value, after);
    }
/** Comments about this class */
    public QueryBuilder and() throws QueryBuilderException {
        if(operator())
            return append(" ",AND," ");
        return this;
    }
/** Comments about this class */
    public QueryBuilder or() throws QueryBuilderException {
        if(operator())
            return append(" ",OR," ");
        return this;
    }
/** Comments about this class */
    public QueryBuilder not() throws QueryBuilderException {
        if(operator())
            return append(" ",NOT," ");
        return this;
    }
/** Comments about this class */
    public QueryBuilder andNot() throws QueryBuilderException {
        if(operator())
            return append(" ",AND," ",NOT," ");
        return this;
    }

    /**
     * assumes @param strs consists of a condition
     * 		and does not end with an operator
     * @throws QueryBuilderException
     */
    public QueryBuilder insertAfter(String... strs) throws QueryBuilderException {
        condition();
        return append(strs);
    }
/** Comments about this class */
    public QueryBuilder append(String... strs) {
        for(String str:strs) {
            query.append(str);
        }
        return this;
    }
/** Comments about this class */
    public QueryBuilder insertBefore(String... strs) {
        StringBuilder temp = new StringBuilder();
        for(String str:strs) {
            temp.append(str);
        }

        if(query.length() == 0) operatorToggle = false;

        query.insert(0, temp);
        return this;
    }
/** Comments about this class */
    private void condition() throws QueryBuilderException {
        if(!operatorToggle)
            throw new QueryBuilderException("putting two conditions next to each other: " + query());

        operatorToggle = false;
    }
/** Comments about this class */
    private boolean operator() throws QueryBuilderException {
        if(query.length() == 0)
            return false;

        if(operatorToggle)
            throw new QueryBuilderException("putting two operators next to each other: " + query());

        operatorToggle = true;

        return true;
    }
/** Comments about this class */
    public void reset() {
        query.setLength(0);
        operatorToggle = true;
    }
/** Comments about this class */
    public String query() {
        return query.toString();
    }
/** Comments about this class */
    @Override
    public String toString() {
        return query();
    }
/** Comments about this class */
    public static QueryBuilder build() {
        return new QueryBuilder();
    }
/** Comments about this class */
    public static QueryBuilder build(String key, String value) throws QueryBuilderException {
        return new QueryBuilder(key, value);
    }
/** Comments about this class */
    @SuppressWarnings("serial")
    public static class QueryBuilderException extends Exception {
        public QueryBuilderException() {
            super();
        }

        public QueryBuilderException(String message) {
            super(message);
        }
    }
}
