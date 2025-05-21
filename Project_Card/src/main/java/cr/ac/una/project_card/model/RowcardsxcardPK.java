/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.project_card.model;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.math.BigInteger;

/**
 *
 * @author sofia
 */
@Embeddable
public class RowcardsxcardPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "RXC_ROC_ID")
    private BigInteger rxcRocId;
    @Basic(optional = false)
    @Column(name = "RXC_CAR_ID")
    private BigInteger rxcCarId;

    public RowcardsxcardPK() {
    }

    public RowcardsxcardPK(BigInteger rxcRocId, BigInteger rxcCarId) {
        this.rxcRocId = rxcRocId;
        this.rxcCarId = rxcCarId;
    }

    public BigInteger getRxcRocId() {
        return rxcRocId;
    }

    public void setRxcRocId(BigInteger rxcRocId) {
        this.rxcRocId = rxcRocId;
    }

    public BigInteger getRxcCarId() {
        return rxcCarId;
    }

    public void setRxcCarId(BigInteger rxcCarId) {
        this.rxcCarId = rxcCarId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rxcRocId != null ? rxcRocId.hashCode() : 0);
        hash += (rxcCarId != null ? rxcCarId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RowcardsxcardPK)) {
            return false;
        }
        RowcardsxcardPK other = (RowcardsxcardPK) object;
        if ((this.rxcRocId == null && other.rxcRocId != null) || (this.rxcRocId != null && !this.rxcRocId.equals(other.rxcRocId))) {
            return false;
        }
        if ((this.rxcCarId == null && other.rxcCarId != null) || (this.rxcCarId != null && !this.rxcCarId.equals(other.rxcCarId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.una.project_card.model.RowcardsxcardPK[ rxcRocId=" + rxcRocId + ", rxcCarId=" + rxcCarId + " ]";
    }
    
}
