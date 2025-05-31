package cr.ac.una.project_card.service;

import cr.ac.una.project_card.model.Game;
import cr.ac.una.project_card.model.GameDto;
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

/** * * @author ashly */
public class GameService {
    
    private EntityManager em = EntityManagerHelper.getInstance().getManager();
    private EntityTransaction et;

    public Respuesta getPlayerName(String name) {
        try {
            Query qryGame = em.createNamedQuery("Player.findByName", Game.class);
            qryGame.setParameter("name", name);
            Game game = (Game) qryGame.getSingleResult();
            GameDto playerDto = new GameDto(game);
            return new Respuesta(true, " ", " ", "Jugador", playerDto);
        } catch (NoResultException ex) {
            return new Respuesta(false, "No existe un jugador con las credenciales ingresadas.", "getPlayerName NoResultException");
        } catch (NonUniqueResultException ex) {
            Logger.getLogger(PlayerService.class.getName()).log(Level.SEVERE, "Ocurrio un error al consultar el jugador.", ex);
            return new Respuesta(false, "Ocurrio un error al consultar el jugador.", "getPlayerName NonUniqueResultException");
        } catch (Exception ex) {
            Logger.getLogger(PlayerService.class.getName()).log(Level.SEVERE, "Error obteniendo el jugador  [" + name + "]", ex);
            return new Respuesta(false, "Error obtener el jugador.", "getPlayerId " + ex.getMessage());
        }
    }

    public Respuesta SavePlayer(GameDto gameDto) {
        try {
            et = em.getTransaction();
            et.begin();
            Game player;
            Query query = em.createNamedQuery("Player.findByName");
            //query.setParameter("name", gameDto.getName());
            List<Game> playerList = query.getResultList();//qryUsuario.getResultList()-> este para mas de un registro, y el que puse es para solo un unico registro
            if (!playerList.isEmpty()) {
                et.rollback();
                return new Respuesta(false, "El nombre del jugador ya existe.", "", "Jugador ", null);
            } else {
                if (gameDto.getId() != null && gameDto.getId() > 0) {
                    player = em.find(Game.class, gameDto.getId());
                    if (player == null) {
                        et.rollback();
                        return new Respuesta(false, "No se encontró el jugador a modificar", "SavePlayer NoResultException");
                    }
                    player.update(gameDto);
                    player = em.merge(player);
                } else {
                    player = new Game(gameDto);
                    em.persist(player);
                }
                et.commit();
                return new Respuesta(true, "", "", "Jugador", new GameDto(player));
            }
        } catch (Exception ex) {
            et.rollback();
            Logger.getLogger(PlayerService.class.getName()).log(Level.SEVERE, "Error guardando el jugador[" + gameDto + "]", ex);
            return new Respuesta(false, "Error guardando el jugador.", "Jugador " + ex.getMessage());
        }
    }

    public Respuesta EditPlayerId(GameDto gameDto) {
        try {
            et = em.getTransaction();
            et.begin();
            Game player;
            if (gameDto.getId() != null && gameDto.getId() > 0) {
                player = em.find(Game.class, gameDto.getId());
                if (player == null) {
                    et.rollback();
                    return new Respuesta(false, "No se encontró el jugador a modificar", "SavePlayer NoResultException");
                }
                player.update(gameDto);
                player = em.merge(player);
            } else {
                player = new Game(gameDto);
                em.persist(player);
            }
            et.commit();
            return new Respuesta(true, "", "", "Jugador", new GameDto(player));

        } catch (Exception ex) {
            et.rollback();
            Logger.getLogger(PlayerService.class.getName()).log(Level.SEVERE, "Error guardando el jugador[" + gameDto + "]", ex);
            return new Respuesta(false, "Error guardando el jugador.", "Jugador " + ex.getMessage());
        }
    }
}
