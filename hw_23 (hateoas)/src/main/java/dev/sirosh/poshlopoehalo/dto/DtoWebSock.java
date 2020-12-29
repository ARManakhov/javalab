package dev.sirosh.poshlopoehalo.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class DtoWebSock {
    String type;
    Object payload;
}
