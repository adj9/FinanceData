/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Alessandro
 */
public class KeysTest {
    public static String geyKey(JSONObject obj) {
        return obj.toString().split("\"")[1];
    }
    
    public static void main(String[] args) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("K1", "VK1");
            obj.put("K2", "VK2");
            obj.put("K3", "VK3");
        } catch (JSONException ex) {
            Logger.getLogger(KeysTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        System.out.println("JSON: " + obj.toString());
        
        String[] splitMoreObj = obj.toString().split(",");
        
        for (int i = 0; i < splitMoreObj.length; ++i) {             
            System.out.println(splitMoreObj[i]);
        }
        try {
            System.out.println("Key first Obj: " + geyKey(new JSONObject(splitMoreObj[0] + '}')));
        } catch (JSONException ex) {
            Logger.getLogger(KeysTest.class.getName()).log(Level.SEVERE, null, ex);
        }                
        
        JSONArray array = new JSONArray();
        array.put(obj);
        System.out.println(array.toString());
        JSONObject obj1 = new JSONObject();
        try {
            obj1.put("new1", "vnew1");
            obj1.put("new2", "vnew2");
        } catch (JSONException ex) {
            Logger.getLogger(KeysTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        array.put(obj1);
        System.out.println(array.toString());
        
        JSONObject total = new JSONObject();
        try {
            total.put("aa", array);
        } catch (JSONException ex) {
            Logger.getLogger(KeysTest.class.getName()).log(Level.SEVERE, null, ex);
        }
                
        System.out.println(total.toString());        
        JSONArray a = null;
        try {
            a = total.getJSONArray("aa");
        } catch (JSONException ex) {
            Logger.getLogger(KeysTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Is-> " + a.toString());        
    }
}
