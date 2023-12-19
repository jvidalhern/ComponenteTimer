package componentetimer.gui;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Componente para realizar una cuenta atrás
 *
 * @author Juan José Vidal Hernández
 */
public class TemporizadorComponent extends JComponent {

    //Declaración de variables
    private final Timer timer;
    private String formattedTime;
    private int minutos;
    private int segundos;
    private int series;
    private Clip startClip;
    private Clip endClip;

    /**
     * Constructor del componente
     *
     * @param minutos del temporizador
     * @param segundos del temporizador
     * @param sonidoInicio si se debe oir el sonido de incio del temporizador
     * @param sonidoFin si se debe oir el sonido de fin del temporizador
     */
    public TemporizadorComponent(String minutos, String segundos, Boolean sonidoInicio, Boolean sonidoFin) {
        //Formateo de cómo se mostrará el tiempo
        formattedTime = "00:00";
        timer = new Timer(1000, e -> ActualizarTemporizador(minutos, segundos, sonidoInicio, sonidoFin));
        cargarSonidoInicio();
        cargarSonidoFin();
    }

    /**
     * Método para iniciar la cuenta atrás
     *
     * @param minutos de la cuenta atrás
     * @param segundos de la cuenta atrás
     * @param sonidoInicio si se debe oir el sonido al iniciar la cuenta atrás
     * @param series número de series que se debe repetir la cuenta atrás
     */
    public void iniciarCuentaAtras(String minutos, String segundos, boolean sonidoInicio, String series) {
        validarEntradas(minutos, segundos, series);
        if (sonidoInicio) {
                playSonidoInicio();
        }
        timer.start();        
    }

    /**
     * Método para cargar el sonido de inicio
     */
    private void cargarSonidoInicio() {
        // Configurar el sonido de inicio
        try {
            File startSoundFile = new File("src/main/resources/start.wav");
            startClip = AudioSystem.getClip();
            startClip.open(AudioSystem.getAudioInputStream(startSoundFile));
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
    }

    /**
     * Método para cargar el sonido de fin
     */
    private void cargarSonidoFin() {
        // Configurar el sonido de fin
        try {
            File endSoundFile = new File("src/main/resources/end.wav");
            endClip = AudioSystem.getClip();
            endClip.open(AudioSystem.getAudioInputStream(endSoundFile));
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
    }

    /**
     * Ejecuta el sonido de inicio
     */
    private void playSonidoInicio() {
        if (startClip != null) {
            startClip.setFramePosition(0);
            startClip.start();
        }
    }

    /**
     * Ejecuta el sonido de fin
     */
    private void playSonidoFin() {
        if (endClip != null) {
            endClip.setFramePosition(0);
            endClip.start();
        }
    }

    /**
     * Actualiza el temporizador de modo que se reproduzca con los valores
     * designados por el usuario
     *
     * @param minutosAActualizar
     * @param segundosAActualizar
     * @param sonidoInicio
     * @param sonidoFin
     */
    private void ActualizarTemporizador(String minutosAActualizar, String segundosAActualizar, Boolean sonidoInicio, Boolean sonidoFin) {
        int minutosActualizados = validarEntero(minutosAActualizar, "Minutos");
        int segundosActualizados = validarEntero(segundosAActualizar, "Segundos");
        formattedTime = String.format("%02d:%02d", minutos, segundos);
        repaint();

        if (minutos == minutosActualizados && segundos == segundosActualizados) {
            if (sonidoInicio) {
                playSonidoInicio();
            }
        }

        if (minutos == 0 && segundos == 0) {
            if (sonidoFin) {
                playSonidoFin();
            }

            if (--series == 0) {
                timer.stop();
            } else {
                minutos = minutosActualizados;
                segundos = segundosActualizados;
            }
        } else {
            if (segundos > 0) {
                segundos--;
            } else {
                minutos--;
                segundos = 59;
            }
        }
    }

    /**
     * Método para validar todas las entradas al temporizador
     *
     * @param minutos número de minutos
     * @param segundos número de segundos
     * @param series número de series
     */
    public void validarEntradas(String minutos, String segundos, String series) {
        int minutosInt = validarEntero(minutos, "Minutos");
        int segundosInt = validarEntero(segundos, "Segundos");
        int seriesInt = validarSeries(series, "Series");

        // Asignar los valores verificados a las variables de la clase
        this.minutos = minutosInt;
        this.segundos = segundosInt;
        this.series = seriesInt;
    }

    /**
     * Valida el valor del campo minutos y segundos, deben ser un entero entre 0
     * y 60
     *
     * @param valor cadena recibida y valor a validar
     * @param nombreCampo nombre del campo para pintar el mensaje de error
     * @return el valor si es válido o una excepción si incumple alguna
     * validación
     */
    private int validarEntero(String valor, String nombreCampo) {
        if (valor.equals("")) {
            return 0;  // Si es una cadena vacía, asigna el valor predeterminado
        }

        try {
            int entero = Integer.parseInt(valor);

            if (entero < 0 || entero > 60) {
                throw new IllegalArgumentException("Error: " + nombreCampo + " debe estar entre 0 y 60.");
            }

            return entero;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Error: " + nombreCampo + " debe ser un número entero válido.");
        }
    }

    /**
     * Valida el valor del campo series, debe ser un entero entre 1 y 20
     *
     * @param valor cadena recibida y valor a validar
     * @param nombreCampo nombre del campo para pintar el mensaje de error
     * @return
     */
    private int validarSeries(String valor, String nombreCampo) {
        if (valor.equals("")) {
            return 1;
        }

        try {
            int entero = Integer.parseInt(valor);

            if (entero < 1 || entero > 20) {
                throw new IllegalArgumentException("Error: " + nombreCampo + " debe estar entre 1 y 20.");
            }

            return entero;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Error: " + nombreCampo + " debe ser un número entero válido.");
        }
    }

    /**
     * Sobreescritura del método paintComponent para pintar el componente
     *
     * @param g
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawString(formattedTime, 10, 20);
    }

    /**
     * Método main para probar el componente por separado sin necesidad de tener
     * una aplicación a parte
     *
     * @param args
     */
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("TemporizadorComponent");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(200, 100);

            TemporizadorComponent temporizador = new TemporizadorComponent("", "7", true, true);
            frame.add(temporizador);

            JButton startButton = new JButton("Iniciar Temporizador");
            startButton.addActionListener(e -> {
                temporizador.iniciarCuentaAtras("", "7", true, "3");
            });

            frame.add(startButton, BorderLayout.SOUTH);

            frame.setVisible(true);
        });
    }
}
