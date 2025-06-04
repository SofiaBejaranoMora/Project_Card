/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.project_card.service;

import cr.ac.una.project_card.model.Achievement;
import cr.ac.una.project_card.model.AchievementDto;
import cr.ac.una.project_card.model.Game;
import cr.ac.una.project_card.model.GameDto;
import cr.ac.una.project_card.model.Player;
import cr.ac.una.project_card.model.PlayerDto;
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
 *
 * @author sofia
 */
public class PlayerService {

    private EntityManager em = EntityManagerHelper.getInstance().getManager();
    private EntityTransaction et;
    
        public Respuesta getPlayerId(Long id) {
        try {
            Query qryPlayer = em.createNamedQuery("Player.findById", Player.class);
            qryPlayer.setParameter("id", id);
            Player player = (Player) qryPlayer.getSingleResult();
            PlayerDto playerDto = new PlayerDto(player);
            for (Game game : player.getGames()) {
                playerDto.getGameList().add(new GameDto(game));
            }
            for (Achievement achievement : player.getAchievements()) {
                playerDto.getAchievementList().add(new AchievementDto(achievement));
            }
            return new Respuesta(true, " ", " ", "Jugador", playerDto);
        } catch (NoResultException ex) {
            return new Respuesta(false, "No existe un jugador con las credenciales ingresadas.", "getPlayerName NoResultException");
        } catch (NonUniqueResultException ex) {
            Logger.getLogger(PlayerService.class.getName()).log(Level.SEVERE, "Ocurrio un error al consultar el jugador.", ex);
            return new Respuesta(false, "Ocurrio un error al consultar el jugador.", "getPlayerName NonUniqueResultException");
        } catch (Exception ex) {
            Logger.getLogger(PlayerService.class.getName()).log(Level.SEVERE, "Error obteniendo el jugador  [" + id + "]", ex);
            return new Respuesta(false, "Error obtener el jugador.", "getPlayerId " + ex.getMessage());
        }
    }

    public Respuesta getPlayerName(String name) {
        try {
            Query qryPlayer = em.createNamedQuery("Player.findByName", Player.class);
            qryPlayer.setParameter("name", name);
            Player player = (Player) qryPlayer.getSingleResult();
            PlayerDto playerDto = new PlayerDto(player);
            for (Game game : player.getGames()) {
                playerDto.getGameList().add(new GameDto(game));
            }
            for (Achievement achievement : player.getAchievements()) {
                playerDto.getAchievementList().add(new AchievementDto(achievement));
            }
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

    public Respuesta SavePlayer(PlayerDto playerDto) {
        try {
            et = em.getTransaction();
            et.begin();
            Player player;
            Query query = em.createNamedQuery("Player.findByName");
            query.setParameter("name", playerDto.getName());
            List<Player> playerList = query.getResultList();//qryUsuario.getResultList()-> este para mas de un registro, y el que puse es para solo un unico registro
            if (!playerList.isEmpty()) {
                et.rollback();
                return new Respuesta(false, "El nombre del jugador ya existe.", "", "Jugador ", null);
            } else {
                if (playerDto.getId() != null && playerDto.getId() > 0) {
                    player = em.find(Player.class, playerDto.getId());
                    if (player == null) {
                        et.rollback();
                        return new Respuesta(false, "No se encontró el jugador a modificar", "SavePlayer NoResultException");
                    }
                    player.update(playerDto);
                    player = em.merge(player);
                } else {
                    player = new Player(playerDto);
                    em.persist(player);
                }
                et.commit();
                return new Respuesta(true, "", "", "Jugador", new PlayerDto(player));
            }
        } catch (Exception ex) {
            et.rollback();
            Logger.getLogger(PlayerService.class.getName()).log(Level.SEVERE, "Error guardando el jugador[" + playerDto + "]", ex);
            return new Respuesta(false, "Error guardando el jugador.", "Jugador " + ex.getMessage());
        }
    }

    public Respuesta EditPlayerId(PlayerDto playerDto) {
        try {
            et = em.getTransaction();
            et.begin();
            Player player;
            if (playerDto.getId() != null && playerDto.getId() > 0) {
                player = em.find(Player.class, playerDto.getId());
                if (player == null) {
                    et.rollback();
                    return new Respuesta(false, "No se encontró el jugador a modificar", "SavePlayer NoResultException");
                }
                player.update(playerDto);
                player = em.merge(player);
            } else {
                player = new Player(playerDto);
                em.persist(player);
            }
            et.commit();
            return new Respuesta(true, "", "", "Jugador", new PlayerDto(player));

        } catch (Exception ex) {
            et.rollback();
            Logger.getLogger(PlayerService.class.getName()).log(Level.SEVERE, "Error guardando el jugador[" + playerDto + "]", ex);
            return new Respuesta(false, "Error guardando el jugador.", "Jugador " + ex.getMessage());
        }
    }

    public Respuesta findPlayerByName(String name) {
        try {
            Query query = em.createNamedQuery("Player.findByName");
            query.setParameter("name", name);
            List<Player> playerList = query.getResultList();

            if (!playerList.isEmpty()) {
                return new Respuesta(true, "Usuario encontrado", "", "Jugador", playerList.get(0));
            } else {
                return new Respuesta(false, "Usuario no encontrado", "", "Jugador", null);
            }
        } catch (Exception ex) {
            Logger.getLogger(PlayerService.class.getName()).log(Level.SEVERE, "Error buscando el jugador[" + name + "]", ex);
            return new Respuesta(false, "Error buscando el jugador.", "Jugador " + ex.getMessage());
        }
    }
}
