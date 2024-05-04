class Prato {
    private int tempoMinComer; // Tempo necessário para realizar o serviço atual
    private int tempoMaxComer; // Tipo de serviço que o barbeiro está realizando
    private int tempoMedioComer; // Tipo de serviço que o barbeiro está realizando
    private String nome;
    // Construtor
    public Prato() {
    
    this.nome = gerarNomePrato();
    if(this.nome.equals("Prato A")){
        this.tempoMinComer=random.nextInt(10) + 5;
        this.tempoMaxComer=random.nextInt(10) + 20
        this.tempoMedioComer=Mean(this.tempoMinComer,this.tempoMaxComer)
    }
    if(this.nome.equals("Prato B")){
        this.tempoMinComer=random.nextInt(10) + 10;
        this.tempoMaxComer=random.nextInt(10) + 25
        this.tempoMedioComer=Mean(this.tempoMinComer,this.tempoMaxComer)
    }
    if(this.nome.equals("Prato C")){
        this.tempoMinComer=random.nextInt(10) + 7;
        this.tempoMaxComer=random.nextInt(10) + 22
        this.tempoMedioComer=Mean(this.tempoMinComer,this.tempoMaxComer)
    }
    
    }

private String gerarNomePrato() {
    int contadorA = 0;
    int contadorB = 0;
    int contadorC = 0;

    // Contar quantas instâncias existem de Empregado A, B e C
   

    // Determinar o próximo nome com base no contador
    if (contadorA == 0) {
        return "Prato A";
    } else if (contadorB==0 ) {
        return "Prato B";
    } else {
        return "Prato C";
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
public gettempomincomer(){
    return this.tempoMinComer
}
public gettempoMaxcomer(){
    return this.tempoMaxComer
}
public gettempoMediocomer(){
    return this.tempoMedioComer
}
public getNomE(){
    return this.nome
}
}
