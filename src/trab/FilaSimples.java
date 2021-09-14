package trab;

import java.util.ArrayList;
import java.util.Random;

public class FilaSimples {
    private Random random;
    private double tempoParaPrimeiroCliente;
    private final double taxaDeChegadaMin;
    private final double taxaDeChegadaMax;
    private double taxaDeSaidaMin;
    private double taxaDeSaidaMax;
    private final int numeroServidores;
    private final int capacidadeFila;
    private int tamanhoFila;
    private double tempoDeSimulacao;
    private ArrayList<Double> temposPorEstado;
    private ArrayList<Evento> filaDeEventos;
    private double[] aleatorios;
    private int contAleatorios;
    double numeroAleatorio;
    private int quantAleatorios;

    public FilaSimples(int numeroServidores, int capacidadeFila, double taxaChegadaMin, double taxaChegadaMax,
                       double taxaSaidaMin, double taxaSaidaMax, double tempoPrimeiroCliente, int quantAleatorios) {
        this.numeroServidores = numeroServidores;
        this.capacidadeFila = capacidadeFila;
        this.taxaDeChegadaMin = taxaChegadaMin;
        this.taxaDeChegadaMax = taxaChegadaMax;
        this.taxaDeSaidaMin = taxaSaidaMin;
        this.taxaDeSaidaMax = taxaSaidaMax;
        this.tempoParaPrimeiroCliente = tempoPrimeiroCliente;
        this.random = new Random();
        this.filaDeEventos = new ArrayList<>();
        this.contAleatorios = 0;
        this.numeroAleatorio = 0;
        this.temposPorEstado = new ArrayList<>();
        this.quantAleatorios = quantAleatorios;

        this.aleatorios = lcm(0.12345, 1, 5, 5, 100000);
    }

    //Extraido de: https://www.geeksforgeeks.org/java-program-to-implement-the-linear-congruential-generator-for-pseudo-random-number-generation/
    // Function to generate random numbers
    static double[] lcm(double seed, int mod, int multiplier, int inc, int noOfRandomNum) {
        // Initialize the seed state
        double[] randomNums = new double[noOfRandomNum];

        randomNums[0] = seed;

        // Traverse to generate required
        // numbers of random numbers
        for (int i = 1; i < noOfRandomNum; i++) {

            // Follow the linear congruential method
            randomNums[i] = ((randomNums[i - 1] * multiplier) + inc) % mod;
        }

        return randomNums;
    }

    public void iniciar() {
        filaDeEventos.add(new Evento(TipoEvento.CHEGADA, tempoParaPrimeiroCliente));
        int indiceProximaExecucao = 0;

        while (contAleatorios < quantAleatorios) {
            indiceProximaExecucao = proximaExecucao();
            if (filaDeEventos.get(indiceProximaExecucao).getEvento() == TipoEvento.CHEGADA) {
                chegada(indiceProximaExecucao);
            } else {
                saida(indiceProximaExecucao);
            }
        }

        resultados();
    }

    private void chegada(int indiceProximaExecucao) {
        if (temposPorEstado.size() <= tamanhoFila) {
            temposPorEstado.add(0.0);
        }

        temposPorEstado.set(tamanhoFila, filaDeEventos.get(indiceProximaExecucao).getTempo() - tempoDeSimulacao + temposPorEstado.get(tamanhoFila));
        tempoDeSimulacao = filaDeEventos.get(indiceProximaExecucao).getTempo();
        filaDeEventos.remove(indiceProximaExecucao);

        if (capacidadeFila == 0 || tamanhoFila < capacidadeFila) {
            tamanhoFila++;
            if (tamanhoFila <= numeroServidores && contAleatorios < quantAleatorios) {
                //numeroAleatorio = (taxaDeSaidaMax - taxaDeSaidaMin) * random.nextDouble() + taxaDeSaidaMin;
                numeroAleatorio = (taxaDeSaidaMax - taxaDeSaidaMin) * aleatorios[contAleatorios] + taxaDeSaidaMin;
                contAleatorios++;
                filaDeEventos.add(new Evento(TipoEvento.SAIDA, numeroAleatorio + tempoDeSimulacao));
            }
        }

        if (contAleatorios < quantAleatorios) {
            //numeroAleatorio = (taxaDeChegadaMax - taxaDeChegadaMin) * random.nextDouble() + taxaDeChegadaMin;
            numeroAleatorio = (taxaDeChegadaMax - taxaDeChegadaMin) * aleatorios[contAleatorios] + taxaDeChegadaMin;
            contAleatorios++;
            filaDeEventos.add(new Evento(TipoEvento.CHEGADA, numeroAleatorio + tempoDeSimulacao));
        }
    }

    private void saida(int indiceProximaExecucao) {
        if (temposPorEstado.size() <= tamanhoFila) {
            temposPorEstado.add(0.0);
        }
        temposPorEstado.set(tamanhoFila, filaDeEventos.get(indiceProximaExecucao).getTempo() - tempoDeSimulacao + temposPorEstado.get(tamanhoFila));
        tempoDeSimulacao = filaDeEventos.get(indiceProximaExecucao).getTempo();
        filaDeEventos.remove(indiceProximaExecucao);
        tamanhoFila--;

        if (tamanhoFila >= numeroServidores && contAleatorios < quantAleatorios) {
            //numeroAleatorio = (taxaDeSaidaMax - taxaDeSaidaMin) * random.nextDouble() + taxaDeSaidaMin;
            numeroAleatorio = (taxaDeSaidaMax - taxaDeSaidaMin) * aleatorios[contAleatorios] + taxaDeSaidaMin;
            contAleatorios++;
            filaDeEventos.add(new Evento(TipoEvento.SAIDA, numeroAleatorio + tempoDeSimulacao));
        }

    }

    private int proximaExecucao() {
        double menorTempo = 99999999;
        int indiceProximaExecucao = 0;

        for (int i = 0; i < filaDeEventos.size(); i++) {
            if (filaDeEventos.get(i).getTempo() < menorTempo) {
                menorTempo = filaDeEventos.get(i).getTempo();
                indiceProximaExecucao = i;
            }
        }
        return indiceProximaExecucao;
    }

    private void resultados() {
        System.out.println("\nTEMPO POR ESTADO: ");
        for (int i = 0; i < temposPorEstado.size(); i++) {
            System.out.println("Tempo no estado " + i + ": " + temposPorEstado.get(i));
        }

        System.out.println("\nPROBABILIDADE POR ESTADO: ");
        for (int i = 0; i < temposPorEstado.size(); i++) {
            System.out.println("Probabilidade no estado " + i + ": " + temposPorEstado.get(i) * 100 / tempoDeSimulacao + "%");
        }

        System.out.println("\nTEMPO TOTAL: " + tempoDeSimulacao);
        System.out.println("\nTAMANHO FINAL DA FILA: " + tamanhoFila);
    }
}
