package com.innowise.educationalsystem.course.mapper;

import com.innowise.educationalsystem.course.dto.CourseDto;
import com.innowise.educationalsystem.course.dto.CreateCourseDto;
import com.innowise.educationalsystem.course.dto.UpdateCourseDto;
import com.innowise.educationalsystem.course.entity.Course;
import java.util.List;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CourseMapper {
    Course courseDtoToCourse(CourseDto courseDto);

    CourseDto courseToCourseDto(Course course);

    Course createCourseDtoToCourse(CreateCourseDto createCourseDto);

    CreateCourseDto courseToCreateCourseDto(Course course);

    Course updateCourseDtoToCourse(UpdateCourseDto updateCourseDto);

    UpdateCourseDto courseToUpdateCourseDto(Course course);

    List<Course> toEntityList(List<CourseDto> courseDtoList);

    List<CourseDto> toDtoList(List<Course> courseList);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Course partialUpdate(CourseDto courseDto, @MappingTarget Course course);
}
