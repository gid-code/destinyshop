package com.gidcode.destinyshop.model;

import com.gidcode.destinyshop.dto.CartDto;
import com.gidcode.destinyshop.dto.UserDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.NaturalId;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;

    @NaturalId
    private String email;
    private String password;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Cart cart;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders;

    public UserDto toDto(){
        CartDto cartDto;
        if (cart == null) {
            cartDto = null;
        } else {
            cartDto = cart.toDto();
        }
        return new UserDto(
                id,
                firstName,
                lastName,
                email,
                orders.stream().map(Order::toDto).toList(),
                cartDto
        );
    }
}
