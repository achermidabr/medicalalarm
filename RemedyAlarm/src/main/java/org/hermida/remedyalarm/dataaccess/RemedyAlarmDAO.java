/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hermida.remedyalarm.dataaccess;

import org.hermida.remedyalarm.entities.RemedyAlarm;
import java.util.Calendar;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 *
 * @author achermida
 */
public class RemedyAlarmDAO {

        public SortedSet<RemedyAlarm> getAlertas(Long from, Long to){
            SortedSet<RemedyAlarm> alertas = new TreeSet<RemedyAlarm>();
            for(RemedyAlarm alertaProx : DB.INSTANCE.findAll()){
             if(from < alertaProx.getProxima().getTimeInMillis() && alertaProx.getProxima().getTimeInMillis() < to){
                    alertas.add(alertaProx);
                }
            }
            return alertas;
        }
        
        public void adicionaAlerta(RemedyAlarm timer){
            DB.INSTANCE.insert(timer);
        }
        
        public void removeAlerta(RemedyAlarm alerta){
            DB.INSTANCE.delete(alerta);
        }
        
        public void trocaAlerta(RemedyAlarm alertaAntigo, RemedyAlarm alertaNovo){
            DB.INSTANCE.delete(alertaAntigo);
            DB.INSTANCE.insert(alertaNovo);
        }
        
        public static void initTest(){
            Calendar agora = Calendar.getInstance();
            agora.add(Calendar.MINUTE, 1);
            RemedyAlarm al1 = new RemedyAlarm("Remedio 1",agora, 3,1, RemedyAlarm.INTERVALTYPE.MINUTE);
            
            Calendar agora2 = Calendar.getInstance();
            agora2.add(Calendar.MINUTE, 2);
            RemedyAlarm al2 = new RemedyAlarm("Remedio 2",agora2, 3, 1, RemedyAlarm.INTERVALTYPE.MINUTE);
            
            DB.INSTANCE.insert(al1);
            DB.INSTANCE.insert(al2);
        }
    
}
