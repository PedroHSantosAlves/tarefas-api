package com.phdroca.tarefas.service;

import com.phdroca.tarefas.exception.TarefaNaoEncontradaException;
import com.phdroca.tarefas.model.StatusTarefa;
import com.phdroca.tarefas.model.Tarefa;
import com.phdroca.tarefas.repository.TarefaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TarefaService {

    private final TarefaRepository repository;

    public TarefaService(TarefaRepository repository) {
        this.repository = repository;
    }

    public List<Tarefa> listar(StatusTarefa status) {
        return status == null ? repository.findAll() : repository.findByStatus(status);
    }

    public Tarefa buscarPorId(Long id) {
        return repository.findById(id).orElseThrow(() -> new TarefaNaoEncontradaException(id));
    }

    public Tarefa criar(Tarefa tarefa) {
        return repository.save(tarefa);
    }

    public Tarefa atualizar(Long id, Tarefa dados) {
        Tarefa tarefa = buscarPorId(id);
        tarefa.setTitulo(dados.getTitulo());
        tarefa.setDescricao(dados.getDescricao());
        tarefa.setStatus(dados.getStatus());
        return repository.save(tarefa);
    }

    public void deletar(Long id) {
        repository.delete(buscarPorId(id));
    }
}
