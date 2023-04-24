package edu.pucp.sisdvac;

import edu.pucp.sisdvac.controller.dto.VolunteerDto;
import edu.pucp.sisdvac.domain.enums.DocumentType;
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
    CommandLineRunner run(VolunteerServiceImpl volunteerService) {
        return args -> {
            volunteerService.saveVolunteer(
                    VolunteerDto.builder()
                            .email("email@email.com")
                            .firstName("Juan")
                            .lastName("Perez")
                            .documentType(DocumentType.DNI)
                            .documentNumber("72471761")
                            .contactNumber("978592715")
                            .build());
        };
    }

}
