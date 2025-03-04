package axiomatika.converter.jsonconverter.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name="xslt")
public class XsltEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable=false)
    private Long id;

    @Column(name="content", nullable = false)
    private String content;

    public XsltEntity() {
        ;
    }

    public XsltEntity(String content) {
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
        XsltEntity xslt = (XsltEntity) o;
        return Objects.equals(id, xslt.id) && Objects.equals(content, xslt.content);
    }
}
