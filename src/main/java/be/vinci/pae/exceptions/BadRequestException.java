package be.vinci.pae.exceptions;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

public class BadRequestException extends WebApplicationException {

  public BadRequestException() {
    super(Response.Status.BAD_REQUEST);
  }

  /**
   * Make an BadRequestException with the custom message.
   *
   * @param message custom error message
   */
  public BadRequestException(String message) {
    super(message, Response.Status.BAD_REQUEST);
  }

}