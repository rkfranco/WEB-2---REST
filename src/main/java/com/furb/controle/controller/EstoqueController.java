package com.furb.controle.controller;

import com.furb.controle.dao.DAOCategoria;
import com.furb.controle.dao.DAOMarca;
import com.furb.controle.dao.DAOProduto;
import com.furb.controle.error.ResourceNotFoundException;
import com.furb.controle.model.categoria.CategoriaDAO;
import com.furb.controle.model.marca.MarcaDAO;
import com.furb.controle.model.produto.ProdutoDAO;
import com.furb.controle.model.produto.ProdutoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class EstoqueController {

    @Autowired
    private DAOMarca marcaRepository;

    @Autowired
    private DAOCategoria categoriaRepository;

    @Autowired
    private DAOProduto produtoRepository;

    // MARCA

    @RequestMapping(value = "/getAllMarcas", method = RequestMethod.GET)
    public List<MarcaDAO> getAllMarcas() {
        return (List<MarcaDAO>) marcaRepository.findAll();
    }

    @RequestMapping(value = "/getMarcaById", method = RequestMethod.GET)
    public MarcaDAO getMarcaById(@RequestParam("id") Integer id) {
        Optional<MarcaDAO> marca = marcaRepository.findById(id);
        if (marca.isEmpty())
            throw new ResourceNotFoundException("Nenhuma marca encontrada para o id: " + id);
        return marca.get();
    }

    @RequestMapping(value = "/getMarcaByCNPJ", method = RequestMethod.GET)
    public MarcaDAO getMarcaByCNPJ(@RequestParam("cnpj") String cnpj) {
        Optional<MarcaDAO> marca = marcaRepository.findBycnpj(cnpj);
        if (marca.isEmpty())
            throw new ResourceNotFoundException("Nenhuma marca encontrada para o cnpj: " + cnpj);
        return marca.get();
    }

    @RequestMapping(value = "/getMarcaByNome", method = RequestMethod.GET)
    public MarcaDAO getMarcaByNome(@RequestParam("nome") String nome) {
        Optional<MarcaDAO> marca = marcaRepository.findBynome(nome);
        if (marca.isEmpty())
            throw new ResourceNotFoundException("Nenhuma marca encontrada com o nome: " + nome);
        return marca.get();
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
        Optional<MarcaDAO> marca = marcaRepository.findById(id);
        if (marca.isEmpty())
            throw new ResourceNotFoundException("Nenhuma marca encontrada para o id: " + id);
        return marca.map(
                marcaNova -> {
                    //marca.setMARCA_ID(newMarca.getMARCA_ID()); -> N pode alterar poís é o id
                    marcaNova.setCnpj(newMarca.getCnpj());
                    marcaNova.setRazaoSocial(newMarca.getRazaoSocial());
                    marcaNova.setNome(newMarca.getNome());
                    return marcaRepository.save(marcaNova);
                }
        );
    }

    @RequestMapping(value = "/deleteMarca", method = RequestMethod.DELETE)
    public String deleteMarca(@RequestParam("id") Integer id) {
        if (marcaRepository.findById(id).isEmpty())
            throw new ResourceNotFoundException("Nenhuma marca encontrada para o id:" + id);
        marcaRepository.deleteById(id);
        return "Marca deletada com sucesso!";
    }

    // PRODUTO

    @RequestMapping(value = "/getAllProdutos", method = RequestMethod.GET)
    public List<ProdutoDAO> getAllProdutos() {
        return (List<ProdutoDAO>) produtoRepository.findAll();
    }

    @RequestMapping(value = "/getProdutoById", method = RequestMethod.GET)
    public ProdutoDAO getProdutoById(@RequestParam("id") int id) {
        Optional<ProdutoDAO> produto = produtoRepository.findById(id);
        if (produto.isEmpty())
            throw new ResourceNotFoundException("Nenhum produto encontrado para o id: " + id);
        return produto.get();
    }

    @RequestMapping(value = "/getProdutoByNome", method = RequestMethod.GET)
    public ProdutoDAO getProdutoByNome(@RequestParam("nome") String nome) {
        Optional<ProdutoDAO> produto = produtoRepository.findBynome(nome);
        if (produto.isEmpty())
            throw new ResourceNotFoundException("Nenhum produto encontrado com o nome: " + nome);
        return produto.get();
    }

    @RequestMapping(value = "/getProdutoByMarcaId", method = RequestMethod.GET)
    public ProdutoDAO[] getProdutoByMarcaId(@RequestParam("id") int id) {
        MarcaDAO marca = this.getMarcaById(id);
        Optional<ProdutoDAO[]> produtos = produtoRepository.findByMarca(marca);
        if (produtos.isEmpty())
            throw new ResourceNotFoundException("Nenhum produto vinculado a esta marca com id: " + id + " foi encontrado");
        return produtos.get();
    }

    @RequestMapping(value = "/getProdutoByCategoriaId", method = RequestMethod.GET)
    public ProdutoDAO[] getProdutoByCategoriaId(@RequestParam("id") int id) {
        CategoriaDAO categoria = this.getCategoriaById(id);
        Optional<ProdutoDAO[]> produtos = produtoRepository.findByCategoria(categoria);
        if (produtos.isEmpty())
            throw new ResourceNotFoundException("Nenhum produto encontrado vinculado a categoria com id: " + id);
        return produtos.get();
    }

    @RequestMapping(value = "/checkProduto", method = RequestMethod.GET)
    public String produtoExiste(@RequestParam("id") int id) {
        if (produtoRepository.existsById(id)) {
            return "Um produto existente possui este ID";
        }
        return "Nenhum produto utiliza este ID no momento";
    }

    @RequestMapping(value = "/addProduto", method = RequestMethod.POST)
    public String addProduto(@RequestBody ProdutoDTO newProduto) {
        ProdutoDAO produto = new ProdutoDAO();
        Optional<MarcaDAO> marca = marcaRepository.findById(newProduto.getMarcaId());
        Optional<CategoriaDAO> categoria = categoriaRepository.findById(newProduto.getCategoriaId());
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
        produtoRepository.save(produto);
        return "Produto salvo!";
    }

    @RequestMapping(value = "/updateProdutoById", method = RequestMethod.PUT)
    public Optional<ProdutoDAO> updateProdutoById(@RequestBody ProdutoDTO newProduto, @RequestParam("id") int id) {
        return produtoRepository.findById(id).map(
                produto -> {
                    produto.setNome(newProduto.getNome());
                    produto.setDescricao(newProduto.getDescricao());
                    produto.setPreco(newProduto.getPreco());
                    Optional<MarcaDAO> marca = marcaRepository.findById(newProduto.getMarcaId());
                    Optional<CategoriaDAO> categoria = categoriaRepository.findById(newProduto.getCategoriaId());
                    if (marca.isEmpty()) {
                        throw new ResourceNotFoundException("Nenhuma marca encontrada para o id" + newProduto.getMarcaId());
                    }
                    if (categoria.isEmpty()) {
                        throw new ResourceNotFoundException("Nenhuma categoria encontrada para o id" + newProduto.getCategoriaId());
                    }
                    produto.setMarca(marca.get());
                    produto.setCategoria(categoria.get());
                    produto.setQtdEstoque(newProduto.getQtdEstoque());
                    return produtoRepository.save(produto);
                }
        );
    }

    @RequestMapping(value = "/deleteProdutoById", method = RequestMethod.DELETE)
    public String deleteProdutoById(@RequestParam("id") Integer id) {
        if (produtoRepository.findById(id).isEmpty())
            throw new ResourceNotFoundException("Nenhum produto encontrado para o id:" + id);
        produtoRepository.deleteById(id);
        return "Produto deletado com sucesso!";
    }

    // CATEGORIA
    @RequestMapping(value = "/getAllCategorias", method = RequestMethod.GET)
    public List<CategoriaDAO> getAllCategorias() {
        return (List<CategoriaDAO>) categoriaRepository.findAll();
    }

    @RequestMapping(value = "/getCategoriaById", method = RequestMethod.GET)
    public CategoriaDAO getCategoriaById(@RequestParam("id") int id) {
        Optional<CategoriaDAO> categoria = categoriaRepository.findById(id);
        if (categoria.isEmpty())
            throw new ResourceNotFoundException("Nenhuma categoria encontrada para o id: " + id);
        return categoria.get();
    }

    @RequestMapping(value = "/getCategoriaByNome", method = RequestMethod.GET)
    public CategoriaDAO getCategoriaByNome(@RequestParam("nome") String nome) {
        Optional<CategoriaDAO> categoria = categoriaRepository.findByNome(nome);
        if (categoria.isEmpty())
            throw new ResourceNotFoundException("Nenhuma categoria encontrada com o nome: " + nome);
        return categoria.get();
    }

    @RequestMapping(value = "/getCategoriaByProdutoId", method = RequestMethod.GET)
    public CategoriaDAO getCategoriaByMarca(@RequestParam("id") int id) {
        ProdutoDAO produto = new ProdutoDAO();
        produto.setId(id);
        Optional<CategoriaDAO> categoria = categoriaRepository.findByProduto(produto);
        if (categoria.isEmpty())
            throw new ResourceNotFoundException("Nenhum produto encontrado para o id: " + id);
        return categoria.get();
    }

    @RequestMapping(value = "/checkCategoria", method = RequestMethod.GET)
    public String categoriaExiste(@RequestParam("id") int id) {
        if (categoriaRepository.existsById(id)) {
            return "Uma categoria existente possui este ID";
        }
        return "Nenhuma categoria utiliza este ID no momento";
    }

    @RequestMapping(value = "/addCategoria", method = RequestMethod.POST)
    public String addCategoria(@RequestBody CategoriaDAO categoria) {
        categoriaRepository.save(categoria);
        return "Categoria salva!";
    }

    @RequestMapping(value = "/updateCategoria", method = RequestMethod.PUT)
    public Optional<CategoriaDAO> updateCategoria(@RequestBody CategoriaDAO newCategoria, @RequestParam("id") Integer id) {
        Optional<CategoriaDAO> categoria = categoriaRepository.findById(id);
        if (categoria.isEmpty())
            throw new ResourceNotFoundException("Nenhuma categoria encontrada para o id: " + id);
        return categoria.map(
                oldCategoria -> {
                    oldCategoria.setNome(newCategoria.getNome());
                    oldCategoria.setDescricao(newCategoria.getDescricao());
                    return categoriaRepository.save(oldCategoria);
                }
        );
    }

    @RequestMapping(value = "/deleteCategoria", method = RequestMethod.DELETE)
    public String deleteCategoria(@RequestParam("id") Integer id) {
        if (categoriaRepository.findById(id).isEmpty())
            throw new ResourceNotFoundException("Nenhuma categoria encontrada para o id:" + id);
        categoriaRepository.deleteById(id);
        return "Categoria deletada com sucesso!";
    }

    // TESTE

    @RequestMapping(value = "/teste", method = RequestMethod.GET)
    public String teste() {
        return "Teste foi um sucesso!";
    }
}
