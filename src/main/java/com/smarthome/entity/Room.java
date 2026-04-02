package com.smarthome.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
 @Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Room extends Base {

    @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
     @JoinColumn(name = "house_id")
    @JsonIgnore
    // link back to the house.
    private House house;

   @OneToMany(mappedBy = "room")
    // list of all devices currently assigned to this room
    private List<Device> devices;
}