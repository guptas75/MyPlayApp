package models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import play.db.jpa.*;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "product", uniqueConstraints = { @UniqueConstraint(columnNames = "product_id") })
@NamedQueries({
        @NamedQuery(name = "Product.findById", query = "SELECT p FROM Product p where p.id=:id")
})
public class Product implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_id", unique = true, nullable = false)
    private Integer id;

    @Length(max = 100)
    @Column(name = "name")
    private String name = "";

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Transactional
    public void update() {
        JPA.em().merge(this);
    }

    @Transactional
    public void save() {
        JPA.em().persist(this);
    }
    
    @Transactional(readOnly=true)
    public static Product findById(final Integer id) {
        Product p = null;
    
        List<?> results = JPA.em().createNamedQuery("Product.findById").setParameter("id", id).getResultList();
        if (!results.isEmpty()) {
            p = (Product) results.get(0);
        }
        
        return p;
    }
}
