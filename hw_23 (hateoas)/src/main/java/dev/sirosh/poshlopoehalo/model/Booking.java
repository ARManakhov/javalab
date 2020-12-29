package dev.sirosh.poshlopoehalo.model;


import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Getter
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userr")
    User user;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "movment")
    Movement movement;
}
