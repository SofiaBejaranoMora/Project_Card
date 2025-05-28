/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.project_card.model;

import java.util.List;
import java.util.Objects;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author sofia
 */
public class GameDto {

    private StringProperty id;
    private StringProperty time;
    private StringProperty score;
    private StringProperty hasWon;
    private StringProperty difficulty;
    private List<Card> cards;
    private Player player; //Cambiar a playerDto
    private List<Stackcard> stackCards;
    private Long version;
    
    public GameDto() {
        this.id = new SimpleStringProperty("");
        this.time = new SimpleStringProperty("");
        this.score = new SimpleStringProperty("");
        this.hasWon = new SimpleStringProperty("");
        this.difficulty = new SimpleStringProperty("");
    }

    public GameDto(Game game) {
        this();
        this.id.set(game.getId().toString());
        this.time.set(game.getTime().toString());
        this.score.set(game.getScore().toString());
        this.hasWon.set(game.getHasWon());
        this.difficulty.set(game.getDifficulty().toString());
        this.version=game.getVersion();
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

    public Long getTime() {
        if (this.time.get() != null & !this.time.get().isBlank()) {
            return Long.valueOf(time.get());
        }
        return null;
    }

    public void setTime(Long time) {
        this.time.set(time.toString());
    }

    public Long getScore() {
        if (this.score.get() != null & !this.score.get().isBlank()) {
            return Long.valueOf(score.get());
        }
        return null;
    }

    public void setScore(Long score) {
        this.score.set(score.toString());
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getHasWon() {
        return hasWon.get();
    }

    public void setHasWon(String hasWon) {
        this.hasWon.set(hasWon);
    }

    public Long getDifficulty() {
        if (this.difficulty.get() != null & !this.difficulty.get().isBlank()) {
            return Long.valueOf(difficulty.get());
        }
        return null;
    }

    public void setDifficulty(Long difficulty) {
        this.difficulty.set(difficulty.toString());
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public List<Stackcard> getStackCards() {
        return stackCards;
    }

    public void setStackCards(List<Stackcard> stackCards) {
        this.stackCards = stackCards;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public String toString() {
        return "GameDto{" + "time=" + time + ", score=" + score + ", difficulty=" + difficulty + '}';
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
        final GameDto other = (GameDto) obj;
        return Objects.equals(this.id, other.id);
    }
    
}
