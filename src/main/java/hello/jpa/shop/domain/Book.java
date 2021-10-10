package hello.jpa.shop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity @Getter @Setter
@Table(name = "BOOK")
public class Book extends Item {
    private String author;
    private String isbn;
}
