package cr.ac.una.project_card.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author sofia
 */
public class PlayerDto {

    public StringProperty id;
    public StringProperty name;
    public StringProperty accumulatedPoint;
    public StringProperty cardStyle;
    public StringProperty cardBackImageName;
    private Long version;
    private List<AchievementDto> achievementList;
    private List<GameDto> gameList;

    public PlayerDto() {
        this.id = new SimpleStringProperty("");
        this.name = new SimpleStringProperty("");
        this.accumulatedPoint = new SimpleStringProperty("");
        this.cardStyle = new SimpleStringProperty("");
        this.cardBackImageName = new SimpleStringProperty("");
        this.gameList = new ArrayList<>();
        this.achievementList = new ArrayList<>();
    }

    public PlayerDto(String name, Long accumulatedPoint, Long cardStyle, String cardBackImageName) {
        this();
        this.name.set(name);
        this.accumulatedPoint.set(accumulatedPoint.toString());
        this.cardStyle.set(cardStyle.toString());
        this.cardBackImageName.set(cardBackImageName);
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
        if (this.id.get() != null && !this.id.get().isBlank()) { // linea de error
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

    public List<AchievementDto> getAchievementList() {
        return achievementList;
    }

    public void setAchievementList(List<AchievementDto> achievementList) {
        this.achievementList = achievementList;
    }

    public List<GameDto> getGameList() {
        return gameList;
    }

    public void setGameList(List<GameDto> gameList) {
        this.gameList = gameList;
    }

    public StringProperty getNameProperty() {
        return name;
    }

    public void setNameProperty(StringProperty name) {
        this.name = name;
    }

    public StringProperty getAccumulatedPointProperty() {
        return accumulatedPoint;
    }

    public void setAccumulatedPointProperty(StringProperty accumulatedPoint) {
        this.accumulatedPoint = accumulatedPoint;
    }

    public StringProperty getCardStyleProperty() {
        return cardStyle;
    }

    public void setCardStyleProperty(StringProperty cardStyle) {
        this.cardStyle = cardStyle;
    }

    public StringProperty getCardBackImageNameProperty() {
        return cardBackImageName;
    }

    public void setCardBackImageNameProperty(StringProperty cardBackImageName) {
        this.cardBackImageName = cardBackImageName;
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
