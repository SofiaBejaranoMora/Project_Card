/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.project_card.model;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author sofia
 */
@Entity
@Table(name = "PLAYER", schema = "PRO")
@NamedQueries({
    @NamedQuery(name = "Player.findAll", query = "SELECT p FROM Player p"),
    /*@NamedQuery(name = "Player.findByPlaId", query = "SELECT p FROM Player p WHERE p.plaId = :plaId"),
    @NamedQuery(name = "Player.findByPlaName", query = "SELECT p FROM Player p WHERE p.plaName = :plaName"),
    @NamedQuery(name = "Player.findByPlaAccumulatedpoint", query = "SELECT p FROM Player p WHERE p.plaAccumulatedpoint = :plaAccumulatedpoint"),
    @NamedQuery(name = "Player.findByPlaCardstyle", query = "SELECT p FROM Player p WHERE p.plaCardstyle = :plaCardstyle"),
    @NamedQuery(name = "Player.findByPlaVersion", query = "SELECT p FROM Player p WHERE p.plaVersion = :plaVersion"),
    @NamedQuery(name = "Player.findByPlaCardbackimagename", query = "SELECT p FROM Player p WHERE p.plaCardbackimagename = :plaCardbackimagename")*/})
public class Player implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @SequenceGenerator(name = "PLAYER_PLA_ID_GENERATOR", sequenceName = "pro.Player_seq01", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PLAYER_PLA_ID_GENERATOR")
    @Basic(optional = false)
    @Column(name = "PLA_ID")
    private Long id;
    @Basic(optional = false)
    @Column(name = "PLA_NAME")
    private String name;
    @Column(name = "PLA_ACCUMULATEDPOINT")
    private Long accumulatedPoint;
    @Basic(optional = false)
    @Column(name = "PLA_CARDSTYLE")
    private Long cardStyle;
    @Version
    @Column(name = "PLA_VERSION")
    private Long version;
    @Basic(optional = false)
    @Column(name = "PLA_CARDBACKIMAGENAME")
    private String cardBackImageName;
    @JoinTable(name = "PLAYERXACHIEVEMENT", joinColumns = {
        @JoinColumn(name = "PXA_PLA_ID", referencedColumnName = "PLA_ID")}, inverseJoinColumns = {
        @JoinColumn(name = "PXA_ACH_ID", referencedColumnName = "ACH_ID")})
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Achievement> achievements;
    @OneToMany(mappedBy = "player", fetch = FetchType.LAZY)
    private List<Game> games;

    public Player() {
    }

    public Player(Long id) {
        this.id = id;
    }

    public Player(PlayerDto playerDto) {
        this.id = playerDto.getId();
        update(playerDto);
    }

    public void update(PlayerDto playerDto) {
        this.id = playerDto.getId();
        this.name = playerDto.getName();
        this.accumulatedPoint = playerDto.getAccumulatedPoint();
        this.cardStyle = playerDto.getCardStyle();
        this.cardBackImageName = playerDto.getCardBackImageName();
        this.version = playerDto.getVersion();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getAccumulatedpoint() {
        return accumulatedPoint;
    }

    public void setAccumulatedpoint(Long accumulatedPoint) {
        this.accumulatedPoint = accumulatedPoint;
    }

    public Long getCardstyle() {
        return cardStyle;
    }

    public void setCardstyle(Long cardStyle) {
        this.cardStyle = cardStyle;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getCardBackImageName() {
        return cardBackImageName;
    }

    public void setCardBackImageName(String cardBackImageName) {
        this.cardBackImageName = cardBackImageName;
    }

    public List<Achievement> getAchievements() {
        return achievements;
    }

    public void setAchievements(List<Achievement> achievements) {
        this.achievements = achievements;
    }

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
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
        if (!(object instanceof Player)) {
            return false;
        }
        Player other = (Player) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.una.project_card.model.Player[ plaId=" + id + " ]";
    }

}
