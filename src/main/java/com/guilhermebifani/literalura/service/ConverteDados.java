package com.guilhermebifani.literalura.service;

// Imports corretos do pacote padrão do Spring Boot
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConverteDados {
    private ObjectMapper mapper = new ObjectMapper();

    public <T> T obterDados(String json, Class<T> classe) {
        try {
            return mapper.readValue(json, classe);
        } catch (JsonProcessingException e) { // <-- Corrigido aqui (sem o 'p' a mais)
            throw new RuntimeException("Erro ao converter dados do JSON", e);
        }
    }
}