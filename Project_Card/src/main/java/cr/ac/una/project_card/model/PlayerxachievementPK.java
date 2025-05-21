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
public class PlayerxachievementPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "PXA_PLA_ID")
    private BigInteger pxaPlaId;
    @Basic(optional = false)
    @Column(name = "PXA_ACH_ID")
    private BigInteger pxaAchId;

    public PlayerxachievementPK() {
    }

    public PlayerxachievementPK(BigInteger pxaPlaId, BigInteger pxaAchId) {
        this.pxaPlaId = pxaPlaId;
        this.pxaAchId = pxaAchId;
    }

    public BigInteger getPxaPlaId() {
        return pxaPlaId;
    }

    public void setPxaPlaId(BigInteger pxaPlaId) {
        this.pxaPlaId = pxaPlaId;
    }

    public BigInteger getPxaAchId() {
        return pxaAchId;
    }

    public void setPxaAchId(BigInteger pxaAchId) {
        this.pxaAchId = pxaAchId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pxaPlaId != null ? pxaPlaId.hashCode() : 0);
        hash += (pxaAchId != null ? pxaAchId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlayerxachievementPK)) {
            return false;
        }
        PlayerxachievementPK other = (PlayerxachievementPK) object;
        if ((this.pxaPlaId == null && other.pxaPlaId != null) || (this.pxaPlaId != null && !this.pxaPlaId.equals(other.pxaPlaId))) {
            return false;
        }
        if ((this.pxaAchId == null && other.pxaAchId != null) || (this.pxaAchId != null && !this.pxaAchId.equals(other.pxaAchId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.una.project_card.model.PlayerxachievementPK[ pxaPlaId=" + pxaPlaId + ", pxaAchId=" + pxaAchId + " ]";
    }
    
}
