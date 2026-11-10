package com.tecsup.petclinic.webs;

import com.tecsup.petclinic.dtos.VetDTO;
import com.tecsup.petclinic.exceptions.VetNotFoundException;
import com.tecsup.petclinic.services.VetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vets")
@Slf4j
public class VetController {

    private final VetService vetService;

    public VetController(VetService vetService) {
        this.vetService = vetService;
    }

    @GetMapping
    public ResponseEntity<List<VetDTO>> findAll() {
        List<VetDTO> list = vetService.findAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VetDTO> findById(@PathVariable Integer id) {
        try {
            VetDTO vetDTO = vetService.findById(id);
            return ResponseEntity.ok(vetDTO);
        } catch (VetNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<VetDTO> create(@RequestBody VetDTO vetDTO) {
        VetDTO newVet = vetService.create(vetDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newVet);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VetDTO> update(@RequestBody VetDTO vetDTO, @PathVariable Integer id) {
        try {
            VetDTO updatedVet = vetService.update(vetDTO, id);
            return ResponseEntity.ok(updatedVet);
        } catch (VetNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        try {
            vetService.delete(id);
            return ResponseEntity.ok().build();
        } catch (VetNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}