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
            for (Achievement achievementDto : achievementsList) {
                achievementsDtoList.add(new AchievementDto(achievementDto));
            }
            return new Respuesta(true, " ", " ", "Logros", achievementsDtoList);
        } catch (NoResultException ex) {
            return new Respuesta(false, "No existen logros.", "NoResultException/loadAllPlayer");
        } catch (Exception ex) {
            Logger.getLogger(AchievementsService.class.getName()).log(Level.SEVERE, "Error obteniendo los logros", ex);
            return new Respuesta(false, "Error obtener logros.", "loadAllAchievement" + ex.getMessage());
        }
    }
}
