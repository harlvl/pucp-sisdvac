package edu.pucp.sisdvac;

import edu.pucp.sisdvac.controller.dto.*;
import edu.pucp.sisdvac.domain.enums.DocumentType;
import edu.pucp.sisdvac.domain.enums.FormulationItemType;
import edu.pucp.sisdvac.domain.enums.Stage;
import edu.pucp.sisdvac.domain.enums.Status;
import edu.pucp.sisdvac.domain.enums.SubjectType;
import edu.pucp.sisdvac.domain.enums.TppItemType;
import edu.pucp.sisdvac.domain.user.Role;
import edu.pucp.sisdvac.security.auth.AuthenticationService;
import edu.pucp.sisdvac.security.auth.RegisterRequest;
import edu.pucp.sisdvac.service.impl.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class SisdvacApplication {

    public static void main(String[] args) {
        SpringApplication.run(SisdvacApplication.class, args);
    }
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        String uiHost = "http://localhost:4200";
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedMethods("*")
                        .allowedHeaders("*")
                        .allowedOrigins("*");
            }
        };
    }

    @Bean
    CommandLineRunner run(VolunteerServiceImpl volunteerService,
                          TestSubjectServiceImpl testSubjectService,
                          AuthenticationService authenticationService,
                          UserServiceImpl userService,
                          ResearchServiceImpl researchService,
                          TrialServiceImpl trialService) {
        return args -> {

            // build preclinical trials
            // trial 1
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

            List<FormulationItemDto> formulationItemDtos = new ArrayList<>();
            formulationItemDtos.add(FormulationItemDto.builder()
                    .type(FormulationItemType.COMPOSITION)
                    .detail("Biocompatible y no tóxico")
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
                    .formulation(FormulationDto.builder()
                            .items(formulationItemDtos)
                            .build())
                    .tpp(TppDto.builder()
                            .items(itemDtos)
                            .build())
                    .build();

            TrialDto preclinicalTrial2 = TrialDto.builder()
                    .insNumber("1234567891")
                    .stage(Stage.PRECLINICAL)
                    .startDate(new Date())
                    .title("Estudio clinico de prueba 2")
                    .status(
                            TrialStatusDto.builder()
                                    .name("Estado inicial")
                                    .startDate(new Date())
                                    .build()
                    )
                    .advanceItems(advanceItems)
                    .formulation(FormulationDto.builder()
                    .items(formulationItemDtos)
                    .build())
                    .tpp(TppDto.builder()
                            .items(itemDtos)
                            .build())
                    .build();

            trialService.save(preclinicalTrial);
            trialService.save(preclinicalTrial2);

            // register admin user
            authenticationService.register(
                    RegisterRequest.builder()
                            .email("luis.viguria@pucp.pe")
                            .password("1234")
                            .firstName("Luis")
                            .lastName("Viguria")
                            .role(Role.ADMIN)
                            .documentType(DocumentType.DNI)
                            .documentNumber("72471761")
                            .build()
            );

            //register doctor user
            authenticationService.register(
                    RegisterRequest.builder()
                            .email("francisco.bolognesi@pucp.pe")
                            .password("1234")
                            .firstName("Francisco")
                            .lastName("Bolognesi")
                            .role(Role.DOCTOR_MAIN)
                            .documentType(DocumentType.DNI)
                            .documentNumber("72471762")
                            .build()
            );

            //register doctor member user
            authenticationService.register(
                    RegisterRequest.builder()
                            .email("jose.olaya@pucp.pe")
                            .password("1234")
                            .firstName("Jose")
                            .lastName("Olaya")
                            .role(Role.DOCTOR_MEMBER)
                            .documentType(DocumentType.DNI)
                            .documentNumber("72471763")
                            .build()
            );

            //register doctor member user
            authenticationService.register(
                    RegisterRequest.builder()
                            .email("miguel.grau@pucp.pe")
                            .password("1234")
                            .firstName("Miguel")
                            .lastName("Grau")
                            .role(Role.DOCTOR_MEMBER)
                            .documentType(DocumentType.DNI)
                            .documentNumber("72471233")
                            .build()
            );

            //register doctor member user
            authenticationService.register(
                    RegisterRequest.builder()
                            .email("hip.una@pucp.pe")
                            .password("1234")
                            .firstName("Hipolito")
                            .lastName("Unanue")
                            .role(Role.DOCTOR_MEMBER)
                            .documentType(DocumentType.DNI)
                            .documentNumber("789071233")
                            .build()
            );

            //register assistant user
            authenticationService.register(
                    RegisterRequest.builder()
                            .email("luis.miguel@pucp.pe")
                            .password("1234")
                            .firstName("Luis")
                            .lastName("Miguel")
                            .role(Role.ASSISTANT)
                            .documentType(DocumentType.DNI)
                            .documentNumber("67871763")
                            .build()
            );

            //register assistant user
            authenticationService.register(
                    RegisterRequest.builder()
                            .email("mic.bas@pucp.pe")
                            .password("1234")
                            .firstName("Micaela")
                            .lastName("Bastidas")
                            .role(Role.ASSISTANT)
                            .documentType(DocumentType.DNI)
                            .documentNumber("6000763")
                            .build()
            );

            List<UserDto> users = new ArrayList<>();
            users.add(userService.findByEmail("jose.olaya@pucp.pe"));
//            users.add(userService.findByEmail("francisco.bolognesi@pucp.pe"));

            TrialDto preclinicalTrial3 = TrialDto.builder()
                    .insNumber("9834567891")
                    .stage(Stage.PRECLINICAL)
                    .startDate(new Date())
                    .title("Estudio clinico de prueba 3")
                    .status(
                            TrialStatusDto.builder()
                                    .name("Estado inicial")
                                    .startDate(new Date())
                                    .build()
                    )
                    .advanceItems(advanceItems)
                    .formulation(FormulationDto.builder()
                            .items(formulationItemDtos)
                            .build())
                    .tpp(TppDto.builder()
                            .items(itemDtos)
                            .build())
                    .build();

            List<TrialDto> trialDtos = new ArrayList<>();
            trialDtos.add(preclinicalTrial3);

            // create new research and attach a doctor
            researchService.save(ResearchDto.builder()
                    .title("Investigacion contra el sarampion")
                    .insNumber("567345234")
                    .startDate(new Date())
                    .users(users)
                    .trials(trialDtos)
                    .build()
            );




            // create new volunteer
            volunteerService.save(
                    VolunteerDto.builder()
                            .email("email@email.com")
                            .firstName("Juan")
                            .lastName("Perez")
                            .documentType(DocumentType.DNI)
                            .documentNumber("62471761")
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
