package api.gef.security.service;

import api.gef.entity.Usuario;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.UUID;

@Service
public class TokenService {

    @Value("${app.secret}")
    private String appSecret;

    @SneakyThrows
    public String generateToken(Usuario usuario) {
        JWSSigner signer = new MACSigner(appSecret);

        JWTClaimsSet jwtClaimSet = new JWTClaimsSet.Builder()
                .issuer("api-gestao-espaco-fisico")
                .subject(usuario.getEmail())
                .claim("nome-usuario", usuario.getNome())
                .expirationTime(new Date(System.currentTimeMillis() + 60 * 60 * 1000))
                .jwtID(UUID.randomUUID().toString())
                .build();

        SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), jwtClaimSet);
        signedJWT.sign(signer);

        return signedJWT.serialize();
    }

    @SneakyThrows
    public boolean isValid(String token) {
        SignedJWT signedJWT = SignedJWT.parse(token);
        JWSVerifier verifier = new MACVerifier(appSecret);

        return signedJWT.verify(verifier);
    }

    public JWTClaimsSet extractClaims(String token) throws ParseException {
        JWSObject jwsObject = JWSObject.parse(token);
        return JWTClaimsSet.parse(jwsObject.getPayload().toJSONObject());
    }
}