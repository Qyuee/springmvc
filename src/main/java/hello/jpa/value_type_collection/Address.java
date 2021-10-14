package hello.jpa.value_type_collection;

import lombok.Getter;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable @Getter
public class Address {
    private String city;
    private String street;
    private String zipcode;

    // 임베디드 값 타입 -> 기본 생성자 필수
    public Address() {
    }

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }

    // 불변객체 -> setter 제거

    // 값 타입 비교 보장 -> equals() 재정의 필요
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(city, address.city) && Objects.equals(street, address.street) && Objects.equals(zipcode, address.zipcode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(city, street, zipcode);
    }
}
