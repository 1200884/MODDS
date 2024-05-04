class Mesa {
    private int id;
    // Construtor
    public Mesa() {
    
    this.id = gerarNomePrato();
    
    }

private String gerarNomePrato() {
    int contador1 = 0;
    int contador2 = 0;
    int contador3 = 0;
    int contador4 = 0;
    int contador5 = 0;
    int contador6 = 0;
    int contador7 = 0;
    int contador8 = 0;
    int contador9 = 0;
    int contador10 = 0;


    if (contador1 == 0) {
        return "Mesa 1";
    } else if (contador2==0 ) {
        return "Mesa 2";
    } else if (contador3==0 ) {
        return "Mesa 3";
    }else if (contador4==0 ) {
        return "Mesa 4";
    }else if (contador5==0 ) {
        return "Mesa 5";
    }else if (contador6==0 ) {
        return "Mesa 6";
    }else if (contador7==0 ) {
        return "Mesa 7";
    }
    else if (contador8==0 ) {
        return "Mesa 8";
    }else if (contador9==0 ) {
        return "Mesa 9";
    }else {
        return "Mesa 10";
    }

     for (Mesa mesa : listaMesas) {
        if (mesa.id.equals("Mesa 1")) {
            contador1++;
        } else if (mesa.id.equals("Mesa 2")) {
            contador2++;
        } else if (mesa.id.equals("Mesa 3")) {
            contador3++;
        }
        else if (mesa.id.equals("Mesa 4")) {
            contador4++;
        }
        else if (mesa.id.equals("Mesa 5")) {
            contador5++;
        }
        else if (mesa.id.equals("Mesa 6")) {
            contador6++;
        }
        else if (mesa.id.equals("Mesa 7")) {
            contador7++;
        }
        else if (mesa.id.equals("Mesa 8")) {
            contador8++;
        }
        else if (mesa.id.equals("Mesa 9")) {
            contador9++;
        }
        else if (mesa.id.equals("Mesa 10")) {
            contador10++;
        }
    }
}
public getId(){
    return this.id;

}
