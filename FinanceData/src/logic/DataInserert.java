/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JTextField;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import test.JSONTest;

/**
 *
 * @author Alessandro
 */
public class DataInserert extends Setup {    
    private Set<JSONObject> objFinanceData;    
    // private Setup setup;
    
//  Informazioni del database
    public static String NAME_KEY = "name";
    private static String QUOTATION_KEY = "quotation";
    private static String MINIMUN_KEY = "minimum";
    private static String MAXIMUM_KEY = "maximum";
    private static String VOLUMES_KEY = "valumes";
    private static String DATA_KEY = "data";
    private static String DATAS_KEY = "datas";
            
    
    public DataInserert() {
        this.objFinanceData = new HashSet<JSONObject>();        
        // this.setup = new Setup();
        
        // this.loadToJSON();         
    }
    
    private void loadToJSON() {
        BufferedReader buffer = null;
        String path = this.getDatabaseAbsolutePath();
        try {
            buffer = new BufferedReader(new FileReader(path));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DataInserert.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String line = null;
        while (true) {            
            try {
                line = buffer.readLine();
            } catch (IOException ex) {
                Logger.getLogger(DataInserert.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            if (line == null) {
                break;
            } else {
                try {
                    this.objFinanceData.add(new JSONObject(line));
                } catch (JSONException ex) {
                    Logger.getLogger(DataInserert.class.getName()).log(Level.SEVERE, null, ex);
                }                
            }                                    
        }
    }
    
    /**
     * 
     * @param name
     * @param quotation
     * @param minimum
     * @param maximum
     * @param volumes 
     */
    public void addData(JTextField name, JTextField quotation, JTextField minimum, JTextField maximum, JTextField volumes) {
//      Creazione dell'oggetto JSON con i nuovi dati               
        JSONObject newObj = new JSONObject();
        
//      Creazione dell'oggetto Date
        Date date = new Date();    
        
//      Caricamento dei dato nell'oggetto newObj
        try {
            // newObj.put(NAME_KEY, name.getText());
            newObj.put(QUOTATION_KEY, quotation.getText());
            newObj.put(MINIMUN_KEY, minimum.getText());
            newObj.put(MAXIMUM_KEY, maximum.getText());
            newObj.put(VOLUMES_KEY, volumes.getText());
            newObj.put(DATA_KEY, date.toString());
        } catch (JSONException ex) {
            Logger.getLogger(DataInserert.class.getName()).log(Level.SEVERE, null, ex);
        }
//      ---        

//      Caso del primo salvaraggio nel database
        BufferedReader buffer = null;
        try {
            buffer = new BufferedReader(new FileReader(this.getDatabaseAbsolutePath()));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DataInserert.class.getName()).log(Level.SEVERE, null, ex);
        }

        String line = null;
        try {
            line = buffer.readLine();
        } catch (IOException ex) {
            Logger.getLogger(DataInserert.class.getName()).log(Level.SEVERE, null, ex);
        }

        Iterator<JSONObject> iterator = null;
        if (line == null) { // Se il file è vuoto 
            JSONObject newData = addDataFirstTime(name.getText(), newObj);

//          Aggiunta del nuovo titolo finanziario nel Set
            objFinanceData.add(newData);                                                    
        } else {            
//          Caso in cui il nome del  titolo è già presente nel database
//          Dichiarazione e inizializzazone delle risorse necessarie        
            iterator = objFinanceData.iterator();
            String titleName = null;
            int index = 0;
            // JSONObject objIndex = null;

            /*Iterator<JSONObject> prevIterator = null;
//          Ciclio per verificare se il nome del titolo è già stato inserito nel database
            while (iterator.hasNext()) {       
                objIndex = iterator.next();
                ++index;            
            }*/

//          Aggiunta del nuovo dato
            for (JSONObject objIndex : objFinanceData) {                
//          Prendo il nome del titolo finanziario             
                try {
                    titleName = objIndex.getString(NAME_KEY);
                } catch (JSONException ex) {
                    Logger.getLogger(DataInserert.class.getName()).log(Level.SEVERE, null, ex);
                }                
//              Caso in cui il nome esista già nel database
                if (name.getText() == titleName) {                    

                    try {   //  NON VA BENE!!                        
                        objIndex.getJSONArray(DATAS_KEY).put(newObj);                                                
                    } catch (JSONException ex) {
                        Logger.getLogger(DataInserert.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {                                        
//                  Caso in cio il nome non esiste nel database                
                    JSONObject newData = addDataFirstTime(name.getText(), newObj);

//                  Aggiunta del nuovo titolo finanziario nel Set
                    objFinanceData.add(newData);                                    
                }                
            }   // Fine ciclo for-eac
        }   // File eslse per l'aggiunto di un dato con il databese già pieno
        
//      Scrittura dei dati sul file          
        FileWriter write = null;                        
        try {
            write = new FileWriter(this.getDatabaseAbsolutePath());
            System.out.println("DEBUG: " + newObj.toString());
            
            iterator = null;
            iterator = this.objFinanceData.iterator();
            while (iterator.hasNext()) {                
                write.write(iterator.next().toString());
            }
            
            write.flush();
            write.close();
        } catch (IOException ex) {
            Logger.getLogger(JSONTest.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }    

    private JSONObject addDataFirstTime(String financeTtleName, JSONObject dataObj) {        
//      Caso in cio il nome non esiste nel database                
        JSONObject newTitle = new JSONObject();                   
        JSONArray array = new JSONArray();                                        
        try {
            newTitle.put(NAME_KEY, financeTtleName);                
            array.put(dataObj);
            newTitle.put(DATAS_KEY, array);
        } catch (JSONException ex) {
            Logger.getLogger(DataInserert.class.getName()).log(Level.SEVERE, null, ex);
        }

        return newTitle;
    }
    
    private void writeFile() {
//      Scrittura del file del database
        FileWriter write = null;        
        Iterator<JSONObject> iterator = objFinanceData.iterator();
        while (iterator.hasNext()) {            
            try {
                write = new FileWriter(this.getDatabaseAbsolutePath());
                
                write.write(iterator.next().toString());
                write.flush();
                write.close();
            } catch (IOException ex) {
                Logger.getLogger(JSONTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }             
    }
}
