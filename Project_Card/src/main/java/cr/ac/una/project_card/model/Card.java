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
@Table(name = "CARD")
@NamedQueries({
    @NamedQuery(name = "Card.findAll", query = "SELECT c FROM Card c"),
    @NamedQuery(name = "Card.findById", query = "SELECT c FROM Card c WHERE c.id = :id"),
    @NamedQuery(name = "Card.findByNumber", query = "SELECT c FROM Card c WHERE c.Number = :Number"),
    @NamedQuery(name = "Card.findByType", query = "SELECT c FROM Card c WHERE c.Type = :Type"),
    @NamedQuery(name = "Card.findByVersion", query = "SELECT c FROM Card c WHERE c.Version = :Version")})
public class Card implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "CAR_ID")
    private Long id;
    @Basic(optional = false)
    @Column(name = "CAR_NUMBER")
    private Long number;
    @Basic(optional = false)
    @Column(name = "CAR_TYPE")
    private String type;
    @Basic(optional = false)
    @Column(name = "CAR_VERSION")
    private Long version;

    public Card() {
    }

    public Card(Long carId) {
        this.id = carId;
    }

    public Card(CardDto cardDto) {
        this.id = cardDto.getId();
        actualizar(cardDto);
    }

    public void actualizar(CardDto cardDto) {
        this.id = cardDto.getId();
        this.number = cardDto.getNumber();
        this.type = cardDto.getType();
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
