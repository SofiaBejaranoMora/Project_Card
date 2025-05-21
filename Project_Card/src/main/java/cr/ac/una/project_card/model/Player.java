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
@Table(name = "PLAYER")
@NamedQueries({
    @NamedQuery(name = "Player.findAll", query = "SELECT p FROM Player p"),
    @NamedQuery(name = "Player.findByPlaId", query = "SELECT p FROM Player p WHERE p.plaId = :plaId"),
    @NamedQuery(name = "Player.findByPlaName", query = "SELECT p FROM Player p WHERE p.plaName = :plaName"),
    @NamedQuery(name = "Player.findByPlaAccumulatedpoint", query = "SELECT p FROM Player p WHERE p.plaAccumulatedpoint = :plaAccumulatedpoint"),
    @NamedQuery(name = "Player.findByPlaCardstyle", query = "SELECT p FROM Player p WHERE p.plaCardstyle = :plaCardstyle"),
    @NamedQuery(name = "Player.findByPlaVersion", query = "SELECT p FROM Player p WHERE p.plaVersion = :plaVersion"),
    @NamedQuery(name = "Player.findByPlaBackground", query = "SELECT p FROM Player p WHERE p.plaBackground = :plaBackground")})
public class Player implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "PLA_ID")
    private BigDecimal plaId;
    @Basic(optional = false)
    @Column(name = "PLA_NAME")
    private String plaName;
    @Column(name = "PLA_ACCUMULATEDPOINT")
    private BigInteger plaAccumulatedpoint;
    @Basic(optional = false)
    @Column(name = "PLA_CARDSTYLE")
    private BigInteger plaCardstyle;
    @Basic(optional = false)
    @Column(name = "PLA_VERSION")
    private BigInteger plaVersion;
    @Basic(optional = false)
    @Column(name = "PLA_BACKGROUND")
    private String plaBackground;

    public Player() {
    }

    public Player(BigDecimal plaId) {
        this.plaId = plaId;
    }

    public Player(BigDecimal plaId, String plaName, BigInteger plaCardstyle, BigInteger plaVersion, String plaBackground) {
        this.plaId = plaId;
        this.plaName = plaName;
        this.plaCardstyle = plaCardstyle;
        this.plaVersion = plaVersion;
        this.plaBackground = plaBackground;
    }

    public BigDecimal getPlaId() {
        return plaId;
    }

    public void setPlaId(BigDecimal plaId) {
        this.plaId = plaId;
    }

    public String getPlaName() {
        return plaName;
    }

    public void setPlaName(String plaName) {
        this.plaName = plaName;
    }

    public BigInteger getPlaAccumulatedpoint() {
        return plaAccumulatedpoint;
    }

    public void setPlaAccumulatedpoint(BigInteger plaAccumulatedpoint) {
        this.plaAccumulatedpoint = plaAccumulatedpoint;
    }

    public BigInteger getPlaCardstyle() {
        return plaCardstyle;
    }

    public void setPlaCardstyle(BigInteger plaCardstyle) {
        this.plaCardstyle = plaCardstyle;
    }

    public BigInteger getPlaVersion() {
        return plaVersion;
    }

    public void setPlaVersion(BigInteger plaVersion) {
        this.plaVersion = plaVersion;
    }

    public String getPlaBackground() {
        return plaBackground;
    }

    public void setPlaBackground(String plaBackground) {
        this.plaBackground = plaBackground;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (plaId != null ? plaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Player)) {
            return false;
        }
        Player other = (Player) object;
        if ((this.plaId == null && other.plaId != null) || (this.plaId != null && !this.plaId.equals(other.plaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.una.project_card.model.Player[ plaId=" + plaId + " ]";
    }
    
}
