/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 * 
 * URL: http://www.mkyong.com/java/json-simple-example-read-and-write-json/
 */
package test;

import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Alessandro
 */
public class JSONTest {
    private static void writeFile(JSONObject obj, String fileName) {
        FileWriter write = null;
        try {
            write = new FileWriter(System.getProperty("user.dir") + "/" + fileName);
            
            write.write(obj.toString());
            write.flush();
            write.close();
        } catch (IOException ex) {
            Logger.getLogger(JSONTest.class.getName()).log(Level.SEVERE, null, ex);
        }      
    }
    
    public static void main(String[] args) {
//      Caricamento di un'oggetto JSONObject        
        JSONObject obj = new JSONObject();
        
        try {
            obj.put("K1", "VK1");
            obj.put("K2", "VK2");
            obj.put("K3", "VK3");
            obj.put("K4", "VK4");
        } catch (JSONException ex) {
            Logger.getLogger(JSONTest.class.getName()).log(Level.SEVERE, null, ex);
        }                
//      ---
        
//      Stampa        
        System.out.print(obj);
//      ---     

//      Scrittura su file        
        writeFile(obj, "test.json");                   
//      ---        
        
//      Copiadi un'oggetto JSONObject          
        JSONObject objCopy = null;
        try {            
            objCopy = new JSONObject(obj.toString());
        } catch (JSONException ex) {
            Logger.getLogger(JSONTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
//      Stampa dei valori delle chiavi        
        String s1 = null;
        String s2 = null;
        try {
            s1 = (String) objCopy.get("K1");
            s2 = (String) objCopy.get("K2");
        } catch (JSONException ex) {
            Logger.getLogger(JSONTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("\nK1 -> " + s1 + ", K2 -> " + s2 + ", lng = " + objCopy.length());
//      ---
        
//        Inserimento di (chiave_5, valore_5)
        String ss = null;
        try {
            objCopy.append("K5", "VK5");
        } catch (JSONException ex) {
            Logger.getLogger(JSONTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("objCopy - " + objCopy);
//      ---        
        
        writeFile(objCopy, "test.json");
        
//      Inserimento di un nuovo dato in una chiave creando un JSONArray associato alla chiave
        try {
            objCopy.accumulate("K1", "K11");
        } catch (JSONException ex) {
            Logger.getLogger(JSONTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("new objCopy - " + objCopy.toString());
//      ---
        
//      Stampa dei valori di JSONArray        
        JSONArray objArray = null;
        try {
            objArray = objCopy.getJSONArray("K1");
        } catch (JSONException ex) {
            Logger.getLogger(JSONTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            s1 = objCopy.getString("K1");
            s2 = (String) objCopy.get("K2");
        } catch (JSONException ex) {
            Logger.getLogger(JSONTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("\nK1 -> " + s1 + ", K2 -> " + s2 + ", lng = " + objCopy.length());                        
//      ---        
        System.out.println("Value K1: " + objArray.toString());
        try {
            s1 = objArray.getString(0);
            s2 = objArray.getString(1);
        } catch (JSONException ex) {
            Logger.getLogger(JSONTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("JSONArray: value1: " + s1 + ", value2: " + s2);
        
        objArray.put("K111");
        System.out.println("New value K1: " + objArray.toString());
        
        try {
            objCopy.put("K1", objArray);
        } catch (JSONException ex) {
            Logger.getLogger(JSONTest.class.getName()).log(Level.SEVERE, null, ex);
        }        
        System.out.println("New value K1 on objCopy: " + objCopy.toString());
        
//      Modifica del valore della chiave K2        
        try {
            objCopy.put("K2", "VK22");
        } catch (JSONException ex) {
            Logger.getLogger(JSONTest.class.getName()).log(Level.SEVERE, null, ex);            
        }        
        System.out.println("New objCopy: " + objCopy.toString());
//      ---
        
//      Come trattare JSONArray        
        objArray = null;
        try {
            objArray = new JSONArray(obj);
        } catch (JSONException ex) {
            Logger.getLogger(JSONTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("---");
        s1 = null;
        s2 = null;
        try {
            s1 = objArray.getString(0);
            s2 = objArray.getString(1);
        } catch (JSONException ex) {
            Logger.getLogger(JSONTest.class.getName()).log(Level.SEVERE, null, ex);
        }                
        System.out.println("A1 -> " + s1 + ", A2 -> " + s2);
//      ---        
    }    
}
