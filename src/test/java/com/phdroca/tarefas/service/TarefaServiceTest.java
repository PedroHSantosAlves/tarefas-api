package com.phdroca.tarefas.service;

import com.phdroca.tarefas.exception.TarefaNaoEncontradaException;
import com.phdroca.tarefas.model.StatusTarefa;
import com.phdroca.tarefas.model.Tarefa;
import com.phdroca.tarefas.repository.TarefaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TarefaServiceTest {

    @Mock
    private TarefaRepository repository;

    @InjectMocks
    private TarefaService service;

    @Test
    void listarSemFiltroDeveRetornarTodasAsTarefas() {
        when(repository.findAll()).thenReturn(List.of(new Tarefa(), new Tarefa()));

        List<Tarefa> resultado = service.listar(null);

        assertEquals(2, resultado.size());
        verify(repository, never()).findByStatus(any());
    }

    @Test
    void listarComStatusDeveFiltrarPorStatus() {
        when(repository.findByStatus(StatusTarefa.CONCLUIDA)).thenReturn(List.of(new Tarefa()));

        List<Tarefa> resultado = service.listar(StatusTarefa.CONCLUIDA);

        assertEquals(1, resultado.size());
        verify(repository, never()).findAll();
    }

    @Test
    void buscarPorIdInexistenteDeveLancarExcecao() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(TarefaNaoEncontradaException.class, () -> service.buscarPorId(99L));
    }

    @Test
    void criarDeveSalvarEDevolverATarefa() {
        Tarefa tarefa = new Tarefa();
        tarefa.setTitulo("Estudar JPA");
        when(repository.save(tarefa)).thenReturn(tarefa);

        Tarefa salva = service.criar(tarefa);

        assertEquals("Estudar JPA", salva.getTitulo());
        verify(repository).save(tarefa);
    }

    @Test
    void atualizarDeveAlterarOsCampos() {
        Tarefa existente = new Tarefa();
        existente.setTitulo("Titulo antigo");
        when(repository.findById(1L)).thenReturn(Optional.of(existente));
        when(repository.save(any(Tarefa.class))).thenAnswer(invocacao -> invocacao.getArgument(0));

        Tarefa dados = new Tarefa();
        dados.setTitulo("Titulo novo");
        dados.setDescricao("Descricao nova");
        dados.setStatus(StatusTarefa.EM_ANDAMENTO);

        Tarefa atualizada = service.atualizar(1L, dados);

        assertEquals("Titulo novo", atualizada.getTitulo());
        assertEquals("Descricao nova", atualizada.getDescricao());
        assertEquals(StatusTarefa.EM_ANDAMENTO, atualizada.getStatus());
    }

    @Test
    void deletarInexistenteDeveLancarExcecaoSemApagarNada() {
        when(repository.findById(5L)).thenReturn(Optional.empty());

        assertThrows(TarefaNaoEncontradaException.class, () -> service.deletar(5L));
        verify(repository, never()).delete(any());
    }
}
