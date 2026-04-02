package com.smarthome.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
  @AllArgsConstructor
@Builder
public class User extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;

    private String name;

   @Column(unique = true, nullable = false)

    private String email;

    @ManyToMany(mappedBy = "members")
     @JsonIgnore

   private List<House> houses;
}