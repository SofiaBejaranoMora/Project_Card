package cr.ac.una.project_card.model;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "CARD", schema = "PRO")
@NamedQueries({
    @NamedQuery(name = "Card.findAll", query = "SELECT c FROM Card c"),
    @NamedQuery(name = "Card.findByType", query = "SELECT c FROM Card c WHERE c.type = :type"),
    /*@NamedQuery(name = "Card.findByCarId", query = "SELECT c FROM Card c WHERE c.carId = :carId"),
    @NamedQuery(name = "Card.findByCarNumber", query = "SELECT c FROM Card c WHERE c.carNumber = :carNumber"),
    @NamedQuery(name = "Card.findByCarType", query = "SELECT c FROM Card c WHERE c.carType = :carType"),
    @NamedQuery(name = "Card.findByCarVersion", query = "SELECT c FROM Card c WHERE c.carVersion = :carVersion")*/})
public class Card implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @SequenceGenerator(name = "CARD_CAR_ID_GENERATOR", sequenceName = "pro.Card_seq05", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CARD_CAR_ID_GENERATOR")
    @Basic(optional = false)
    @Column(name = "CAR_ID")
    private Long id;
    @Basic(optional = false)
    @Column(name = "CAR_NUMBER")
    private Long number;
    @Basic(optional = false)
    @Column(name = "CAR_TYPE")
    private String type;
    @Version
    @Column(name = "CAR_VERSION")
    private Long version;
    @ManyToMany(mappedBy = "cards", fetch = FetchType.LAZY)
    private List<Game> games;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "card")
    private List<Stackcardxcard> stackCardxCards;

    public Card() {
    }

    public Card(Long id) {
        this.id = id;
    }

    public Card(CardDto cardDto) {
        this.id = cardDto.getId();
        update(cardDto);
    }

    public void update(CardDto cardDto) {
        this.id = cardDto.getId();
        this.number = cardDto.getNumber();
        String type = cardDto.getType();
        if (type.equalsIgnoreCase("T") || type.equalsIgnoreCase("C") || type.equalsIgnoreCase("D") || type.equalsIgnoreCase("P")) {
            this.type = type.toUpperCase();
        }
        this.version = cardDto.getVersion();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }

    public List<Stackcardxcard> getStackCardxCards() {
        return stackCardxCards;
    }

    public void setStackCardxCards(List<Stackcardxcard> stackCardxCards) {
        this.stackCardxCards = stackCardxCards;
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
        if (!(object instanceof Card)) {
            return false;
        }
        Card other = (Card) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.una.project_card.model.Card[ carId=" + id + " ]";
    }

}
