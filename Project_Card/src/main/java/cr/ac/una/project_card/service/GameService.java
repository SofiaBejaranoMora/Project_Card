package cr.ac.una.project_card.service;

import cr.ac.una.project_card.model.Card;
import cr.ac.una.project_card.model.CardDto;
import cr.ac.una.project_card.model.Game;
import cr.ac.una.project_card.model.GameDto;
import cr.ac.una.project_card.model.Player;
import cr.ac.una.project_card.model.PlayerDto;
import cr.ac.una.project_card.model.StackcardDto;
import cr.ac.una.project_card.util.EntityManagerHelper;
import cr.ac.una.project_card.util.Respuesta;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.Query;
import java.util.List;
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
            GameDto gameDto = new GameDto((Game) qryGame.getSingleResult());
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

    public Respuesta SaveGame(GameDto gameDto, PlayerDto playerDto/*, List<CardDto> cardDtoList , Lis<columnas> stackCardDtoList*/ ) {
        try {
            et = em.getTransaction();
            et.begin();
            Game game;
            Query query = em.createNamedQuery("Game.findByNamePlayerId");
            query.setParameter("playerId", playerDto.getId());
            query.setParameter("name", gameDto.getName());
            List<Game> gameList = query.getResultList();//qryUsuario.getResultList()-> este para mas de un registro, y el que puse es para solo un unico registro
            if (!gameList.isEmpty()) {
                et.rollback();
                return new Respuesta(false, "El nombre del juego ya existe.", "", "Partida ", null);
            } else {
                if (gameDto.getId() != null && gameDto.getId() > 0) {
                    et.rollback();
                    return new Respuesta(false, "Este game ya existe", "SaveGame NoResultException");
                } else {
                    game = new Game(gameDto);
                    if (playerDto.getId() != null) {
                        Player player = em.find(Player.class, playerDto.getId());
                        if (player == null) {
                            et.rollback();
                            return new Respuesta(false, "No se encontró el jugador asociado.", "SaveGame NoResultException");
                        }
                        player.getGames().add(game);
                        game.setPlayer(player);
                        
//                        for (CardDto cardDto : cardDtoList) {
//                            game.getCards().add(em.find(Card.class, cardDto.getId())); 
//                        }
//                        for (StackcardDto stackcardDto : stackCardDtoList) {
//                            game.getStackCards().add(em.find(Card.class, stackcardDto.getId()));
//                        }
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
            et.rollback();
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
