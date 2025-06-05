package cr.ac.una.project_card.service;

import cr.ac.una.project_card.model.Card;
import cr.ac.una.project_card.model.CardDto;
import cr.ac.una.project_card.model.Player;
import cr.ac.una.project_card.util.EntityManagerHelper;
import cr.ac.una.project_card.util.Respuesta;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sofia
 */
public class CardService {

    private EntityManager em = EntityManagerHelper.getInstance().getManager();
    private EntityTransaction et;
    
    public Respuesta getCardId(Long id) {
        try {
            Query queryCard = em.createNamedQuery("Card.findById", Card.class);
            queryCard.setParameter("id", id);
            CardDto cardDto= new CardDto((Card)queryCard.getSingleResult());
            return new Respuesta(true, " ", " ", "Carta", cardDto);
        } catch (NoResultException ex) {
            return new Respuesta(false, "No existe cartas con las credenciales ingresadas.", "NoResultException/getCardType");
        } catch (Exception ex) {
            Logger.getLogger(CardService.class.getName()).log(Level.SEVERE, "Error obteniendo las cartas", ex);
            return new Respuesta(false, "Error obtener cartas.", "getCardType" + ex.getMessage());
        }
    }

    public Respuesta getCardType(String type) {
        try {
            Query queryCard = em.createNamedQuery("Card.findByType", Card.class);
            queryCard.setParameter("type", type);
            List<CardDto> cardDtoList = new ArrayList<>();
            List<Card> cardList = queryCard.getResultList();
            for (Card card : cardList) {
                cardDtoList.add(new CardDto(card));
            }
            return new Respuesta(true, " ", " ", "Cartas", cardDtoList);
        } catch (NoResultException ex) {
            return new Respuesta(false, "No existe cartas con las credenciales ingresadas.", "NoResultException/getCardType");
        } catch (Exception ex) {
            Logger.getLogger(CardService.class.getName()).log(Level.SEVERE, "Error obteniendo las cartas", ex);
            return new Respuesta(false, "Error obtener cartas.", "getCardType" + ex.getMessage());
        }
    }
}
