package gov.nysenate.openleg;

import gov.nysenate.openleg.model.Change;
import gov.nysenate.openleg.processors.AgendaProcessor;
import gov.nysenate.openleg.processors.BillProcessor;
import gov.nysenate.openleg.processors.CalendarProcessor;
import gov.nysenate.openleg.processors.TranscriptProcessor;
import gov.nysenate.openleg.util.ChangeLogger;
import gov.nysenate.openleg.util.Storage;
import gov.nysenate.openleg.util.Timer;
import gov.nysenate.util.Config;

import java.io.BufferedReader;
import java.io.File; 
import java.io.IOException; 
import java.io.StringReader;
import java.util.Arrays; 
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.JAXBException;
import javax.xml.bind.UnmarshalException;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
// Richiede commento

/**
 * PJDCC - Summary for class responsabilities.
 *
 * @author 
 * @since 
 * @version 
 */
public class Environment
{
    private final Logger logger = Logger.getLogger(Environment.class);

    private final File directory;
    private final File stagingDirectory;
    private final File workingDirectory;
    private final File storageDirectory;
    private final File archiveDirectory;
/** Comments about this class */
    public Environment(String directoryPath)
    {
        this(new File(directoryPath));
    }
/** Comments about this class */
    public Environment(Config config, String prefix)
    {
        this.directory = new File(config.getValue(prefix+".directory"));
        this.stagingDirectory = new File(config.getValue(prefix+".data"));
        this.workingDirectory = new File(config.getValue(prefix+".work"));
        this.storageDirectory = new File(config.getValue(prefix+".storage"));
        this.archiveDirectory = new File(config.getValue(prefix+".archive"));
    }
/** Comments about this class */
    public Environment(File directory)
    {
        this.directory = directory;
        this.stagingDirectory = new File(directory,"data");
        this.workingDirectory = new File(directory,"work");
        this.storageDirectory = new File(directory,"json");
        this.archiveDirectory = new File(directory,"archive");
    }
/** Comments about this class */
    public File getDirectory()
    {
        return directory;
    }
/** Comments about this class */
    public File getStagingDirectory()
    {
        return stagingDirectory;
    }
/** Comments about this class */
    public File getWorkingDirectory()
    {
        return workingDirectory;
    }
/** Comments about this class */
    public File getStorageDirectory()
    {
        return storageDirectory;
    }
/** Comments about this class */
    public File getArchiveDirectory()
    {
        return archiveDirectory;
    }
/** Comments about this class */
    public void create() throws IOException
    {
        FileUtils.forceMkdir(directory);
        FileUtils.forceMkdir(stagingDirectory);
        FileUtils.forceMkdir(workingDirectory);
        FileUtils.forceMkdir(storageDirectory);
        FileUtils.forceMkdir(archiveDirectory);
    }
/** Comments about this class */
    public void delete() throws IOException
    {
        FileUtils.deleteQuietly(directory);
    }
/** Comments about this class */
    public void reset() throws IOException
    {
        delete();
        create();
    }

    ///////////////////////////////////////////////
    // Staging Stuff!
/** Comments about this class */
    public void stageFiles(File...files) throws IOException
    {
        stageFiles(Arrays.asList(files));
    }
/** Comments about this class */
    public void stageFiles(Collection<File> files) throws IOException
    {
        stageFiles(files.iterator());
    }
/** Comments about this class */
    public void stageFiles(Iterator<File> fileIterator) throws IOException
    {
        while (fileIterator.hasNext()) {
            File file = fileIterator.next();
            logger.debug("Staging "+file);
            FileUtils.copyFileToDirectory(file, stagingDirectory);
        }
    }

    ///////////////////////////////////////////////
    // Collating Stuff

    //this fields will be not modified in the code, they have read-only use
    private static final Pattern cdataPattern = Pattern.compile("<\\!\\[CDATA\\[(.*?)\\]\\]>");
    private static final Pattern senannotatedOpenPattern = Pattern.compile("<senannotated.+");
    
