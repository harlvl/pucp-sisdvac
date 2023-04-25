package edu.pucp.sisdvac.dao.parser;

import edu.pucp.sisdvac.controller.dto.CandidateVaccineDto;
import edu.pucp.sisdvac.domain.CandidateVaccine;

public class CandidateVaccineParser {
    public static CandidateVaccineDto toDto(CandidateVaccine input) {
        return BaseParser.parse(input, CandidateVaccineDto.class);
    }

    public static CandidateVaccine fromDto(CandidateVaccineDto input){
        return BaseParser.parse(input, CandidateVaccine.class);
    }
}
