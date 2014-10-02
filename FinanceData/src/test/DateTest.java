/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.util.Date;

/**
 *
 * @author Alessandro
 */
public class DateTest {
    public static void main(String[] args) {
        Date date = new Date();
        
        System.out.println(date.toString());
        
        String[] now = date.toString().split(" ");
        for (int i = 0; i < now.length; ++i) {
            System.out.println(i + now[i]);
            
        }
    }
}
