public class Cliente {
    int id;
    int tempoChegada;
    int tempoAtendimento;
    long tempoInicioAtendimento;
    long tempoFimAtendimento;



    public Cliente(int id, int tempoChegada, int tempoAtendimento) {
        this.id = id;
        this.tempoChegada = tempoChegada;
        this.tempoAtendimento = tempoAtendimento;
    }

    long TempoEspera(){
        return tempoAtendimento - tempoChegada;
    }

    long TempoTotalBanco(){
        return tempoFimAtendimento - tempoChegada;
    }

    

}
