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
public class GamexcardPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "GXC_GAM_ID")
    private BigInteger gxcGamId;
    @Basic(optional = false)
    @Column(name = "GXC_CAR_ID")
    private BigInteger gxcCarId;

    public GamexcardPK() {
    }

    public GamexcardPK(BigInteger gxcGamId, BigInteger gxcCarId) {
        this.gxcGamId = gxcGamId;
        this.gxcCarId = gxcCarId;
    }

    public BigInteger getGxcGamId() {
        return gxcGamId;
    }

    public void setGxcGamId(BigInteger gxcGamId) {
        this.gxcGamId = gxcGamId;
    }

    public BigInteger getGxcCarId() {
        return gxcCarId;
    }

    public void setGxcCarId(BigInteger gxcCarId) {
        this.gxcCarId = gxcCarId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (gxcGamId != null ? gxcGamId.hashCode() : 0);
        hash += (gxcCarId != null ? gxcCarId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GamexcardPK)) {
            return false;
        }
        GamexcardPK other = (GamexcardPK) object;
        if ((this.gxcGamId == null && other.gxcGamId != null) || (this.gxcGamId != null && !this.gxcGamId.equals(other.gxcGamId))) {
            return false;
        }
        if ((this.gxcCarId == null && other.gxcCarId != null) || (this.gxcCarId != null && !this.gxcCarId.equals(other.gxcCarId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.una.project_card.model.GamexcardPK[ gxcGamId=" + gxcGamId + ", gxcCarId=" + gxcCarId + " ]";
    }
    
}
