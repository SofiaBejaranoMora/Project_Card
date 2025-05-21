/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.project_card.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CardDto {

    private StringProperty id;
    private StringProperty number;
    private StringProperty type;
    private Long version;

    public CardDto() {
        this.id =  new SimpleStringProperty("");
        this.number = new SimpleStringProperty("");
        this.type = new SimpleStringProperty("");
    }

    public CardDto(Card card) {
        this.id.set(card.getId().toString());
        this.number.set(card.getNumber().toString());
        this.type.set(card.getType());
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

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public StringProperty getIdProperty () {
        return id;
    }

    public void setIdProperty (StringProperty id) {
        this.id = id;
    }

    public StringProperty getNumberProperty () {
        return number;
    }

    public void setNumberProperty (StringProperty number) {
        this.number = number;
    }

    public StringProperty getTypeProperty () {
        return type;
    }

    public void setTypeProperty (StringProperty type) {
        this.type = type;
    }

    public Long getVersionProperty () {
        return version;
    }

    public void setVersionProperty(Long version) {
        this.version = version;
    }
}
