package br.com.orchestrated.productvalidationservice.core.service;

import br.com.orchestrated.productvalidationservice.core.dto.CreateProductRequest;
import br.com.orchestrated.productvalidationservice.core.dto.UpdateProductRequest;
import br.com.orchestrated.productvalidationservice.core.dto.ValidateRequest;
import br.com.orchestrated.productvalidationservice.core.dto.ValidateResponse;
import br.com.orchestrated.productvalidationservice.core.model.Product;
import br.com.orchestrated.productvalidationservice.core.producer.KafkaProducer;
import br.com.orchestrated.productvalidationservice.core.repository.ProductRepository;
import br.com.orchestrated.productvalidationservice.core.utils.JsonUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final KafkaProducer producer;
    private final JsonUtil jsonUtil;

    public Product createProduct(CreateProductRequest req) {
        validateCreate(req);
        Product product = Product.builder()
                .name(req.getName())
                .description(req.getDescription())
                .price(req.getPrice())
                .available(req.getAvailable())
                .build();
        product = productRepository.save(product);

        Map<String, Object> payload = new HashMap<>();
        payload.put("event", "ProdutoCriado");
        payload.put("product", product);
        producer.sendEvent(jsonUtil.toJson(payload));

        return product;
    }

    public Product updateProduct(Integer id, UpdateProductRequest req) {
        var opt = productRepository.findById(id);
        if (opt.isEmpty()) {
            throw new IllegalArgumentException("Product not found");
        }
        var product = opt.get();
        if (req.getName() != null) product.setName(req.getName());
        if (req.getDescription() != null) product.setDescription(req.getDescription());
        if (req.getPrice() != null) product.setPrice(req.getPrice());
        if (req.getAvailable() != null) product.setAvailable(req.getAvailable());

        if (product.getPrice() < 0) throw new IllegalArgumentException("Price cannot be negative");
        if (product.getAvailable() < 0) throw new IllegalArgumentException("Available cannot be negative");

        product = productRepository.save(product);

        Map<String, Object> payload = new HashMap<>();
        payload.put("event", "ProdutoAtualizado");
        payload.put("product", product);
        producer.sendEvent(jsonUtil.toJson(payload));

        return product;
    }

    public Product getProduct(Integer id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
    }

    public ValidateResponse validateProduct(ValidateRequest req) {
        var opt = productRepository.findById(req.getProductId());
        if (opt.isEmpty()) {
            Map<String, Object> payload = Map.of("event", "ProdutoNaoEncontrado", "productId", req.getProductId());
            producer.sendEvent(jsonUtil.toJson(payload));
            throw new IllegalArgumentException("Product not found");
        }
        var product = opt.get();
        boolean available = product.getAvailable() >= req.getQuantity();
        Map<String, Object> payload = Map.of(
                "event", available ? "ProdutoValidado" : "ProdutoInvalido",
                "productId", product.getId(),
                "requestedQuantity", req.getQuantity(),
                "available", product.getAvailable()
        );
        producer.sendEvent(jsonUtil.toJson(payload));
        return new ValidateResponse(available, available ? "Available" : "Insufficient stock");
    }

    private void validateCreate(CreateProductRequest req) {
        if (req.getPrice() == null || req.getPrice() < 0) throw new IllegalArgumentException("Price cannot be negative");
        if (req.getAvailable() == null || req.getAvailable() < 0) throw new IllegalArgumentException("Available cannot be negative");
    }
}
