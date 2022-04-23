package be.vinci.pae.business.ucc;

import be.vinci.pae.business.domain.dto.InterestDTO;
import be.vinci.pae.business.domain.dto.ObjectDTO;
import be.vinci.pae.business.domain.dto.OfferDTO;
import be.vinci.pae.dal.dao.InterestDAO;
import be.vinci.pae.dal.dao.ObjectDAO;
import be.vinci.pae.dal.dao.OfferDAO;
import be.vinci.pae.dal.services.DALService;
import be.vinci.pae.exceptions.ForbiddenException;
import be.vinci.pae.exceptions.NotFoundException;
import jakarta.inject.Inject;
import java.util.List;

public class InterestUCCImpl implements InterestUCC {

  @Inject
  private InterestDAO interestDAO;
  @Inject
  private OfferDAO offerDAO;
  @Inject
  private DALService dalService;
  @Inject
  private ObjectDAO objectDAO;

  /**
   * Find an interest, by the id of the interested member and the id of the object.
   *
   * @param idObject : id object of the interest.
   * @param idMember : id of interested member.
   * @return interestDTO having the idObject and idMember.
   */
  @Override
  public InterestDTO getInterest(int idObject, int idMember) {
    try {
      dalService.startTransaction();
      InterestDTO interestDTO = interestDAO.getOne(idObject, idMember);
      if (interestDTO == null) {
        throw new NotFoundException("Interest not found");
      }
      dalService.commitTransaction();
      return interestDTO;
    } catch (Exception e) {
      dalService.rollBackTransaction();
      throw e;
    }
  }

  /**
   * Add one interest.
   *
   * @param item : interestDTO object.
   * @return item.
   */
  @Override
  public InterestDTO addOne(InterestDTO item) {
    try {
      dalService.startTransaction();
      if (interestDAO.getOne(item.getObject().getIdObject(), item.getIdMember()) != null) {
        //change name exception
        throw new NotFoundException("An Interest for this Object and Member already exists");
      }
      // if there is no interest
      if (interestDAO.getAll(item.getObject().getIdObject()).isEmpty()) {
        ObjectDTO objectDTO = objectDAO.getOne(item.getObject().getIdObject());
        if (objectDTO == null) {
          throw new NotFoundException("Object not found");
        }
        objectDTO.setStatus("interested");
        objectDAO.updateOne(objectDTO);
        OfferDTO offerDTO = offerDAO.getOneByObject(objectDTO.getIdObject());
        offerDTO.setStatus("interested");
        offerDAO.updateOne(offerDTO);
      }
      interestDAO.addOne(item);
      dalService.commitTransaction();
    } catch (Exception e) {
      dalService.rollBackTransaction();
      throw e;
    }
    return item;
  }

  /**
   * Assign the offer to a member.
   *
   * @param interestDTO : the interest informations (id of the object and id of the member).
   * @return objectDTO updated.
   */
  @Override
  public InterestDTO assignOffer(InterestDTO interestDTO) {
    try {
      dalService.startTransaction();

      OfferDTO offerDTO = offerDAO.getLastObjectOffer(interestDTO.getObject().getIdObject());

      if (!offerDTO.getStatus().equals("interested") || !interestDTO.getObject().getStatus()
          .equals("interested")) {
        throw new ForbiddenException("L'offre n'est pas en mesure d'être assigné");
      }

      if (interestDAO.getAssignedInterest(offerDTO.getObject().getIdObject()) != null) {
        throw new ForbiddenException("L'offre est déjà assignée à un membre");
      }

      if (!interestDTO.getStatus().equals("published")) {
        throw new ForbiddenException("Le membre n'est pas éligible à l'assignement");
      }

      interestDTO = interestDAO.getOne(interestDTO.getObject().getIdObject(),
          interestDTO.getIdMember());
      if (interestDTO == null) {
        throw new NotFoundException("Le membre ne présente pas d'intérêt");
      }

      // update offer to assigned
      offerDTO.getObject().setStatus("assigned");
      offerDTO.setStatus("assigned");
      offerDAO.updateOne(offerDTO);

      // update interest to assigned
      interestDTO.setStatus("assigned");
      interestDAO.updateStatus(interestDTO);

      dalService.commitTransaction();
    } catch (Exception e) {
      dalService.rollBackTransaction();
      throw e;
    }

    return interestDTO;
  }

  /**
   * Get a list of interest, by an id object.
   *
   * @param idObject the object we want to retrieve the interests
   * @return a list of interest, by an id object
   */
  @Override
  public List<InterestDTO> getInterestedCount(int idObject) {
    List<InterestDTO> interestDTOList;
    try {
      dalService.startTransaction();
      ObjectDTO objectDTO = objectDAO.getOne(idObject);
      if (objectDTO == null) {
        throw new NotFoundException("Object not found");
      }
      interestDTOList = interestDAO.getAllPublished(idObject);
      dalService.commitTransaction();
    } catch (Exception e) {
      dalService.rollBackTransaction();
      throw e;
    }
    return interestDTOList;
  }

  /**
   * Get a list of notificated interest in an id object.
   *
   * @param idMember the member we want to retrieve notifications
   * @return a list of interest, by an id member
   */
  @Override
  public List<InterestDTO> getNotifications(int idMember) {
    List<InterestDTO> interestDTOList;
    try {
      dalService.startTransaction();
      interestDTOList = interestDAO.getAllNotification(idMember);
      dalService.commitTransaction();
    } catch (Exception e) {
      dalService.rollBackTransaction();
      throw e;
    }
    return interestDTOList;
  }


}
