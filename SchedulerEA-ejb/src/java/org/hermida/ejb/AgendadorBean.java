/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hermida.ejb;

import com.sun.istack.logging.Logger;
import org.hermida.entities.AlertaDeRemedio;
import org.hermida.dataaccess.AgendaDAO;
import java.util.SortedSet;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.inject.Inject;

/**
 *
 * @author achermida
 */
@Singleton
public class AgendadorBean {
    
    @Inject
    private AgendaDAO agendadao;
    private static final Logger logger = Logger.getLogger(AgendadorBean.class);
    private static long last = 0;
    
    static{
        AgendaDAO.initTest();
    }
  
    @Schedule(second="*/30", minute="*",hour="*", persistent=false)
    public void verifiquePorRemedios(){
        Long agora = System.currentTimeMillis();
        SortedSet<AlertaDeRemedio> subset = agendadao.getAlertas(last, agora);
        if(!subset.isEmpty()){
            for(AlertaDeRemedio alerta : subset){
                logger.info("Achei um timer: "+alerta);
                AlertaDeRemedio novo = alerta.clone();
                novo.ajustaQuantidade();
                agendadao.trocaAlerta(alerta, novo);
            }
        }else{
            last = agora;
            logger.info("Agora: "+last);
            logger.info("Nenhum alerta foi encontrado para esse período ... ");
        }
    }
}