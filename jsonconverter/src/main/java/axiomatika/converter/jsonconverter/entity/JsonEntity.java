package axiomatika.converter.jsonconverter.entity;

import jakarta.persistence.*;

@Entity
@Table(name="json")
public class JsonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable=false)
    private Long id;

    @Column(name="content", nullable = false)
    private String content;

    public JsonEntity() {
        ;
    }

    public JsonEntity(String content) {
        this.content = content;
    }

    public JsonEntity(Long id, String content) {
        this.id = id;
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
        JsonEntity json = (JsonEntity) o;
        return id.equals(json.id) && content.equals(json.content);
    }
}