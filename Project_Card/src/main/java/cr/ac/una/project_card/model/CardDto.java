
package cr.ac.una.project_card.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;

public class CardDto {
    
    public StringProperty id;
    public StringProperty number;
    public StringProperty type;
    private List<GameDto> games;
    private List<StackcardxcardDto> stackCardxCards;
    private Long version;

    public CardDto() {
        this.id = new SimpleStringProperty("");
        this.number = new SimpleStringProperty("");
        this.type = new SimpleStringProperty("");
        this.stackCardxCards = new ArrayList<>();
        this.games = new ArrayList<>();
    }

    public CardDto(Card card) {
        this();
        this.id.set(card.getId().toString());
        this.number.set(card.getNumber().toString());
        this.type.set(card.getType());
        this.version= card.getId();
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

    public Long getNumber() {
        if (this.number.get() != null && !this.number.get().isBlank()) {
            return Long.valueOf(this.number.get());
        } else {
            return null;
        }
    }

    public void setNumber(Long number) {
        this.number.set(number.toString());
    }

    public String getType() {
        return type.get();
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public List<GameDto> getGames() {
        return games;
    }

    public void setGames(List<GameDto> games) {
        this.games = games;
    }

    public List<StackcardxcardDto> getStackCardxCards() {
        return stackCardxCards;
    }

    public void setStackCardxCards(List<StackcardxcardDto> stackCardxCards) {
        this.stackCardxCards = stackCardxCards;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "CardDto{" + "number=" + number + ", type=" + type + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.id);
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
        final CardDto other = (CardDto) obj;
        return Objects.equals(this.id, other.id);
    }
}
