package cr.ac.una.project_card.service;

import cr.ac.una.project_card.model.Card;
import cr.ac.una.project_card.model.CardDto;
import cr.ac.una.project_card.model.Game;
import cr.ac.una.project_card.model.GameDto;
import cr.ac.una.project_card.model.Player;
import cr.ac.una.project_card.model.PlayerDto;
import cr.ac.una.project_card.model.Stackcard;
import cr.ac.una.project_card.model.StackcardDto;
import cr.ac.una.project_card.model.Stackcardxcard;
import cr.ac.una.project_card.model.StackcardxcardDto;
import cr.ac.una.project_card.util.EntityManagerHelper;
import cr.ac.una.project_card.util.Respuesta;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.Query;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * * * @author ashly
 */
public class GameService {

    private EntityManager em = EntityManagerHelper.getInstance().getManager();
    private EntityTransaction et;

    public Respuesta getGameID(Long id) {
        try {
            Query qryGame = em.createNamedQuery("Game.findById", Game.class);
            qryGame.setParameter("id", id);
            Game game = (Game) qryGame.getSingleResult();
            GameDto gameDto = new GameDto(game);
            List<StackcardDto> stackcardDtoList = new ArrayList<>();
            for (Card card : game.getCards()) {
                gameDto.getCards().add(new CardDto(card));
            }
            for (Stackcard stackcard : game.getStackCards()) {
                List<StackcardxcardDto> stackcardxcardDtoList = new ArrayList<>();
                for (Stackcardxcard stackcardxcard : stackcard.getStackCardxCards()) {
                    stackcardxcardDtoList.add(new StackcardxcardDto(stackcardxcard));
                }
                StackcardDto stackcardDto = new StackcardDto(stackcard);
                Collections.sort(stackcardxcardDtoList, Comparator.comparing(StackcardxcardDto::getPositionNumber));
                stackcardDto.setStackCardxCards(stackcardxcardDtoList);
                stackcardDtoList.add(stackcardDto);
            }
            Collections.sort(stackcardDtoList, Comparator.comparing(StackcardDto::getRowCardNumber)); // ordena las columnas por el numero de columna
            gameDto.setStackCards(stackcardDtoList);
            return new Respuesta(true, "", "", "Partida", gameDto);
        } catch (NoResultException ex) {
            return new Respuesta(false, "No existe una partida con el id ingresado.", "getGameID NoResultException");
        } catch (NonUniqueResultException ex) {
            Logger.getLogger(GameService.class.getName()).log(Level.SEVERE, "Ocurrio un error al consultar la partida.", ex);
            return new Respuesta(false, "Ocurrio un error al consultar la partida.", "getGameID NonUniqueResultException");
        } catch (Exception ex) {
            Logger.getLogger(GameService.class.getName()).log(Level.SEVERE, "Error obteniendo la partida [" + id + "]", ex);
            return new Respuesta(false, "Error obteniendo La partida.", "getGameID " + ex.getMessage());
        }
    }

    public Respuesta getGameName(String name) {
        try {
            Query qryGame = em.createNamedQuery("Game.findByName", Game.class);
            qryGame.setParameter("name", name);
            Game game = (Game) qryGame.getSingleResult();
            GameDto gameDto = new GameDto(game);
            return new Respuesta(true, " ", " ", "Partida", gameDto);
        } catch (NoResultException ex) {
            return new Respuesta(false, "No existe una partida bajo ese nombre.", "getGameName NoResultException");
        } catch (NonUniqueResultException ex) {
            Logger.getLogger(GameService.class.getName()).log(Level.SEVERE, "Ocurrio un error al consultar la partida.", ex);
            return new Respuesta(false, "Ocurrio un error al consultar la partida.", "getGameName NonUniqueResultException");
        } catch (Exception ex) {
            Logger.getLogger(GameService.class.getName()).log(Level.SEVERE, "Error obteniendo la partida[" + name + "]", ex);
            return new Respuesta(false, "Error obtener la partida.", "getGameId " + ex.getMessage());
        }
    }

