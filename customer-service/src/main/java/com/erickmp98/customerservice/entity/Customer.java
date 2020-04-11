package com.erickmp98.customerservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
@Table(name = "customers")
public class Customer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "id number value must not be empty")
    @Size(min = 8, max =8 , message = "id number size is 8")
    @Column(name = "number_id",unique = true , length = 8, nullable = false)
    private String numberID;


    @NotEmpty(message = "Firstname value must not be empty")
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotEmpty(message = "Lastname value must not be empty")
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotEmpty(message = "email value must not be empty")
    @Email(message = "it's not a well-formed email")
    @Column(unique = true,nullable = false)
    private String email;

    @Column(name = "photo_url")
    private String photoUrl;

    @NotNull(message = "value region must not be empty")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="region_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    private Region region;

    private String state;
}
