/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hermida.remedyalarm.dataaccess;

import java.util.TreeSet;
import org.hermida.remedyalarm.entities.RemedyAlarm;

/**
 *
 * @author achermida
 */
public enum DB {
    INSTANCE;
    
    private static TreeSet<RemedyAlarm> set = new TreeSet<RemedyAlarm>();

    public void insert(RemedyAlarm alerta){
        set.add(alerta);
    }
    
    public RemedyAlarm delete(RemedyAlarm alerta){
        RemedyAlarm result = set.lower(alerta);
        if(result != null){
            set.remove(alerta);
        }
        return result;
    }
    
    public RemedyAlarm find(RemedyAlarm alerta){
        return set.lower(alerta);
    }
    
    public TreeSet<RemedyAlarm> findAll(){
        return set;
    }
            
    
}