    public Respuesta SaveGame(GameDto gameDto, PlayerDto playerDto, List<CardDto> cardDtoList, List<StackcardDto> stackcardDtoList) {
        try {
            et = em.getTransaction();
            et.begin();
            Game game;
            Query query = em.createNamedQuery("Game.findByNamePlayerId");
            query.setParameter("playerId", playerDto.getId());
            query.setParameter("name", gameDto.getName());
            List<Game> gameList = query.getResultList();//qryUsuario.getResultList()-> este para mas de un registro, y el que puse es para solo un unico registro
            if (!gameList.isEmpty()) { // revisa si hay games con ese nombre en el mismo jugador
                et.rollback();
                return new Respuesta(false, "El nombre del juego ya existe.", "", "Partida ", null);
            } else { // despues de verificar que no haya un game con ese nombre

                if (gameDto.getId() != null && gameDto.getId() > 0) { //Se revisa si el gameDto  id
                    et.rollback();
                    return new Respuesta(false, "Este game ya existe", "SaveGame NoResultException");
                } else { // si no tiene id empezamos con la parte crear el 
                    game = new Game(gameDto);

                    if (playerDto.getId() != null && !cardDtoList.isEmpty() && !stackcardDtoList.isEmpty()) {// revisa que el jugador, mazo y columnas a la que se va a relacionar exista
                        Player player = em.find(Player.class, playerDto.getId());

                        if (player == null) { //Ve si el jugador se encontro
                            et.rollback();
                            return new Respuesta(false, "No se encontró el jugador asociado.", "SaveGame NoResultException");
                        }
                        player.getGames().add(game);
                        game.setPlayer(player);

                        Set<Long> idsVistas = new HashSet<>();
                        for (CardDto cardDto : cardDtoList) { //Se relacionan todas las cartas del mazo al juego
                            Long id = cardDto.getId();
                            if (!idsVistas.add(id)) {
                                System.out.println("⚠️ Carta duplicada con ID: " + id);
                                continue;
                            }
                            Card card = em.find(Card.class, cardDto.getId());
                            if (card == null) { //Ve si el carta se encontro
                                et.rollback();
                                return new Respuesta(false, "No se encontró el carta asociado.", "SaveGame NoResultException");
                            }
                            game.getCards().add(card);
                        }
                        for (StackcardDto stackcardDto : stackcardDtoList) { // se relacionan todas columnas al juego
                            Stackcard stackcard = em.find(Stackcard.class, stackcardDto.getId());
                            if (stackcard == null) { //Ve si el carta se encontro
                                et.rollback();
                                return new Respuesta(false, "No se encontró el columna asociado.", "SaveGame NoResultException");
                            }
                            stackcard.setGame(game);
                            game.getStackCards().add(stackcard);
                        }
                    } else {
                        et.rollback();
                        return new Respuesta(false, "El jugador no existe.", "SaveGame NoResultException");
                    }
                    em.persist(game);
                }
                et.commit();
                return new Respuesta(true, "", "", "Partida", new GameDto(game));
            }
        } catch (Exception ex) {
            et.rollback();// error
            Logger.getLogger(GameService.class.getName()).log(Level.SEVERE, "Error guardando la partida[" + gameDto + "]", ex);
            return new Respuesta(false, "Error guardando la partida.", "Partida " + ex.getMessage());
        }
    }

    public Respuesta EditGameId(GameDto gameDto) {
        try {
            et = em.getTransaction();
            et.begin();
            Game game;
            if (gameDto.getId() != null && gameDto.getId() > 0) {
                game = em.find(Game.class, gameDto.getId());
                if (game == null) {
                    et.rollback();
                    return new Respuesta(false, "No se encontró la partida a actualizar", "EditGameId NoResultException");
                }
                game.update(gameDto);
                game = em.merge(game);
            } else {
                game = new Game(gameDto);
                em.persist(game);
            }
            et.commit();
            return new Respuesta(true, "", "", "Partida", new GameDto(game));

        } catch (Exception ex) {
            et.rollback();
            Logger.getLogger(GameService.class.getName()).log(Level.SEVERE, "Error guardando la partida[" + gameDto + "]", ex);
            return new Respuesta(false, "Error guardando la partida.", "Partida " + ex.getMessage());
        }
    }
}
