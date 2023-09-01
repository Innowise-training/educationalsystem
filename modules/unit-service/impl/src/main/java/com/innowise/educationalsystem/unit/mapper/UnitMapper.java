package com.innowise.educationalsystem.unit.mapper;

import com.innowise.educationalsystem.unit.dto.UnitDto;
import com.innowise.educationalsystem.unit.dto.CreateUnitDto;
import com.innowise.educationalsystem.unit.dto.UpdateUnitDto;
import com.innowise.educationalsystem.unit.entity.Unit;
import java.util.List;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UnitMapper {
    Unit unitDtoToUnit(UnitDto unitDto);

    UnitDto unitToUnitDto(Unit unit);

    Unit createUnitDtoToUnit(CreateUnitDto createUnitDto);

    CreateUnitDto unitToCreateUnitDto(Unit unit);

    Unit updateUnitDtoToUnit(UpdateUnitDto updateUnitDto);

    UpdateUnitDto unitToUpdateUnitDto(Unit unit);

    List<Unit> toEntityList(List<UnitDto> unitDtoList);

    List<UnitDto> toDtoList(List<Unit> unitList);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Unit partialUpdate(UnitDto unitDto, @MappingTarget Unit unit);
}
