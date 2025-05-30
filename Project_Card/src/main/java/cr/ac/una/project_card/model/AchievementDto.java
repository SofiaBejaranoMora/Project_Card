package cr.ac.una.project_card.model;


import java.util.List;
import java.util.Objects;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class AchievementDto {

    public StringProperty id;
    public StringProperty name;
    public StringProperty imageName;
    public StringProperty description;
    public StringProperty amount;
    public StringProperty type;
    private Long version;
    private List<PlayerDto> players;

    public AchievementDto() {
        this.id = new SimpleStringProperty("");
        this.name = new SimpleStringProperty("");
        this.imageName = new SimpleStringProperty("");
        this.description = new SimpleStringProperty("");
        this.amount = new SimpleStringProperty("");
        this.type = new SimpleStringProperty("");
    }

    public AchievementDto(Achievement achievement) {
        this();
        this.id.set(achievement.getId().toString());
        this.name.set(achievement.getName());
        this.imageName.set(achievement.getImageName());
        this.description.set(achievement.getDescription());
        this.amount.set(achievement.getAmount().toString());
        this.type.set(achievement.getType());
        this.version = achievement.getVersion();
    }

    public Long getId() {
        if (this.id.get() != null & !this.id.get().isBlank()) {
            return Long.valueOf(id.get());
        }
        return null;
    }

    public void setId(Long id) {
        this.id.set(id.toString());
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getImageName() {
        return imageName.get();
    }

    public void setImageName(String imageName) {
        this.imageName.set(imageName);
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public Long getAmount() {
        if (this.amount.get() != null & !this.amount.get().isBlank()) {
            return Long.valueOf(amount.get());
        }
        return null;
    }

    public void setAmount(Long amount) {
        this.amount.set(amount.toString());
    }

    public String getType() {
        return type.get();
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public List<PlayerDto> getPlayes() {
        return players;
    }

    public void setPlayers(List<PlayerDto> players) {
        this.players = players;
    }

    @Override
    public String toString() {
        return type.toString();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AchievementDto other = (AchievementDto) obj;
        return Objects.equals(this.id, other.id);
    }
    
}
