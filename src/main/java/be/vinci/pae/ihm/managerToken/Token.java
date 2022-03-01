package be.vinci.pae.ihm.managerToken;

import be.vinci.pae.business.domain.dto.MemberDTO;
import be.vinci.pae.utils.Config;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class Token {

  private final Algorithm jwtAlgorithm = Algorithm.HMAC256(Config.getProperty("JWTSecret"));

  private String getToken(MemberDTO memberDTO, Date date) {
    String token = null;
    try {
      token = JWT.create().withIssuer("auth0")
          .withClaim("user", memberDTO.getMemberId())
          .withExpiresAt(date)
          .sign(this.jwtAlgorithm);

    } catch (Exception e) {
      System.out.println("Unable to create token");
    }
    return token;
  }

  public String withRememberMe(MemberDTO memberDTO) {
    Date date = Date.from(Instant.now().plus(30, ChronoUnit.DAYS));
    System.out.println("long " + date);
    return getToken(memberDTO, date);
  }

  public String withoutRememberMe(MemberDTO memberDTO) {
    Date date = Date.from(Instant.now().plus(12, ChronoUnit.HOURS));
    System.out.println("short " + date);
    return getToken(memberDTO, date);
  }
}
