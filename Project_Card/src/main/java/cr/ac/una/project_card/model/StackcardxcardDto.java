
package cr.ac.una.project_card.model;

import java.util.Objects;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class StackcardxcardDto {

    public StringProperty id;
    public BooleanProperty isFaceUp;
    private Long version;
    private Card card;
    private Stackcard stackCard;

    public StackcardxcardDto() {
        this.id =  new SimpleStringProperty("");
        this.isFaceUp = new SimpleBooleanProperty(false);
    }

    public StackcardxcardDto(Stackcardxcard stackcardxcard) {
         this();
        this.id.set(stackcardxcard.toString());
        this.isFaceUp.set(stackcardxcard.getIsFaceUp().equals("T"));
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
