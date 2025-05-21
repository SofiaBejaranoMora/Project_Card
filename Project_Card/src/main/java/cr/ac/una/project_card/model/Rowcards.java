/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.project_card.model;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 *
 * @author sofia
 */
@Entity
@Table(name = "ROWCARDS")
@NamedQueries({
    @NamedQuery(name = "Rowcards.findAll", query = "SELECT r FROM Rowcards r"),
    @NamedQuery(name = "Rowcards.findByRocId", query = "SELECT r FROM Rowcards r WHERE r.rocId = :rocId"),
    @NamedQuery(name = "Rowcards.findByRocNumberinvisiblecard", query = "SELECT r FROM Rowcards r WHERE r.rocNumberinvisiblecard = :rocNumberinvisiblecard"),
    @NamedQuery(name = "Rowcards.findByRocVersion", query = "SELECT r FROM Rowcards r WHERE r.rocVersion = :rocVersion"),
    @NamedQuery(name = "Rowcards.findByRocGamId", query = "SELECT r FROM Rowcards r WHERE r.rocGamId = :rocGamId")})
public class Rowcards implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ROC_ID")
    private BigDecimal rocId;
    @Column(name = "ROC_NUMBERINVISIBLECARD")
    private BigInteger rocNumberinvisiblecard;
    @Basic(optional = false)
    @Column(name = "ROC_VERSION")
    private BigInteger rocVersion;
    @Column(name = "ROC_GAM_ID")
    private BigInteger rocGamId;

    public Rowcards() {
    }

    public Rowcards(BigDecimal rocId) {
        this.rocId = rocId;
    }

    public Rowcards(BigDecimal rocId, BigInteger rocVersion) {
        this.rocId = rocId;
        this.rocVersion = rocVersion;
    }

    public BigDecimal getRocId() {
        return rocId;
    }

    public void setRocId(BigDecimal rocId) {
        this.rocId = rocId;
    }

    public BigInteger getRocNumberinvisiblecard() {
        return rocNumberinvisiblecard;
    }

    public void setRocNumberinvisiblecard(BigInteger rocNumberinvisiblecard) {
        this.rocNumberinvisiblecard = rocNumberinvisiblecard;
    }

    public BigInteger getRocVersion() {
        return rocVersion;
    }

    public void setRocVersion(BigInteger rocVersion) {
        this.rocVersion = rocVersion;
    }

    public BigInteger getRocGamId() {
        return rocGamId;
    }

    public void setRocGamId(BigInteger rocGamId) {
        this.rocGamId = rocGamId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rocId != null ? rocId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Rowcards)) {
            return false;
        }
        Rowcards other = (Rowcards) object;
        if ((this.rocId == null && other.rocId != null) || (this.rocId != null && !this.rocId.equals(other.rocId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.una.project_card.model.Rowcards[ rocId=" + rocId + " ]";
    }
    
}
