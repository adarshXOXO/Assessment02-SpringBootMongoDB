package com.example.SpringBootMongoDBAssessment.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Date;

@Service
public class TokenService {
    public static final String token_secret = "dsFSA24351FSAkmkdkfd";

    //Create a Token
    public String createToken(ObjectId userID){

        try{
            Algorithm algo = Algorithm.HMAC256(token_secret); //Choosing HMCA Algo for coding
            String token = JWT.create().withClaim("userID", userID.toString()).
                    withClaim("createAt", new Date()).
                    sign(algo);  //JWT with user a specific claim and sign it
            return token;
        }
        catch(UnsupportedEncodingException | JWTCreationException e){
            e.printStackTrace(); //Exception handling
        }

        return null;
    }

    //Decoding token and gets userID claim
    public String getUserToken(String token)
    {
        try{
            Algorithm algo = Algorithm.HMAC256(token_secret);
            //Finger the token
            JWTVerifier jwtVerifier = JWT.require(algo).build();

            DecodedJWT decodedJWT = jwtVerifier.verify(token);

            //etClaim to get userID
            return decodedJWT.getClaim("userID").asString();
        }
        catch (JWTCreationException | UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        return null;
    }


    //Checks if the token is valid
    public boolean isTokenValid(String token)
    {
        String userID = this.getUserToken(token);
        return userID != null;
    }
}
