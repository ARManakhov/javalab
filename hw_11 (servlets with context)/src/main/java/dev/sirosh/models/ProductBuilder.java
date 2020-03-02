package dev.sirosh.models;

public final class ProductBuilder {
    private long id;
    private String name;
    private String description;
    private double cost;
    private boolean isDeleted;

    private ProductBuilder() {
    }

    public static ProductBuilder aProduct() {
        return new ProductBuilder();
    }

    public ProductBuilder withId(long id) {
        this.id = id;
        return this;
    }

    public ProductBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public ProductBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public ProductBuilder withCost(double cost) {
        this.cost = cost;
        return this;
    }

    public ProductBuilder isDeleited(boolean isDeleted) {
        this.isDeleted = isDeleted;
        return this;
    }

    public Product build() {
        Product product = new Product();
        product.setId(id);
        product.setName(name);
        product.setDescription(description);
        product.setCost(cost);
        product.setDeleted(isDeleted);
        return product;
    }
}
