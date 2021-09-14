package trab;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        double tempoPrimeiroCliente = 0;
        double taxaChegadaMin = 0;
        double taxaChegadaMax = 0;
        double taxaSaidaMin = 0;
        double taxaSaidaMax = 0;
        int numeroServidores = 0;
        int capacidadeFila = 0;
        int quantAleatorios = 0;

        Scanner scanner = new Scanner(System.in);

        System.out.println("Iniciando algoritmo fila simples");

        System.out.println("Quantos servidores tem a fila?");
        numeroServidores = 2;//scanner.nextInt();

        System.out.println("Qual a capacidade da fila (se infinito, digite 0)?");
        capacidadeFila = 5;//scanner.nextInt();

        System.out.println("Qual a taxa minima de chegada?");
        taxaChegadaMin = 2;//scanner.nextDouble();

        System.out.println("Qual a taxa maxima de chegada?");
        taxaChegadaMax = 4;//scanner.nextDouble();

        System.out.println("Qual a taxa minima de atendimento?");
        taxaSaidaMin = 3;//scanner.nextDouble();

        System.out.println("Qual a taxa maxima de atendimento?");
        taxaSaidaMax = 5;//scanner.nextDouble();

        System.out.println("Quanto tempo demora para chegar o primeiro cliente?");
        tempoPrimeiroCliente = 3;//scanner.nextDouble();

        System.out.println ("Quantos aleatórios serão executados?");
        quantAleatorios = 100000;//scanner.nextInt();

        FilaSimples filaSimples = new FilaSimples(numeroServidores, capacidadeFila, taxaChegadaMin, taxaChegadaMax, taxaSaidaMin, taxaSaidaMax, tempoPrimeiroCliente, quantAleatorios);

        filaSimples.iniciar();
    }
}
