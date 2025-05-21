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
@Table(name = "GAMEXCARD")
@NamedQueries({
    @NamedQuery(name = "Gamexcard.findAll", query = "SELECT g FROM Gamexcard g"),
    @NamedQuery(name = "Gamexcard.findByGxcGamId", query = "SELECT g FROM Gamexcard g WHERE g.gamexcardPK.gxcGamId = :gxcGamId"),
    @NamedQuery(name = "Gamexcard.findByGxcCarId", query = "SELECT g FROM Gamexcard g WHERE g.gamexcardPK.gxcCarId = :gxcCarId")})
public class Gamexcard implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected GamexcardPK gamexcardPK;

    public Gamexcard() {
    }

    public Gamexcard(GamexcardPK gamexcardPK) {
        this.gamexcardPK = gamexcardPK;
    }

    public Gamexcard(BigInteger gxcGamId, BigInteger gxcCarId) {
        this.gamexcardPK = new GamexcardPK(gxcGamId, gxcCarId);
    }

    public GamexcardPK getGamexcardPK() {
        return gamexcardPK;
    }

    public void setGamexcardPK(GamexcardPK gamexcardPK) {
        this.gamexcardPK = gamexcardPK;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (gamexcardPK != null ? gamexcardPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Gamexcard)) {
            return false;
        }
        Gamexcard other = (Gamexcard) object;
        if ((this.gamexcardPK == null && other.gamexcardPK != null) || (this.gamexcardPK != null && !this.gamexcardPK.equals(other.gamexcardPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.una.project_card.model.Gamexcard[ gamexcardPK=" + gamexcardPK + " ]";
    }
    
}
