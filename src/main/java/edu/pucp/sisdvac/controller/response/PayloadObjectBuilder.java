package edu.pucp.sisdvac.controller.response;

import edu.pucp.sisdvac.controller.response.RestResponse;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@Builder
public class PayloadObjectBuilder {
    public static RestResponse buildPayloadObject(Object input) {
        return RestResponse.builder()
                .timestamp(LocalDateTime.now())
                .payload(input)
                .status(HttpStatus.OK)
                .build();
    }

    public static RestResponse buildPayloadObject(Object input, Integer size) {
        return RestResponse.builder()
                .timestamp(LocalDateTime.now())
                .payload(input)
                .hits(size)
                .status(HttpStatus.OK)
                .build();
    }

    public static RestResponse buildPayloadObject(Object input, HttpStatus status) {
        return RestResponse.builder()
                .timestamp(LocalDateTime.now())
                .payload(input)
                .status(status)
                .build();
    }

    public static RestResponse buildPayloadObject(Object input, HttpStatus status, Integer size) {
        return RestResponse.builder()
                .timestamp(LocalDateTime.now())
                .payload(input)
                .status(status)
                .hits(size)
                .build();
    }
}
