package com.projeto.backend.domain.regional;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionalRepository extends JpaRepository<Regional, Integer> {
    /**
     * Busca regionais ativas com paginação.
     */
    Page<Regional> findByAtivoTrue(Pageable pageable);

    /**
     * Busca regionais por nome (parcial, case-insensitive).
     */
    Page<Regional> findByNomeContainingIgnoreCaseAndAtivoTrue(String nome, Pageable pageable);
    
    /**
     * Busca regional por ID externo (da API).
     */
    Optional<Regional> findByExternalId(Long externalId);
    
    /**
     * Conta regionais ativas.
     */
    long countByAtivoTrue();

}
