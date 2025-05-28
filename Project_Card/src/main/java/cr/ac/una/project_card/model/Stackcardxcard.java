/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.project_card.model;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "STACKCARDXCARD", schema = "PRO")
@NamedQueries({
    @NamedQuery(name = "Stackcardxcard.findAll", query = "SELECT s FROM Stackcardxcard s"),
    @NamedQuery(name = "Stackcardxcard.findBySxcId", query = "SELECT s FROM Stackcardxcard s WHERE s.sxcId = :sxcId"),
    @NamedQuery(name = "Stackcardxcard.findBySxcIsfaceup", query = "SELECT s FROM Stackcardxcard s WHERE s.sxcIsfaceup = :sxcIsfaceup"),
    @NamedQuery(name = "Stackcardxcard.findBySxcVersion", query = "SELECT s FROM Stackcardxcard s WHERE s.sxcVersion = :sxcVersion")})
public class Stackcardxcard implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @SequenceGenerator(name = "STACKCARDXCARD_SXC_ID_GENERATOR", sequenceName = "pro.StackCardxCard_seq06", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STACKCARDXCARD_SXC_ID_GENERATOR")
    @Basic(optional = false)
    @Column(name = "SXC_ID")
    private Long id;
    @Basic(optional = false)
    @Column(name = "SXC_ISFACEUP")
    private String isFaceUp;
    @Basic(optional = false)
    @Column(name = "SXC_VERSION")
    private Long version;
    @JoinColumn(name = "SXC_CAR_ID", referencedColumnName = "CAR_ID")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Card card;
    @JoinColumn(name = "SXC_ROC_ID", referencedColumnName = "STA_ID")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Stackcard stackCard;

    public Stackcardxcard() {
    }

    public Stackcardxcard(Long id) {
        this.id = id;
    }

    public Stackcardxcard(StackcardxcardDto stackcardxcardDto) {
        this.id = stackcardxcardDto.getId();
        update(stackcardxcardDto);
    }

    public void update(StackcardxcardDto stackcardxcardDto) {
        this.id = stackcardxcardDto.getId();
        this.isFaceUp = stackcardxcardDto.getIsFaceUp() ? "T" : "F";
        this.version = stackcardxcardDto.getVersion();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIsFaceUp() {
        return isFaceUp;
    }

    public void setIsFaceUp(String isFaceUp) {
        this.isFaceUp = isFaceUp;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public Stackcard getStackCard() {
        return stackCard;
    }

    public void setStackCard(Stackcard stackCard) {
        this.stackCard = stackCard;
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
        if (!(object instanceof Stackcardxcard)) {
            return false;
        }
        Stackcardxcard other = (Stackcardxcard) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.una.project_card.model.Stackcardxcard[ sxcId=" + id + " ]";
    }

}
