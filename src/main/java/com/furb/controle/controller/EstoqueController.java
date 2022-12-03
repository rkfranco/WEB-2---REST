package com.furb.controle.controller;

import com.furb.controle.dao.DAOCategoria;
import com.furb.controle.dao.DAOMarca;
import com.furb.controle.dao.DAOProduto;
import com.furb.controle.model.CategoriaDAO;
import com.furb.controle.model.MarcaDAO;
import com.furb.controle.model.ProdutoDAO;
import com.furb.controle.model.ProdutoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageNotReadableException;
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
    public Optional<MarcaDAO> getMarcaById(@RequestParam("id") Integer id) {
        //TODO: Verificar se tem que tratar
        return marcaRepository.findById(id);
    }

    @RequestMapping(value = "/getMarcaByCNPJ", method = RequestMethod.GET)
    public Optional<MarcaDAO> getMarcaByCNPJ(@RequestParam("cnpj") String cnpj) {
        return marcaRepository.findBycnpj(cnpj);
    }
    @RequestMapping(value = "/getMarcaByNome", method = RequestMethod.GET)
    public Optional<MarcaDAO> getMarcaByNome(@RequestParam("nome") String nome) {
        return marcaRepository.findBynome(nome);
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

    @RequestMapping(value = "/deleteMarca", method = RequestMethod.DELETE)
    public String deleteMarca(@RequestParam("id") Integer id){
        marcaRepository.deleteById(id);
        return "Marca deletada com sucesso!";
    }

    // PRODUTO

    @RequestMapping(value = "/getAllProdutos", method = RequestMethod.GET)
    public List<ProdutoDAO> getAllProdutos() {
        return (List<ProdutoDAO>) produtoRepository.findAll();
    }

    @RequestMapping(value = "/getProdutoById", method = RequestMethod.GET)
    public Optional<ProdutoDAO> getProdutoById(@RequestParam("id") int id) {
        //TODO: Verificar se tem que tratar
        return produtoRepository.findById(id);
    }

    @RequestMapping(value = "/getProdutoByNome", method = RequestMethod.GET)
    public Optional<ProdutoDAO> getProdutoByNome(@RequestParam("nome") String nome) {
        //TODO: Verificar se tem que tratar
        return produtoRepository.findBynome(nome);
    }

    @RequestMapping(value = "/getProdutoByMarcaId", method = RequestMethod.GET)
    public Optional<ProdutoDAO[]> getProdutoByMarcaId(@RequestParam("id") int id) {
        //TODO: Verificar se tem que tratar
        MarcaDAO marca = new MarcaDAO();
        marca.setMARCA_ID(id);
        return produtoRepository.findByMarca(marca);
    }

    @RequestMapping(value = "/getProdutoByCategoriaId", method = RequestMethod.GET)
    public Optional<ProdutoDAO[]> getProdutoByCategoriaId(@RequestParam("id") int id) {
        //TODO: Verificar se tem que tratar
        return produtoRepository.findByCategoriaId(id);
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
        produto.setId(newProduto.getId());
        produto.setNome(newProduto.getNome());
        produto.setDescricao(newProduto.getDescricao());
        produto.setPreco(newProduto.getPreco());
        Optional<MarcaDAO> marca = marcaRepository.findById(newProduto.getMarcaId());
        Optional<CategoriaDAO> categoria = categoriaRepository.findById(newProduto.getCategoriaId());
        if (marca.isEmpty() || categoria.isEmpty()){
            throw new IllegalArgumentException();
            //TODO: implementar erro para a marca
        }
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
                    if (marca.isEmpty() || categoria.isEmpty()){
                        throw new IllegalArgumentException();
                        //TODO: implementar erro para a marca
                    }
                    produto.setMarca(marca.get());
                    produto.setCategoria(categoria.get());
                    produto.setQtdEstoque(newProduto.getQtdEstoque());
                    return produtoRepository.save(produto);
                }
        );
    }

    @RequestMapping(value = "/deleteProdutoById", method = RequestMethod.DELETE)
    public String deleteProdutoById(@RequestParam("id") Integer id){
        produtoRepository.deleteById(id);
        return "Produto deletado com sucesso!";
    }

    // CATEGORIA
    @RequestMapping(value = "/getAllCategorias", method = RequestMethod.GET)
    public List<CategoriaDAO> getAllCategorias() {
        return (List<CategoriaDAO>) categoriaRepository.findAll();
    }

    @RequestMapping(value = "/getCategoriaById", method = RequestMethod.GET)
    public Optional<CategoriaDAO> getCategoriaById(@RequestParam("id") int id) {
        //TODO: Verificar se tem que tratar
        return categoriaRepository.findById(id);
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
        return categoriaRepository.findById(id).map(
                categoria -> {
                    categoria.setNome(newCategoria.getNome());
                    categoria.setDescricao(newCategoria.getDescricao());
                    return categoriaRepository.save(categoria);
                    // TODO: Caso o id n exista o valor retornado é null e n faz nada
                }
        );
    }

    @RequestMapping(value = "/deleteCategoria", method = RequestMethod.DELETE)
    public String deleteCategoria(@RequestParam("id") Integer id){
        categoriaRepository.deleteById(id);
        return "Categoria deletada com sucesso!";
    }

    // TESTE

    @RequestMapping(value = "/teste", method = RequestMethod.GET)
    public String teste() {
        return "Teste foi um sucesso!";
    }
}
