package com.disid.restful.model;

import org.springframework.roo.addon.javabean.annotations.RooJavaBean;
import org.springframework.roo.addon.javabean.annotations.RooToString;
import org.springframework.roo.addon.jpa.annotations.entity.RooJpaEntity;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;

@RooJavaBean
@RooToString
@RooJpaEntity
// @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
// property = "id")
public class Category {

  /**
   */
  private String name;

  /**
   */
  private String description;

  /**
   * Bidirectional aggregation many-to-many relationship. Parent side.
   */
  @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "categories",
      fetch = FetchType.LAZY)
  private Set<Product> products = new HashSet<Product>();

  /**
   * Changed to private to avoid being generated by Roo, but this method is not really needed.
   */
  private void setProducts(Set<Product> products) {
    this.products = products;
  }

  /**
   * Adds a list of products to the category, taking care to update the 
   * relationship from the {@link Product} to the
   * {@link Category} either.
   * @param productsToAdd products to add to the category (required)
   * @throws IllegalArgumentException if products is null or empty
   */
  public void addToProducts(Collection<Product> productsToAdd) {
    Assert.notEmpty(productsToAdd, "At least one product to add is required");
    for (Product product : productsToAdd) {
      this.products.add(product);
      product.getCategories().add(this);
    }
  }

  /**
   * Removes a list of products from this category, taking care to update the 
   * relationship from the {@link Product} to the
   * {@link Category} either.
   * @param productsToRemove products to remove from the customer (required)
   * @throws IllegalArgumentException if products is empty
   */
  public void removeFromProducts(Collection<Product> productsToRemove) {
    Assert.notEmpty(productsToRemove, "At least one product to remove is required");
    for (Product product : productsToRemove) {
      this.products.remove(product);
      product.getCategories().remove(this);
    }
  }
}
