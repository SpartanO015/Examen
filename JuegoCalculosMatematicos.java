import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

 

public class JuegoCalculosMatematicos extends JFrame implements ActionListener, KeyListener {
    private JLabel labelNumero1, labelNumero2, labelOperador, labelRespuestas;
    private JTextField textFieldRespuesta;
    private JButton botonIniciar;
    private Timer timer;
    private Random random;
    private int numero1, numero2, resultado, respuestasCorrectas, oportunidadesRestantes;

 

    public JuegoCalculosMatematicos() {
        super("Juego de Cálculos Matemáticos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 250);
        setResizable(false);

 

        random = new Random();
        oportunidadesRestantes = 3;

 

        JPanel panelPrincipal = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(5, 5, 5, 5);

 

        labelNumero1 = new JLabel();
        panelPrincipal.add(labelNumero1, constraints);

 

        labelOperador = new JLabel();
        constraints.gridx++;
        panelPrincipal.add(labelOperador, constraints);

 

        labelNumero2 = new JLabel();
        constraints.gridx++;
        panelPrincipal.add(labelNumero2, constraints);

 

        labelRespuestas = new JLabel("Respuestas correctas: 0");
        constraints.gridy++;
        constraints.gridx = 0;
        constraints.gridwidth = 2;
        panelPrincipal.add(labelRespuestas, constraints);

 

        textFieldRespuesta = new JTextField(10);
        textFieldRespuesta.setEnabled(false);
        textFieldRespuesta.addKeyListener(this);
        constraints.gridy++;
        panelPrincipal.add(textFieldRespuesta, constraints);

 

        botonIniciar = new JButton("Iniciar");
        botonIniciar.addActionListener(this);
        constraints.gridy++;
        panelPrincipal.add(botonIniciar, constraints);

 

        getContentPane().add(panelPrincipal, BorderLayout.CENTER);

 

        setLocationRelativeTo(null);
        setVisible(true);
    }

 

    private void generarPregunta() {
        numero1 = random.nextInt(90) + 10; // Número aleatorio de 2 dígitos
        numero2 = random.nextInt(90) + 10; // Número aleatorio de 2 dígitos

 

        int operador = random.nextInt(4); // 0: suma, 1: resta, 2: multiplicación, 3: división

 

        switch (operador) {
            case 0:
                labelOperador.setText("+");
                resultado = numero1 + numero2;
                break;
            case 1:
                labelOperador.setText("-");
                resultado = numero1 - numero2;
                break;
            case 2:
                labelOperador.setText("x");
                resultado = numero1 * numero2;
                break;
            case 3:
                labelOperador.setText("/");
                resultado = numero1 / numero2;
                break;
        }

 

        labelNumero1.setText(String.valueOf(numero1));
        labelNumero2.setText(String.valueOf(numero2));

 

        textFieldRespuesta.setText("");
        textFieldRespuesta.setEnabled(true);
        textFieldRespuesta.requestFocus();

 

        timer = new Timer(5000, this); // 5 segundos para responder
        timer.setRepeats(false);
        timer.start();
    }

 

    private void verificarRespuesta() {
        try {
            int respuesta = Integer.parseInt(textFieldRespuesta.getText());

 

            if (respuesta == resultado) {
                respuestasCorrectas++;
                labelRespuestas.setText("Respuestas correctas: " + respuestasCorrectas);
            } else {
                oportunidadesRestantes--;
                JOptionPane.showMessageDialog(this, "Respuesta incorrecta. Oportunidades restantes: " + oportunidadesRestantes);
                if (oportunidadesRestantes == 0) {
                    terminarJuego();
                    return;
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingrese una respuesta válida." +
                    "\nRecuerde que dispone de solo 5 segundos.");
        }

 

        generarPregunta();
    }

 

    private void terminarJuego() {
        textFieldRespuesta.setEnabled(false);
        botonIniciar.setEnabled(true);
        timer.stop();

 

        JOptionPane.showMessageDialog(this, "Juego terminado. Respuestas correctas: " + respuestasCorrectas);
        respuestasCorrectas = 0;
        oportunidadesRestantes = 3;
        labelRespuestas.setText("Respuestas correctas: 0");
    }

 

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == botonIniciar) {
            botonIniciar.setEnabled(false);
            generarPregunta();
        } else if (e.getSource() == timer) {
            verificarRespuesta();
        }
    }

 

    @Override
    public void keyTyped(KeyEvent e) {
    }

 

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            verificarRespuesta();
        }
    }

 

    @Override
    public void keyReleased(KeyEvent e) {
    }

 

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new JuegoCalculosMatematicos();
            }
        });
    }
}
