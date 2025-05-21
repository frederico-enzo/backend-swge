package br.com.uniamerica.estacionamento;

import br.com.uniamerica.estacionamento.entity.*;
import br.com.uniamerica.estacionamento.repository.*;
import br.com.uniamerica.estacionamento.service.MovimentacaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MovimentacaoServiceTest {

    @InjectMocks
    private MovimentacaoService movimentacaoService;

    @Mock
    private MovimentacaoRepository movimentacaoRepository;
    @Mock
    private CondutorRepository condutorRepository;
    @Mock
    private VeiculoRepository veiculoRepository;
    @Mock
    private ConfiguracaoRepository configuracaoRepository;

    private Movimentacao movimentacao;
    private Condutor condutor;
    private Veiculo veiculo;
    private Configuracao configuracao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Configurando objetos de exemplo
        condutor = new Condutor();
        condutor.setId(1L);
        condutor.setNome("John Doe");
        condutor.setAtivo(true);

        veiculo = new Veiculo();
        veiculo.setId(1L);
        veiculo.setPlaca("ABC-1234");
        veiculo.setAtivo(true);
        veiculo.setTipo(Tipo.CARRO);

        configuracao = new Configuracao();
        configuracao.setQntdCarro(10);
        configuracao.setValorHora(BigDecimal.valueOf(10.0));

        movimentacao = new Movimentacao();
        movimentacao.setId(1L);
        movimentacao.setDataEntrada(LocalDateTime.now().minusHours(2));
        movimentacao.setDataSaida(LocalDateTime.now());
        movimentacao.setVeiculo(veiculo);
        movimentacao.setCondutor(condutor);
    }

    @Test
    void testNovaMovimentacao_VeiculoInativo() {
        // Alterando o estado do veículo para inativo
        veiculo.setAtivo(false);

        // Mockando os repositórios
        when(configuracaoRepository.getConfiguracao()).thenReturn(configuracao);
        when(condutorRepository.findById(1L)).thenReturn(Optional.of(condutor));
        when(veiculoRepository.findById(1L)).thenReturn(Optional.of(veiculo));

        // Chamando o método e verificando a exceção
        Exception exception = assertThrows(IllegalArgumentException.class, () -> movimentacaoService.novaMovimentacao(movimentacao));
        assertEquals("Veiculo [ ABC-1234 ] está desativado!", exception.getMessage());
    }


    @Test
    void testEditar_MovimentacaoNaoExistente() {
        // Mockando os repositórios
        when(movimentacaoRepository.findById(1L)).thenReturn(Optional.empty());

        // Chamando o método e verificando a exceção
        Exception exception = assertThrows(IllegalArgumentException.class, () -> movimentacaoService.editar(1L, movimentacao));
        assertEquals("Movimentação não existe!", exception.getMessage());
    }


    @Test
    void testDesativar_Sucesso() {
        // Mockando os repositórios
        when(movimentacaoRepository.findById(1L)).thenReturn(Optional.of(movimentacao));

        // Chamando o método
        ResponseEntity<?> result = movimentacaoService.desativar(1L);

        // Verificando o resultado
        assertNotNull(result);
        assertTrue(result.getStatusCode().is2xxSuccessful());
        assertEquals("Movimentação [ 1 ] DESATIVADA", result.getBody());
    }

    @Test
    void testDesativar_MovimentacaoNaoExistente() {
        // Mockando os repositórios
        when(movimentacaoRepository.findById(1L)).thenReturn(Optional.empty());

        // Chamando o método e verificando a exceção
        Exception exception = assertThrows(IllegalArgumentException.class, () -> movimentacaoService.desativar(1L));
        assertEquals("Movimentação não encontrada!", exception.getMessage());
    }
}
