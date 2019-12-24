package ru.sirosh.dto;

public final class DtoProductBuilder {
    public long id;
    public String name;
    public String description;
    public double cost;
    public String token;

    private DtoProductBuilder() {
    }

    public static DtoProductBuilder aDtoProduct() {
        return new DtoProductBuilder();
    }

    public DtoProductBuilder withId(long id) {
        this.id = id;
        return this;
    }

    public DtoProductBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public DtoProductBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public DtoProductBuilder withCost(double cost) {
        this.cost = cost;
        return this;
    }

    public DtoProductBuilder withToken(String token) {
        this.token = token;
        return this;
    }

    public DtoProduct build() {
        DtoProduct dtoProduct = new DtoProduct();
        dtoProduct.setId(id);
        dtoProduct.setName(name);
        dtoProduct.setDescription(description);
        dtoProduct.setCost(cost);
        dtoProduct.setToken(token);
        return dtoProduct;
    }
}
