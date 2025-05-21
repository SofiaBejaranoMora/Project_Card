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
@Table(name = "ROWCARDSXCARD")
@NamedQueries({
    @NamedQuery(name = "Rowcardsxcard.findAll", query = "SELECT r FROM Rowcardsxcard r"),
    @NamedQuery(name = "Rowcardsxcard.findByRxcRocId", query = "SELECT r FROM Rowcardsxcard r WHERE r.rowcardsxcardPK.rxcRocId = :rxcRocId"),
    @NamedQuery(name = "Rowcardsxcard.findByRxcCarId", query = "SELECT r FROM Rowcardsxcard r WHERE r.rowcardsxcardPK.rxcCarId = :rxcCarId")})
public class Rowcardsxcard implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected RowcardsxcardPK rowcardsxcardPK;

    public Rowcardsxcard() {
    }

    public Rowcardsxcard(RowcardsxcardPK rowcardsxcardPK) {
        this.rowcardsxcardPK = rowcardsxcardPK;
    }

    public Rowcardsxcard(BigInteger rxcRocId, BigInteger rxcCarId) {
        this.rowcardsxcardPK = new RowcardsxcardPK(rxcRocId, rxcCarId);
    }

    public RowcardsxcardPK getRowcardsxcardPK() {
        return rowcardsxcardPK;
    }

    public void setRowcardsxcardPK(RowcardsxcardPK rowcardsxcardPK) {
        this.rowcardsxcardPK = rowcardsxcardPK;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rowcardsxcardPK != null ? rowcardsxcardPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Rowcardsxcard)) {
            return false;
        }
        Rowcardsxcard other = (Rowcardsxcard) object;
        if ((this.rowcardsxcardPK == null && other.rowcardsxcardPK != null) || (this.rowcardsxcardPK != null && !this.rowcardsxcardPK.equals(other.rowcardsxcardPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.una.project_card.model.Rowcardsxcard[ rowcardsxcardPK=" + rowcardsxcardPK + " ]";
    }
    
}
