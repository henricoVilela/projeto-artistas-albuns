package com.projeto.backend.shared;

public class StringUtils {
	
	/**
     * Normaliza (remove acentos e converte para minúsculas).
     */
	public static String normalizar(String nome) {
        if (nome == null) return "";
        return java.text.Normalizer.normalize(nome, java.text.Normalizer.Form.NFD)
                .replaceAll("[\\p{InCombiningDiacriticalMarks}]", "")
                .toLowerCase();
    }

}
