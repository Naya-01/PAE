package be.vinci.pae.ihm;

import be.vinci.pae.business.domain.dto.TypeDTO;
import be.vinci.pae.business.ucc.TypeUCC;
import be.vinci.pae.ihm.filters.Authorize;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Singleton
@Path("/type")
public class TypeResource {

  private static final ObjectMapper jsonMapper = new ObjectMapper();

  @Inject
  private TypeUCC typeUCC;

  /**
   * GET a type by his id.
   *
   * @param id : the type id
   * @return a json of the type
   */
  @GET
  @Path("/id/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize
  public ObjectNode getType(@PathParam("id") int id) {
    TypeDTO typeDTO = typeUCC.getType(id);
    return jsonMapper.createObjectNode().putPOJO("type", typeDTO);
  }

  /**
   * GET a type by his name.
   *
   * @param typeName : the type name
   * @return a json of the type
   */
  @GET
  @Path("/type_name/{typeName}")
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize
  public ObjectNode getType(@PathParam("typeName") String typeName) {
    TypeDTO typeDTO = typeUCC.getType(typeName);
    return jsonMapper.createObjectNode().putPOJO("type", typeDTO);
  }

  /**
   * GET all default types.
   *
   * @return a json of the default types
   */
  @GET
  @Path("/allDefault")
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize
  public ObjectNode getAllDefaultTypes() {
    JsonNode allCollection = jsonMapper.valueToTree(typeUCC.getAllDefaultTypes());
    return jsonMapper.createObjectNode().putPOJO("type", allCollection);
  }


}