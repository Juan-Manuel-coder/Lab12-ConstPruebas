package com.tecsup.petclinic.webs;

import com.tecsup.petclinic.dtos.TypeDTO;
import com.tecsup.petclinic.exceptions.TypeNotFoundException;
import com.tecsup.petclinic.services.TypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/types")
@Slf4j
public class TypesController {

    private final TypeService typeService;

    public TypesController(TypeService typeService) {
        this.typeService = typeService;
    }

    @GetMapping
    public ResponseEntity<List<TypeDTO>> findAll() {
        List<TypeDTO> list = typeService.findAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TypeDTO> findById(@PathVariable Integer id) {
        try {
            TypeDTO typeDTO = typeService.findById(id);
            return ResponseEntity.ok(typeDTO);
        } catch (TypeNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<TypeDTO> create(@RequestBody TypeDTO typeDTO) {
        TypeDTO newType = typeService.create(typeDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newType);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TypeDTO> update(@RequestBody TypeDTO typeDTO, @PathVariable Integer id) {
        try {
            TypeDTO updatedType = typeService.update(typeDTO, id);
            return ResponseEntity.ok(updatedType);
        } catch (TypeNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        try {
            typeService.delete(id);
            return ResponseEntity.ok().build();
        } catch (TypeNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}