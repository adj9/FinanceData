/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import gui.FinanceData;
import gui.Impostation;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import test.JSONTest;

/**
 *
 * @author Alessandro
 */
public class Setup {
    private File database;    
    private JSONObject objSetup;    
    
    /**
     * Costruttore di default
     */
    public Setup() { 
        this.database = null;
        this.objSetup = new JSONObject();
        
        this.init();
    }    
    
    private void init() {
        BufferedReader buffer = null;
        try {
            buffer = 
                    new BufferedReader(new FileReader(System.getProperty("user.dir") + "/Setup.json"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Setup.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String line = null;
        try {
            line = buffer.readLine();
        } catch (IOException ex) {
            Logger.getLogger(Setup.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (line != null) {
            try {
                objSetup = new JSONObject(line);
                initDatabase(objSetup.getString("path_database"));
            } catch (JSONException ex) {
                Logger.getLogger(Setup.class.getName()).log(Level.SEVERE, null, ex);
            }            
        }
    }
    
    /**
     * Metodo per creare il file del databse.
     * 
     * @param pathDatabase 
     */
    public void initDatabase(String pathDatabase) {
        if (this.database != null) {
            this.database = null;
            this.database = new File(pathDatabase);
        } else {
            this.database = new File(pathDatabase);
        }
    }
    
    /**
     * Metodo per settare il path corrente del database.
     */
    public void setDatabase() {                
        try {
            this.objSetup.put("path_database", this.database.getAbsoluteFile());
        } catch (JSONException ex) {
            Logger.getLogger(Setup.class.getName()).log(Level.SEVERE, null, ex);
        }
        
//      Scrittura del file Setup.json        
        FileWriter write = null;
        try {
            write = new FileWriter(System.getProperty("user.dir") + "/Setup.json");
            
            write.write(this.objSetup.toString());
            write.flush();
            write.close();
        } catch (IOException ex) {
            Logger.getLogger(JSONTest.class.getName()).log(Level.SEVERE, null, ex);
        }      
        
//      Inizializzare il file per il nuovo database        
        try {
            this.initDatabase(this.objSetup.getString("path_database"));
        } catch (JSONException ex) {
            Logger.getLogger(Setup.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
    public String getDatabaseAbsolutePath() {
        return this.database.getAbsolutePath();
    }
}
