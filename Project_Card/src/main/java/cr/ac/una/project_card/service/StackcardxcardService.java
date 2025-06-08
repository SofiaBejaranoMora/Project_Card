/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.project_card.service;

import cr.ac.una.project_card.model.Card;
import cr.ac.una.project_card.model.CardDto;
import cr.ac.una.project_card.model.Stackcard;
import cr.ac.una.project_card.model.StackcardDto;
import cr.ac.una.project_card.model.Stackcardxcard;
import cr.ac.una.project_card.model.StackcardxcardDto;
import cr.ac.una.project_card.util.EntityManagerHelper;
import cr.ac.una.project_card.util.Respuesta;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.ArrayList;
import java.util.List;
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
                CardDto cardDto = stackcardxcardDto.getCard();
                StackcardDto stackcardDto = stackcardxcardDto.getStackCard();
                stackcardxcard = new Stackcardxcard(stackcardxcardDto);

                if (cardDto != null && stackcardDto != null) { // revisamos que la carta u columan al que se va a relacionar existab
                    Stackcard stackcard = em.find(Stackcard.class, stackcardDto.getId());
                    Card card = em.find(Card.class, cardDto.getId());

                    if (stackcard != null && card != null) { // revisamos si la columna y la carta se encontraron
                        card.getStackCardxCards().add(stackcardxcard);
                        stackcard.getStackCardxCards().add(stackcardxcard);
                        stackcardxcard.setCard(card);
                        stackcardxcard.setStackCard(stackcard);
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

    public Respuesta SaveStackcardxCardList(List<StackcardxcardDto> stackcardDtoList) {
        try {
            et = em.getTransaction();
            et.begin();
            if (stackcardDtoList.isEmpty()) {
                et.rollback();
                return new Respuesta(false, "La lista stackcardDtoList esta vacia", "SaveStackcardxCard NoResultException");
            } else {
                Stackcardxcard stackcardxcard;
                List<StackcardxcardDto> resultStackcardDtoList = new ArrayList<>();
                for (StackcardxcardDto stackcardxcardDto : stackcardDtoList) {
                    if (stackcardxcardDto.getId() != null && stackcardxcardDto.getId() > 0) {
                        et.rollback();
                        return new Respuesta(false, "Esta stackcardxCard ya existe: " + stackcardxcardDto.getId(), "SaveStackcardxCard NoResultException");
                    } else {
                        CardDto cardDto = stackcardxcardDto.getCard();
                        StackcardDto stackcardDto = stackcardxcardDto.getStackCard();
                        stackcardxcard = new Stackcardxcard(stackcardxcardDto);

                        if (cardDto == null && stackcardDto == null) {
                            et.rollback();
                            return new Respuesta(false, "La columna o la carta no existe.", "SaveStackcardxCard NoResultException");
                        }
                        // revisamos que la carta u columan al que se va a relacionar existab
                        Stackcard stackcard = em.find(Stackcard.class, stackcardDto.getId());
                        Card card = em.find(Card.class, cardDto.getId());

                        if (stackcard == null && card == null) { // revisamos si la columna y la carta se encontraron
                            et.rollback();
                            return new Respuesta(false, "No se encontro la entidad de una columna o la carta a la que asociar el stackcardxcard.", "SaveStackcardxCardList NoResultException");
                        }
                        
                        card.getStackCardxCards().add(stackcardxcard);
                        stackcard.getStackCardxCards().add(stackcardxcard);
                        stackcardxcard.setCard(card);
                        stackcardxcard.setStackCard(stackcard);

                        em.persist(stackcardxcard); // crear un registro nuevo;
                        resultStackcardDtoList.add(new StackcardxcardDto(stackcardxcard));
                    }
                }
                et.commit();// para guardar en pase de datos
                return new Respuesta(true, " ", " ", "Stackcardxcard", resultStackcardDtoList);
            }
        } catch (Exception ex) {
            et.rollback(); // lo de vuelve como estaba antes del begin si no he hecho commit
            Logger.getLogger(StackcardxcardService.class.getName()).log(Level.SEVERE, "Error guardando la stackcardxCard", ex);
            return new Respuesta(false, "Error guardando la stackcardxCard.", "StackcardxCard " + ex.getMessage());
        }
    }

    public Respuesta updateStackcardxCardList(List<StackcardxcardDto> stackcardDtoList) {
        try {
            et = em.getTransaction();
            et.begin();
            if (stackcardDtoList.isEmpty()) {
                et.rollback();
                return new Respuesta(false, "La lista stackcardDtoList esta vacia", "SaveStackcardxCard NoResultException");
            } else {
                Stackcardxcard stackcardxcard;
                if (!cleanStackCardLits(stackcardDtoList)) { // se limpean la lista de StackcardxCard que tenga el Stackcard y se devuelve un true si no hubieron errores
                    et.rollback();
                    return new Respuesta(false, "No se pudieron limpiar la lista de columnasxcartas de columnas.", "SaveStackcardxCardList NoResultException");
                }
                for (StackcardxcardDto stackcardxcardDto : stackcardDtoList) {

                    if (stackcardxcardDto.getId() != null && stackcardxcardDto.getId() > 0) {
                        StackcardDto stackcardDto = stackcardxcardDto.getStackCard();
                        stackcardxcard = em.find(Stackcardxcard.class, stackcardxcardDto.getId());

                        if (stackcardxcard == null) { // verifica que se encontrara la entidad de stackcardDto y si no se encontro tira el error
                            et.rollback();
                            return new Respuesta(false, "No se pudo encontrar la entidad de stackcardDto: " + stackcardDto.getId(), "SaveStackcardxCardList NoResultException");
                        }
                        stackcardxcard.update(stackcardxcardDto);

                        if (stackcardDto != null) { // revisamos que la carta u columan al que se va a relacionar existab
                            Stackcard stackcard = em.find(Stackcard.class, stackcardDto.getId());
                            if (stackcard == null) { // revisamos si la columna y la carta se encontraron
                                et.rollback();
                                return new Respuesta(false, "No se encontro la entidad de una columna o la carta a la que asociar el stackcardxcard.", "SaveStackcardxCardList NoResultException");
                            }
                            stackcard.getStackCardxCards().add(stackcardxcard);
                            stackcardxcard.setStackCard(stackcard);

                        } else {
                            et.rollback();
                            return new Respuesta(false, "La columna o la carta no existe.", "SaveStackcardxCard NoResultException");
                        }
                        em.merge(stackcardxcard); // actualiza un registro nuevo;

                    } else {
                        et.rollback();
                        return new Respuesta(false, "Esta stackcardxCard no existe: " + stackcardxcardDto.getId(), "SaveStackcardxCard NoResultException");
                    }
                }
            }
            et.commit();// para guardar en pase de datos
            return new Respuesta(true, " ", " ", "Stackcardxcard", null);
        } catch (Exception ex) {
            et.rollback(); // lo de vuelve como estaba antes del begin si no he hecho commit
            Logger.getLogger(StackcardxcardService.class.getName()).log(Level.SEVERE, "Error guardando la stackcardxCard", ex);
            return new Respuesta(false, "Error guardando la stackcardxCard.", "StackcardxCard " + ex.getMessage());
        }
    }

    private Boolean cleanStackCardLits(List<StackcardxcardDto> stackcardxcardDtoList) {
        Stackcardxcard stackcardxcard;
        for (StackcardxcardDto stackcardxcardDto : stackcardxcardDtoList) {
            stackcardxcard = em.find(Stackcardxcard.class, stackcardxcardDto.getId());
            if (stackcardxcard == null) {
                return false;
            }
            stackcardxcard.getStackCard().setStackCardxCards(new ArrayList<>());
        }
        return true;
    }
}
