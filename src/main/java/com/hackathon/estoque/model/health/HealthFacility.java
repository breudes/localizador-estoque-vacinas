package com.hackathon.estoque.model.health;

import com.hackathon.estoque.dto.AddressRequestDTO;
import com.hackathon.estoque.model.Address;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Entity
@Table(name = "health_facilities")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HealthFacility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String cnes;
    private String email;
    private String phone;
    private boolean active = true;
    @OneToOne(mappedBy = "healthFacility", cascade = CascadeType.ALL)
    private Address address;
    @OneToMany(mappedBy = "healthFacility", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Inventory> vaccinesInventory;
    private Long createdBy;
    private Long updatedBy;

    public void setAddress(AddressRequestDTO address) {
        Address newAddress = new Address();
        newAddress.setCep(address.getCep());
        newAddress.setStreet(address.getStreet());
        newAddress.setCity(address.getCity());
        newAddress.setState(address.getState());
        this.address = newAddress;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
