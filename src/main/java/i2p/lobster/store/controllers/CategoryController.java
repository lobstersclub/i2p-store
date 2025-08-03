package i2p.lobster.store.controllers;


import i2p.lobster.store.dtos.CreateCategoryDto;
import i2p.lobster.store.models.Category;
import i2p.lobster.store.repository.CategoryRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    @Autowired
    private CategoryRepository categoryRepository;


    @PostMapping
    public ResponseEntity<Category> createCategory(@Valid @RequestBody CreateCategoryDto dto){
        try {
            Category newCategory = new Category();
            newCategory.setName(dto.getName());
            if (dto.getDescription() != null) {
                newCategory.setDescription(dto.getDescription());
            }
            Category saved = categoryRepository.save(newCategory);
            return ResponseEntity.ok(saved);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }





    @GetMapping
    public List<Category> getAllCategories(){
        return categoryRepository.findAll();
    }



}
