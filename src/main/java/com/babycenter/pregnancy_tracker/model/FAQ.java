package com.babycenter.pregnancy_tracker.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "faqs")
@Getter
@Setter
@NoArgsConstructor
public class FAQ {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 5, max = 255)
    private String question;

    @NotNull
    @Size(min = 10, max = 1000)
    private String answer;

    private String username;

    @Enumerated(EnumType.STRING)
    private FAQStatus status = FAQStatus.PENDING;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}