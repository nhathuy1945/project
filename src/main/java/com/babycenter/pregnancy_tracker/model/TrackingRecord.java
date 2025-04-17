package com.babycenter.pregnancy_tracker.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "tracking_records")
@Getter
@Setter
@NoArgsConstructor
public class TrackingRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;
    private double weight;
    private double babyLength;
    private String bloodPressure;
    private String testResults;
    private LocalDate vaccinationSchedule;
    private String notes;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}