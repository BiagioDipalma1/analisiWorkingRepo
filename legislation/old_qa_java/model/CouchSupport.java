package gov.nysenate.openleg.qa.model;

import gov.nysenate.openleg.qa.ProblemBillRepository;

import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
// Richiede commento

/** 
 * PJDCC - Summary for class responsabilities.
 *
 * @author 
 * @since 
 * @version 
 */
public class CouchSupport {
    /**
       * Comments about this field
       */
    public static final String DATABASE_NAME = "qa";
    /**
       * Comments about this field
       */
    private static final boolean CREATE_IF_NOT_EXIST = true;

    /**
       * Comments about this field
       */
    protected CouchInstance instance = null;
    /**
       * Comments about this field
       */
    public final ProblemBillRepository pbr;
/** Comments about this class */
    public CouchSupport() {
        this(DATABASE_NAME, CREATE_IF_NOT_EXIST);
    }
/** Comments about this class */
    public CouchSupport(String databaseName) {
        this(databaseName, CREATE_IF_NOT_EXIST);
    }
/** Comments about this class */
    public CouchSupport(String databaseName, boolean createIfNotExist) {
        this(databaseName, createIfNotExist, new StdHttpClient.Builder().build());
    }
/** Comments about this class */
    public CouchSupport(String databaseName, boolean createIfNotExist, HttpClient httpClient) {
        System.setProperty("org.ektorp.support.AutoUpdateViewOnChange", "true");
        instance = CouchInstance.getInstance(databaseName, createIfNotExist, httpClient);
        pbr = new ProblemBillRepository(instance.getConnector());
    }
}
