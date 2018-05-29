 package gov.nysenate.openleg.converter;


import gov.nysenate.openleg.model.*;
 import gov.nysenate.openleg.api.servlets.converter.NotConvertException;


import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.*;
import java.util.*;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.google.gson.JsonArray; 
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;


import java.time.*;
// Richiede commento

/**
 * PJDCC - Summary for class responsabilities.
 *
 * @author 
 * @since 
 * @version 
 */
@SuppressWarnings("unchecked")
public class LuceneJsonConverter
{
    /**
       * Comments about this class
       */
    public static final Logger logger = Logger.getLogger(LuceneJsonConverter.class);
    protected static HashMap<String,JsonObject> cachedSimpleBills = new HashMap<String,JsonObject>();
    LocalDateTime date2= LocalDateTime.now();
/** Comments about this class */
    private JsonObject editNode(JsonObject node, BaseObject o)throws NotConvertException{
        
        if(o instanceof Bill) {
            cachedSimpleBills.remove(((Bill)o).getBillId());
            node = converter(o, null);
        }
        else if(o instanceof Meeting) {
            node = converter(o, null);
        }
        else if(o instanceof Transcript) {
            node = converter(o, transcript_exclude());
        }
        else if(o instanceof Calendar) {
            node = converter(o, null);
        }
        else if(o instanceof Action) {
            node = converter(o, null);
        }
        else if(o instanceof Vote) {
            node = converter(o, null);
        }
        else {
            throw new NotConvertException("Cannot convert BaseObject of type: "+o.getOtype());
        }
        
        return node;        
    }
    
    /**
     * accepts and sends applicable objects to be converted to json via converter(object,list)
     * this is necessary to give each object it's "exclude" list
     */
    public static String toString(BaseObject o) {
        
        if(o != null) {
            JsonObject root = new JsonObject();
            try {
                JsonObject node = null;
                node = editNode (node, o);
                root.add(o.getOtype(), node);
                return root.toString();
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
                return null;
            }
        }
        else {
            return null;
        }
    }


    /**
     * accepts an object and a list of fields that should be excluded from json output.
     * any field in the object aside from those noted as excluded will be processed,
     * in particular this method will explicitly handle generic arguments and branches
     * to other methods for more complex data types (dependent on type)
     */
    private static JsonObject converter(Object o, List<String> exclude) throws Exception
    {
        List<Field> fields = new ArrayList<Field>();
        Class<?> cls = o.getClass();
        while (cls != null) {
            fields.addAll(Arrays.asList(cls.getDeclaredFields()));
            cls = cls.getSuperclass();
        }

        JsonObject root = new JsonObject();
        if(exclude == null) {
            exclude = new ArrayList<String>();
        }

        for(Field f:fields) {
            if(controlField(f)) {
                String name = StringUtils.capitalize(f.getName());
                String type = f.getType().getSimpleName();
                Field value = String.class.getDeclaredField("value");
                if(!exclude.contains(f.getName())) {
                value.setAccessible(false);
                Object obj = f.get(o);
                f.setAccessible(false);

                if(obj == null) {
                    root.add(f.getName(), null);
                }
                else {
                    if(isPrimitive(obj)) {
                        root.addProperty(f.getName(), obj.toString());
                    }
                    else{
                        root = workOnRoot(root,obj,type,f,o,name);
                    }
                }
                }
            }
        }
         return root;
    }
    /** Comments about this class */
    private static boolean controlField(Field f){
        boolean flag= false;
        if(!f.getName().contains("jdo") && !Modifier.isStatic(f.getModifiers())){
            flag = true;
        }
        
        return flag;
    }
    /** Comments about this class */
    private void editCache(Bill bill, Object obj)throws Exception{
       if(!cachedSimpleBills.containsKey(bill.getBillId())) {
            cachedSimpleBills.put(bill.getBillId(), converter(obj,internal_bill_exclude()));
        } 
    }
    /** Comments about this class */
    private static JsonObject workOnRoot(JsonObject root, Object obj, String type, Field f, Object o, String name){
        
        try{
        if(type.equals("Bill")) {
            Bill bill = (Bill)obj;

            editCache(bill, obj);

            root.add(f.getName(), cachedSimpleBills.get(bill.getBillId()));
        }
        else if(type.equals("Date")) {
            Date d = (Date)obj;
            root.addProperty(f.getName(), (d != null) ? d.getTime() + "":"");
        }
        else if(isCollection(type)) {
            JsonArray jarray = null;
            jarray = workOnCollections(o, obj, jarray);
            root.add(f.getName(), jarray);
        }
        else if(type.equals("Person")) {
            root.add(f.getName(),converter(obj, null));
        }
        else if (type.equals("Agenda")) {
            root.add(f.getName(),converter(obj,agenda_exclude()));
        }
        else {
            throw new UnknownTypeException ("UNKNOWN: " + type + "(type):" + name + " (name) IN CLASS " + o.getClass().getSimpleName());
        }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        
        return root;
    }
    /** Comments about this class */
    private static boolean isCollection(String type){
        boolean flag = false;
        if(type.equals("List") || type.equals("HashSet")){
            flag = true;
        }
        return flag;
    }
    /** Comments about this class */
    private static JsonArray workOnCollections(Object o, Object obj, JsonArray jarray){
        Collection<?> collection = ((Collection<?>)obj);
        Iterator<?> iter = collection.iterator();
        Collection <String> coll = null;
        String [] array = new String[collection.size()];
        collection.toArray(array);
        for(int i =0; i < array.length; i++){
            coll.add(array[i]);
        }
        if (iter.hasNext()) {
            if (iter.next() instanceof String) {
                for(String str : coll) {
                    JsonPrimitive jp = new JsonPrimitive(str);
                    jarray.add(jp);
                }
            }
            else {
                jarray = (JsonArray)LuceneJsonConverter.class.getDeclaredMethod("list" + o.getClass().getSimpleName(),Collection.class).invoke(null,collection);
            }
        }
        return jarray;
    }
    
/** Comments about this class */
    private static boolean isPrimitive(Object obj)
    {
        return obj != null && (
                obj.getClass().isPrimitive()
                || obj instanceof Boolean
                || obj instanceof Byte
                || obj instanceof Character
                || obj instanceof Double
                || obj instanceof Float
                || obj instanceof Integer
                || obj instanceof Long
                || obj instanceof Short
                || obj instanceof String);
    }




/** Comments about this class */
    private static List<String> agenda_exclude()
    {
        return Arrays.asList("addendums");
    }


/** Comments about this class */
    private static List<String> internal_bill_exclude()
    {
        return Arrays.asList("actions", "fulltext", "memo", "sortIndex", "votes");
    }
/** Comments about this class */
    private static List<String> transcript_exclude()
    {
        return Arrays.asList("relatedBills", "transcriptTextProcessed");
    }
}
