package com.tecsup.petclinic.webs;

import com.tecsup.petclinic.dtos.OwnerDTO;
import com.tecsup.petclinic.exceptions.OwnerNotFoundException;
import com.tecsup.petclinic.services.OwnerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/owners")
@Slf4j
public class OwnerController {

    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @GetMapping
    public ResponseEntity<List<OwnerDTO>> findAll() {
        List<OwnerDTO> list = ownerService.findAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OwnerDTO> findById(@PathVariable Integer id) {
        try {
            OwnerDTO ownerDTO = ownerService.findById(id);
            return ResponseEntity.ok(ownerDTO);
        } catch (OwnerNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<OwnerDTO> create(@RequestBody OwnerDTO ownerDTO) {
        OwnerDTO newOwner = ownerService.create(ownerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newOwner);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OwnerDTO> update(@RequestBody OwnerDTO ownerDTO, @PathVariable Integer id) {
        try {
            OwnerDTO updatedOwner = ownerService.update(ownerDTO, id);
            return ResponseEntity.ok(updatedOwner);
        } catch (OwnerNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        try {
            ownerService.delete(id);
            return ResponseEntity.ok().build();
        } catch (OwnerNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}