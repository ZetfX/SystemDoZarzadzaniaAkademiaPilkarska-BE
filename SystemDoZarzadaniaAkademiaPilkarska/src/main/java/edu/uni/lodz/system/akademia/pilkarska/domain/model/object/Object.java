package edu.uni.lodz.system.akademia.pilkarska.domain.model.object;

import edu.uni.lodz.system.akademia.pilkarska.domain.model.academy.Academy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Getter
@Setter
@Entity
public class Object {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String placeName;
    private String street;
    private String city;
    private String zipCode;
    @OneToOne
    private Academy academy;

    public Object(String placeName, String street, String city, String zipCode,Academy academy) {
        this.placeName = placeName;
        this.street = street;
        this.city = city;
        this.zipCode = zipCode;
        this.academy = academy;
    }
}
