package com.tecsup.petclinic.webs;

import com.tecsup.petclinic.dtos.VetSpecialtyDTO;
import com.tecsup.petclinic.exceptions.VetSpecialtyNotFoundException;
import com.tecsup.petclinic.services.VetSpecialtyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vet_specialties")
@Slf4j
public class VetSpecialtyController {

    private final VetSpecialtyService vetSpecialtyService;

    public VetSpecialtyController(VetSpecialtyService vetSpecialtyService) {
        this.vetSpecialtyService = vetSpecialtyService;
    }

    @GetMapping
    public ResponseEntity<List<VetSpecialtyDTO>> findAll() {
        List<VetSpecialtyDTO> list = vetSpecialtyService.findAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VetSpecialtyDTO> findById(@PathVariable Integer id) {
        try {
            VetSpecialtyDTO dto = vetSpecialtyService.findById(id);
            return ResponseEntity.ok(dto);
        } catch (VetSpecialtyNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<VetSpecialtyDTO> create(@RequestBody VetSpecialtyDTO vetSpecialtyDTO) {
        VetSpecialtyDTO newDTO = vetSpecialtyService.create(vetSpecialtyDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VetSpecialtyDTO> update(@RequestBody VetSpecialtyDTO vetSpecialtyDTO, @PathVariable Integer id) {
        try {
            VetSpecialtyDTO updatedDTO = vetSpecialtyService.update(vetSpecialtyDTO, id);
            return ResponseEntity.ok(updatedDTO);
        } catch (VetSpecialtyNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        try {
            vetSpecialtyService.delete(id);
            return ResponseEntity.ok().build();
        } catch (VetSpecialtyNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}