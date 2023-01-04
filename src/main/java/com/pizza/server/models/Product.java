package com.pizza.server.models;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.NonNull;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "products")
@NoArgsConstructor
@RequiredArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NonNull
    @Column(name = "title", length = 60, nullable = false)
    @NotBlank(message = "Title is required")
    @Size(min = 1, max = 60, message = "Title must be between 1 and 60 characters")
    private String title;

    @NonNull
    @Column(name = "description", length = 200, nullable = false)
    @NotBlank(message = "Description is required")
    @Size(min = 1, max = 200, message = "Description must be between 1 and 200 characters")
    private String description;

    @NonNull
    @Column(name = "img", columnDefinition = "TEXT", nullable = false)
    @NotBlank(message = "Img is mandatory")
    private String img;

    @NonNull
    @Column(name = "prices", columnDefinition = "TEXT", nullable = false)
    @NotBlank(message = "Prices are mandatory")
    private String prices;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ExtraOption> extraOptions;

    @JsonIgnore
    @ManyToMany(mappedBy = "products")
    private Set<Order> orders;

    // *************************** Getters and Setters ***************************

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImg() {
        return this.img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getPrices() {
        return this.prices;
    }

    public void setPrices(String prices) {
        this.prices = prices;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<ExtraOption> getExtraOptions() {
        return this.extraOptions;
    }

    public void setExtraOptions(List<ExtraOption> extraOptions) {
        this.extraOptions = extraOptions;
    }

    public Set<Order> getOrders() {
        return this.orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

}
