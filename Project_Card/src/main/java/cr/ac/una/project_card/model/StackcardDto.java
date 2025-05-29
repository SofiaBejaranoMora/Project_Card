/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.project_card.model;

import java.util.List;
import java.util.Objects;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class StackcardDto {

    public StringProperty id;
    public StringProperty rowCardNumber;
    private GameDto game;
    private Long version;
    private List<StackcardxcardDto> stackCardxCards;

    public StackcardDto() {
        this.id = new SimpleStringProperty("");
        this.rowCardNumber = new SimpleStringProperty("");
    }

    public StackcardDto(Stackcard stackcard) {
        this();
        this.id.set(stackcard.getId().toString());
        this.rowCardNumber.set(stackcard.getRowCardNumber().toString());
        this.version = stackcard.getVersion();
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

    public Long getRowCardNumber() {
        if (this.rowCardNumber.get() != null & !this.rowCardNumber.get().isBlank()) {
            return Long.valueOf(rowCardNumber.get());
        }
        return null;
    }

    public void setRowCardNumber(Long rowCardNumber) {
        this.rowCardNumber.set(rowCardNumber.toString());
    }

    public GameDto getGame() {
        return game;
    }

    public void setGame(GameDto game) {
        this.game = game;
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
        return "StackcardDto{" + "rowCardNumber=" + rowCardNumber + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 61 * hash + Objects.hashCode(this.id);
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
        final StackcardDto other = (StackcardDto) obj;
        return Objects.equals(this.id, other.id);
    }

}
