package org.com.itpple.spot.server.errorexception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResultMessage {
    private String code;
    private String message;
}