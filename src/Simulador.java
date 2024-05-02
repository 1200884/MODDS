import java.util.Random;

class Simulador {
    private static final int TEMPO_SIMULACAO = 144; // Tempo total da simulação em minutos
    private static final int CAPACIDADE_FILA = 5; // Capacidade máxima da fila de espera
    private static final int NUM_BARBEIROS = 2; // Número de barbeiros na barbearia

    private Barbearia barbearia;
    private int tempoAtual;

    // Método principal que inicia a simulação
    public void simular() {
        inicializarBarbearia();

        for (tempoAtual = 0; tempoAtual < TEMPO_SIMULACAO; tempoAtual++) {
            System.out.println("Tempo: " + tempoAtual);

            // Gera clientes aleatórios
            gerarClientesAleatorios();

            // Atualiza o estado dos barbeiros e da barbearia
            barbearia.atualizarBarbeiros();

            // Verifica se há barbeiros disponíveis para atender clientes
            while (!barbearia.filaVazia() && barbearia.barbeiroLivre() != null) {
                Barbeiro barbeiroLivre = barbearia.barbeiroLivre();
                Cliente cliente = barbearia.removerClienteFila();
                barbeiroLivre.iniciarServico(cliente.getTipoServico(), cliente.getTempoServico());
            }

            // Aguarda 1 minuto antes de avançar para o próximo tempo
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // Inicializa a barbearia com a capacidade da fila e o número de barbeiros
    private void inicializarBarbearia() {
        barbearia = new Barbearia(CAPACIDADE_FILA, NUM_BARBEIROS);
    }

    // Gera clientes aleatórios
    private void gerarClientesAleatorios() {
        Random random = new Random();
        if (random.nextDouble() < 0.4) { // Probabilidade de chegada de um cliente a cada minuto
            Cliente cliente = Cliente.gerarClienteAleatorio(tempoAtual);
            barbearia.adicionarClienteFila(cliente);
        }
    }
}
