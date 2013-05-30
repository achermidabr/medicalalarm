/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hermida.dataaccess;

import org.hermida.entities.AlertaDeRemedio;
import java.util.Calendar;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 *
 * @author achermida
 */
public class AgendaDAO {

        public SortedSet<AlertaDeRemedio> getAlertas(Long from, Long to){
            SortedSet<AlertaDeRemedio> alertas = new TreeSet<AlertaDeRemedio>();
            for(AlertaDeRemedio alertaProx : DB.INSTANCE.findAll()){
             if(from < alertaProx.getProxima().getTimeInMillis() && alertaProx.getProxima().getTimeInMillis() < to){
                    alertas.add(alertaProx);
                }
            }
            return alertas;
        }
        
        public void adicionaAlerta(AlertaDeRemedio timer){
            DB.INSTANCE.insert(timer);
        }
        
        public void removeAlerta(AlertaDeRemedio alerta){
            DB.INSTANCE.delete(alerta);
        }
        
        public void trocaAlerta(AlertaDeRemedio alertaAntigo, AlertaDeRemedio alertaNovo){
            DB.INSTANCE.delete(alertaAntigo);
            DB.INSTANCE.insert(alertaNovo);
        }
        
        public static void initTest(){
            Calendar agora = Calendar.getInstance();
            agora.add(Calendar.MINUTE, 1);
            AlertaDeRemedio al1 = new AlertaDeRemedio("Remedio 1",agora, 3,1, AlertaDeRemedio.TIPOINTERVALO.MINUTO);
            
            Calendar agora2 = Calendar.getInstance();
            agora2.add(Calendar.MINUTE, 2);
            AlertaDeRemedio al2 = new AlertaDeRemedio("Remedio 2",agora2, 3, 1, AlertaDeRemedio.TIPOINTERVALO.MINUTO);
            
            DB.INSTANCE.insert(al1);
            DB.INSTANCE.insert(al2);
        }
    
}
