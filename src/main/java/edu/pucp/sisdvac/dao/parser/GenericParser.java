package edu.pucp.sisdvac.dao.parser;

import edu.pucp.sisdvac.domain.EvaluationItem;
import edu.pucp.sisdvac.domain.GenericEvaluationItem;
import edu.pucp.sisdvac.domain.enums.EvaluationFormulaEnum;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class GenericParser {
    public static Collection<EvaluationItem> convertMapToItemCollection(Map<String, BigDecimal> map) {
        Collection<EvaluationItem> response = new ArrayList<>();

        for (Map.Entry<String, BigDecimal> set : map.entrySet()) {
            response.add(
                    EvaluationItem.builder()
                            .type(EvaluationFormulaEnum.valueOf(set.getKey()))
                            .detail(set.getValue())
                            .build()
            );
        }

        return response;
    }
    public static Collection<GenericEvaluationItem> convertMapToGenericItemCollection(Map<String, BigDecimal> map) {
        Collection<GenericEvaluationItem> response = new ArrayList<>();

        for (Map.Entry<String, BigDecimal> set : map.entrySet()) {
            response.add(
                    GenericEvaluationItem.builder()
                            .type(EvaluationFormulaEnum.valueOf(set.getKey()))
                            .detail(set.getValue())
                            .build()
            );
        }

        return response;
    }

    public static Map<String, BigDecimal> convertItemCollectionToMap(Collection<EvaluationItem> items) {
        HashMap<String, BigDecimal> response = new HashMap<>();

        for (EvaluationItem item :
                items) {
            response.put(String.valueOf(item.getType()), item.getDetail());
        }

        return response;
    }

    public static Map<String, BigDecimal> convertGenericItemCollectionToMap(Collection<GenericEvaluationItem> items) {
        HashMap<String, BigDecimal> response = new HashMap<>();

        for (GenericEvaluationItem item :
                items) {
            response.put(String.valueOf(item.getType()), item.getDetail());
        }

        return response;
    }
}
