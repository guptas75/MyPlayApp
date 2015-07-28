package models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Query;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import play.db.jpa.*;


@Entity
@Table(name = "sales", uniqueConstraints = { @UniqueConstraint(columnNames = "sales_id") })
public class SalesEntry implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "sales_id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "date", nullable = false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Date date = new Date();
    
    @ManyToOne(targetEntity = Product.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false, referencedColumnName = "product_id")
    private Product product;
    
    @Column(name = "amount", columnDefinition="NUMERIC(7,2) default '0.00'")
    private double amount = 0;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Transactional
    public void update() {
        JPA.em().merge(this);
    }

    @Transactional
    public void save() {
        JPA.em().persist(this);
    }
    
    @SuppressWarnings("unchecked")
    @Transactional(readOnly=true) 
    public static List<Object[]> generateReport() {
    	
    	String qs="select p.name, count(s.product_id), sum(s.amount) from sales s join product p on s.product_id=p.product_id group by p.product_id";
        Query query= JPA.em().createNativeQuery(qs);
        List<Object[]> results= query.getResultList();
        return results;
      }
}
