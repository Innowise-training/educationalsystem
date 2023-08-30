package com.innowise.educationalsystem.unit.controller;

import com.innowise.educationalsystem.unit.dto.UnitDto;
import com.innowise.educationalsystem.unit.dto.CreateUnitDto;
import com.innowise.educationalsystem.unit.dto.UpdateUnitDto;
import com.innowise.educationalsystem.unit.entity.Unit;
import com.innowise.educationalsystem.unit.mapper.UnitMapper;
import com.innowise.educationalsystem.unit.service.impl.UnitServiceImpl;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/units")
public class UnitController {
    private final UnitServiceImpl unitService;

    private final UnitMapper unitMapper;

    @GetMapping
    public ResponseEntity<List<UnitDto>> getAllUnits() {
        List<Unit> units = unitService.findAll();
        return ResponseEntity.ok().body(unitMapper.toDtoList(units));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UnitDto> getUnitById(@PathVariable("id") String id) {
        Unit unit = unitService.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Unit not found with id: " + id));
        return ResponseEntity.ok().body(unitMapper.unitToUnitDto(unit));
    }

    @PostMapping
    public ResponseEntity<UnitDto> createUnit(@Valid @RequestBody CreateUnitDto createUnitDto) {
        Unit unit = unitMapper.createUnitDtoToUnit(createUnitDto);
        Unit createdUnit = unitService.save(unit);
        return ResponseEntity.status(HttpStatus.CREATED).body(unitMapper.unitToUnitDto(createdUnit));
    }

    @PutMapping
    public ResponseEntity<UnitDto> updateUnit(@Valid @RequestBody UpdateUnitDto updateUnitDto) {
        Unit unit = unitMapper.updateUnitDtoToUnit(updateUnitDto);
        Unit updatedUnit = unitService.save(unit);
        return ResponseEntity.ok().body(unitMapper.unitToUnitDto(updatedUnit));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUnit(@PathVariable String id) {
        unitService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
