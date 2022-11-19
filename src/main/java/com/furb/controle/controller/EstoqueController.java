package com.furb.controle.controller;

import com.furb.controle.dao.DAOMarca;
import com.furb.controle.model.MarcaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class EstoqueController {

    @Autowired
    private DAOMarca marcaRepository;

    // MARCA

    @RequestMapping(value = "/getAllMarcas", method = RequestMethod.GET)
    public List<MarcaDAO> getAllMarcas() {
        return (List<MarcaDAO>) marcaRepository.findAll();
    }

    @RequestMapping(value = "/getMarcaById", method = RequestMethod.GET)
    public Optional<MarcaDAO> getMarcaById(@RequestParam("id") int id) {
        //TODO: Verificar se tem que tratar
        System.out.println(id);
        return marcaRepository.findById(id);
    }

    @RequestMapping(value = "/checkMarca", method = RequestMethod.GET)
    public String marcaExiste(@RequestParam("id") int id) {
        if (marcaRepository.existsById(id)) {
            return "Uma marca existente possui este ID";
        }
        return "Nenhuma marca utiliza este ID no momento";
    }

    @RequestMapping(value = "/addMarca", method = RequestMethod.POST)
    public String addMarca(@RequestBody MarcaDAO marca) {
        marcaRepository.save(marca);
        return "Marca salva!";
    }

    @RequestMapping(value = "/updateMarca", method = RequestMethod.PUT)
    public Optional<MarcaDAO> updateMarca(@RequestBody MarcaDAO newMarca, @RequestParam("id") int id) {
        return marcaRepository.findById(id).map(
                marca -> {
                    //marca.setMARCA_ID(newMarca.getMARCA_ID()); -> N pode alterar poís é o id
                    marca.setCnpj(newMarca.getCnpj());
                    marca.setRazaoSocial(newMarca.getRazaoSocial());
                    marca.setNome(newMarca.getNome());
                    return marcaRepository.save(marca);
                }
        );
    }


    // PRODUTO

    // TESTE

    @RequestMapping(value = "/teste", method = RequestMethod.GET)
    public String teste() {
        return "Teste foi um sucesso!";
    }
}
