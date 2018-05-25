package gov.nysenate.openleg.lucene;

import java.io.Reader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.core.StopAnalyzer;
import org.apache.lucene.analysis.core.StopFilter;
import org.apache.lucene.util.Version;
// Richiede commento

/**
 * PJDCC - Summary for class responsabilities.
 *
 * @author 
 * @since 
 * @version 
 */
public class OpenLegislationAnalyzer extends Analyzer
{
    private final Version matchVersion;
/** Comments about this class */

    public OpenLegislationAnalyzer(Version matchVersion)
    {
        this.matchVersion = matchVersion;
    }
/** Comments about this class */

    @Override
    protected TokenStreamComponents createComponents(String fieldName, Reader reader)
    {
        Tokenizer source = new OpenLegislationTokenizer(matchVersion, reader);
        TokenFilter filter = new StopFilter(matchVersion, source, StopAnalyzer.ENGLISH_STOP_WORDS_SET);
        filter = new LowerCaseFilter(matchVersion, filter);
        return new TokenStreamComponents(source, filter);
    }
}