    private static final Pattern senagendaOpenPattern = Pattern.compile("<senagenda.+");
    private static final Pattern senagendaEndPattern = Pattern.compile("</senagenda.+");
    private static final Pattern sencalendarOpenPattern = Pattern.compile("<sencalendar.+");
    private static final Pattern sencalendarEndPattern = Pattern.compile("</sencalendar.+");
/** Comments about this class */
    public void collateFiles(File...files) throws IOException
    {
        collateFiles(Arrays.asList(files));
    }
/** Comments about this class */
    public void collateFiles(Collection<File> files) throws IOException
    {
        collateFiles(files.iterator());
    }
/** Comments about this class */
    public void collateFiles(Iterator<File> fileIterator) throws IOException
    {
        File workDirectory = getWorkingDirectory();
        File billDirectory = new File(workDirectory, "bills");
        File agendaDirectory = new File(workDirectory, "agendas");
        File calendarDirectory = new File(workDirectory, "calendars");
        File annotationDirectory = new File(workDirectory, "annotations");
        File transcriptDirectory = new File(workDirectory, "transcripts");
        FileUtils.forceMkdir(billDirectory);
        FileUtils.forceMkdir(agendaDirectory);
        FileUtils.forceMkdir(calendarDirectory);
        FileUtils.forceMkdir(annotationDirectory);
        FileUtils.forceMkdir(transcriptDirectory);
        Pattern senannotatedEndPattern = Pattern.compile("</senannotated.+");
        
        File file = null;
        
        // br viene inizializzato con workDirectory solo per non inizializzarlo nel ciclo. Tale modifica non produrrà effetti.
        BufferedReader br = new BufferedReader(new StringReader(FileUtils.readFileToString(workDirectory, "UTF-8")));
        File calendarFile = null;
        File agendaFile = null;
        File annotationFile = null;
        while(fileIterator.hasNext()) {
            file = fileIterator.next();
            logger.info("Processing: "+file);

            int partsCounter = 1;

            if (file.getName().equalsIgnoreCase("CMS.TEXT")) {
                System.out.println("niente");
            }
            else if (!file.getName().startsWith("SOBI")) {
                logger.info("Moving transcript: "+file);
                FileUtils.moveFileToDirectory(file, transcriptDirectory, false);

            }
            else {
                br = new BufferedReader(new StringReader(FileUtils.readFileToString(file, "UTF-8")));

                String line;
                String d=br.readLine();
          
                while((line = d) != null) {
                    if(sencalendarOpenPattern.matcher(line).find()) {
                        calendarFile = new File(calendarDirectory, file.getName()+"-calendar-"+partsCounter+".xml");
                        extractXmlDocumentToFile(br,line,sencalendarEndPattern, calendarFile);
                        partsCounter++;
                        
                    }
                    else if(senagendaOpenPattern.matcher(line).find()) {
                        agendaFile = new File(agendaDirectory, file.getName()+"-agenda-"+partsCounter+".xml");
                        extractXmlDocumentToFile(br,line,senagendaEndPattern, agendaFile);
                        partsCounter++;
                    }
                    else if(senannotatedOpenPattern.matcher(line).find()) {
                        annotationFile = new File(annotationDirectory, file.getName()+"-annotation-"+partsCounter+".xml");
                        extractXmlDocumentToFile(br,line,senannotatedEndPattern, annotationFile);
                        partsCounter++;
                    }
                }

                br.close();
                logger.info("Moving bill: "+file);
                FileUtils.moveFileToDirectory(file, billDirectory, false);
            }
        }
    }
/** Comments about this class */
    private void extractXmlDocumentToFile(BufferedReader br, String openTag, Pattern closePattern, File destination) throws IOException
    {
        logger.info("Extracting: "+destination);

        StringBuffer dataBuffer = new StringBuffer();
        dataBuffer.append("<?xml version='1.0' encoding='UTF-8'?>&newl;");
        dataBuffer.append("<SENATEDATA>&newl;");
        dataBuffer.append(openTag).append("&newl;");

        String line;
        String d=br.readLine();
        while((line = d) != null) {
            // We shouldn't have to replace the valid UTF-8 character right?
            // sb.append(in.replaceAll("\\xb9","&sect;") + "&newl;");
            dataBuffer.append(line).append("&newl;");
            if(closePattern.matcher(line).find())
                break;
        }
        String data = dataBuffer.append("</SENATEDATA>").toString();

        StringBuffer xmlBuffer = new StringBuffer();
        Matcher cdataMatcher = cdataPattern.matcher(data);
        boolean e=cdataMatcher.find();
        while(e) {
            String cdata = cdataMatcher.group(0);
            // Remove all our &newl; from inside of CDATA blocks as it doesn't belong.
            cdata = cdata.replaceAll("&newl;","").replaceAll("\\\\n", "\n");
            cdataMatcher.appendReplacement(xmlBuffer, Matcher.quoteReplacement(cdata));
        }
        cdataMatcher.appendTail(xmlBuffer);
        String xml = xmlBuffer.toString();

        // TODO: What the hell are these last two doing?
        xml = xml.replaceAll("&newl;", "\n").replaceAll("(?!\n)\\p{Cntrl}","").replaceAll("(?!\\.{2})[ ]{2,}"," ");

        FileUtils.write(destination, xml);
    }


