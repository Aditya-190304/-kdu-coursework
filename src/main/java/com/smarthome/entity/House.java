package com.smarthome.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class House extends Base {

     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;

     private String name;
    private String address;

    // The person who created the house is the Admin
    @ManyToOne
     @JoinColumn(name = "admin_user_id")
    private User admin;

    // A house has many members (including the admin)
    // We initialize this list so we can add people to it immediately without errors
    @ManyToMany
    @JoinTable(
           name = "house_members",
            joinColumns = @JoinColumn(name = "house_id"),
           inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @Builder.Default
     private List<User> members = new ArrayList<>();

    // If we delete a house, we should delete all its rooms too (CascadeType.ALL)
    @OneToMany(mappedBy = "house", cascade = CascadeType.ALL)
     private List<Room> rooms;
}