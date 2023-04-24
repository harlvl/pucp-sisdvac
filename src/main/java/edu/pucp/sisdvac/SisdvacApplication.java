package edu.pucp.sisdvac;

import edu.pucp.sisdvac.controller.dto.TestSubjectDto;
import edu.pucp.sisdvac.controller.dto.VolunteerDto;
import edu.pucp.sisdvac.domain.enums.DocumentType;
import edu.pucp.sisdvac.domain.enums.Status;
import edu.pucp.sisdvac.domain.enums.SubjectType;
import edu.pucp.sisdvac.domain.user.Role;
import edu.pucp.sisdvac.security.auth.AuthenticationService;
import edu.pucp.sisdvac.security.auth.RegisterRequest;
import edu.pucp.sisdvac.service.impl.TestSubjectServiceImpl;
import edu.pucp.sisdvac.service.impl.VolunteerServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SisdvacApplication {

    public static void main(String[] args) {
        SpringApplication.run(SisdvacApplication.class, args);
    }

    @Bean
    CommandLineRunner run(VolunteerServiceImpl volunteerService, TestSubjectServiceImpl testSubjectService, AuthenticationService authenticationService) {
        return args -> {
            authenticationService.register(
                    RegisterRequest.builder()
                            .email("luis.viguria@pucp.pe")
                            .password("1234")
                            .firstName("Luis")
                            .lastName("Viguria")
                            .role(Role.ADMIN)
                            .build()
            );

            volunteerService.save(
                    VolunteerDto.builder()
                            .email("email@email.com")
                            .firstName("Juan")
                            .lastName("Perez")
                            .documentType(DocumentType.DNI)
                            .documentNumber("72471761")
                            .contactNumber("978592715")
                            .build());

            testSubjectService.save(
                    TestSubjectDto.builder()
                            .codeName("MICKEY01")
                            .subjectType(SubjectType.RODENT)
                            .status(Status.ACTIVE)
                            .build());
        };
    }

}
