/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.project_card.service;

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
   

    public Respuesta SavePlayer(PlayerDto playerDto) {
        try {
            et = em.getTransaction();
            et.begin();
            Player player;
            if (playerDto.getId() != null && playerDto.getId() > 0) {
                player = em.find(Player.class, playerDto.getId());
                if (player == null) {
                    return new Respuesta(false, "No se encontro el jugador a modificar", "SavePlayer NoResultadoException");
                }
                player.update(playerDto);
                player = em.merge(player);
            } else {
                Query query = em.createNamedQuery("Player.findByName");
                query.setParameter("name", playerDto.getName());
                List<Player> playerList = query.getResultList();
                if (playerList != null) {
                    et.rollback();
                    return new Respuesta(false, "El nombre del jugador ya existe.", "", "Jugador ",null);
                } else {
                    player = new Player(playerDto);
                    em.persist(player);
                }
            }
            et.commit();
            return new Respuesta(true, "", "", "Jugador", new PlayerDto(player));
        } catch (Exception ex) {
            et.rollback();
            Logger.getLogger(PlayerService.class.getName()).log(Level.SEVERE, "Error guardando el jugador[" + playerDto + "]", ex);
            return new Respuesta(false, "Error guardando el jugador.", "Jugador " + ex.getMessage());
        }
    }
}
