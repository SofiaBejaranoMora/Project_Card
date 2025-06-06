/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.project_card.service;

import cr.ac.una.project_card.model.Card;
import cr.ac.una.project_card.model.CardDto;
import cr.ac.una.project_card.model.Player;
import cr.ac.una.project_card.model.Stackcard;
import cr.ac.una.project_card.model.StackcardDto;
import cr.ac.una.project_card.model.Stackcardxcard;
import cr.ac.una.project_card.model.StackcardxcardDto;
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
public class StackcardxcardService {

    private EntityManager em = EntityManagerHelper.getInstance().getManager();
    private EntityTransaction et;

    public Respuesta SaveStackcardxCard(StackcardxcardDto stackcardxcardDto) {
        try {
            et = em.getTransaction();
            et.begin();
            Stackcardxcard stackcardxcard;
            if (stackcardxcardDto.getId() != null && stackcardxcardDto.getId() > 0) {
                et.rollback();
                return new Respuesta(false, "Esta stackcardxCard ya existe", "SaveStackcardxCard NoResultException");
            } else {
                
                StackcardDto stackcardDto = stackcardxcardDto.getStackCard();
                CardDto cardDto = stackcardxcardDto.getCard();
                stackcardxcard = new Stackcardxcard(stackcardxcardDto);
                
                if (cardDto != null && stackcardDto != null) { // revisamos que la carta u columan al que se va a relacionar existab
                    Stackcard stackcard = em.find(Stackcard.class, stackcardDto.getId());
                    Card card = em.find(Card.class, cardDto.getId());

                    if (stackcard != null && card != null) { // revisamos si la columna y la carta se encontraron
                        card.getStackCardxCards().add(stackcardxcard);
                        stackcard.getStackCardxCards().add(stackcardxcard);
                        
                    } else {
                        et.rollback();
                        return new Respuesta(false, "No se encontró el jugador asociado.", "SaveGame NoResultException");
                    }
                    
                } else {
                    et.rollback();
                    return new Respuesta(false, "La columna o la carta no existe.", "SaveStackcardxCard NoResultException");
                }
                em.persist(stackcardxcard); // crear un registro nuevo;
            }
            et.commit();// para guardar en pase de datos
            return new Respuesta(true, " ", " ", "Stackcardxcard", new StackcardxcardDto(stackcardxcard));
        } catch (Exception ex) {
            et.rollback(); // lo de vuelve como estaba antes del begin si no he hecho commit
            Logger.getLogger(StackcardxcardService.class.getName()).log(Level.SEVERE, "Error guardando la stackcardxCard", ex);
            return new Respuesta(false, "Error guardando la stackcardxCard.", "StackcardxCard " + ex.getMessage());
        }
    }
}
