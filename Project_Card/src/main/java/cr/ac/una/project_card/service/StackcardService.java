/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.project_card.service;

import cr.ac.una.project_card.model.Stackcard;
import cr.ac.una.project_card.model.StackcardDto;
import cr.ac.una.project_card.util.EntityManagerHelper;
import cr.ac.una.project_card.util.Respuesta;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sofia
 */
public class StackcardService {

    private EntityManager em = EntityManagerHelper.getInstance().getManager();
    private EntityTransaction et;

    public Respuesta SaveStackcard(StackcardDto stackcardDto) {
        try {
            et = em.getTransaction();
            et.begin();
            Stackcard stackcard;
            if (stackcardDto.getId() != null && stackcardDto.getId() > 0) {
                et.rollback();
                return new Respuesta(false, "Este game ya existe", "SaveGame NoResultException");
            } else {
                stackcard = new Stackcard(stackcardDto);
                em.persist(stackcard);// crear un registro nuevo;
            }
            et.commit();// para guardar en pase de datos
            return new Respuesta(true, " ", " ", "Columna", new StackcardDto(stackcard));
        } catch (Exception ex) {
            et.rollback(); // lo de vuelve como estaba antes del begin si no he hecho commit
            Logger.getLogger(Stackcard.class.getName()).log(Level.SEVERE, "Error guardando la columna", ex);
            return new Respuesta(false, "Error guardando la columna.", "Columna " + ex.getMessage());
        }
    }

}
