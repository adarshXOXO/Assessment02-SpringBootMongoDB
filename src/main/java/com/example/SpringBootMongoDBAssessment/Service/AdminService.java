package com.example.SpringBootMongoDBAssessment.Service;

import com.example.SpringBootMongoDBAssessment.Model.Admin;
import com.example.SpringBootMongoDBAssessment.Repository.AdminRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    private AdminRepository adminRepository;
    private TokenService tokenService;


    AdminService(AdminRepository userRepository, TokenService tokenService)
    {
        this.adminRepository = userRepository;
        this.tokenService = tokenService;
    }


    //Signup Admin function
    public String signUp(Admin admin){
        Admin savedUser = adminRepository.save(admin);
        return String.format( "{\n\t \"Message\": \"Successfully Create the user\",\n\t \"data\": %s \n}", admin.toString());
    }

    //Login Admin function
    public String login(String email, String password)
    {
        List<Admin> foundUsers = adminRepository.getAdminByEmail(email);
        if (foundUsers.isEmpty())
        {
            return "Authentication failed";
        }
        else if(!foundUsers.get(0).getPassword().equals(password))
        {
            return "Incorrect Password";
        }

        String token = tokenService.createToken(foundUsers.get(0).getId());

        return String.format("{\12\t\"message\": \"Successfully logged in\",\12\t" +
                        "\"data\":{\"Name\": \"%s\", \n\t\t \"Email\": \"%s\" },\12\t\"token\": \"%s\" \n }", foundUsers.get(0).getName(),
                foundUsers.get(0).getEmail(),token);
    }
}
