package com.zource.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.zource.model.jsonViews.ProductView;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;


@Entity
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "products"})

@Table(name = "stock")
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    @NotBlank
    @JsonView(ProductView.Short.class)
    private String name;

    @Column(name = "symbol", nullable = false)
    @NotBlank
    private String symbol;

    @Column(name = "description")
    private String description;

    @Column(name = "type")
    private String type;

    /*@Column(name = "enabled", columnDefinition = "bit default 0", nullable = false)
    private boolean enabled = true;

    @Column(name = "featured", columnDefinition = "bit default 0", nullable = false)
    private boolean featured;*/

   /* @OneToMany(mappedBy = "brand", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Product> products = new HashSet<>();
*/

/*
    @OneToMany(mappedBy = "stock", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Product> products = new HashSet<>();

    @OneToMany(mappedBy = "brand", fetch = FetchType.LAZY, cascade=CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<Category> brandCategories;
*/

}