    ///////////////////////////////////////////////////
    // Ingest Stuff
/** Comments about this class */
    public static class FileNameComparator implements Comparator<File>
    {
        public int compare(File a, File b)
        {
            return a.getName().compareTo(b.getName());
        }
    }
/** Comments about this class */
    public HashMap<String, Change> ingestFiles(File...files)
    {
        return ingestFiles(Arrays.asList(files));
    }

        Timer timer = new Timer();
        Storage storage = new Storage(getStorageDirectory());
        BillProcessor billProcessor = new BillProcessor();
        CalendarProcessor calendarProcessor = new CalendarProcessor();
        AgendaProcessor agendaProcessor = new AgendaProcessor();
        TranscriptProcessor transcriptProcessor = new TranscriptProcessor();
   /** Comments about this class */     
    public void findType(String type, File file){
        
        if (type.equals("bills")) {
            billProcessor.process(file, storage);
        }
        else if (type.equals("calendars")) {
            calendarProcessor.process(file, storage);
        }
        else if (type.equals("agendas")) {
            agendaProcessor.process(file, storage);
        }
        else if (type.equals("annotations")) {
            System.out.println("niente");
        }
        else if (type.equals("transcripts")) {
            transcriptProcessor.process(file, storage);
        }

        // To avoid memory issues, occasionally flush changes to file-system and truncate memory
        if (storage.memory.size() > 4000) {
            storage.flush();
            storage.clear();
        }
    }
    /** Comments about this class */
    public HashMap<String, Change> ingestFiles(Collection<File> files)
    {
       

        Collections.sort((List<File>)files, new FileNameComparator());

        // Process each file individually, flushing changes to storage as necessary
        // Each file processor should produce a change log indicating what happened
        timer.start();
        try {
        for(File file : files) {
            
                logger.debug("Ingesting: "+file);
                String type = file.getParentFile().getName();
                
                findType(type, file);

            
            
        }
    } catch (IOException e) {
                logger.error("Issue with "+file.getName(), e);
            }
            catch (UnmarshalException e) {
                logger.error("Issue with "+file.getName(), e);
            }
            catch (JAXBException e) {
                logger.error("Unable to parse xml "+file.getName(), e);
            }
        
        storage.flush();
        logger.info(timer.stop()+" seconds to injest "+files.size()+" files.");
        return ChangeLogger.getChangeLog();
    }
}
