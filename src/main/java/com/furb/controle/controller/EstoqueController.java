package com.furb.controle.controller;

import com.furb.controle.dao.CategoriaDAO;
import com.furb.controle.dao.MarcaDAO;
import com.furb.controle.dao.ProdutoDAO;
import com.furb.controle.error.ResourceNotFoundException;
import com.furb.controle.model.categoria.DAOCategoria;
import com.furb.controle.model.marca.DAOMarca;
import com.furb.controle.model.produto.DAOProduto;
import com.furb.controle.model.produto.ProdutoDTO;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class EstoqueController {

    @Autowired
    private MarcaDAO marcaDAO;

    @Autowired
    private CategoriaDAO categoriaDAO;

    @Autowired
    private ProdutoDAO produtoDAO;

    // MARCA

    @GetMapping("/getAllMarcas")
    public ResponseEntity getAllMarcas() {
        return ResponseEntity.ok().body(marcaDAO.findAll());
    }

    @GetMapping("/getMarcaById")
    public ResponseEntity<DAOMarca> getMarcaById(@RequestParam("id") Integer id) {
        Optional<DAOMarca> marca = marcaDAO.findById(id);
        if (marca.isEmpty())
            throw new ResourceNotFoundException("Nenhuma marca encontrada para o id: " + id);
        return ResponseEntity.ok().body(marca.get());
    }

    @GetMapping("/getMarcaByCNPJ")
    public ResponseEntity<DAOMarca> getMarcaByCNPJ(@RequestParam("cnpj") String cnpj) {
        Optional<DAOMarca> marca = marcaDAO.findBycnpj(cnpj);
        if (marca.isEmpty())
            throw new ResourceNotFoundException("Nenhuma marca encontrada para o cnpj: " + cnpj);
        return ResponseEntity.ok().body(marca.get());
    }

    @GetMapping("/getMarcaByNome")
    public ResponseEntity<DAOMarca> getMarcaByNome(@RequestParam("nome") String nome) {
        Optional<DAOMarca> marca = marcaDAO.findBynome(nome);
        if (marca.isEmpty())
            throw new ResourceNotFoundException("Nenhuma marca encontrada com o nome: " + nome);
        return ResponseEntity.ok().body(marca.get());
    }

    @GetMapping("/checkMarca")
    public ResponseEntity<String> marcaExiste(@RequestParam("id") int id) {
        if (marcaDAO.existsById(id)) {
            return ResponseEntity.ok().body("Uma marca existente possui este ID");
        }
        return ResponseEntity.ok().body("Nenhuma marca utiliza este ID no momento");
    }

    @PostMapping("/addMarca")
    public ResponseEntity addMarca(@RequestBody DAOMarca marca) {
        return ResponseEntity.ok().body(marcaDAO.save(marca));
    }

    @PutMapping("/updateMarca")
    public ResponseEntity updateMarca(@RequestBody DAOMarca newMarca, @RequestParam("id") int id) {
        Optional<DAOMarca> marca = marcaDAO.findById(id);
        if (marca.isEmpty())
            throw new ResourceNotFoundException("Nenhuma marca encontrada para o id: " + id);
        return ResponseEntity.ok().body(marca.map(
                        marcaNova -> {
                            //marca.setMARCA_ID(newMarca.getMARCA_ID()); -> N pode alterar poís é o id
                            marcaNova.setCnpj(newMarca.getCnpj());
                            marcaNova.setRazaoSocial(newMarca.getRazaoSocial());
                            marcaNova.setNome(newMarca.getNome());
                            return marcaDAO.save(marcaNova);
                        }
                )
        );
    }

    @DeleteMapping("/deleteMarca")
    public ResponseEntity deleteMarca(@RequestParam("id") Integer id) {
        if (marcaDAO.findById(id).isEmpty())
            throw new ResourceNotFoundException("Nenhuma marca encontrada para o id:" + id);
        marcaDAO.deleteById(id);
        return ResponseEntity.ok().body("Marca deletada com sucesso!");
    }

    // PRODUTO

    @GetMapping("/getAllProdutos")
    public ResponseEntity<Iterable<DAOProduto>> getAllProdutos() {
        return ResponseEntity.ok().body(produtoDAO.findAll());
    }

    @GetMapping("/getProdutoById")
    public ResponseEntity<DAOProduto> getProdutoById(@RequestParam("id") int id) {
        Optional<DAOProduto> produto = produtoDAO.findById(id);
        if (produto.isEmpty())
            throw new ResourceNotFoundException("Nenhum produto encontrado para o id: " + id);
        return ResponseEntity.ok().body(produto.get());
    }

    @GetMapping("/getProdutoByNome")
    public ResponseEntity<DAOProduto> getProdutoByNome(@RequestParam("nome") String nome) {
        Optional<DAOProduto> produto = produtoDAO.findBynome(nome);
        if (produto.isEmpty())
            throw new ResourceNotFoundException("Nenhum produto encontrado com o nome: " + nome);
        return ResponseEntity.ok().body(produto.get());
    }

    @RequestMapping(value = "/getProdutoByMarcaId", method = RequestMethod.GET)
    public DAOProduto[] getProdutoByMarcaId(@RequestParam("id") int id) {
        Optional<DAOMarca> marca = marcaDAO.findById(id);
        if (marca.isEmpty())
            throw new ResourceNotFoundException("Nenhuma marca encontrada para o id: " + id);
        Optional<DAOProduto[]> produtos = produtoDAO.findByMarca(marca.get());
        if (produtos.isEmpty())
            throw new ResourceNotFoundException("Nenhum produto vinculado a esta marca com id: " + id + " foi encontrado");
        return produtos.get();
    }

    @GetMapping("/getProdutoByCategoriaId")
    public ResponseEntity<DAOProduto[]> getProdutoByCategoriaId(@RequestParam("id") int id) {
        Optional<DAOCategoria> categoria = categoriaDAO.findById(id);
        if (categoria.isEmpty())
            throw new ResourceNotFoundException("Nenhuma marca encontrada para o id: " + id);
        Optional<DAOProduto[]> produtos = produtoDAO.findByCategoria(categoria.get());
        if (produtos.isEmpty())
            throw new ResourceNotFoundException("Nenhum produto encontrado vinculado a categoria com id: " + id);
        return ResponseEntity.ok().body(produtos.get());
    }

    @GetMapping("/checkProduto")
    public ResponseEntity<String> produtoExiste(@RequestParam("id") int id) {
        if (produtoDAO.existsById(id)) {
            return ResponseEntity.ok().body("Um produto existente possui este ID");
        }
        return ResponseEntity.ok().body("Nenhum produto utiliza este ID no momento");
    }

    @PostMapping("/addProduto")
    public ResponseEntity<DAOProduto> addProduto(@RequestBody ProdutoDTO newProduto) {
        DAOProduto produto = new DAOProduto();
        Optional<DAOMarca> marca = marcaDAO.findById(newProduto.getMarcaId());
        Optional<DAOCategoria> categoria = categoriaDAO.findById(newProduto.getCategoriaId());
        if (marca.isEmpty()) {
            throw new ResourceNotFoundException("Nenhuma marca encontrada para o id" + newProduto.getMarcaId());
        }
        if (categoria.isEmpty()) {
            throw new ResourceNotFoundException("Nenhuma categoria encontrada para o id" + newProduto.getCategoriaId());
        }
        produto.toMap(newProduto, marca.get(), categoria.get());
        return ResponseEntity.ok().body(produtoDAO.save(produto));
    }

    @PutMapping("/updateProdutoById")
    public ResponseEntity<Optional<Object>> updateProdutoById(@RequestBody ProdutoDTO newProduto, @RequestParam("id") int id) {
        return ResponseEntity.ok().body(produtoDAO.findById(id).map(
                        produto -> {
                            Optional<DAOMarca> marca = marcaDAO.findById(newProduto.getMarcaId());
                            Optional<DAOCategoria> categoria = categoriaDAO.findById(newProduto.getCategoriaId());
                            if (marca.isEmpty()) {
                                throw new ResourceNotFoundException("Nenhuma marca encontrada para o id" + newProduto.getMarcaId());
                            }
                            if (categoria.isEmpty()) {
                                throw new ResourceNotFoundException("Nenhuma categoria encontrada para o id" + newProduto.getCategoriaId());
                            }
                            produto.toMap(newProduto, marca.get(), categoria.get());
                            return produtoDAO.save(produto);
                        }
                )
        );
    }

    @DeleteMapping("/deleteProdutoById")
    public ResponseEntity<String> deleteProdutoById(@RequestParam("id") Integer id) {
        if (produtoDAO.findById(id).isEmpty())
            throw new ResourceNotFoundException("Nenhum produto encontrado para o id:" + id);
        produtoDAO.deleteById(id);
        return ResponseEntity.ok().body("Produto deletado com sucesso!");
    }

    // CATEGORIA
    @GetMapping("/getAllCategorias")
    public ResponseEntity<Iterable<DAOCategoria>> getAllCategorias() {
        return ResponseEntity.ok().body(categoriaDAO.findAll());
    }

    @GetMapping("/getCategoriaById")
    public ResponseEntity<DAOCategoria> getCategoriaById(@RequestParam("id") int id) {
        Optional<DAOCategoria> categoria = categoriaDAO.findById(id);
        if (categoria.isEmpty())
            throw new ResourceNotFoundException("Nenhuma categoria encontrada para o id: " + id);
        return ResponseEntity.ok().body(categoria.get());
    }

    @GetMapping("/getCategoriaByNome")
    public ResponseEntity<DAOCategoria> getCategoriaByNome(@RequestParam("nome") String nome) {
        Optional<DAOCategoria> categoria = categoriaDAO.findByNome(nome);
        if (categoria.isEmpty())
            throw new ResourceNotFoundException("Nenhuma categoria encontrada com o nome: " + nome);
        return ResponseEntity.ok().body(categoria.get());
    }

    @GetMapping("/getCategoriaByProdutoId")
    public ResponseEntity<DAOCategoria> getCategoriaByMarca(@RequestParam("id") int id) {
        DAOProduto produto = new DAOProduto();
        produto.setId(id);
        Optional<DAOCategoria> categoria = categoriaDAO.findByProduto(produto);
        if (categoria.isEmpty())
            throw new ResourceNotFoundException("Nenhum produto encontrado para o id: " + id);
        return ResponseEntity.ok().body(categoria.get());
    }

    @GetMapping("/checkCategoria")
    public ResponseEntity<String> categoriaExiste(@RequestParam("id") int id) {
        if (categoriaDAO.existsById(id)) {
            return ResponseEntity.ok().body("Uma categoria existente possui este ID");
        }
        return ResponseEntity.ok().body("Nenhuma categoria utiliza este ID no momento");
    }

    @PostMapping("/addCategoria")
    public ResponseEntity<DAOCategoria> addCategoria(@RequestBody DAOCategoria categoria) {
        return ResponseEntity.ok().body(categoriaDAO.save(categoria));
    }

    @PutMapping("/updateCategoria")
    public ResponseEntity<Optional<Object>> updateCategoria(@RequestBody DAOCategoria newCategoria, @RequestParam("id") Integer id) {
        Optional<DAOCategoria> categoria = categoriaDAO.findById(id);
        if (categoria.isEmpty())
            throw new ResourceNotFoundException("Nenhuma categoria encontrada para o id: " + id);
        return ResponseEntity.ok().body(categoria.map(
                oldCategoria -> {
                    oldCategoria.setNome(newCategoria.getNome());
                    oldCategoria.setDescricao(newCategoria.getDescricao());
                    return categoriaDAO.save(oldCategoria);
                }
        ));
    }

    @DeleteMapping("/deleteCategoria")
    public ResponseEntity<String> deleteCategoria(@RequestParam("id") Integer id) {
        if (categoriaDAO.findById(id).isEmpty())
            throw new ResourceNotFoundException("Nenhuma categoria encontrada para o id:" + id);
        categoriaDAO.deleteById(id);
        return ResponseEntity.ok().body("Categoria deletada com sucesso!");
    }

    // TESTE

    @RequestMapping(value = "/teste", method = RequestMethod.GET)
    public String teste() {
        return "Teste foi um sucesso!";
    }
}
