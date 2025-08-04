package i2p.lobster.store.controllers;


import com.oracle.svm.core.annotate.Delete;
import i2p.lobster.store.dtos.CreateCategoryDto;
import i2p.lobster.store.dtos.UpdateCategoryDto;
import i2p.lobster.store.models.Category;
import i2p.lobster.store.repository.CategoryRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

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

    @GetMapping(path="/{id}")
    public ResponseEntity<Category> getCategory(@PathVariable Long id) {
        try{
            Category found = categoryRepository.findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
            return ResponseEntity.ok(found);
        }
        catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }



    @GetMapping
    public List<Category> getAllCategories(){
        return categoryRepository.findAll();
    }

    @PatchMapping(path="/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody UpdateCategoryDto dto){
        try{
            Category updated = categoryRepository.findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Не найден ID категории, которую нужно править"));
            if(dto.getName() != null){
                updated.setName(dto.getName());
            }
            if(dto.getDescription() != null){
                updated.setDescription(dto.getDescription());
            }

            Category saved = categoryRepository.save(updated);
            return ResponseEntity.ok(saved);
        }
        catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(path="/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id){
        try{
            categoryRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }



}
