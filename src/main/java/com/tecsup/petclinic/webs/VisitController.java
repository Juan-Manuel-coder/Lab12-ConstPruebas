package com.tecsup.petclinic.webs;

import com.tecsup.petclinic.dtos.VisitDTO;
import com.tecsup.petclinic.exceptions.VisitNotFoundException;
import com.tecsup.petclinic.services.VisitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/visits")
@Slf4j
public class VisitController {

    private final VisitService visitService;

    public VisitController(VisitService visitService) {
        this.visitService = visitService;
    }

    @GetMapping
    public ResponseEntity<List<VisitDTO>> findAll() {
        List<VisitDTO> list = visitService.findAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VisitDTO> findById(@PathVariable Integer id) {
        try {
            VisitDTO visitDTO = visitService.findById(id);
            return ResponseEntity.ok(visitDTO);
        } catch (VisitNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<VisitDTO> create(@RequestBody VisitDTO visitDTO) {
        VisitDTO newVisit = visitService.create(visitDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newVisit);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VisitDTO> update(@RequestBody VisitDTO visitDTO, @PathVariable Integer id) {
        try {
            VisitDTO updatedVisit = visitService.update(visitDTO, id);
            return ResponseEntity.ok(updatedVisit);
        } catch (VisitNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        try {
            visitService.delete(id);
            return ResponseEntity.ok().build();
        } catch (VisitNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}