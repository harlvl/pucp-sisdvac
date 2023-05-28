package edu.pucp.sisdvac;

import edu.pucp.sisdvac.controller.dto.*;
import edu.pucp.sisdvac.domain.enums.*;
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
import java.util.Collection;
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
                            .stage(Stage.PRECLINICAL)
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

            List<FormulationDto> formulationDtos = new ArrayList<>();
            formulationDtos.add(FormulationDto.builder()
                    .status(FormulationStatus.ACTIVE)
                    .order(1)
                    .items(formulationItemDtos)
                    .createdAt(new Date())
                    .build());

            TrialDto preclinicalTrial = TrialDto.builder()
                    .insNumber("PER-103-20")
                    .stage(Stage.PRECLINICAL)
                    .startDate(new Date())
                    .title("Estudio clinico de prueba 1")
                    .status(
                            TrialStatusDto.builder()
                                    .name("Estado inicial")
                                    .startDate(new Date())
                                    .build()
                    )
//                    .advances(advanceItems)
                    .formulations(formulationDtos)
                    .tpp(TppDto.builder()
                            .items(itemDtos)
                            .build())
                    .build();

            TrialDto preclinicalTrial2 = TrialDto.builder()
                    .insNumber("PER-203-20")
                    .stage(Stage.PRECLINICAL)
                    .startDate(new Date())
                    .title("Estudio clinico de prueba 2")
                    .status(
                            TrialStatusDto.builder()
                                    .name("Estado inicial")
                                    .startDate(new Date())
                                    .build()
                    )
//                    .advances(advanceItems)
                    .formulations(formulationDtos)
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

            //register doctor main user
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

            //register doctor main user
            authenticationService.register(
                    RegisterRequest.builder()
                            .email("abe.qui@pucp.pe")
                            .password("1234")
                            .firstName("Abelardo")
                            .lastName("Quiñones")
                            .role(Role.DOCTOR_MAIN)
                            .documentType(DocumentType.DNI)
                            .documentNumber("00000762")
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
                            .documentNumber("11111111")
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

            //register assistant user
            authenticationService.register(
                    RegisterRequest.builder()
                            .email("bri.sil@pucp.pe")
                            .password("1234")
                            .firstName("Brigida")
                            .lastName("Silva")
                            .role(Role.ASSISTANT)
                            .documentType(DocumentType.DNI)
                            .documentNumber("60239763")
                            .build()
            );

            //register assistant user
            authenticationService.register(
                    RegisterRequest.builder()
                            .email("ma.pa@pucp.pe")
                            .password("1234")
                            .firstName("Maria")
                            .lastName("Parado")
                            .role(Role.ASSISTANT)
                            .documentType(DocumentType.DNI)
                            .documentNumber("602318763")
                            .build()
            );

            List<UserDto> users = new ArrayList<>();
            users.add(userService.findByEmail("jose.olaya@pucp.pe"));
            users.add(userService.findByEmail("abe.qui@pucp.pe"));
            users.add(userService.findByEmail("ma.pa@pucp.pe"));

            FormulationDto formulationDto = FormulationDto.builder()
                    .items(formulationItemDtos)
                    .status(FormulationStatus.ACTIVE)
                    .order(1)
                    .createdAt(new Date())
                    .build();
            List<FormulationDto> formulationDtos3 = new ArrayList<>();
            formulationDtos3.add(formulationDto);


            AnimalStudyDto animalStudyDto = AnimalStudyDto.builder()
                    .objectives("Objetivo de prueba")
                    .animalModel(SubjectType.RODENT)
                    .sampleSize(1)
                    .ethicalGuidelines("National Committee for Research Ethics")
                    .ethicalGuidelinesUri("https://www.forskningsetikk.no/en/guidelines/science-and-technology/guidelines-for-research-ethics-in-science-and-technology/")
                    .build();
            AdvanceDto advanceDto = AdvanceDto.builder()
                    .stage(Stage.PRECLINICAL)
                    .subjectName("MICKEY01")
                    .animalStudy(animalStudyDto)
                    .startDate(new Date())
                    .createdAt(new Date())
                    .lastUpdatedAt(new Date())
                    .build();

            Collection<AdvanceDto> advanceDtos = new ArrayList<>();
            advanceDtos.add(advanceDto);

            TrialDto preclinicalTrial3 = TrialDto.builder()
                    .insNumber("PER-303-20")
                    .stage(Stage.PRECLINICAL)
                    .startDate(new Date())
                    .title("Estudio contra la rabia")
                    .status(
                            TrialStatusDto.builder()
                                    .name("Estado inicial")
                                    .startDate(new Date())
                                    .build()
                    )
                    .advanceDtos(advanceDtos)
//                    .advances(advanceItems)
                    .formulations(formulationDtos3)
                    .tpp(TppDto.builder()
                            .items(itemDtos)
                            .build())
                    .build();

            List<TrialDto> trialDtos = new ArrayList<>();
            trialDtos.add(preclinicalTrial3);

            // create new research and attach a doctor
            researchService.save(ResearchDto.builder()
                    .title("Investigacion contra el sarampion")
                    .insNumber("PER-103-20")
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

            testSubjectService.save(
                    TestSubjectDto.builder()
                            .codeName("MICRO01")
                            .subjectType(SubjectType.MICRO)
                            .status(Status.ACTIVE)
                            .build());
        };
    }

}
