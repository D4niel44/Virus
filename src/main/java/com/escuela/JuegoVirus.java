package com.escuela;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class JuegoVirus {

  public static final int NUM_CARTAS_REPARTIR = 3;
  private static List<String> mazo;
  private static List<String> descartes;
  private static List<List<String>> jugadores;

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    System.out.println("Ingrese la cantidad de jugadores (2-6): ");
    int numJugadores = scanner.nextInt();

    if (numJugadores < 2 || numJugadores > 6) {
      System.out.println("Número de jugadores no válido. Debe ser entre 2 y 6.");
      return;
    }

    inicializarJuego(numJugadores);
    repartirCartas();

    while (!haGanadoAlguien()) {
      for (int i = 0; i < numJugadores; i++) {
        turnoJugador(jugadores.get(i));
      }
    }

    System.out.println("¡Fin del juego!");
    scanner.close();
  }

  private static void inicializarJuego(int numJugadores) {
    mazo = new ArrayList<>();
    descartes = new ArrayList<>();
    jugadores = new ArrayList<>();

    // Inicializar el mazo con las cartas del juego
    inicializarMazo();

    // Barajar el mazo
    Collections.shuffle(mazo);

    // Inicializar las listas de jugadores
    for (int i = 0; i < numJugadores; i++) {
      jugadores.add(new ArrayList<>());
    }
  }

  private static void inicializarMazo() {
    mazo = new ArrayList<>();

    // Añadir cartas tipo órgano
    agregarCartasTipoOrgano("Corazon", 4);
    agregarCartasTipoOrgano("Hueso", 4);
    agregarCartasTipoOrgano("Estomago", 4);
    agregarCartasTipoOrgano("Cerebro", 4);

    // Añadir cartas tipo virus
    agregarCartasTipoVirus("VirusCorazon", 4);
    agregarCartasTipoVirus("VirusHueso", 4);
    agregarCartasTipoVirus("VirusEstomago", 4);
    agregarCartasTipoVirus("VirusCerebro", 4);

    // Añadir cartas tipo medicina
    agregarCartasTipoMedicina("MedicinaCorazon", 4);
    agregarCartasTipoMedicina("MedicinaHueso", 4);
    agregarCartasTipoMedicina("MedicinaEstomago", 4);
    agregarCartasTipoMedicina("MedicinaCerebro", 4);

    // Añadir cartas tipo tratamiento
    agregarCartasTipoTratamiento("Trasplante", 3);
    agregarCartasTipoTratamiento("LadronOrganos", 3);
    agregarCartasTipoTratamiento("GuanteLatex", 1);
    agregarCartasTipoTratamiento("Contagio", 2);
    agregarCartasTipoTratamiento("ErrorMedico", 1);

    // Añadir cartas multicolor
    agregarCartasTipoMulticolor("OrganoMulticolor", 1);
    agregarCartasTipoMulticolor("VirusMulticolor", 1);
    agregarCartasTipoMulticolor("MedicinaMulticolor", 4);

    // Barajar el mazo
    Collections.shuffle(mazo);
  }

  private static void agregarCartasTipoOrgano(String tipo, int cantidad) {
    for (int i = 0; i < cantidad; i++) {
      mazo.add("Organo" + tipo);
    }
  }

  private static void agregarCartasTipoVirus(String tipo, int cantidad) {
    for (int i = 0; i < cantidad; i++) {
      mazo.add("Virus" + tipo);
    }
  }

  private static void agregarCartasTipoMedicina(String tipo, int cantidad) {
    for (int i = 0; i < cantidad; i++) {
      mazo.add("Medicina" + tipo);
    }
  }

  private static void agregarCartasTipoTratamiento(String tipo, int cantidad) {
    for (int i = 0; i < cantidad; i++) {
      mazo.add(tipo);
    }
  }

  private static void agregarCartasTipoMulticolor(String tipo, int cantidad) {
    for (int i = 0; i < cantidad; i++) {
      mazo.add(tipo);
    }
  }

  private static void repartirCartas() {

    for (int j = 0; j < NUM_CARTAS_REPARTIR; j++) {
      for (List<String> jugador : jugadores) {
        jugador.add(robarCarta());
      }
    }
  }

  private static String robarCarta() {
    if (mazo.isEmpty()) {
      mazo.addAll(descartes);
      descartes.clear();
      Collections.shuffle(mazo);
    }
    return mazo.remove(0);
  }

  private static void turnoJugador(List<String> manoJugador) {
    Scanner scanner = new Scanner(System.in);

    imprimirEstadoJuego(manoJugador);

    System.out.println("Elige una acción (JUGAR o DESCARTAR):");
    String accion = scanner.nextLine();

    if (accion.equalsIgnoreCase("JUGAR")) {
      jugarCarta(manoJugador);
      manoJugador.add(robarCarta());
    } else if (accion.equalsIgnoreCase("DESCARTAR")) {
      descartarCarta(manoJugador);
      while (manoJugador.size() < NUM_CARTAS_REPARTIR) {
        manoJugador.add(robarCarta());
      }
    } else {
      System.out.println("Acción no válida. Elige JUGAR o DESCARTAR.");
    }
  }

  private static void imprimirEstadoJuego(List<String> manoJugador) {
    System.out.println("Mano del jugador: " + manoJugador);
    System.out.println("Pila de descartes: " + descartes);
  }

  private static void jugarCarta(List<String> manoJugador) {
    Scanner scanner = new Scanner(System.in);

    imprimirEstadoJuego(manoJugador);

    System.out.println("Elige una carta para jugar:");
    String cartaSeleccionada = scanner.nextLine();

    if (manoJugador.contains(cartaSeleccionada)) {
      if (cartaSeleccionada.startsWith("Organo")) {
        // Acciones para jugar una carta de órgano
        System.out.println("Has jugado una carta de órgano.");
      } else if (cartaSeleccionada.startsWith("Virus")) {
        // Acciones para jugar una carta de virus
        System.out.println("Has jugado una carta de virus.");
      } else if (cartaSeleccionada.startsWith("Medicina")) {
        // Acciones para jugar una carta de medicina
        System.out.println("Has jugado una carta de medicina.");
      } else if (cartaSeleccionada.equals("Trasplante")) {
        // Acciones para jugar la carta de trasplante
        System.out.println("Has jugado la carta de trasplante.");
      } else if (cartaSeleccionada.equals("LadronOrganos")) {
        // Acciones para jugar la carta de ladrón de órganos
        System.out.println("Has jugado la carta de ladrón de órganos.");
      } else if (cartaSeleccionada.equals("GuanteLatex")) {
        // Acciones para jugar la carta de guante de látex
        System.out.println("Has jugado la carta de guante de látex.");
      } else if (cartaSeleccionada.equals("Contagio")) {
        // Acciones para jugar la carta de contagio
        System.out.println("Has jugado la carta de contagio.");
      } else if (cartaSeleccionada.equals("ErrorMedico")) {
        // Acciones para jugar la carta de error médico
        System.out.println("Has jugado la carta de error médico.");
      } else if (cartaSeleccionada.equals("OrganoMulticolor")) {
        // Acciones para jugar la carta de órgano multicolor
        System.out.println("Has jugado la carta de órgano multicolor.");
      } else if (cartaSeleccionada.equals("VirusMulticolor")) {
        // Acciones para jugar la carta de virus multicolor
        System.out.println("Has jugado la carta de virus multicolor.");
      } else if (cartaSeleccionada.equals("MedicinaMulticolor")) {
        // Acciones para jugar la carta de medicina multicolor
        System.out.println("Has jugado la carta de medicina multicolor.");
      } else {
        System.out.println("Acción no válida. Inténtalo de nuevo.");
        jugarCarta(manoJugador);
      }
    } else {
      System.out.println("Carta no válida. Inténtalo de nuevo.");
      jugarCarta(manoJugador);
    }
  }

  private static void descartarCarta(List<String> manoJugador) {
    Scanner scanner = new Scanner(System.in);

    while (!manoJugador.isEmpty()) {
      System.out.println("Elige una carta para descartar (o PASAR para pasar el turno):");
      String cartaDescartada = scanner.nextLine();
      if (cartaDescartada.equals("PASAR")) {
        return;
      } else if (manoJugador.contains(cartaDescartada)) {
        manoJugador.remove(cartaDescartada);
        descartes.add(cartaDescartada);
      } else {
        System.out.println("Carta no válida. Inténtalo de nuevo.");
      }
      imprimirEstadoJuego(manoJugador);
    }
    System.out.println("Ya no quedan cartas en la mano, pasando turno.");
  }

  private static boolean haGanadoAlguien() {
    // Lógica para determinar si algún jugador ha ganado
    return false;
  }
}