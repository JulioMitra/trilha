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


//    @PostMapping
//    @Transactional
//    public ResponseEntity create(@RequestBody @Valid CreateDimension dados, UriComponentsBuilder uriBuilder) {
//        var dimension = new Dimension(dados);
//        repository.save(dimension);
//
//        var uri = uriBuilder.path("/dimen/{id}").buildAndExpand(dimension.getId()).toUri();
//        return ResponseEntity.created(uri).body(new DimensionDetail(dimension));
//    }

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

    @PostMapping
    @Transactional
    public ResponseEntity createDimension(@RequestBody @Valid CreateDimension dados, UriComponentsBuilder uriBuilder) {
        var dimension = new Dimension(dados);
        repository.save(dimension);
        dimensionService.createDimension(dimension);

        var uri = uriBuilder.path("/dimen/{id}").buildAndExpand(dimension.getId()).toUri();
        return ResponseEntity.created(uri).body(new DimensionDetail(dimension));
    }

    // Membro de Dimensão
    @GetMapping("/member/{id}")
    public ResponseEntity findDimensionMember(@PathVariable long id) {
        return ResponseEntity.ok(dimensionService.findDimensionMember(id));
    }

    @PostMapping("/member/{id}")
    @Transactional
    public ResponseEntity createDimensionMember(@RequestBody @Valid CreateDimensionMember dados,
                                                @PathVariable long id, UriComponentsBuilder uriBuilder) {
        var dimension = new Dimension(dados);
        dimensionService.createDimensionMember(dimension, id);

        var uri = uriBuilder.path("/dimen/member/{id}").buildAndExpand(dimension.getId()).toUri();
        return ResponseEntity.created(uri).body(new DimensionDetailMemeber(dimension));
      }

    @PutMapping("/member/{id}")
    @Transactional
    public ResponseEntity updateDimensionMember(@RequestBody @Valid UpdateDimensionMember dados, @PathVariable long id) {
        var dimension = new Dimension(dados);
        dimensionService.updateDimensionMember(dimension, id);
        return ResponseEntity.ok(dimensionService.findDimensionMemberById(id, dimension.getId()));
    }

    @DeleteMapping("/member/{id}")
    @Transactional
    public ResponseEntity deleteDimensionMember(@RequestBody @Valid DeleteDimensionMember dados, @PathVariable Long id) {
        var dimension = new Dimension(dados);
        dimensionService.deleteDimensionMember(dimension, id);
        return  ResponseEntity.ok("Membro com id: "+dimension.getId()+" excluído!");
    }

    // Relacionamento de Membros de Dimensão
    @PostMapping("/relation/{id}")
    @Transactional
    public ResponseEntity createRelationMember(@RequestBody @Valid CreateRelationMember dados, @PathVariable Long id,
     UriComponentsBuilder uriBuilder) {
        var dimension = new RelationMember(dados);
        dimensionService.createRelationMember(dimension, id);

        var uri = uriBuilder.path("/dimen/relation/{id}").buildAndExpand(id).toUri();
        return ResponseEntity.created(uri).body("Relacionamento entre a Dimensão Pai "+id+" e a Dimensão Filha "+dados.idTabelaFilho()
                                 + " realizado com sucesso!");
    }

    @PutMapping("/relation/{id}")
    @Transactional
    public ResponseEntity updateRelationMember(@RequestBody @Valid UpdateRelationMember dados, @PathVariable Long id) {
        var dimension = new RelationMember(dados);
        dimensionService.updateRelationMember(dimension, id);
        return ResponseEntity.ok("Relacionamento entre membros da Dimensão Pai "+id+" e da Dimensão Filha "
                                 +dados.idTabelaFilho()+ " atualizados com sucesso!");
    }

    @DeleteMapping("/relation/{id}")
    @Transactional
    public ResponseEntity deleteRelationMember(@RequestBody @Valid DeleteRelationMember dados, @PathVariable Long id) {
        var dimension = new RelationMember(dados);
        dimensionService.deleteRelationMember(dimension, id);
        return ResponseEntity.ok("Relacionamento entre membros da Dimensão Pai "+id+" e da Dimensão Filha "
                                 +dados.idTabelaFilho()+ " excluídos com sucesso!");
    }

    @GetMapping("/relation/tree/{id}")
    public ResponseEntity showRelationTree(@PathVariable Long id) {
        DimensionNode dimensionNode = dimensionService.showRelationTree(id);
        if(dimensionNode == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dimensionNode);
    }

//    @GetMapping("/relation/member/{id}")
//    public ResponseEntity showRelationMember(@PathVariable Long id) {
//        DimensionNode dimensionNode = dimensionService.showRelationMember(id);
//        if(dimensionNode == null) {
//            return ResponseEntity.notFound().build();
//        }
//        return ResponseEntity.ok(dimensionNode);
//    }

    @GetMapping("/relation/{id}")
    public ResponseEntity getTabela(@PathVariable Long id) {
        List<Tabela> resultado = dimensionService.showRelationMember(id);
        return ResponseEntity.ok(resultado);
    }
}