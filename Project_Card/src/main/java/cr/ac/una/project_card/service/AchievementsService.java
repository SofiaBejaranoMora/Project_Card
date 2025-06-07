/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.project_card.service;

import cr.ac.una.project_card.model.Achievement;
import cr.ac.una.project_card.model.AchievementDto;
import cr.ac.una.project_card.model.Player;
import cr.ac.una.project_card.model.PlayerDto;
import cr.ac.una.project_card.util.EntityManagerHelper;
import cr.ac.una.project_card.util.Respuesta;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AchievementsService {

    private EntityManager em = EntityManagerHelper.getInstance().getManager();
    private EntityTransaction et;

    public Respuesta loadAllAchievement() {
        try {
            Query queryAchievements = em.createNamedQuery("Achievement.findAll", Achievement.class);
            List<Achievement> achievementsList = queryAchievements.getResultList();
            List<AchievementDto> achievementsDtoList = new ArrayList<>();
            for (Achievement achievement : achievementsList) {
                achievementsDtoList.add(new AchievementDto(achievement));
            }
            return new Respuesta(true, " ", " ", "Logros", achievementsDtoList);
        } catch (NoResultException ex) {
            return new Respuesta(false, "No existen logros.", "NoResultException/loadAllPlayer");
        } catch (Exception ex) {
            Logger.getLogger(AchievementsService.class.getName()).log(Level.SEVERE, "Error obteniendo los logros", ex);
            return new Respuesta(false, "Error obtener logros.", "Logro" + ex.getMessage());
        }
    }

    public Respuesta addPlayerAchieveme(PlayerDto playerDto, String name) {
        try {
            et = em.getTransaction();
            et.begin();
            Player player;
            Achievement achievement ;
            if (playerDto.getId() != null && playerDto.getId() > 0) {
                player = em.find(Player.class, playerDto.getId());
                if (player == null) {
                    return new Respuesta(false, "No se encontró el empleado a guardar", "guardarEmpleado NoResultException");
                }
                Query qryAchieveme = em.createNamedQuery("Achievement.findByName", Achievement.class);
                qryAchieveme.setParameter("name", name);
                achievement = (Achievement) qryAchieveme.getSingleResult();
                if (achievement == null) {
                    et.rollback();
                    return new Respuesta(false, "No se encontró el logro: "+ name, "addPlayerAchieveme NoResultException");
                } 
                player.getAchievements().add(achievement);
                achievement.getPlayers().add(player);
                em.merge(achievement);// devuelve el objetoa ctualizado
            } else {
                et.rollback();
                return new Respuesta(false, "No se encontró el jugador al cual agregar el logro", "addPlayerAchieveme NoResultException");
            }
            et.commit();// para guardar en pase de datos
            return new Respuesta(true, " ", " ", "Logro", new AchievementDto(achievement));

        } catch (Exception ex) {
            et.rollback(); // lo de vuelve como estaba antes del begin si no he hecho commit
            Logger.getLogger(AchievementsService.class.getName()).log(Level.SEVERE, "Error guardando la planilla", ex);
            return new Respuesta(false, "Error agreando el jugador al logro .", "Logro " + ex.getMessage());
        }
    }

    public Respuesta getAchievemenSearchParameter(String name, String type,Long id, Boolean enablePlayer) {
        try {
            Query queryAchievements;
            if (enablePlayer){
                 queryAchievements = em.createNamedQuery("Achievement.findByNameAndTypePlayerId", Achievement.class);
            }
            else{
                queryAchievements = em.createNamedQuery("Achievement.findByNameAndTypeNotPlayerID", Achievement.class);
            }
            
            queryAchievements.setParameter("name", "%" + name + "%");
            queryAchievements.setParameter("type", "%" + type + "%");
            queryAchievements.setParameter("playerId", id);
            List<AchievementDto> achievementsDtoList = new ArrayList<>();
            List<Achievement> achievementsList = queryAchievements.getResultList();
            for (Achievement achievement : achievementsList) {
                achievementsDtoList.add(new AchievementDto(achievement));
            }
            return new Respuesta(true, " ", " ", "Logro", achievementsDtoList);
        } catch (NoResultException ex) {
            return new Respuesta(false, "No existe un logro con las credenciales ingresadas.", "getAchievementParameter NoResultException");
        } catch (Exception ex) {
            Logger.getLogger(AchievementsService.class.getName()).log(Level.SEVERE, "Error obteniendo el logro  [" + name + type + "]", ex);
            return new Respuesta(false, "Error obtener el logro.", "Logro " + ex.getMessage());
        }
    }

    public Respuesta getAchievemenNoPlayerId(Long id) {
        try {
            Query queryAchievements = em.createNamedQuery("Achievement.findByNameIdNot", Achievement.class);
            queryAchievements.setParameter("playerId", id);
            List<AchievementDto> achievementsDtoList = new ArrayList<>();
            List<Achievement> achievementsList = queryAchievements.getResultList();
            for (Achievement achievement : achievementsList) {
                achievementsDtoList.add(new AchievementDto(achievement));
            }
            return new Respuesta(true, " ", " ", "Logro", achievementsDtoList);
        } catch (NoResultException ex) {
            return new Respuesta(false, "No existe logro no obtenido con las credenciales ingresadas.", "getAchievemenNoPlayerId NoResultException");
        } catch (Exception ex) {
            Logger.getLogger(AchievementsService.class.getName()).log(Level.SEVERE, "Error obteniendo los logros no obtenidos de  [" + id + "]", ex);
            return new Respuesta(false, "Error obtener el logro.", "Logro " + ex.getMessage());
        }
    }
}
