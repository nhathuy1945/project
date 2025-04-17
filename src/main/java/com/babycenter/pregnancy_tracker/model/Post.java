package com.babycenter.pregnancy_tracker.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "posts")
@Getter
@Setter
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 10, max = 5000)
    private String content;

    private String username;

    @Enumerated(EnumType.STRING)
    private ChartType chartType;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}