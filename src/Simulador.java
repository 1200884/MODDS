import java.util.Random;

class Simulador {
    private static final int TEMPO_SIMULACAO = 144; // Tempo total da simulação em minutos
    private static final int CAPACIDADE_FILA = 20; // Capacidade máxima da fila de espera
    private static final int NUM_EMPREGADOSMESA = 3; 
    private static final int NUM_EMPREGADOSPAGAMENTO = 1; 
    private static final int NUM_MESAS = 10; 
    private static final int NUM_PRATOS = 3; 
    private static final int NUM_COZINHEIROS = 3; 

    private Restaurante restaurante;
    private int tempoAtual;

    // Método principal que inicia a simulação
    public void simular() {
        inicializarRestaurante();

        for (tempoAtual = 0; tempoAtual < TEMPO_SIMULACAO; tempoAtual++) {
            System.out.println("Tempo: " + tempoAtual);

            // Gera clientes aleatórios
            gerarClientesAleatorios();

            // Atualiza o estado dos empreatualizarempregadoMesa e da restaurante
            restaurante.atualizarempregadoMesa();

            // Verifica se há empreatualizarempregadoMesa disponíveis para atender clientes
            while (!restaurante.filaVazia() && restaurante.empregadoMesaLivre() != null) {
                EmpregadoMesa empregadoMesaLivre = restaurante.empregadoMesaLivre();
                Cliente cliente = restaurante.removerClienteFila();
                empregadoMesaLivre.iniciarServico(cliente.getTipoServico(), cliente.getTempoServico());
            }

            // Aguarda 1 minuto antes de avançar para o próximo tempo
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // Inicializa a restaurante com a capacidade da fila e o número de empreatualizarempregadoMesa
    private void inicializarRestaurante() {
        restaurante = new Restaurante(CAPACIDADE_FILA, NUM_EMPREGADOSMESA);
    }

    // Gera clientes aleatórios
    private void gerarClientesAleatorios() {
        Random random = new Random();
        if (random.nextDouble() < 0.4) { // Probabilidade de chegada de um cliente a cada minuto
            Cliente cliente = Cliente.gerarClienteAleatorio(tempoAtual);
            restaurante.adicionarClienteFila(cliente);
        }
    }
}
