package axiomatika.converter.jsonconverter.entity;

import jakarta.persistence.*;

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
}
