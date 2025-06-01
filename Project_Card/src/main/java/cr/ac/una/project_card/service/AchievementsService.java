/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.project_card.service;

import cr.ac.una.project_card.model.Achievement;
import cr.ac.una.project_card.model.AchievementDto;
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
            return new Respuesta(false, "Error obtener logros.", "loadAllAchievement" + ex.getMessage());
        }
    }

    public Respuesta getAchievementType(String type) {
        try {
            Query queryAchievements = em.createNamedQuery("Achievement.findByType", Achievement.class);
            queryAchievements.setParameter("type", type);
            List<AchievementDto> achievementsDtoList = new ArrayList<>();
            List<Achievement> achievementsList = queryAchievements.getResultList();
            for (Achievement achievement : achievementsList) {
                achievementsDtoList.add(new AchievementDto(achievement));
            }
            return new Respuesta(true, " ", " ", "Logros", achievementsDtoList);
        } catch (NoResultException ex) {
            return new Respuesta(false, "No existe logros con las credenciales ingresadas.", "NoResultException/getAchievementType");
        } catch (Exception ex) {
            Logger.getLogger(AchievementsService.class.getName()).log(Level.SEVERE, "Error obteniendo las logros", ex);
            return new Respuesta(false, "Error obtener logros.", "getAchievementType" + ex.getMessage());
        }
    }
}

