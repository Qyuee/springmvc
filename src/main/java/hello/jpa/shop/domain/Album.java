package hello.jpa.shop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity @Getter @Setter
@Table(name = "ALBUM")
public class Album extends Item {
    private String artist;
    private String etc;
}
