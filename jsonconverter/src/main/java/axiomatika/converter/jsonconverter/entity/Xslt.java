package axiomatika.converter.jsonconverter.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name="xslt")
public class Xslt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable=false)
    private Long id;

    @Column(name="content", nullable = false)
    private String content;

    public Xslt() {
        ;
    }

    public Xslt(String content) {
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Xslt xslt = (Xslt) o;
        return Objects.equals(id, xslt.id) && Objects.equals(content, xslt.content);
    }
}
