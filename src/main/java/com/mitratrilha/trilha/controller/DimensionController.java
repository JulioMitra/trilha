package com.mitratrilha.trilha.controller;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.mitratrilha.trilha.domain.dimension.*;
import com.mitratrilha.trilha.repository.DimensionRepository;
import com.mitratrilha.trilha.service.DimensionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("dimen")
public class DimensionController {

    @Autowired
    private DimensionRepository repository;

    @Autowired
    private DimensionService dimensionService;


    @PostMapping
    @Transactional
    public ResponseEntity create(@RequestBody @Valid CreateDimension dados, UriComponentsBuilder uriBuilder) {
        var dimension = new Dimension(dados);
        repository.save(dimension);

        var uri = uriBuilder.path("/dimen/{id}").buildAndExpand(dimension.getId()).toUri();
        return ResponseEntity.created(uri).body(new DimensionDetail(dimension));
    }

    @GetMapping("/{id}")
    @JsonManagedReference
    public List<DimensionParents> read(@PathVariable Long id) {
        return repository.findAllById(id).stream().map(dimension -> {
            var parents = read(repository.findAllBySonid(dimension.getId()));
            return new DimensionParents(dimension, parents);
        }).toList();
    }

    @JsonBackReference
    List<DimensionParents> read(List<Dimension> dimensions) {
        return dimensions.stream().map(dimension -> {
            var parents = read(repository.findAllBySonid(dimension.getId()));
            return new DimensionParents(dimension, parents);
        }).toList();
    }


    @GetMapping
    public List<DimensionDetail> read() {
        return repository.findAll().stream().map(DimensionDetail::new).toList();
    }
    @PutMapping
    @Transactional
    public ResponseEntity update(@RequestBody @Valid UpdateDimension dados) {
        var dimension = repository.getReferenceById(dados.id());
        dimension.updateDimension(dados);

        return ResponseEntity.ok(new DimensionDetail(dimension));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity delete(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.ok("Dimensão excluída!");
    }

    @PostMapping("/jdbc")
    @Transactional
    public ResponseEntity createDimensionTeste(@RequestBody @Valid CreateDimensionTeste dados) {
        var dimension = new Dimension(dados);
        repository.save(dimension);
        dimensionService.createDimensionTeste(dimension);
        return ResponseEntity.ok(new DimensionDetail(dimension));
    }

    @GetMapping("/jdbc/{id}")
    public List<Dimension> read1(@PathVariable long id) {
        return dimensionService.findDimensionMember(id);
    }

    @PostMapping("/jdbc/{id}")
    @Transactional
    public ResponseEntity createDimensionMember(@RequestBody @Valid CreateDimensionMember dados, @PathVariable long id) {
        var dimension = new Dimension(dados);
        dimensionService.createDimensionMembers(dimension, id);
        return ResponseEntity.ok(dimensionService.findDimensionMemberByNameLast(id, dimension.getName()));
      }
}

