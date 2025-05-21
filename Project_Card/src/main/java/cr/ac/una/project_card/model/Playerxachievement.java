/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.project_card.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.math.BigInteger;

/**
 *
 * @author sofia
 */
@Entity
@Table(name = "PLAYERXACHIEVEMENT")
@NamedQueries({
    @NamedQuery(name = "Playerxachievement.findAll", query = "SELECT p FROM Playerxachievement p"),
    @NamedQuery(name = "Playerxachievement.findByPxaPlaId", query = "SELECT p FROM Playerxachievement p WHERE p.playerxachievementPK.pxaPlaId = :pxaPlaId"),
    @NamedQuery(name = "Playerxachievement.findByPxaAchId", query = "SELECT p FROM Playerxachievement p WHERE p.playerxachievementPK.pxaAchId = :pxaAchId")})
public class Playerxachievement implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PlayerxachievementPK playerxachievementPK;

    public Playerxachievement() {
    }

    public Playerxachievement(PlayerxachievementPK playerxachievementPK) {
        this.playerxachievementPK = playerxachievementPK;
    }

    public Playerxachievement(BigInteger pxaPlaId, BigInteger pxaAchId) {
        this.playerxachievementPK = new PlayerxachievementPK(pxaPlaId, pxaAchId);
    }

    public PlayerxachievementPK getPlayerxachievementPK() {
        return playerxachievementPK;
    }

    public void setPlayerxachievementPK(PlayerxachievementPK playerxachievementPK) {
        this.playerxachievementPK = playerxachievementPK;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (playerxachievementPK != null ? playerxachievementPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Playerxachievement)) {
            return false;
        }
        Playerxachievement other = (Playerxachievement) object;
        if ((this.playerxachievementPK == null && other.playerxachievementPK != null) || (this.playerxachievementPK != null && !this.playerxachievementPK.equals(other.playerxachievementPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.una.project_card.model.Playerxachievement[ playerxachievementPK=" + playerxachievementPK + " ]";
    }
    
}
