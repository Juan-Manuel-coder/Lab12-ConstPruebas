package com.tecsup.petclinic.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "vet_specialties")
@Data
public class VetSpecialty {

    @Id
    @Column(name = "vet_id")
    private Integer vetId;

    @Column(name = "specialty_id")
    private Integer specialtyId;

    @ManyToOne
    @JoinColumn(name = "vet_id", insertable = false, updatable = false)
    private Vet vet;

    @ManyToOne
    @JoinColumn(name = "specialty_id", insertable = false, updatable = false)
    private Specialty specialty;
}