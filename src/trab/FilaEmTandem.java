package trab;

import java.util.ArrayList;
import java.util.Random;

public class FilaEmTandem {
    private Random random;
    private final double taxaChegadaMin;
    private final double taxaChegadaMax;
    private final double taxaTrocaMin;
    private final double taxaTrocaMax;
    private final double taxaSaidaMin;
    private final double taxaSaidaMax;
    private double tempoPrimeiroCliente;
    private final int servidoresFila1;
    private final int capacidadeFila1;
    private final int servidoresFila2;
    private final int capacidadeFila2;
    private int tamanhoFila1;
    private int tamanhoFila2;
    private double tempoSimulacao;
    private ArrayList<Double> temposEstado1;
    private ArrayList<Double> temposEstado2;
    private ArrayList<Evento> filaEventos;
    private final double[] aleatorios;
    private int contAleatorios;
    private double numeroAleatorio;
    private final int quantAleatorios;
    private int eventosRealizados;
    private int totalEventos;


    public FilaEmTandem(int servidoresFila1, int capacidadeFila1, double taxaChegadaMin, double taxaChegadaMax,
                        double taxaTrocaMin, double taxaTrocaMax, int servidoresFila2, int capacidadeFila2,
                        double taxaSaidaMin, double taxaSaidaMax, double tempoPrimeiroCliente, int quantAleatorios) {

        this.servidoresFila1 = servidoresFila1;
        this.capacidadeFila1 = capacidadeFila1;
        this.taxaChegadaMin = taxaChegadaMin;
        this.taxaChegadaMax = taxaChegadaMax;
        this.taxaTrocaMin = taxaTrocaMin;
        this.taxaTrocaMax = taxaTrocaMax;
        this.servidoresFila2 = servidoresFila2;
        this.capacidadeFila2 = capacidadeFila2;
        this.taxaSaidaMin = taxaSaidaMin;
        this.taxaSaidaMax = taxaSaidaMax;
        this.tempoPrimeiroCliente = tempoPrimeiroCliente;
        this.temposEstado1 = new ArrayList<>();
        this.temposEstado2 = new ArrayList<>();
        this.numeroAleatorio = 0;
        this.tempoSimulacao = 0;
        this.filaEventos = new ArrayList<Evento>();
        this.contAleatorios = 0;
        this.tamanhoFila1 = 0;
        this.tamanhoFila2 = 0;
        this.random = new Random();
        this.quantAleatorios = quantAleatorios;
        this.totalEventos = 0;
        this.eventosRealizados = 0;

        this.aleatorios = lcm(0.12345, 1, 5, 5, 100000);
    }

