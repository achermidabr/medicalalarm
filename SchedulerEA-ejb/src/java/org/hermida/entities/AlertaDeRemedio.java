/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hermida.entities;

import org.hermida.*;
import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;

/**
 *
 * @author achermida
 */
@Entity
public class AlertaDeRemedio implements Serializable, Comparable<AlertaDeRemedio> {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nomeRemedio;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Calendar proxima;
    private int quantidadeVezes;
    private long intervalo;
    private TIPOINTERVALO tipoIntervalo;

    public enum TIPOINTERVALO {

        DIA(Calendar.DATE), HORA(Calendar.HOUR), MINUTO(Calendar.MINUTE);
        
        int valorDoCalendario;
        
        TIPOINTERVALO(int valorDoCalendario){
            this.valorDoCalendario = valorDoCalendario;
        }
        
        public int getEquivalenteDoCalendario(){
            return this.valorDoCalendario;
        }
    };

    public AlertaDeRemedio() {
    }

    @Override
    public AlertaDeRemedio clone() {
        AlertaDeRemedio clone = new AlertaDeRemedio();
        clone.id = this.id;
        clone.intervalo = this.intervalo;
        clone.nomeRemedio = this.nomeRemedio;
        clone.proxima = this.proxima;
        clone.quantidadeVezes = this.quantidadeVezes;
        clone.tipoIntervalo = this.tipoIntervalo;
        return clone;
    }
    
    public AlertaDeRemedio(String nomeRemedio, Calendar timer, int qVezes, long intervalo, TIPOINTERVALO tipoInterv) {
        if (qVezes != 0) {
            this.nomeRemedio = nomeRemedio;
            this.proxima = timer;
            this.quantidadeVezes = qVezes;
            this.intervalo = intervalo;
            this.tipoIntervalo = tipoInterv;
        } else {
            throw new IllegalArgumentException("Quantidade de vezes deve ser maior que zero.");
        }
    }

    public AlertaDeRemedio(Calendar timer, int qVezes) {
        this("Generico", timer, qVezes, 12, TIPOINTERVALO.HORA);
    }

    public AlertaDeRemedio(Long timer, int qVezes) {
        if (qVezes != 0) {
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(timer);
            nomeRemedio = "Generico";
            proxima = cal;
            quantidadeVezes = qVezes;
        } else {
            throw new IllegalArgumentException("Quantidade de vezes deve ser maior que zero.");
        }
    }

    public void ajustaQuantidade() {
        quantidadeVezes--;
        if (quantidadeVezes != 0) {
           proxima.add(this.tipoIntervalo.valorDoCalendario, quantidadeVezes);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AlertaDeRemedio)) {
            return false;
        }
        AlertaDeRemedio other = (AlertaDeRemedio) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "AlertaDeRemedio[ Remédio=" + nomeRemedio + " :: tomar às= " + proxima.getTime() + " em intervalos de " + intervalo + " " + tipoIntervalo + " por mais + " + quantidadeVezes + "]";
    }

    public String getNomeRemedio() {
        return nomeRemedio;
    }

    public Calendar getProxima() {
        return proxima;
    }

    public void setProxima(Calendar proxima) {
        this.proxima = proxima;
        quantidadeVezes--;
    }

    public int getQuantidadeVezes() {
        return quantidadeVezes;
    }

    public void setQuantidadeVezes(int quantidadeVezes) {
        this.quantidadeVezes = quantidadeVezes;
    }

    @Override
    public int compareTo(AlertaDeRemedio o) {
        if (o == null) {
            return -1;
        } else {
            return (int) (this.getProxima().getTimeInMillis() - o.getProxima().getTimeInMillis());
        }
    }

    public long getIntervalo() {
        return intervalo;
    }
    
    public long getIntervalorEmMilis(){
        long result = 0;
        switch(tipoIntervalo){
            case DIA:
                result = intervalo * 60 * 60 * 60 * 60 * 24;
                break;
            case HORA:
                result = intervalo * 60 * 60 * 60 * 60;
                break;
            case MINUTO:
                result = intervalo * 60 * 60 * 60;
                break;
        }
        return result;
    }

    public TIPOINTERVALO getTipoIntervalo() {
        return tipoIntervalo;
    }
}
