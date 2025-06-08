package cr.ac.una.project_card.model;

import java.util.Objects;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class StackcardxcardDto {

    public StringProperty id;
    public BooleanProperty isFaceUp;
    public StringProperty positionNumber;
    private Long version;
    private CardDto card;
    private StackcardDto stackCard;

    public StackcardxcardDto() {
        this.id = new SimpleStringProperty("");
        this.isFaceUp = new SimpleBooleanProperty(false);
        this.positionNumber = new SimpleStringProperty("");
    }
    
    public StackcardxcardDto(Boolean isFaceUp, Long positionNumber) {
        this();
        this.isFaceUp.set(isFaceUp);
        this.positionNumber.set(positionNumber.toString());
    }

    public StackcardxcardDto(Stackcardxcard stackcardxcard) {
        this();
        this.id.set(stackcardxcard.getId().toString());
        this.isFaceUp.set(stackcardxcard.getIsFaceUp().equals("T"));
        this.positionNumber.set(stackcardxcard.getPositionNumber().toString());
        this.card=new CardDto(stackcardxcard.getCard());
        this.stackCard=new StackcardDto(stackcardxcard.getStackCard());
        this.version = stackcardxcard.getVersion();
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

    public Boolean getIsFaceUp() {
        return isFaceUp.get();
    }

    public void setIsFaceUp(Boolean isFaceUp) {
        this.isFaceUp.set(isFaceUp);
    }

    public Long getPositionNumber() {
        if (this.positionNumber.get() != null && !this.positionNumber.get().isBlank()) {
            return Long.valueOf(this.positionNumber.get());
        } else {
            return null;
        }
    }

    public void setPositionNumber(Long positionNumber) {
        this.positionNumber.set(positionNumber.toString());
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public CardDto getCard() {
        return card;
    }

    public void setCard(CardDto card) {
        this.card = card;
    }

    public StackcardDto getStackCard() {
        return stackCard;
    }

    public void setStackCard(StackcardDto stackCard) {
        this.stackCard = stackCard;
    }

    @Override
    public String toString() {
        return "StackcardxcardDto{" + "isFaceUp=" + isFaceUp + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.id);
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
        final StackcardxcardDto other = (StackcardxcardDto) obj;
        return Objects.equals(this.id, other.id);
    }
    
    

}
