package com.sparta.delivery.user.entity;

import com.sparta.delivery.common.BaseEntity;
import com.sparta.delivery.user.dto.SignUpRequest;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.Type;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "p_user")
@Builder
@SQLRestriction("deleted_at is null")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "user_name")
    private String username;

    private String nickName;

    private String email;

    private String password;

    private String phone;

    private String address;

    private String zipcode;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    private boolean isPublic;


    public static User toEntity(SignUpRequest signUpRequest) {
        return User.builder()
                .email(signUpRequest.getEmail())
                .username(signUpRequest.getUsername())
                .address(signUpRequest.getAddress())
                .zipcode(signUpRequest.getZipcode())
                .nickName(signUpRequest.getNickname())
                .phone(signUpRequest.getTel())
                .role(signUpRequest.getRole())
                .password(signUpRequest.getPassword())
                .isPublic(true)
                .build();
    }


    public void delete(UUID userId) {
        this.markDeleted(userId);
    }
}
