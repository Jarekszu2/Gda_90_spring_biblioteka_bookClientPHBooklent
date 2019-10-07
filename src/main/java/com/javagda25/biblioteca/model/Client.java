package com.javagda25.biblioteca.model;

import lombok.*;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String surname;
//    @Column(nullable = false)
    private String idNumber;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    @Cascade(value = {org.hibernate.annotations.CascadeType.REMOVE})
    private Set<BookLent> setOfBookLent;
}
