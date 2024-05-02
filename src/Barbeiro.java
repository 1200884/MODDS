class Barbeiro {
    private boolean ocupado;
    private int tempoServicoAtual; // Tempo necessário para realizar o serviço atual
    private String tipoServicoAtual; // Tipo de serviço que o barbeiro está realizando

    // Construtor
    public Barbeiro() {
        this.ocupado = false;
        this.tempoServicoAtual = 0;
        this.tipoServicoAtual = "";
    }

    // Verifica se o barbeiro está ocupado
    public boolean estaOcupado() {
        return ocupado;
    }

    // Inicia um novo serviço
    public void iniciarServico(String tipoServico, int tempoServico) {
        this.ocupado = true;
        this.tipoServicoAtual = tipoServico;
        this.tempoServicoAtual = tempoServico;
        System.out.println("Barbeiro iniciou serviço de " + tipoServico);
    }

    // Atualiza o tempo necessário para realizar o serviço atual
    public void atualizarTempoServico() {
        if (ocupado) {
            tempoServicoAtual--;
            if (tempoServicoAtual <= 0) {
                concluirServico();
            }
        }
    }

    // Conclui o serviço atual
    private void concluirServico() {
        this.ocupado = false;
        System.out.println("Barbeiro concluiu serviço de " + tipoServicoAtual);
        this.tipoServicoAtual = "";
        this.tempoServicoAtual = 0;
    }
}
