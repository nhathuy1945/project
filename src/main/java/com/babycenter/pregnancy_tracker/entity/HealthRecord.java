package com.babycenter.pregnancy_tracker.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import java.time.LocalDate;

@Entity
@Table(name = "health_record")
public class HealthRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    private LocalDate recordDate;

    @Min(value = 0, message = "Weight must be positive")
    private double weight;

    @Min(value = 0, message = "Blood pressure must be positive")
    private int bloodPressureSystolic;

    @Min(value = 0, message = "Blood pressure must be positive")
    private int bloodPressureDiastolic;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDate getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(LocalDate recordDate) {
        this.recordDate = recordDate;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getBloodPressureSystolic() {
        return bloodPressureSystolic;
    }

    public void setBloodPressureSystolic(int bloodPressureSystolic) {
        this.bloodPressureSystolic = bloodPressureSystolic;
    }

    public int getBloodPressureDiastolic() {
        return bloodPressureDiastolic;
    }

    public void setBloodPressureDiastolic(int bloodPressureDiastolic) {
        this.bloodPressureDiastolic = bloodPressureDiastolic;
    }
}