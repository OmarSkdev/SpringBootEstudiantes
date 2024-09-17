package com.app.web.repositorio;

import com.app.web.entidad.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EstudianteRepositorio extends JpaRepository<Estudiante, Long> {

    Optional<Estudiante> findByEmail(String email);
}