    //Extraido de: https://www.geeksforgeeks.org/java-program-to-implement-the-linear-congruential-generator-for-pseudo-random-number-generation/
    // Function to generate random numbers
    double[] lcm(double seed, int mod, int multiplier, int inc, int noOfRandomNum) {
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

    public void executar() {
        filaEventos.add(new Evento(TipoEvento.CHEGADA, tempoPrimeiroCliente));
        totalEventos++;

        int indiceProximaExecucao = 0;
        while (contAleatorios < quantAleatorios) {
            indiceProximaExecucao = proximaExecucaoT();

            if (filaEventos.get(indiceProximaExecucao).getEvento() == TipoEvento.CHEGADA) {
                chegada(indiceProximaExecucao);
            } else if (filaEventos.get(indiceProximaExecucao).getEvento() == TipoEvento.TROCA) {
                troca(indiceProximaExecucao);
            } else {
                saida(indiceProximaExecucao);
            }
        }
        resultados();
    }

    private void chegada(int indiceProximaExecucao) {
        if (temposEstado1.size() <= tamanhoFila1){
            temposEstado1.add(0.0);
        }

        if (temposEstado2.size() <= tamanhoFila2){
            temposEstado2.add(0.0);
        }
        temposEstado1.set(tamanhoFila1, filaEventos.get(indiceProximaExecucao).getTempo() - tempoSimulacao + temposEstado1.get(tamanhoFila1));
        temposEstado2.set(tamanhoFila2, filaEventos.get(indiceProximaExecucao).getTempo() - tempoSimulacao + temposEstado2.get(tamanhoFila2));
        tempoSimulacao = filaEventos.get(indiceProximaExecucao).getTempo();
        filaEventos.remove(indiceProximaExecucao);
        eventosRealizados++;

        if (capacidadeFila1 == 0 || tamanhoFila1 < capacidadeFila1) {
            tamanhoFila1++;

            if (tamanhoFila1 <= servidoresFila1 && contAleatorios < quantAleatorios) {
                numeroAleatorio = (taxaTrocaMax - taxaTrocaMin) * aleatorios[contAleatorios] + taxaTrocaMin;
                contAleatorios++;
                filaEventos.add(new Evento(TipoEvento.TROCA, numeroAleatorio + tempoSimulacao));
                totalEventos++;
            }
        }

        if (contAleatorios < quantAleatorios) {
            numeroAleatorio = (taxaChegadaMax - taxaChegadaMin) * aleatorios[contAleatorios] + taxaChegadaMin;
            contAleatorios++;
            filaEventos.add(new Evento(TipoEvento.CHEGADA, numeroAleatorio + tempoSimulacao));
            totalEventos++;
        }
    }

    private void troca(int indiceProximaExecucao) {
        if (temposEstado1.size() <= tamanhoFila1){
            temposEstado1.add(0.0);
        }

        if (temposEstado2.size() <= tamanhoFila2){
            temposEstado2.add(0.0);
        }
        temposEstado1.set(tamanhoFila1, filaEventos.get(indiceProximaExecucao).getTempo() - tempoSimulacao + temposEstado1.get(tamanhoFila1));
        temposEstado2.set(tamanhoFila2, filaEventos.get(indiceProximaExecucao).getTempo() - tempoSimulacao + temposEstado2.get(tamanhoFila2));
        tempoSimulacao = filaEventos.get(indiceProximaExecucao).getTempo();
        filaEventos.remove(indiceProximaExecucao);
        eventosRealizados++;

        tamanhoFila1--;

        if (tamanhoFila1 >= servidoresFila1 && contAleatorios < quantAleatorios) {
            numeroAleatorio = (taxaTrocaMax - taxaTrocaMin) * aleatorios[contAleatorios] + taxaTrocaMin;
            contAleatorios++;
            filaEventos.add(new Evento(TipoEvento.TROCA, numeroAleatorio + tempoSimulacao));
            totalEventos++;
        }

        if (capacidadeFila2 == 0 || tamanhoFila2 < capacidadeFila2) {
            tamanhoFila2++;

            if (tamanhoFila2 <= servidoresFila2 && contAleatorios < quantAleatorios) {
                numeroAleatorio = (taxaSaidaMax - taxaSaidaMin) * aleatorios[contAleatorios] + taxaSaidaMin;
                contAleatorios++;
                filaEventos.add(new Evento(TipoEvento.SAIDA, numeroAleatorio + tempoSimulacao));
                totalEventos++;
            }
        }
    }

    private void saida(int indiceProximaExecucao) {
        if (temposEstado1.size() <= tamanhoFila1){
            temposEstado1.add(0.0);
        }

        if (temposEstado2.size() <= tamanhoFila2){
            temposEstado2.add(0.0);
        }

        temposEstado1.set(tamanhoFila1, filaEventos.get(indiceProximaExecucao).getTempo() - tempoSimulacao + temposEstado1.get(tamanhoFila1));
        temposEstado2.set(tamanhoFila2, filaEventos.get(indiceProximaExecucao).getTempo() - tempoSimulacao + temposEstado2.get(tamanhoFila2));
        tempoSimulacao = filaEventos.get(indiceProximaExecucao).getTempo();
        filaEventos.remove(indiceProximaExecucao);
        eventosRealizados++;
        tamanhoFila2--;

        if (tamanhoFila2 >= servidoresFila2 && contAleatorios < quantAleatorios) {
            numeroAleatorio = (taxaSaidaMax - taxaSaidaMin) * aleatorios[contAleatorios] + taxaSaidaMin;
            contAleatorios++;
            filaEventos.add(new Evento(TipoEvento.SAIDA , numeroAleatorio + tempoSimulacao));
            totalEventos++;
        }
    }

    private int proximaExecucaoT() {
        double menorTempo = 999999;
        int indiceProximaExecucao = 0;
        for (int i = 0; i < filaEventos.size(); i++) {
            if (filaEventos.get(i).getTempo() < menorTempo) {
                menorTempo = filaEventos.get(i).getTempo();
                indiceProximaExecucao = i;
            }
        }
        return indiceProximaExecucao;
    }

    private void resultados() {
        System.out.println("\nTEMPO TOTAL: " + tempoSimulacao);
        System.out.println("\nTAMANHO FINAL DA FILA 1: " + tamanhoFila1);
        System.out.println("\nTEMPO POR ESTADO DA FILA 1: ");

        for (int i = 0; i < temposEstado1.size(); i++) {
            System.out.println("Tempo no estado " + i + ": " + temposEstado1.get(i));
        }

        System.out.println("\nPROBABILIDADE POR ESTADO NA FILA 1: ");
        for (int i = 0; i < temposEstado1.size() ; i++) {
            System.out.println("Probabilidade no estado " + i + ": " + temposEstado1.get(i) * 100 / tempoSimulacao + "%");
        }

        System.out.println("\n\nTAMANHO FINAL DA FILA 2: " + tamanhoFila2);
        System.out.println("\nTEMPO POR ESTADO DA FILA 2: ");

        for (int i = 0; i < temposEstado2.size(); i++) {
            System.out.println("Tempo no estado " + i + ": " + temposEstado2.get(i));
        }

        System.out.println("\nPROBABILIDADE POR ESTADO NA FILA 2: ");

        for (int i = 0; i < temposEstado2.size() ; i++) {
            System.out.println("Probabilidade no estado " + i + ": " + temposEstado2.get(i) * 100 / tempoSimulacao + "%");
        }

        System.out.println ("\n\nNUMERO DE EVENTOS EXECUTADOS: " + eventosRealizados);
        System.out.println ("\nNUMERO DE EVENTOS AGENDADOS E NAO EXECUTADOS: " + (totalEventos - eventosRealizados));
    }
}