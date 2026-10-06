package com.phdroca.tarefas.repository;

import com.phdroca.tarefas.model.StatusTarefa;
import com.phdroca.tarefas.model.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TarefaRepository extends JpaRepository<Tarefa, Long> {

    List<Tarefa> findByStatus(StatusTarefa status);
}
