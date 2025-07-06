package net.allaoua.digitalbanking.exceptions;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @ToString @Builder
public class ErrorMessage {
    private String message;
    private LocalDateTime timestamp;
    private int code;
}
