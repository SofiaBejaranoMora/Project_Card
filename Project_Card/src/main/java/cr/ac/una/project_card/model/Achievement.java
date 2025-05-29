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
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "ACHIEVEMENT", schema = "PRO")
@NamedQueries({
    @NamedQuery(name = "Achievement.findAll", query = "SELECT a FROM Achievement a"),
    /*@NamedQuery(name = "Achievement.findById", query = "SELECT a FROM Achievement a WHERE a.id = :id"),
    @NamedQuery(name = "Achievement.findByName", query = "SELECT a FROM Achievement a WHERE a.name = :name"),
    @NamedQuery(name = "Achievement.findByAchImagename", query = "SELECT a FROM Achievement a WHERE a.achImagename = :achImagename"),
    @NamedQuery(name = "Achievement.findByAchDescription", query = "SELECT a FROM Achievement a WHERE a.achDescription = :achDescription"),
    @NamedQuery(name = "Achievement.findByAchAmount", query = "SELECT a FROM Achievement a WHERE a.achAmount = :achAmount"),
    @NamedQuery(name = "Achievement.findByAchType", query = "SELECT a FROM Achievement a WHERE a.achType = :achType"),
    @NamedQuery(name = "Achievement.findByAchVersion", query = "SELECT a FROM Achievement a WHERE a.achVersion = :achVersion")*/})
public class Achievement implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @SequenceGenerator(name = "ACHIEVEMENT_ACH_ID_GENERATOR", sequenceName = "pro.Achievement_seq02", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACHIEVEMENT_ACH_ID_GENERATOR")
    @Basic(optional = false)
    @Column(name = "ACH_ID")
    private Long id;
    @Basic(optional = false)
    @Column(name = "ACH_NAME")
    private String name;
    @Basic(optional = false)
    @Column(name = "ACH_IMAGENAME")
    private String imageName;
    @Basic(optional = false)
    @Column(name = "ACH_DESCRIPTION")
    private String description;
    @Basic(optional = false)
    @Column(name = "ACH_AMOUNT")
    private Long amount;
    @Basic(optional = false)
    @Column(name = "ACH_TYPE")
    private String type;
    @Version
    @Column(name = "ACH_VERSION")
    private Long version;
    @ManyToMany(mappedBy = "achievements", fetch = FetchType.LAZY)
    private List<Player> players;

    public Achievement() {
    }

    public Achievement(Long id) {
        this.id = id;
    }

    public Achievement(AchievementDto achievementDto) {
        this.id = achievementDto.getId();
        update(achievementDto);
    }

    public void update(AchievementDto achievementDto) {
        this.id = achievementDto.getId();
        this.name = achievementDto.getName();
        this.imageName = achievementDto.getImageName();
        this.description = achievementDto.getDescription();
        this.amount = achievementDto.getAmount();
        this.type = achievementDto.getType();
        this.version = achievementDto.getVersion();
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

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long achAmount) {
        this.amount = achAmount;
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

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
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
        if (!(object instanceof Achievement)) {
            return false;
        }
        Achievement other = (Achievement) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.una.project_card.model.Achievement[ achId=" + id + " ]";
    }

}
