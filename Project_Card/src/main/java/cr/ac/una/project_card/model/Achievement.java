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
@Table(name = "ACHIEVEMENT")
@NamedQueries({
    @NamedQuery(name = "Achievement.findAll", query = "SELECT a FROM Achievement a"),
    @NamedQuery(name = "Achievement.findByAchId", query = "SELECT a FROM Achievement a WHERE a.achId = :achId"),
    @NamedQuery(name = "Achievement.findByAchName", query = "SELECT a FROM Achievement a WHERE a.achName = :achName"),
    @NamedQuery(name = "Achievement.findByAchImagename", query = "SELECT a FROM Achievement a WHERE a.achImagename = :achImagename"),
    @NamedQuery(name = "Achievement.findByAchDescription", query = "SELECT a FROM Achievement a WHERE a.achDescription = :achDescription"),
    @NamedQuery(name = "Achievement.findByAchAmount", query = "SELECT a FROM Achievement a WHERE a.achAmount = :achAmount"),
    @NamedQuery(name = "Achievement.findByAchType", query = "SELECT a FROM Achievement a WHERE a.achType = :achType"),
    @NamedQuery(name = "Achievement.findByAchVersion", query = "SELECT a FROM Achievement a WHERE a.achVersion = :achVersion")})
public class Achievement implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ACH_ID")
    private BigDecimal achId;
    @Basic(optional = false)
    @Column(name = "ACH_NAME")
    private String achName;
    @Basic(optional = false)
    @Column(name = "ACH_IMAGENAME")
    private String achImagename;
    @Basic(optional = false)
    @Column(name = "ACH_DESCRIPTION")
    private String achDescription;
    @Basic(optional = false)
    @Column(name = "ACH_AMOUNT")
    private BigInteger achAmount;
    @Basic(optional = false)
    @Column(name = "ACH_TYPE")
    private String achType;
    @Basic(optional = false)
    @Column(name = "ACH_VERSION")
    private BigInteger achVersion;

    public Achievement() {
    }

    public Achievement(BigDecimal achId) {
        this.achId = achId;
    }

    public Achievement(BigDecimal achId, String achName, String achImagename, String achDescription, BigInteger achAmount, String achType, BigInteger achVersion) {
        this.achId = achId;
        this.achName = achName;
        this.achImagename = achImagename;
        this.achDescription = achDescription;
        this.achAmount = achAmount;
        this.achType = achType;
        this.achVersion = achVersion;
    }

    public BigDecimal getAchId() {
        return achId;
    }

    public void setAchId(BigDecimal achId) {
        this.achId = achId;
    }

    public String getAchName() {
        return achName;
    }

    public void setAchName(String achName) {
        this.achName = achName;
    }

    public String getAchImagename() {
        return achImagename;
    }

    public void setAchImagename(String achImagename) {
        this.achImagename = achImagename;
    }

    public String getAchDescription() {
        return achDescription;
    }

    public void setAchDescription(String achDescription) {
        this.achDescription = achDescription;
    }

    public BigInteger getAchAmount() {
        return achAmount;
    }

    public void setAchAmount(BigInteger achAmount) {
        this.achAmount = achAmount;
    }

    public String getAchType() {
        return achType;
    }

    public void setAchType(String achType) {
        this.achType = achType;
    }

    public BigInteger getAchVersion() {
        return achVersion;
    }

    public void setAchVersion(BigInteger achVersion) {
        this.achVersion = achVersion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (achId != null ? achId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Achievement)) {
            return false;
        }
        Achievement other = (Achievement) object;
        if ((this.achId == null && other.achId != null) || (this.achId != null && !this.achId.equals(other.achId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.una.project_card.model.Achievement[ achId=" + achId + " ]";
    }
    
}
