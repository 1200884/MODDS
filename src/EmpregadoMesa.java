class EmpregadoMesa {
    private boolean ocupado;
    private int tempoServicoAtual; // Tempo necessário para realizar o serviço atual
    private String tipoServicoAtual; // Tipo de serviço que o barbeiro está realizando
    private String nome;
    // Construtor
    public EmpregadoMesa() {
    this.ocupado = false;
    this.tempoServicoAtual = 0;
    this.tipoServicoAtual = "";
    this.nome = gerarNomeEmpregado();
}

private String gerarNomeEmpregado() {
    int contadorA = 0;
    int contadorB = 0;
    int contadorC = 0;

    // Contar quantas instâncias existem de Empregado A, B e C
   

    // Determinar o próximo nome com base no contador
    if (contadorA == 0) {
        return "Empregado A";
    } else if (contadorB==0 ) {
        return "Empregado B";
    } else {
        return "Empregado C";
    }

     for (EmpregadoMesa empregado : listaEmpregados) {
        if (empregado.nome.equals("Empregado A")) {
            contadorA++;
        } else if (empregado.nome.equals("Empregado B")) {
            contadorB++;
        } else if (empregado.nome.equals("Empregado C")) {
            contadorC++;
        }
    }
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
        System.out.println("Empregado de Mesa está a " + tipoServico);
    }

    // Atualiza o tempo necessário para realizar o serviço atual
    public void atualizarTempoServico() {
        if (ocupado) {
            System.out.println(this.nome +" está a "+ this.tipoServicoAtual+ " e faltam-lhe "+tempoServicoAtual+)
            tempoServicoAtual--;
            if (tempoServicoAtual <= 0) {
                concluirServico();
            }
        }
    }

    // Conclui o serviço atual
    private void concluirServico() {
        this.ocupado = false;
        System.out.println("Emmpregado concluiu servico de " + tipoServicoAtual);
        this.tipoServicoAtual = "";
        this.tempoServicoAtual = 0;
    }
}
