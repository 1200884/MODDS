import java.util.Random;

class Cliente {
    private int tempoChegada;
    private int tempoMinComer; 
    private int tempoMaxComer; 
    private String metodoPagamento;
    private int tempoMedioComer; 
    private int tempoEsperaToleravel; // Tempo de espera tolerável pelo cliente

    // Construtor
    public Cliente(int tempoChegada, int tempoMinComer, int tempoMaxComer, int tempoMedioComer, String metodoPagamento, int tempoEsperaToleravel) {
        this.tempoChegada = tempoChegada;
        this.tempoEsperaToleravel = tempoEsperaToleravel;
        this.tempoMinComer = tempoMinComer;
        this.tempoMaxComer = tempoMaxComer;
        this.metodoPagamento = metodoPagamento;
    }

    // Métodos getters
    public int getTempoChegada() {
        return tempoChegada;
    }

    public int getTempoServico() {
        return tempoServico;
    }
    public int getTempoMinComer(){
        return tempoMinComer;
    }
    
    public int getTempoMaxComer(){
        return tempoMaxComer;
    }
    
    public int getmetodopagamento(){
        return metodopagamento;
    }
    public int getTempoComer(){
        return tempoMedioComer;
    }

    public int getTempoEsperaToleravel() {
        return tempoEsperaToleravel;
    }

    // Método para gerar um cliente aleatório
    public static Cliente gerarClienteAleatorio(int tempoAtual) {
        Random random = new Random();
        // Definindo aleatoriamente os atributos do cliente
        int tempoChegada = tempoAtual;
        int tempoMinComer = random.nextInt(10) + 5; // Tempo de serviço aleatório entre 5 e 15 minutos
        int tempoMaxComer = random.nextInt(20) + 16; // Tempo de serviço aleatório entre 16 e 35 minutos
        int tempoMedioComer = (tempoMinComer + tempoMaxComer)/2;
        String metodoPagamento;
            if (random.nextBoolean()) {
                metodoPagamento = "dinheiro";
            } else {
                metodoPagamento = "cartao de credito";
            }
        int tempoEsperaToleravel = random.nextInt(60); // Tempo de espera tolerável aleatório entre 0 e 60 minutos

        return new Cliente(tempoChegada, tempoMinComer, tempoMaxComer, tempoMedioComer, metodoPagamento, tempoEsperaToleravel);
    }
}