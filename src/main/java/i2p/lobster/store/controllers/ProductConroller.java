package i2p.lobster.store.controllers;

import i2p.lobster.store.dtos.CreateProductDto;
import i2p.lobster.store.models.Product;
import i2p.lobster.store.repository.CategoryRepository;
import i2p.lobster.store.repository.ProductRepository;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

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
}
