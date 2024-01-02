package com.curso.ecommerce.service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.curso.ecommerce.model.Orden;
import com.curso.ecommerce.repository.IOrdenRepository;

@Service
public class OrdenServiceImpl implements IOrdenService {

    @Autowired
    private IOrdenRepository ordenRepository;

    @Override
    public Orden save(Orden orden) {
        return ordenRepository.save(orden);
    }

    @Override
    public List<Orden> findAll() {
        return ordenRepository.findAll();
    }

    public String generarNumeroOrden() {
        int numero;
        String numeroConcatenado;
    
        List<Orden> ordenes = findAll();
    
        if (ordenes.isEmpty()) {
            numero = 1;
        } else {
            List<Integer> numeros = ordenes.stream()
                                           .map(Orden::getNumero)
                                           .filter(Objects::nonNull)
                                           .map(numeroStr -> {
                                               try {
                                                   return Integer.parseInt(numeroStr);
                                               } catch (NumberFormatException e) {
                                                   return null;
                                               }
                                           })
                                           .filter(Objects::nonNull)
                                           .collect(Collectors.toList());
    
            Optional<Integer> maximo = numeros.stream()
                                              .max(Comparator.naturalOrder());
            numero = maximo.orElse(0) + 1;
        }
    
        numeroConcatenado = String.format("%09d", numero);
    
        return numeroConcatenado;
    }    

}
