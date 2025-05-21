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
@Table(name = "GAME")
@NamedQueries({
    @NamedQuery(name = "Game.findAll", query = "SELECT g FROM Game g"),
    @NamedQuery(name = "Game.findByGamId", query = "SELECT g FROM Game g WHERE g.gamId = :gamId"),
    @NamedQuery(name = "Game.findByGamTime", query = "SELECT g FROM Game g WHERE g.gamTime = :gamTime"),
    @NamedQuery(name = "Game.findByGamScore", query = "SELECT g FROM Game g WHERE g.gamScore = :gamScore"),
    @NamedQuery(name = "Game.findByGamVersion", query = "SELECT g FROM Game g WHERE g.gamVersion = :gamVersion"),
    @NamedQuery(name = "Game.findByGamPlanId", query = "SELECT g FROM Game g WHERE g.gamPlanId = :gamPlanId"),
    @NamedQuery(name = "Game.findByGamWon", query = "SELECT g FROM Game g WHERE g.gamWon = :gamWon"),
    @NamedQuery(name = "Game.findByGamDifficulty", query = "SELECT g FROM Game g WHERE g.gamDifficulty = :gamDifficulty")})
public class Game implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "GAM_ID")
    private BigDecimal gamId;
    @Basic(optional = false)
    @Column(name = "GAM_TIME")
    private BigInteger gamTime;
    @Basic(optional = false)
    @Column(name = "GAM_SCORE")
    private BigInteger gamScore;
    @Basic(optional = false)
    @Column(name = "GAM_VERSION")
    private BigInteger gamVersion;
    @Column(name = "GAM_PLAN_ID")
    private BigInteger gamPlanId;
    @Column(name = "GAM_WON")
    private String gamWon;
    @Basic(optional = false)
    @Column(name = "GAM_DIFFICULTY")
    private BigInteger gamDifficulty;

    public Game() {
    }

    public Game(BigDecimal gamId) {
        this.gamId = gamId;
    }

    public Game(BigDecimal gamId, BigInteger gamTime, BigInteger gamScore, BigInteger gamVersion, BigInteger gamDifficulty) {
        this.gamId = gamId;
        this.gamTime = gamTime;
        this.gamScore = gamScore;
        this.gamVersion = gamVersion;
        this.gamDifficulty = gamDifficulty;
    }

    public BigDecimal getGamId() {
        return gamId;
    }

    public void setGamId(BigDecimal gamId) {
        this.gamId = gamId;
    }

    public BigInteger getGamTime() {
        return gamTime;
    }

    public void setGamTime(BigInteger gamTime) {
        this.gamTime = gamTime;
    }

    public BigInteger getGamScore() {
        return gamScore;
    }

    public void setGamScore(BigInteger gamScore) {
        this.gamScore = gamScore;
    }

    public BigInteger getGamVersion() {
        return gamVersion;
    }

    public void setGamVersion(BigInteger gamVersion) {
        this.gamVersion = gamVersion;
    }

    public BigInteger getGamPlanId() {
        return gamPlanId;
    }

    public void setGamPlanId(BigInteger gamPlanId) {
        this.gamPlanId = gamPlanId;
    }

    public String getGamWon() {
        return gamWon;
    }

    public void setGamWon(String gamWon) {
        this.gamWon = gamWon;
    }

    public BigInteger getGamDifficulty() {
        return gamDifficulty;
    }

    public void setGamDifficulty(BigInteger gamDifficulty) {
        this.gamDifficulty = gamDifficulty;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (gamId != null ? gamId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Game)) {
            return false;
        }
        Game other = (Game) object;
        if ((this.gamId == null && other.gamId != null) || (this.gamId != null && !this.gamId.equals(other.gamId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.una.project_card.model.Game[ gamId=" + gamId + " ]";
    }
    
}
