package com.furb.controle.controller;

import com.furb.controle.dao.CategoriaDAO;
import com.furb.controle.dao.MarcaDAO;
import com.furb.controle.dao.ProdutoDAO;
import com.furb.controle.error.ResourceNotFoundException;
import com.furb.controle.model.categoria.DAOCategoria;
import com.furb.controle.model.marca.DAOMarca;
import com.furb.controle.model.produto.DAOProduto;
import com.furb.controle.model.produto.ProdutoDTO;
import org.springframework.beans.factory.annotation.Autowired;
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

    @RequestMapping(value = "/getAllMarcas", method = RequestMethod.GET)
    public List<DAOMarca> getAllMarcas() {
        return (List<DAOMarca>) marcaDAO.findAll();
    }

    @RequestMapping(value = "/getMarcaById", method = RequestMethod.GET)
    public DAOMarca getMarcaById(@RequestParam("id") Integer id) {
        Optional<DAOMarca> marca = marcaDAO.findById(id);
        if (marca.isEmpty())
            throw new ResourceNotFoundException("Nenhuma marca encontrada para o id: " + id);
        return marca.get();
    }

    @RequestMapping(value = "/getMarcaByCNPJ", method = RequestMethod.GET)
    public DAOMarca getMarcaByCNPJ(@RequestParam("cnpj") String cnpj) {
        Optional<DAOMarca> marca = marcaDAO.findBycnpj(cnpj);
        if (marca.isEmpty())
            throw new ResourceNotFoundException("Nenhuma marca encontrada para o cnpj: " + cnpj);
        return marca.get();
    }

    @RequestMapping(value = "/getMarcaByNome", method = RequestMethod.GET)
    public DAOMarca getMarcaByNome(@RequestParam("nome") String nome) {
        Optional<DAOMarca> marca = marcaDAO.findBynome(nome);
        if (marca.isEmpty())
            throw new ResourceNotFoundException("Nenhuma marca encontrada com o nome: " + nome);
        return marca.get();
    }

    @RequestMapping(value = "/checkMarca", method = RequestMethod.GET)
    public String marcaExiste(@RequestParam("id") int id) {
        if (marcaDAO.existsById(id)) {
            return "Uma marca existente possui este ID";
        }
        return "Nenhuma marca utiliza este ID no momento";
    }

    @RequestMapping(value = "/addMarca", method = RequestMethod.POST)
    public String addMarca(@RequestBody DAOMarca marca) {
        marcaDAO.save(marca);
        return "Marca salva!";
    }

    @RequestMapping(value = "/updateMarca", method = RequestMethod.PUT)
    public Optional<DAOMarca> updateMarca(@RequestBody DAOMarca newMarca, @RequestParam("id") int id) {
        Optional<DAOMarca> marca = marcaDAO.findById(id);
        if (marca.isEmpty())
            throw new ResourceNotFoundException("Nenhuma marca encontrada para o id: " + id);
        return marca.map(
                marcaNova -> {
                    //marca.setMARCA_ID(newMarca.getMARCA_ID()); -> N pode alterar poís é o id
                    marcaNova.setCnpj(newMarca.getCnpj());
                    marcaNova.setRazaoSocial(newMarca.getRazaoSocial());
                    marcaNova.setNome(newMarca.getNome());
                    return marcaDAO.save(marcaNova);
                }
        );
    }

    @RequestMapping(value = "/deleteMarca", method = RequestMethod.DELETE)
    public String deleteMarca(@RequestParam("id") Integer id) {
        if (marcaDAO.findById(id).isEmpty())
            throw new ResourceNotFoundException("Nenhuma marca encontrada para o id:" + id);
        marcaDAO.deleteById(id);
        return "Marca deletada com sucesso!";
    }

    // PRODUTO

    @RequestMapping(value = "/getAllProdutos", method = RequestMethod.GET)
    public List<DAOProduto> getAllProdutos() {
        return (List<DAOProduto>) produtoDAO.findAll();
    }

    @RequestMapping(value = "/getProdutoById", method = RequestMethod.GET)
    public DAOProduto getProdutoById(@RequestParam("id") int id) {
        Optional<DAOProduto> produto = produtoDAO.findById(id);
        if (produto.isEmpty())
            throw new ResourceNotFoundException("Nenhum produto encontrado para o id: " + id);
        return produto.get();
    }

    @RequestMapping(value = "/getProdutoByNome", method = RequestMethod.GET)
    public DAOProduto getProdutoByNome(@RequestParam("nome") String nome) {
        Optional<DAOProduto> produto = produtoDAO.findBynome(nome);
        if (produto.isEmpty())
            throw new ResourceNotFoundException("Nenhum produto encontrado com o nome: " + nome);
        return produto.get();
    }

    @RequestMapping(value = "/getProdutoByMarcaId", method = RequestMethod.GET)
    public DAOProduto[] getProdutoByMarcaId(@RequestParam("id") int id) {
        DAOMarca marca = this.getMarcaById(id);
        Optional<DAOProduto[]> produtos = produtoDAO.findByMarca(marca);
        if (produtos.isEmpty())
            throw new ResourceNotFoundException("Nenhum produto vinculado a esta marca com id: " + id + " foi encontrado");
        return produtos.get();
    }

    @RequestMapping(value = "/getProdutoByCategoriaId", method = RequestMethod.GET)
    public DAOProduto[] getProdutoByCategoriaId(@RequestParam("id") int id) {
        DAOCategoria categoria = this.getCategoriaById(id);
        Optional<DAOProduto[]> produtos = produtoDAO.findByCategoria(categoria);
        if (produtos.isEmpty())
            throw new ResourceNotFoundException("Nenhum produto encontrado vinculado a categoria com id: " + id);
        return produtos.get();
    }

    @RequestMapping(value = "/checkProduto", method = RequestMethod.GET)
    public String produtoExiste(@RequestParam("id") int id) {
        if (produtoDAO.existsById(id)) {
            return "Um produto existente possui este ID";
        }
        return "Nenhum produto utiliza este ID no momento";
    }

    @RequestMapping(value = "/addProduto", method = RequestMethod.POST)
    public String addProduto(@RequestBody ProdutoDTO newProduto) {
        DAOProduto produto = new DAOProduto();
        Optional<DAOMarca> marca = marcaDAO.findById(newProduto.getMarcaId());
        Optional<DAOCategoria> categoria = categoriaDAO.findById(newProduto.getCategoriaId());
        if (marca.isEmpty()) {
            throw new ResourceNotFoundException("Nenhuma marca encontrada para o id" + newProduto.getMarcaId());
        }
        if (categoria.isEmpty()) {
            throw new ResourceNotFoundException("Nenhuma categoria encontrada para o id" + newProduto.getCategoriaId());
        }
        produto.setId(newProduto.getId());
        produto.setNome(newProduto.getNome());
        produto.setDescricao(newProduto.getDescricao());
        produto.setPreco(newProduto.getPreco());
        produto.setMarca(marca.get());
        produto.setCategoria(categoria.get());
        produto.setQtdEstoque(newProduto.getQtdEstoque());
        produtoDAO.save(produto);
        return "Produto salvo!";
    }

    @RequestMapping(value = "/updateProdutoById", method = RequestMethod.PUT)
    public Optional<DAOProduto> updateProdutoById(@RequestBody ProdutoDTO newProduto, @RequestParam("id") int id) {
        return produtoDAO.findById(id).map(
                produto -> {
                    produto.setNome(newProduto.getNome());
                    produto.setDescricao(newProduto.getDescricao());
                    produto.setPreco(newProduto.getPreco());
                    Optional<DAOMarca> marca = marcaDAO.findById(newProduto.getMarcaId());
                    Optional<DAOCategoria> categoria = categoriaDAO.findById(newProduto.getCategoriaId());
                    if (marca.isEmpty()) {
                        throw new ResourceNotFoundException("Nenhuma marca encontrada para o id" + newProduto.getMarcaId());
                    }
                    if (categoria.isEmpty()) {
                        throw new ResourceNotFoundException("Nenhuma categoria encontrada para o id" + newProduto.getCategoriaId());
                    }
                    produto.setMarca(marca.get());
                    produto.setCategoria(categoria.get());
                    produto.setQtdEstoque(newProduto.getQtdEstoque());
                    return produtoDAO.save(produto);
                }
        );
    }

    @RequestMapping(value = "/deleteProdutoById", method = RequestMethod.DELETE)
    public String deleteProdutoById(@RequestParam("id") Integer id) {
        if (produtoDAO.findById(id).isEmpty())
            throw new ResourceNotFoundException("Nenhum produto encontrado para o id:" + id);
        produtoDAO.deleteById(id);
        return "Produto deletado com sucesso!";
    }

    // CATEGORIA
    @RequestMapping(value = "/getAllCategorias", method = RequestMethod.GET)
    public List<DAOCategoria> getAllCategorias() {
        return (List<DAOCategoria>) categoriaDAO.findAll();
    }

    @RequestMapping(value = "/getCategoriaById", method = RequestMethod.GET)
    public DAOCategoria getCategoriaById(@RequestParam("id") int id) {
        Optional<DAOCategoria> categoria = categoriaDAO.findById(id);
        if (categoria.isEmpty())
            throw new ResourceNotFoundException("Nenhuma categoria encontrada para o id: " + id);
        return categoria.get();
    }

    @RequestMapping(value = "/getCategoriaByNome", method = RequestMethod.GET)
    public DAOCategoria getCategoriaByNome(@RequestParam("nome") String nome) {
        Optional<DAOCategoria> categoria = categoriaDAO.findByNome(nome);
        if (categoria.isEmpty())
            throw new ResourceNotFoundException("Nenhuma categoria encontrada com o nome: " + nome);
        return categoria.get();
    }

    @RequestMapping(value = "/getCategoriaByProdutoId", method = RequestMethod.GET)
    public DAOCategoria getCategoriaByMarca(@RequestParam("id") int id) {
        DAOProduto produto = new DAOProduto();
        produto.setId(id);
        Optional<DAOCategoria> categoria = categoriaDAO.findByProduto(produto);
        if (categoria.isEmpty())
            throw new ResourceNotFoundException("Nenhum produto encontrado para o id: " + id);
        return categoria.get();
    }

    @RequestMapping(value = "/checkCategoria", method = RequestMethod.GET)
    public String categoriaExiste(@RequestParam("id") int id) {
        if (categoriaDAO.existsById(id)) {
            return "Uma categoria existente possui este ID";
        }
        return "Nenhuma categoria utiliza este ID no momento";
    }

    @RequestMapping(value = "/addCategoria", method = RequestMethod.POST)
    public String addCategoria(@RequestBody DAOCategoria categoria) {
        categoriaDAO.save(categoria);
        return "Categoria salva!";
    }

    @RequestMapping(value = "/updateCategoria", method = RequestMethod.PUT)
    public Optional<DAOCategoria> updateCategoria(@RequestBody DAOCategoria newCategoria, @RequestParam("id") Integer id) {
        Optional<DAOCategoria> categoria = categoriaDAO.findById(id);
        if (categoria.isEmpty())
            throw new ResourceNotFoundException("Nenhuma categoria encontrada para o id: " + id);
        return categoria.map(
                oldCategoria -> {
                    oldCategoria.setNome(newCategoria.getNome());
                    oldCategoria.setDescricao(newCategoria.getDescricao());
                    return categoriaDAO.save(oldCategoria);
                }
        );
    }

    @RequestMapping(value = "/deleteCategoria", method = RequestMethod.DELETE)
    public String deleteCategoria(@RequestParam("id") Integer id) {
        if (categoriaDAO.findById(id).isEmpty())
            throw new ResourceNotFoundException("Nenhuma categoria encontrada para o id:" + id);
        categoriaDAO.deleteById(id);
        return "Categoria deletada com sucesso!";
    }

    // TESTE

    @RequestMapping(value = "/teste", method = RequestMethod.GET)
    public String teste() {
        return "Teste foi um sucesso!";
    }
}
