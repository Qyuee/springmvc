package hello.jpa.value_type;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable @Getter @Setter
public class Address {
    @Column(name = "CITY2", length = 20)
    private String city;
    private String street;
    private String zipcode;

    // 기본생성자 필수
    public Address() { }

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
