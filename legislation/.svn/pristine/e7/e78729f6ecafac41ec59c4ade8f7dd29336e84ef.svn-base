package gov.nysenate.openleg.model;

import java.util.ArrayList;
import java.util.HashMap;

// Richiede commento

/**
 * PJDCC - Summary for class responsabilities.
 *
 * @author 
 * @since 
 * @version 
 */
public class SenateResponse<t, Result> {

  
    HashMap<String, t> metadata;

    ArrayList<Result> results;

    /** Comments about this class */
    public SenateResponse() {
        this.metadata = new HashMap<String,t>();
        this.results = new ArrayList<Result>();
    }
/** Comments about this class */
    public void setResults(ArrayList<Result> results) {
        this.results = results;
    }
/** Comments about this class */
    public ArrayList<Result> getResults() {
        return this.results;
    }
/** Comments about this class */
    public void addResult(Result result) {
        this.results.add(result);
    }
/** Comments about this class */
    public HashMap<String, t> getMetadata() {
        return this.metadata;
    }
/** Comments about this class */
    public void setMetadata(HashMap<String, t> metadata) {
        this.metadata = metadata;
    }
/** Comments about this class */
    public void addMetadataByKey(String key, t value) {
        this.metadata.put(key, value);
    }
/** Comments about this class */
    public t getMetadataByKey(String key) {
        return this.metadata.get(key);
    }
}
