package app.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@Entity
@Table(name = "hobby")
@NoArgsConstructor
public class Hobby {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "wikiLink")
    private String wikiLink;

    @Column(name = "category")
    private String category;

    @Column(name = "style")

    @Enumerated(EnumType.STRING)
    private Style style;


    @ToString.Exclude
    @ManyToMany
    private Set<User> users = new HashSet<>();

    public Hobby(String name, String wikiLink, String category, Style style) {
        this.name = name;
        this.wikiLink = wikiLink;
        this.category = category;
        this.style = style;
    }

    public void addUser(User user){
        users.add(user);
    }

}

