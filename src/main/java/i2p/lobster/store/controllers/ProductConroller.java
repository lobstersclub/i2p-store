package i2p.lobster.store.controllers;

import i2p.lobster.store.dtos.CreateProductDto;
import i2p.lobster.store.dtos.UpdateCategoryDto;
import i2p.lobster.store.dtos.UpdateProductDto;
import i2p.lobster.store.models.Product;
import i2p.lobster.store.repository.CategoryRepository;
import i2p.lobster.store.repository.ProductRepository;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@RestController
@RequestMapping(path="/api/products")
public class ProductConroller {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @PostMapping
    public ResponseEntity<Product> createProduct (@Valid @RequestBody CreateProductDto dto){
        try{
            Product created = new Product();
            created.setName(dto.getName());
            if(dto.getDescription() != null){
                created.setDescription(dto.getDescription());
            }

            created.setPrice(dto.getPrice());

            if(categoryRepository.findById(dto.getCategoryId()).orElse(null) != null) {
                created.setCategory(categoryRepository.findById(dto.getCategoryId()).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Не найдена категория с указанным ID.")));
            }

            Product saved = productRepository.save(created);
            return ResponseEntity.ok(saved);
        }

        catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }

    }

    @GetMapping(path="/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id){
        try {
            Product found = productRepository.findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
            return ResponseEntity.ok(found);
        }

        catch (Exception e) {
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping
    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }


    @PatchMapping(path="/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @Valid @RequestBody UpdateProductDto dto) {
        Product updated = productRepository.findById(id).orElseThrow(() ->  new HttpClientErrorException(HttpStatus.NOT_FOUND));
        if(dto.getName() != null) {
            updated.setName(dto.getName());
        }
        if(dto.getDescription() != null) {
            updated.setDescription(dto.getDescription());
        }
        if(dto.getPrice() != null) {
            updated.setPrice(dto.getPrice());
        }
        if(dto.getCategoryId() != null) {
            if (categoryRepository.existsById(dto.getCategoryId())) {
                updated.setCategory(categoryRepository.findById(dto.getCategoryId()).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND)));
            }
        }

        Product saved = productRepository.save(updated);
        return ResponseEntity.ok(saved);

    }

    @DeleteMapping(path="/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }




}
