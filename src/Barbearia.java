import java.util.LinkedList;
import java.util.Queue;

class Barbearia {
    private Queue<Cliente> filaEspera;
    private int capacidadeFila;
    private Barbeiro[] barbeiros;

    // Construtor
    public Barbearia(int capacidadeFila, int numBarbeiros) {
        this.filaEspera = new LinkedList<>();
        this.capacidadeFila = capacidadeFila;
        this.barbeiros = new Barbeiro[numBarbeiros];
        for (int i = 0; i < numBarbeiros; i++) {
            barbeiros[i] = new Barbeiro();
        }
    }

    // Adiciona um cliente à fila de espera
    public void adicionarClienteFila(Cliente cliente) {
        if (filaEspera.size() < capacidadeFila) {
            filaEspera.add(cliente);
            System.out.println("Cliente adicionado à fila de espera.");
        } else {
            System.out.println("A fila de espera está cheia. O cliente foi embora.");
        }
    }

    // Verifica se a fila de espera está vazia
    public boolean filaVazia() {
        return filaEspera.isEmpty();
    }

    // Atualiza o estado dos barbeiros
    public void atualizarBarbeiros() {
        for (Barbeiro barbeiro : barbeiros) {
            barbeiro.atualizarTempoServico();
        }
    }

    // Método para verificar se algum barbeiro está livre
    public Barbeiro barbeiroLivre() {
        for (Barbeiro barbeiro : barbeiros) {
            if (!barbeiro.estaOcupado()) {
                return barbeiro;
            }
        }
        return null;
    }

    // Método para remover um cliente da fila quando ele é atendido
    public Cliente removerClienteFila() {
        return filaEspera.poll();
    }
}
