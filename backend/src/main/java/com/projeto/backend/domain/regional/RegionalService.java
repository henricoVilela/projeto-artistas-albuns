package com.projeto.backend.domain.regional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projeto.backend.web.dto.regional.RegionalResponse;

@Service
public class RegionalService {

	private static final Logger logger = LoggerFactory.getLogger(RegionalService.class);
	
	@Autowired
	private RegionalRepository regionalRepository;
	
	@Transactional(readOnly = true)
    public Page<RegionalResponse> listar(String nome, int page, int size, String sortBy, String sortDir) {
        logger.info("Listando regionais - nome: {}, page: {}, size: {}", nome, page, size);

        Sort sort = Sort.by(sortDir.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Regional> pageResult;

        if (nome != null && !nome.trim().isEmpty()) {
            pageResult = regionalRepository.findByNomeContainingIgnoreCaseAndAtivoTrue(nome.trim(), pageable);
        } else {
            pageResult = regionalRepository.findByAtivoTrue(pageable);
        }

        return pageResult.map(RegionalResponse::fromEntity);
    }
}
