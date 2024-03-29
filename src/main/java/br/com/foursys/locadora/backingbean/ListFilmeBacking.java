package br.com.foursys.locadora.backingbean;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.foursys.locadora.bean.Filme;
import br.com.foursys.locadora.controller.FilmeController;
import br.com.foursys.locadora.util.JSFUtil;
import br.com.foursys.locadora.util.Mensagem;
import br.com.foursys.locadora.util.Titulo;

@ManagedBean(name = "listFilmeBacking")
@ViewScoped
public class ListFilmeBacking implements Serializable {

	private static final long serialVersionUID = 1L;

	private int tipoPesquisa;

	private String nomePesquisar;
	public static Filme filmeSelecionado;

	private String disponivel;

	private ArrayList<Filme> listaFilmes;

	public ListFilmeBacking() {
		setTipoPesquisa(3);
		carregarPesquisa();
	}

	public int getTipoPesquisa() {
		return tipoPesquisa;
	}

	public void setTipoPesquisa(int tipoPesquisa) {
		this.tipoPesquisa = tipoPesquisa;
	}

	public String getNomePesquisar() {
		return nomePesquisar;
	}

	public void setNomePesquisar(String nomePesquisar) {
		this.nomePesquisar = nomePesquisar;
	}

	public Filme getFilmeSelecionado() {
		return filmeSelecionado;
	}

	public void setFilmeSelecionado(Filme filmeSelecionado) {
		ListFilmeBacking.filmeSelecionado = filmeSelecionado;
	}

	public ArrayList<Filme> getListaFilmes() {
		return listaFilmes;
	}

	public void setListaFilmes(ArrayList<Filme> listaFilmes) {
		this.listaFilmes = listaFilmes;
	}

	public String getDisponivel() {
		return disponivel;
	}

	public void setDisponivel(String disponivel) {
		this.disponivel = disponivel;
	}

	public void sair() {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void pesquisar() {
		try {
			switch (tipoPesquisa) {
			case 1:
				listaFilmes = new FilmeController().buscarPorNome(nomePesquisar, "Sim");
				break;
			case 2:
				listaFilmes = new FilmeController().buscarPorNome(nomePesquisar, "Não");
				break;
			case 3:
				listaFilmes = new FilmeController().buscarPorNome(nomePesquisar);
				break;
			}
		} catch (Exception e) {
			JSFUtil.addErrorMessage(Titulo.CONSULTA_FILME, Mensagem.FILME_ERRO_PESQUISAR);
		}
	}

	public void carregarPesquisa() {
		switch (tipoPesquisa) {
		case 1:
			setNomePesquisar("");
			listaFilmes = new FilmeController().buscarDisponivel("Sim");
			break;
		case 2:
			setNomePesquisar("");
			listaFilmes = new FilmeController().buscarDisponivel("Não");
			break;
		case 3:
			setNomePesquisar("");
			listaFilmes = new FilmeController().buscarTodos();
			break;
		}
	}

	public void alterar() {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("alt-filme.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void detalhar() {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("det-filme.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void excluir() {
		try {
			new FilmeController().excluir(filmeSelecionado);
			pesquisar();
			JSFUtil.addInfoMessage(Titulo.CADASTRO_FILME, Mensagem.FILME_EXCLUIDO);
		} catch (Exception e) {
			JSFUtil.addErrorMessage(Titulo.CADASTRO_FILME, Mensagem.FILME_ERRO_EXCLUIR);
		}
	}

}
