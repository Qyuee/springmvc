package hello.jpa.shop.domain;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
@Getter
public class Address {
    @Column(length = 10)
    private String city;
    private String street;
    @Column(length = 5)
    private String zipcode;

    /*
    1. Address를 별도의 클래스로 분리하여 의미있는 메소드를 별도로 정의 할 수 있다.
    2. Delivery와 member에 Address가 공통적으로 사용된다. 재활용이 가능하다.
     */
    public String getFullAddress() {
        return this.getCity() + ", " + this.getStreet() + ", " + this.getZipcode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(getCity(), address.getCity()) && Objects.equals(getStreet(), address.getStreet()) && Objects.equals(getZipcode(), address.getZipcode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCity(), getStreet(), getZipcode());
    }
}
