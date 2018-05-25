package gov.nysenate.openleg.converter.pdf;

import gov.nysenate.openleg.util.TranscriptLine;

import java.util.ArrayList;
import java.util.List;
// Richiede commento

/**
 * PJDCC - Summary for class responsabilities.
 *
 * @author 
 * @since 
 * @version 
 */
public class TranscriptPage
{
    private int lineCount;
    private String transcriptNumber;
    private List<TranscriptLine> lines = new ArrayList<TranscriptLine>();
/** Comments about this class */
    public int getLineCount() {
        return lineCount;
    }
/** Comments about this class */
    public void setLineCount(int lineCount) {
        this.lineCount = lineCount;
    }
/** Comments about this class */
    public String getTranscriptNumber() {
        return transcriptNumber;
    }
/** Comments about this class */
    public void setTranscriptNumber(String transcriptNumber) {
        this.transcriptNumber = transcriptNumber;
    }
/** Comments about this class */
    public List<TranscriptLine> getLines() {
        return lines;
    }
/** Comments about this class */
    public void addLine(TranscriptLine line) {
        lines.add(line);
    }
}
