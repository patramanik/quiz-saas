package com.quizsaas.repository;

import com.quizsaas.entity.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassroomRepository extends JpaRepository<Classroom, Long> {
    List<Classroom> findByOrganizationId(Long organizationId);

    List<Classroom> findByTeacherId(Long teacherId);
}
