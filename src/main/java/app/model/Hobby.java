package app.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@Entity
@Table(name = "hobby")
public class Hobby {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private  Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "wikiLink")
    private String wikiLink;

    @Column(name = "category")
    private String category;

    @Column(name = "style")

    @Enumerated(EnumType.STRING)
    private Style style;


    @ManyToMany
    private static Set<User> user = new HashSet<>();

    public Hobby(String name, String wikiLink, String category, Style style) {
        this.name = name;
        this.wikiLink = wikiLink;
        this.category = category;
        this.style = style;
    }

    }


}