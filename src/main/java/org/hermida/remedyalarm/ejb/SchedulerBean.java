/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hermida.remedyalarm.ejb;

import org.hermida.remedyalarm.entities.RemedyAlarm;
import org.hermida.remedyalarm.dataaccess.RemedyAlarmDAO;
import java.util.SortedSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.inject.Inject;

/**
 *
 * @author achermida
 */
@Singleton
public class SchedulerBean {
    
    @Inject
    private RemedyAlarmDAO agendadao;
    private static final Logger logger = Logger.getLogger("SchedulerBean.class");
    private static long last = 0;
    
    //Test only
    static{
        RemedyAlarmDAO.initTest();
    }
  
    @Schedule(second="*/30", minute="*",hour="*", persistent=false)
    public void verifiquePorRemedios(){
        Long agora = System.currentTimeMillis();
        SortedSet<RemedyAlarm> subset = agendadao.getAlertas(last, agora);
        if(!subset.isEmpty()){
            for(RemedyAlarm alert : subset){
                logger.log(Level.INFO, "Found an alert: {0}", alert);
                RemedyAlarm novo = alert.clone();
                novo.ajustaQuantidade();
                agendadao.trocaAlerta(alert, novo);
            }
        }else{
            last = agora;
            logger.log(Level.INFO, "Now: {0}", last);
            logger.info("No alarm was found ... ");
        }
    }
}