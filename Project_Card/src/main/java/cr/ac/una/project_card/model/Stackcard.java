/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.project_card.model;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
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
@Table(name = "STACKCARD", schema = "PRO")
@NamedQueries({
    @NamedQuery(name = "Stackcard.findAll", query = "SELECT s FROM Stackcard s"),
    /*@NamedQuery(name = "Stackcard.findByStaId", query = "SELECT s FROM Stackcard s WHERE s.staId = :staId"),
    @NamedQuery(name = "Stackcard.findByStaVersion", query = "SELECT s FROM Stackcard s WHERE s.staVersion = :staVersion"),
    @NamedQuery(name = "Stackcard.findByStaRowcardnumber", query = "SELECT s FROM Stackcard s WHERE s.staRowcardnumber = :staRowcardnumber")*/})
public class Stackcard implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @SequenceGenerator(name = "STACKCARD_STA_ID_GENERATOR", sequenceName = "pro.StackCard_seq04", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STACKCARD_STA_ID_GENERATOR")
    @Basic(optional = false)
    @Column(name = "STA_ID")
    private Long id;
    @Version
    @Column(name = "STA_VERSION")
    private Long version;
    @Basic(optional = false)
    @Column(name = "STA_ROWCARDNUMBER")
    private Long rowCardNumber;
    @JoinColumn(name = "STA_GAM_ID", referencedColumnName = "GAM_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Game game;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "stackCard", fetch = FetchType.LAZY)
    private List<Stackcardxcard> stackCardxCards;

    public Stackcard() {
    }

    public Stackcard(Long id) {
        this.id = id;
    }

    public Stackcard(StackcardDto stackcardDto) {
        this.id = stackcardDto.getId();
        update(stackcardDto);
    }

    public void update(StackcardDto stackcardDto) {
        this.id = stackcardDto.getId();
        this.version = stackcardDto.getVersion();
        this.rowCardNumber = stackcardDto.getRowCardNumber();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Long getRowCardNumber() {
        return rowCardNumber;
    }

    public void setRowCardNumber(Long rowCardNumber) {
        this.rowCardNumber = rowCardNumber;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
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
        if (!(object instanceof Stackcard)) {
            return false;
        }
        Stackcard other = (Stackcard) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.una.project_card.model.Stackcard[ staId=" + id + " ]";
    }

}
