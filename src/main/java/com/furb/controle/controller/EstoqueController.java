package com.furb.controle.controller;

import com.furb.controle.dao.DAOMarcaImpl;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EstoqueController {

    private DAOMarcaImpl marcaRepository = new DAOMarcaImpl();

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String marcaExiste(@RequestBody Integer id) {
        if (marcaRepository.existsById(id)) {
            return "Uma marca existente possui este ID";
        }
        return "Nenhuma marca utiliza este ID no momento";
    }

}
