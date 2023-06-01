package edu.pucp.sisdvac.service.calculator;

import edu.pucp.sisdvac.controller.dto.FormulationEvaluationDto;
import edu.pucp.sisdvac.controller.exception.FormulaCalculationException;
import edu.pucp.sisdvac.controller.request.AnimalStudyEvaluationRequest;
import edu.pucp.sisdvac.domain.enums.EvaluationFormulaEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Calculator {
    private static final Logger LOGGER = LoggerFactory.getLogger(Calculator.class);

    public static Map<String, BigDecimal> calculateFormulas(AnimalStudyEvaluationRequest request) {
        LOGGER.info("Calculating formulas...");
        Map<String, BigDecimal> response = new HashMap<>();

        try {
            response.put(
                    String.valueOf(EvaluationFormulaEnum.EFFICACY),
                    calculateEfficacy(request)
            );
            response.put(
                    String.valueOf(EvaluationFormulaEnum.EFFICIENCY),
                    calculateEfficiency(request)
            );
            response.put(
                    String.valueOf(EvaluationFormulaEnum.SAFETY_INDEX),
                    calculateSafetyIndex(request)
            );
            response.put(
                    String.valueOf(EvaluationFormulaEnum.INCIDENCE_RATE),
                    calculateIncidenceRate(request)
            );
            response.put(
                    String.valueOf(EvaluationFormulaEnum.IMMUNOGENICITY),
                    calculateImmunogenicity(request)
            );
        } catch (Exception e) {
            LOGGER.error(String.format(
                    "Error calculating formula: %s", e.getMessage()
            ));
            throw new FormulaCalculationException(e.getMessage());
        }

        return response;
    }
    public static BigDecimal calculateImmunogenicity(AnimalStudyEvaluationRequest request) {
        return BigDecimal.valueOf(
                (request.getAnimalsWithDetectableImmuneResponse() / request.getTotalVaccinatedAnimals()) * 100L
        );
    }

    public static BigDecimal calculateIncidenceRate(AnimalStudyEvaluationRequest request) {
        if (request.getAdverseEventsVaccinatedGroup().equals(0)) {
            return new BigDecimal(0);
        }

        return BigDecimal.valueOf(
                (request.getAdverseEventsVaccinatedGroup() / request.getTotalVaccinatedAnimals()) * 100L
        );
    }

    public static BigDecimal calculateSafetyIndex(AnimalStudyEvaluationRequest request) {
        return BigDecimal.valueOf(
                request.getLethalDose() / request.getEffectiveDose()
        );
    }

    public static BigDecimal calculateEfficiency(AnimalStudyEvaluationRequest request) {
        return BigDecimal.valueOf(
                (request.getAttackRateUnvaccinatedGroup() - request.getAttackRateVaccinatedGroup())
                        / request.getAttackRateUnvaccinatedGroup()
        );
    }

    public static BigDecimal calculateEfficacy(AnimalStudyEvaluationRequest request) {
        return BigDecimal.valueOf(
                (request.getAttackRateUnvaccinatedGroup() - request.getAttackRateVaccinatedGroup())
                        / request.getAttackRateUnvaccinatedGroup()
        );
    }

    public static Map<String, BigDecimal> calculateFormulas(FormulationEvaluationDto dto) {
        Map<String, BigDecimal> response = new HashMap<>();

        try {
            response.put(String.valueOf(EvaluationFormulaEnum.IMMUNOGENICITY), BigDecimal.valueOf(5.1));
            response.put(String.valueOf(EvaluationFormulaEnum.EFFICACY), BigDecimal.valueOf(2.3));
            response.put(String.valueOf(EvaluationFormulaEnum.EFFICIENCY), BigDecimal.valueOf(3.4));
            response.put(String.valueOf(EvaluationFormulaEnum.SAFETY_INDEX), BigDecimal.valueOf(1.2));
        } catch (Exception e) {
            LOGGER.error(String.format(
                    "Error calculating formula: %s", e.getMessage()
            ));
        }

        return response;
    }
}
