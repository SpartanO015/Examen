import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JuegoGato extends JFrame implements ActionListener {
    private JButton[][] botones;
    private String jugadorActual;
    private String[][] matriz;

    public JuegoGato() {
        super("Juego de Gato");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setResizable(false);

        botones = new JButton[3][3];
        jugadorActual = "O";
        matriz = new String[3][3];

        JPanel panelPrincipal = new JPanel(new GridLayout(3, 3, 5, 5));
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                botones[i][j] = new JButton();
                botones[i][j].setFont(new Font(Font.SANS_SERIF, Font.BOLD, 50));
                botones[i][j].addActionListener(this);
                panelPrincipal.add(botones[i][j]);
            }
        }

        getContentPane().add(panelPrincipal, BorderLayout.CENTER);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private boolean hayGanador() {
        // Verificar filas
        for (int i = 0; i < 3; i++) {
            if (matriz[i][0] != null && matriz[i][0].equals(matriz[i][1]) && matriz[i][0].equals(matriz[i][2])) {
                return true;
            }
        }

        // Verificar columnas
        for (int i = 0; i < 3; i++) {
            if (matriz[0][i] != null && matriz[0][i].equals(matriz[1][i]) && matriz[0][i].equals(matriz[2][i])) {
                return true;
            }
        }

        // Verificar diagonales
        if (matriz[0][0] != null && matriz[0][0].equals(matriz[1][1]) && matriz[0][0].equals(matriz[2][2])) {
            return true;
        }
        if (matriz[0][2] != null && matriz[0][2].equals(matriz[1][1]) && matriz[0][2].equals(matriz[2][0])) {
            return true;
        }

        return false;
    }

    private boolean hayEmpate() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (matriz[i][j] == null) {
                    return false;
                }
            }
        }
        return true;
    }

    private void reiniciarJuego() {
        jugadorActual = "O";
        matriz = new String[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                botones[i][j].setText("");
                botones[i][j].setEnabled(true);
            }
        }
    }

    private void realizarMovimiento(int fila, int columna) {
        matriz[fila][columna] = jugadorActual;
        botones[fila][columna].setText(jugadorActual);
        botones[fila][columna].setEnabled(false);

        if (hayGanador()) {
            JOptionPane.showMessageDialog(this, "¡El jugador " + jugadorActual + " ha ganado!");
            reiniciarJuego();
        } else if (hayEmpate()) {
            JOptionPane.showMessageDialog(this, "¡Empate!");
            reiniciarJuego();
        } else {
            jugadorActual = jugadorActual.equals("O") ? "X" : "O";
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton boton = (JButton) e.getSource();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (botones[i][j] == boton) {
                    realizarMovimiento(i, j);
                    return;
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new JuegoGato();
            }
        });
    }
}
