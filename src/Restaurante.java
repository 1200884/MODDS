import java.util.LinkedList;
import java.util.Queue;

class Restaurante {
    private Queue<Cliente> filaEspera;
    private int capacidadeFila;
    private EmpregadoMesa[] empregadoMesa;

    // Construtor
    public Barbearia(int capacidadeFila, int numempregadoMesa) {
        this.filaEspera = new LinkedList<>();
        this.capacidadeFila = capacidadeFila;
        this.empregadoMesa = new Barbeiro[numempregadoMesa];
        for (int i = 0; i < numempregadoMesa; i++) {
            empregadoMesa[i] = new EmpregadoMesa();
        }
    }

    // Adiciona um cliente à fila de espera
    public void adicionarClienteFila(Cliente cliente) {
        if (filaEspera.size() < capacidadeFila) {
            filaEspera.add(cliente);
            System.out.println("Cliente adicionado na fila de espera.");
        } else {
            System.out.println("A fila de espera esta cheia. O cliente foi embora.");
        }
    }

    // Verifica se a fila de espera está vazia
    public boolean filaVazia() {
        return filaEspera.isEmpty();
    }

    // Atualiza o estado dos empregadoMesa
    public void atualizarempregadoMesa() {
        for (EmpregadoMesa empregadoMesa : empregadoMesa) {
            empregadoMesa.atualizarTempoServico();
        }
    }

    // Método para verificar se algum barbeiro está livre
    public EmpregadoMesa empregadoMesaLivre() {
        for (EmpregadoMesa empregadoMesa : empregadoMesa) {
            if (!empregadoMesa.estaOcupado()) {
                return empregadoMesa;
            }
        }
        return null;
    }

    // Método para remover um cliente da fila quando ele é atendido
    public Cliente removerClienteFila() {
        return filaEspera.poll();
    }
}
