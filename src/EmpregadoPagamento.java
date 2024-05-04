class EmpregadoPagamento {
    private boolean ocupado;
    private int tempoServicoAtual; // Tempo necessário para realizar o serviço atual
    private String tipoServicoAtual; // Tipo de serviço que o empregado está realizando
    private String nome;
    // Construtor
    public EmpregadoPagamento() {
    this.ocupado = false;
    this.tempoServicoAtual = 0;
    this.tipoServicoAtual = "";
    this.nome = "Empregado Pagamento";
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
        System.out.println("Empregado de Pagamento está a receber pagamento em " + tipoServico);
    }

    // Atualiza o tempo necessário para realizar o serviço atual
    public void atualizarTempoServico() {
        if (ocupado) {
            System.out.println(this.nome +" está a receber pagamento em "+ this.tipoServicoAtual+ " e faltam-lhe "+tempoServicoAtual+)
            tempoServicoAtual--;
            if (tempoServicoAtual <= 0) {
                concluirServico();
            }
        }
    }

    // Conclui o serviço atual
    private void concluirServico() {
        this.ocupado = false;
        System.out.println("Emmpregado recebeu o pagamento em " + tipoServicoAtual);
        this.tipoServicoAtual = "";
        this.tempoServicoAtual = 0;
    }
}
