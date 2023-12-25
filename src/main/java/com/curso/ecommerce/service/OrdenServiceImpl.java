package com.curso.ecommerce.service;

import java.util.ArrayList;
import java.util.List;

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
        int numero=0;

        List<Orden> ordenes = findAll();
        
        List<Integer> numeros = new ArrayList<Integer>();
        
        ordenes.stream().forEach(o -> numeros.add(Integer.parseInt(o.getNumero())));

        if (ordenes.isEmpty()) {
            numero = 1;
        }else {
            numero = numeros.stream().max(Integer::compare).get();
            numero++;
        }

        StringBuilder numeroConcatenado = new StringBuilder();
        int numeroLongitud = String.valueOf(numero).length();
        int totalDigitos = 10;  // Total de dígitos deseado en el número

        for (int i = 0; i < totalDigitos - numeroLongitud; i++) {
            numeroConcatenado.append("0");
        }
        numeroConcatenado.append(numero);

        return numeroConcatenado.toString();
    }

}
