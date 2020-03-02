package ru.sirosh.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "@type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = DtoUser.class, name = "user"),
        @JsonSubTypes.Type(value = DtoProduct.class, name = "product"),
        @JsonSubTypes.Type(value = DtoCommand.class, name = "command"),
        @JsonSubTypes.Type(value = DtoMessage.class, name = "message"),
        @JsonSubTypes.Type(value = DtoGetList.class, name = "getList"),
        @JsonSubTypes.Type(value = DtoProducts.class, name = "products"),
        @JsonSubTypes.Type(value = DtoProductList.class, name = "product_list"),
        @JsonSubTypes.Type(value = DtoStatus.class, name = "status"),
        @JsonSubTypes.Type(value = DtoProductsState.class, name = "productsState"),
        @JsonSubTypes.Type(value = DtoAddresses.class, name = "addresses"),
        @JsonSubTypes.Type(value = DtoAddress.class, name = "address"),
        @JsonSubTypes.Type(value = DtoOrders.class, name = "orders"),
        @JsonSubTypes.Type(value = DtoMessages.class, name = "messages")

})
public interface Dto {
}
