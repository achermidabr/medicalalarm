/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hermida.dataaccess;

import java.util.TreeSet;
import org.hermida.entities.AlertaDeRemedio;

/**
 *
 * @author achermida
 */
public enum DB {
    INSTANCE;
    
    private static TreeSet<AlertaDeRemedio> set = new TreeSet<AlertaDeRemedio>();

    public void insert(AlertaDeRemedio alerta){
        set.add(alerta);
    }
    
    public AlertaDeRemedio delete(AlertaDeRemedio alerta){
        AlertaDeRemedio result = set.lower(alerta);
        if(result != null){
            set.remove(alerta);
        }
        return result;
    }
    
    public AlertaDeRemedio find(AlertaDeRemedio alerta){
        return set.lower(alerta);
    }
    
    public TreeSet<AlertaDeRemedio> findAll(){
        return set;
    }
            
    
}
