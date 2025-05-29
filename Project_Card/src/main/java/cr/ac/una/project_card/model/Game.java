
package cr.ac.una.project_card.model;

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
import jakarta.persistence.ManyToOne;
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
@Table(name = "GAME", schema = "PRO")
@NamedQueries({
    @NamedQuery(name = "Game.findAll", query = "SELECT g FROM Game g"),
   /* @NamedQuery(name = "Game.findByGamId", query = "SELECT g FROM Game g WHERE g.gamId = :gamId"),
    @NamedQuery(name = "Game.findByGamTime", query = "SELECT g FROM Game g WHERE g.gamTime = :gamTime"),
    @NamedQuery(name = "Game.findByGamScore", query = "SELECT g FROM Game g WHERE g.gamScore = :gamScore"),
    @NamedQuery(name = "Game.findByGamVersion", query = "SELECT g FROM Game g WHERE g.gamVersion = :gamVersion"),
    @NamedQuery(name = "Game.findByGamHaswon", query = "SELECT g FROM Game g WHERE g.gamHaswon = :gamHaswon"),
    @NamedQuery(name = "Game.findByGamDifficulty", query = "SELECT g FROM Game g WHERE g.gamDifficulty = :gamDifficulty")*/})
public class Game implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
     @SequenceGenerator(name = "GAME_GAM_ID_GENERATOR", sequenceName = "pro.Game_seq03", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GAME_GAM_ID_GENERATOR")
    @Basic(optional = false)
    @Column(name = "GAM_ID")
    private Long id;
    @Basic(optional = false)
    @Column(name = "GAM_TIME")
    private Long time;
    @Basic(optional = false)
    @Column(name = "GAM_SCORE")
    private Long score;
    @Version
    @Column(name = "GAM_VERSION")
    private Long version;
    @Basic(optional = false)
    @Column(name = "GAM_HASWON")
    private String hasWon;
    @Basic(optional = false)
    @Column(name = "GAM_DIFFICULTY")
    private Long difficulty;
    @JoinTable(name = "GAMEXCARD", joinColumns = {
        @JoinColumn(name = "GXC_GAM_ID", referencedColumnName = "GAM_ID")}, inverseJoinColumns = {
        @JoinColumn(name = "GXC_CAR_ID", referencedColumnName = "CAR_ID")})
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Card> cards;
    @JoinColumn(name = "GAM_PLAN_ID", referencedColumnName = "PLA_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Player player;
    @OneToMany(mappedBy = "game", fetch = FetchType.LAZY)
    private List<Stackcard> stackCards;

    public Game() {
    }

    public Game(Long id) {
        this.id = id;
    }

    public Game(GameDto gameDto) {
        this.id = gameDto.getId();
        update(gameDto);
    }

    public void update(GameDto gameDto) {
        this.id = gameDto.getId();
        this.time = gameDto.getTime();
        this.score = gameDto.getScore();
        this.hasWon = gameDto.getHasWon();
        if (hasWon.equalsIgnoreCase("T") || hasWon.equalsIgnoreCase("F") || hasWon.equalsIgnoreCase("N")) {
            this.hasWon = gameDto.getHasWon();
        }
        this.difficulty = gameDto.getDifficulty();
        this.version = gameDto.getVersion();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getHasWon() {
        return hasWon;
    }

    public void setHasWon(String hasWon) {
        this.hasWon = hasWon;
    }

    public Long getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Long difficulty) {
        this.difficulty = difficulty;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public List<Stackcard> getStackCards() {
        return stackCards;
    }

    public void setStackCards(List<Stackcard> stackCards) {
        this.stackCards = stackCards;
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
        if (!(object instanceof Game)) {
            return false;
        }
        Game other = (Game) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.una.project_card.model.Game[ gamId=" + id + " ]";
    }

}
