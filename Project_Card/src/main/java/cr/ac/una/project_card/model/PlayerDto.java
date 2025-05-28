package cr.ac.una.project_card.model;

import java.util.List;
import java.util.Objects;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author sofia
 */
public class PlayerDto {

    private StringProperty id;
    private StringProperty name;
    private StringProperty accumulatedPoint;
    private StringProperty cardStyle;
    private StringProperty cardBackImageName;
    private Long version;
    private List<Achievement> achievementList;
    private List<Game> gameList;

    public PlayerDto() {
        this.id = new SimpleStringProperty("");
        this.name = new SimpleStringProperty("");
        this.accumulatedPoint = new SimpleStringProperty("");
        this.cardStyle = new SimpleStringProperty("");
        this.cardBackImageName = new SimpleStringProperty("");
    }

    public PlayerDto(Player player) {
        this();
        this.id.set(player.getId().toString());
        this.name.set(player.getName());
        this.accumulatedPoint.set(player.getAccumulatedpoint().toString());
        this.cardStyle.set(player.getCardstyle().toString());
        this.cardBackImageName.set(player.getCardBackImageName());
        this.version = player.getVersion();
    }

    public Long getId() {
        if (this.id.get() != null && !this.id.get().isBlank()) {
            return Long.valueOf(this.id.get());
        } else {
            return null;
        }
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

    public Long getAccumulatedPoint() {
         if (this.accumulatedPoint.get() != null && !this.accumulatedPoint.get().isBlank()) {
            return Long.valueOf(this.accumulatedPoint.get());
        } else {
            return null;
        }
    }

    public void setAccumulatedPoint(Long accumulatedPoint) {
       this.accumulatedPoint.set(accumulatedPoint.toString());
    }

    public Long getCardStyle() {
         if (this.cardStyle.get() != null && !this.cardStyle.get().isBlank()) {
            return Long.valueOf(this.cardStyle.get());
        } else {
            return null;
        }
    }

    public void setCardStyle(Long cardStyle) {
        this.cardStyle.set(cardStyle.toString());
    }

    public String getCardBackImageName() {
        return cardBackImageName.get();
    }

    public void setCardBackImageName(String cardBackImageName) {
        this.cardBackImageName.set(cardBackImageName);
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public List<Achievement> getAchievementList() {
        return achievementList;
    }

    public void setAchievementList(List<Achievement> achievementList) {
        this.achievementList = achievementList;
    }

    public List<Game> getGameList() {
        return gameList;
    }

    public void setGameList(List<Game> gameList) {
        this.gameList = gameList;
    }

    @Override
    public String toString() {
        return "PlayerDto{" + "name=" + name + ", accumulatedPoint=" + accumulatedPoint + ", cardStyle=" + cardStyle + ", cardBackImageName=" + cardBackImageName + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 11 * hash + Objects.hashCode(this.id);
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
        final PlayerDto other = (PlayerDto) obj;
        return Objects.equals(this.id, other.id);
    }
    
}
