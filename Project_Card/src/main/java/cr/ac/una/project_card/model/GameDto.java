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

    public StringProperty id;
    public StringProperty name;
    public StringProperty time;
    public StringProperty score;
    public StringProperty hasWon;
    public StringProperty difficulty;
    private List<CardDto> cards;
    private PlayerDto player; 
    private List<StackcardDto> stackCards;
    private Long version;
    
    public GameDto() {
        this.id = new SimpleStringProperty("");
        this.time = new SimpleStringProperty("");
        this.name=new SimpleStringProperty("");
        this.score = new SimpleStringProperty("");
        this.hasWon = new SimpleStringProperty("");
        this.difficulty = new SimpleStringProperty("");
    }
    
    public GameDto(String name, Long difficulty){
        this();
        this.name.set(name);
        this.time.set("0");
        this.score.set("500");
        this.hasWon.set("N");
        this.difficulty.set(difficulty.toString());
    }

    public GameDto(Game game) {
        this();
        this.id.set(game.getId().toString());
        this.name.set(game.getName());
        this.time.set(game.getTime().toString());
        this.score.set(game.getScore().toString());
        this.hasWon.set(game.getHasWon());
        this.difficulty.set(game.getDifficulty().toString());
        this.version=game.getVersion();
    }

    public StringProperty getIdProperty() {
        return id;
    }

    public void setIdProperty(StringProperty id) {
        this.id = id;
    }

    public StringProperty getNameProperty() {
        return name;
    }

    public void setNameProperty(StringProperty name) {
        this.name = name;
    }

    public StringProperty getTimeProperty() {
        return time;
    }

    public void setTimeProperty(StringProperty time) {
        this.time = time;
    }

    public StringProperty getScoreProperty() {
        return score;
    }

    public void setScoreProperty(StringProperty score) {
        this.score = score;
    }

    public StringProperty getHasWonProperty() {
        return hasWon;
    }

    public void setHasWonProperty(StringProperty hasWon) {
        this.hasWon = hasWon;
    }

    public StringProperty getDifficultyProperty() {
        return difficulty;
    }

    public void setDifficultyProperty(StringProperty difficulty) {
        this.difficulty = difficulty;
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

    public List<CardDto> getCards() {
        return cards;
    }

    public void setCards(List<CardDto> cards) {
        this.cards = cards;
    }

    public PlayerDto getPlayer() {
        return player;
    }

    public void setPlayer(PlayerDto player) {
        this.player = player;
    }

    public List<StackcardDto> getStackCards() {
        return stackCards;
    }

    public void setStackCards(List<StackcardDto> stackCards) {
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
