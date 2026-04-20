package com.hackathon.estoque.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "addresses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 255)
    private String street;

    @NotBlank
    @Column(nullable = false, length = 100)
    private String city;

    @Pattern(regexp = "\\d{8}")
    @Column(nullable = false, length = 50)
    private String cep;

    @Size(min = 2, max = 2)
    @Column(nullable = false, length = 2)
    private String state;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @JsonBackReference
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    public Address(String cep, String street, String city, String state, User user) {
        this.cep = cep;
        this.street = street;
        this.city = city;
        this.state = state;
        this.user = user;
    }
}
