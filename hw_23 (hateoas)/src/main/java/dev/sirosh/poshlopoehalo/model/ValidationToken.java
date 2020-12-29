package dev.sirosh.poshlopoehalo.model;

import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Getter
@Setter
@Table(name = "validation_token")
public class ValidationToken {
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    User user;
    String token;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
}
