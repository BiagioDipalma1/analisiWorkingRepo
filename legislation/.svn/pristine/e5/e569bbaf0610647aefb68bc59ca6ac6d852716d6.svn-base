package gov.nysenate.openleg.lucene;

import java.io.Reader;

import org.apache.lucene.analysis.util.CharTokenizer;
import org.apache.lucene.util.Version;
// Richiede commento

/**
 * PJDCC - Summary for class responsabilities.
 *
 * @author 
 * @since 
 * @version 
 */
public class OpenLegislationTokenizer extends CharTokenizer
{
    public OpenLegislationTokenizer(Version matchVersion, Reader in) {
        super(matchVersion, in);
    }
/** Comments about this class */
    public OpenLegislationTokenizer(Version matchVersion, AttributeFactory factory, Reader in) {
        super(matchVersion, factory, in);
    }
/** Comments about this class */
    @Override
    protected boolean isTokenChar(int c)
    {
        return !Character.isWhitespace(c)
               && c != ','
               && c != '.'
               && c != '"'
               && c != '\''
               && c != ';'
               && c != '`';
    }

}
