package hello.jpa.shop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity @Getter @Setter
@Table(name = "MOVIE")
public class Movie extends Item {
    private String director;
    private String actor;
}
