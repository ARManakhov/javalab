package ru.itis.models;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Getter
@Setter
@Table(name = "subscriptions",uniqueConstraints={@UniqueConstraint(columnNames = {"author_id" , "subscriber_id"})})
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @ManyToOne(fetch = FetchType.EAGER)
    User author;
    @ManyToOne(fetch = FetchType.EAGER)
    User subscriber;
}
