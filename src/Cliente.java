import java.util.Random;

class Cliente {
    private int tempoChegada;
    private String tipoServico; // Por exemplo: corte de cabelo, barba, etc.
    private int tempoServico; // Tempo necessário para realizar o serviço
    private int tempoEsperaToleravel; // Tempo de espera tolerável pelo cliente

    // Construtor
    public Cliente(int tempoChegada, String tipoServico, int tempoServico, int tempoEsperaToleravel) {
        this.tempoChegada = tempoChegada;
        this.tipoServico = tipoServico;
        this.tempoServico = tempoServico;
        this.tempoEsperaToleravel = tempoEsperaToleravel;
    }

    // Métodos getters
    public int getTempoChegada() {
        return tempoChegada;
    }

    public String getTipoServico() {
        return tipoServico;
    }

    public int getTempoServico() {
        return tempoServico;
    }

    public int getTempoEsperaToleravel() {
        return tempoEsperaToleravel;
    }

    // Método para gerar um cliente aleatório
    public static Cliente gerarClienteAleatorio(int tempoAtual) {
        Random random = new Random();
        // Definindo aleatoriamente os atributos do cliente
        int tempoChegada = tempoAtual;
        String[] tiposServico = {"corte de cabelo", "barba", "tratamento capilar"}; // Exemplo de tipos de serviço
        String tipoServico = tiposServico[random.nextInt(tiposServico.length)];
        int tempoServico = random.nextInt(30) + 15; // Tempo de serviço aleatório entre 15 e 45 minutos
        int tempoEsperaToleravel = random.nextInt(60); // Tempo de espera tolerável aleatório entre 0 e 60 minutos

        return new Cliente(tempoChegada, tipoServico, tempoServico, tempoEsperaToleravel);
    }
}