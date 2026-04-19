package com.codeyourtree.backend.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name="tree_data")
@Data
public class TreeData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer streak=1;

    @Column(nullable = false)
    private Integer xp=100;

    @Column(name="max_depth", nullable=false)
    private Integer maxDepth=3;

    @Column(name="last_action_date")
    private LocalDate lastActionDate;

    /*
    * Bir kullanıcının bir ağacı olur.
    * */
    //Bu ilişkinin asıl sahibinin User sınıfındaki treeData değişkeni olduğunu söyler.
    @OneToOne(mappedBy = "treeData")
    @JsonIgnore
    //Frontend'e veri gönderirken sonsuz döngüye girmemesi için
    private User user;
}

