package be.vinci.pae.business.ucc;

import be.vinci.pae.business.domain.dto.ObjectDTO;
import be.vinci.pae.dal.dao.ObjectDAO;
import be.vinci.pae.dal.services.DALService;
import be.vinci.pae.exceptions.NotFoundException;
import be.vinci.pae.utils.Config;
import jakarta.inject.Inject;
import java.io.File;
import java.util.List;

public class ObjectUCCImpl implements ObjectUCC {

  @Inject
  private ObjectDAO objectDAO;
  @Inject
  private DALService dalService;

  /**
   * Find an object with his id.
   *
   * @param id : id of the object.
   * @return objectDTO having this id.
   */
  @Override
  public ObjectDTO getObject(int id) {
    ObjectDTO objectDTO;
    try {
      dalService.startTransaction();
      objectDTO = objectDAO.getOne(id);

      if (objectDTO == null) {
        dalService.rollBackTransaction();
        throw new NotFoundException("Objet non trouvé");
      }
    } catch (Exception e) {
      dalService.rollBackTransaction();
      throw e;
    }
    dalService.commitTransaction();
    return objectDTO;
  }

  /**
   * Find all object of a member.
   *
   * @param idMember : id member that we want to get all his object.
   * @return object list of this member.
   */
  @Override
  public List<ObjectDTO> getAllObjectMember(int idMember) {
    List<ObjectDTO> objectDTOList;
    try {
      dalService.startTransaction();
      objectDTOList = objectDAO.getAllObjectOfMember(idMember);

      if (objectDTOList.isEmpty()) {
        dalService.rollBackTransaction();
        throw new NotFoundException("Aucun objet pour ce membre");
      }
    } catch (Exception e) {
      dalService.rollBackTransaction();
      throw e;
    }
    dalService.commitTransaction();
    return objectDTOList;
  }

  /**
   * Update an object.
   *
   * @param objectDTO : object that we want to update.
   * @return object updated
   */
  public ObjectDTO updateOne(ObjectDTO objectDTO) {
    ObjectDTO object;
    try {
      dalService.startTransaction();
      object = objectDAO.getOne(objectDTO.getIdObject());
      if (object == null) {
        dalService.rollBackTransaction();
        throw new NotFoundException("Object not found");
      }
      object = objectDAO.updateOne(objectDTO);
      dalService.commitTransaction();
    } catch (Exception e) {
      dalService.rollBackTransaction();
      throw e;
    }
    return object;
  }

  /**
   * Update the object picture.
   *
   * @param internalPath location of the picture.
   * @param id           of the object.
   * @return Object modified.
   */
  @Override
  public ObjectDTO updateObjectPicture(String internalPath, int id) {
    ObjectDTO objectDTO;
    try {
      objectDTO = objectDAO.getOne(id);
      if (objectDTO == null) {
        throw new NotFoundException("Member not found");
      }

      if (objectDTO.getImage() != null) {
        File f = new File(Config.getProperty("ImagePath") + objectDTO.getImage());
        if (f.exists()) {
          f.delete();
        }

      }

      objectDTO = objectDAO.updateObjectPicture(internalPath, id);
    } catch (Exception e) {
      dalService.rollBackTransaction();
      throw e;
    }
    dalService.commitTransaction();
    return objectDTO;
  }
}
