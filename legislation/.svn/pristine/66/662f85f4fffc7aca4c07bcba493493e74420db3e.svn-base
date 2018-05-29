package gov.nysenate.openleg;

import gov.nysenate.openleg.api.*;
import gov.nysenate.openleg.model.*;
import gov.nysenate.openleg.util.*;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.*;

import java.io.*;
import java.util.*;
// Richiede commento

/**
 * PJDCC - Summary for class responsabilities.
 *
 * @author 
 * @since 
 * @version 
 */
public class BillTextPDFConverter
{
    private static Float fontSize = 12f;
    private static Float top = 740f;
    private static Float billMargin = 10f;
    private static Float resolutionMargin = 46f;
/** Comments about this class */
    public static void write(IBaseObject object, OutputStream out) throws IOException, COSVisitorException, AbstractApiRequest.ApiRequestException {
        if (!(object instanceof Bill)) {
            throw new AbstractApiRequest.ApiRequestException("Unable to convert " + object.getOtype() + "s to pdf.");
        }

        Bill bill = (Bill) object;
        PDDocument doc = new PDDocument();
        PDFont font = PDType1Font.COURIER;

        Float margin = billMargin;
        if (bill.isResolution()) {
            margin = resolutionMargin;
        }

        PDPage pg = new PDPage(PDPage.PAGE_SIZE_LETTER);
        PDPageContentStream contentStream = new PDPageContentStream(doc, pg);
        List<List<String>> pages = TextFormatter.pdfPrintablePages(bill);
        for (List<String> page : pages) {
       

            contentStream.beginText();
            contentStream.setFont(font, fontSize);
            contentStream.moveTextPositionByAmount(margin, top);

            for (String line : page) {
                contentStream.drawString(line);
                contentStream.moveTextPositionByAmount(0, -fontSize);
            }

            contentStream.endText();
            contentStream.close();
            doc.addPage(pg);
        }

        doc.save(out);
        doc.close();
    }

}
