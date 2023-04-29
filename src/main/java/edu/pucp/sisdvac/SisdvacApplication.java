package edu.pucp.sisdvac;

import edu.pucp.sisdvac.controller.dto.*;
import edu.pucp.sisdvac.domain.enums.*;
import edu.pucp.sisdvac.domain.user.Role;
import edu.pucp.sisdvac.security.auth.AuthenticationService;
import edu.pucp.sisdvac.security.auth.RegisterRequest;
import edu.pucp.sisdvac.service.impl.TestSubjectServiceImpl;
import edu.pucp.sisdvac.service.impl.TrialServiceImpl;
import edu.pucp.sisdvac.service.impl.VolunteerServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class SisdvacApplication {

    public static void main(String[] args) {
        SpringApplication.run(SisdvacApplication.class, args);
    }

    @Bean
    CommandLineRunner run(VolunteerServiceImpl volunteerService,
                          TestSubjectServiceImpl testSubjectService,
                          AuthenticationService authenticationService,
                          TrialServiceImpl trialService) {
        return args -> {

            // build preclinical trial
            List<TrialDto.AdvanceItem> advanceItems = new ArrayList<>();
            List<AdverseEventDto> adverseEventDtos = new ArrayList<>();
            adverseEventDtos.add(
                    AdverseEventDto.builder()
                            .sex('F')
                            .patientCID("CID98875421")
                            .description("Alergia a X")
                            .age(23)
                            .build()
            );

            advanceItems.add(
                    TrialDto.AdvanceItem.builder()
                            .adverseEvents(adverseEventDtos)
                            .subjectsTotal(10)
                            .startDate(new Date())
                            .build()
            );

            List<TppItemDto> itemDtos = new ArrayList<>();
            itemDtos.add(TppItemDto.builder()
                    .type(TppItemType.STORAGE_CONDITION)
                    .detail("Under 5 degrees")
                    .build()
            );

            TrialDto preclinicalTrial = TrialDto.builder()
                    .insNumber("123456789")
                    .stage(Stage.PRECLINICAL)
                    .startDate(new Date())
                    .title("Estudio clinico de prueba 1")
                    .status(
                            TrialStatusDto.builder()
                                    .name("Estado inicial")
                                    .startDate(new Date())
                                    .build()
                    )
                    .advanceItems(advanceItems)
                    .tpp(TppDto.builder()
                            .items(itemDtos)
                            .build())
                    .build();

            trialService.save(preclinicalTrial);

            // register admin user
            authenticationService.register(
                    RegisterRequest.builder()
                            .email("luis.viguria@pucp.pe")
                            .password("1234")
                            .firstName("Luis")
                            .lastName("Viguria")
                            .role(Role.ADMIN)
                            .build()
            );

            // create new volunteer
            volunteerService.save(
                    VolunteerDto.builder()
                            .email("email@email.com")
                            .firstName("Juan")
                            .lastName("Perez")
                            .documentType(DocumentType.DNI)
                            .documentNumber("72471761")
                            .contactNumber("978592715")
                            .build());

            // create new test subject
            testSubjectService.save(
                    TestSubjectDto.builder()
                            .codeName("MICKEY01")
                            .subjectType(SubjectType.RODENT)
                            .status(Status.ACTIVE)
                            .build());
        };
    }

}
