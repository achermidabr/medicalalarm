/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hermida.remedyalarm.entities;

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
public class RemedyAlarm implements Serializable, Comparable<RemedyAlarm> {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String medicine;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Calendar nextDose;
    private int numberOfDoses; //how many times this medicine will have to be taken
    private long intervalBetweenDoses; //interval between doses
    private INTERVALTYPE intervalType; //minutes? hours? days?

    public enum INTERVALTYPE {

        DAY(Calendar.DATE), HOUR(Calendar.HOUR), MINUTE(Calendar.MINUTE);
        
        int valorDoCalendario;
        
        INTERVALTYPE(int valorDoCalendario){
            this.valorDoCalendario = valorDoCalendario;
        }
        
        public int getEquivalenteDoCalendario(){
            return this.valorDoCalendario;
        }
    };

    public RemedyAlarm() {
    }

    @Override
    public RemedyAlarm clone() {
        RemedyAlarm clone = new RemedyAlarm();
        clone.id = this.id;
        clone.intervalBetweenDoses = this.intervalBetweenDoses;
        clone.medicine = this.medicine;
        clone.nextDose = this.nextDose;
        clone.numberOfDoses = this.numberOfDoses;
        clone.intervalType = this.intervalType;
        return clone;
    }
    
    public RemedyAlarm(String medicineName, Calendar firstDose, int numOfDoses, long intervalBetweenDoses, INTERVALTYPE intervType) {
        if (numOfDoses != 0) {
            this.medicine = medicineName;
            this.nextDose = firstDose;
            this.numberOfDoses = numOfDoses;
            this.intervalBetweenDoses = intervalBetweenDoses;
            this.intervalType = intervType;
        } else {
            throw new IllegalArgumentException("Number of doses must be greater than zero.");
        }
    }

    public RemedyAlarm(Calendar timer, int qVezes) {
        this("Generico", timer, qVezes, 12, INTERVALTYPE.HOUR);
    }

    public RemedyAlarm(Long timer, int qVezes) {
        if (qVezes != 0) {
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(timer);
            medicine = "Generico";
            nextDose = cal;
            numberOfDoses = qVezes;
        } else {
            throw new IllegalArgumentException("Quantidade de vezes deve ser maior que zero.");
        }
    }

    public void ajustaQuantidade() {
        numberOfDoses--;
        if (numberOfDoses != 0) {
           nextDose.add(this.intervalType.valorDoCalendario, numberOfDoses);
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
        if (!(object instanceof RemedyAlarm)) {
            return false;
        }
        RemedyAlarm other = (RemedyAlarm) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "AlertaDeRemedio[ Remédio=" + medicine + " :: tomar às= " + nextDose.getTime() + " em intervalos de " + intervalBetweenDoses + " " + intervalType + " por mais + " + numberOfDoses + "]";
    }

    public String getNomeRemedio() {
        return medicine;
    }

    public Calendar getProxima() {
        return nextDose;
    }

    public void setProxima(Calendar proxima) {
        this.nextDose = proxima;
        numberOfDoses--;
    }

    public int getQuantidadeVezes() {
        return numberOfDoses;
    }

    public void setQuantidadeVezes(int quantidadeVezes) {
        this.numberOfDoses = quantidadeVezes;
    }

    @Override
    public int compareTo(RemedyAlarm o) {
        if (o == null) {
            return -1;
        } else {
            return (int) (this.getProxima().getTimeInMillis() - o.getProxima().getTimeInMillis());
        }
    }

    public long getIntervalo() {
        return intervalBetweenDoses;
    }
    
    public long getIntervalorEmMilis(){
        long result = 0;
        switch(intervalType){
            case DAY:
                result = intervalBetweenDoses * 60 * 60 * 60 * 60 * 24;
                break;
            case HOUR:
                result = intervalBetweenDoses * 60 * 60 * 60 * 60;
                break;
            case MINUTE:
                result = intervalBetweenDoses * 60 * 60 * 60;
                break;
        }
        return result;
    }

    public INTERVALTYPE getTipoIntervalo() {
        return intervalType;
    }
}
