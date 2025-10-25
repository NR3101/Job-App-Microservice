package com.learn.jobms.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Entity(name = "jobs")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title cannot be blank")
    @Length(min = 1, max = 100, message = "Title must be between 1 and 100 characters")
    private String title;

    @NotBlank(message = "Description cannot be blank")
    @Length(min = 1, max = 500, message = "Description must be between 1 and 500 characters")
    private String description;

    @NotBlank(message = "Minimum Salary cannot be blank")
    @Pattern(regexp = "^[0-9]+$", message = "Minimum Salary must be a valid number")
    private String minSalary;

    @NotBlank(message = "Maximum Salary cannot be blank")
    @Pattern(regexp = "^[0-9]+$", message = "Maximum Salary must be a valid number")
    private String maxSalary;

    @NotBlank(message = "Location cannot be blank")
    @Length(min = 1, max = 100, message = "Location must be between 1 and 100 characters")
    private String location;

    private Long companyId;
}
