package Tema6;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class Calculadora extends JFrame {
	JFrame ventanaError;
	/*Paneles donde se colocan los botones*/
	private JPanel panelNumeros, panelOperaciones;
	/*Numeros del panel*/
	private JTextField numerosPantalla;
	/*Donde se guarda los resultados o los numeros*/
	private double resultado;
	/*Donde se guarda la operacion realizada*/
	private String operacion;
	/*Comprobacion para ver si está inicializada o no una operacion*/
	boolean nuevaOperacion = true;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Calculadora frame = new Calculadora();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Calculadora() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(300, 300, 550, 450);
		setTitle("Calculadora Interfaces");
		setResizable(false);
		
		/*Jpanel donde se muestran los numeros y el total*/
		
		JPanel panel = (JPanel) this.getContentPane();
		panel.setLayout(new BorderLayout());
		
		/*Se le llama al panel pantalla para editarlo*/
		numerosPantalla = new JTextField(" ",30);
		numerosPantalla.setBorder(new EmptyBorder(4, 4, 4, 4));
		numerosPantalla.setFont(new Font("Arial", Font.BOLD, 25));
		numerosPantalla.setHorizontalAlignment(JTextField.RIGHT);
		numerosPantalla.setEditable(false);
		numerosPantalla.setOpaque(true);
		numerosPantalla.setBackground(Color.WHITE);
		numerosPantalla.setForeground(Color.CYAN);
		panel.add("North", numerosPantalla);
		
		/*JPanel panelNumeros*/
		panelNumeros = new JPanel();
		panelNumeros.setLayout(new GridLayout(4, 3));
		panelNumeros.setBorder(new EmptyBorder(3, 3, 4, 4));
		/*Se crea los botones de los numeros*/
		for (int n = 9; n >= 0; n--) {
			nuevoBotonNumerico("" + n);
		}
		/*Se crean estos 2 botones separados*/
		nuevoBotonNumerico(".");
		nuevoBotonNumerico("Boton Ayuda");

		panel.add("Center", panelNumeros);
		/*Se crea un panel para los botones donde van a estar la +, -, / y x */
		panelOperaciones = new JPanel();
		panelOperaciones.setLayout(new GridLayout(6, 1));
		panelOperaciones.setBorder(new EmptyBorder(4, 4, 4, 4));
		/*Se crean botones para poder realizar las operaciones*/
		nuevoBotonOperacion("+");
		nuevoBotonOperacion("-");
		nuevoBotonOperacion("x");
		nuevoBotonOperacion("/");
		nuevoBotonOperacion("=");
		nuevoBotonOperacion("C");
		
		panel.add("East", panelOperaciones);
	
		validate();
	}
	/*Crear botones numericos*/
	private void nuevoBotonNumerico(String digito) {
		JButton btn = new JButton();
		btn.setText(digito);
		btn.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent evt) {
				JButton btn = (JButton) evt.getSource();
				numeroPulsado(btn.getText());
			}
		});
		
		panelNumeros.add(btn);
	}
	/*Crear boton para las operaciones*/
	private void nuevoBotonOperacion(String operacion) {
		JButton btn = new JButton(operacion);
		btn.setForeground(Color.RED);
		btn.setFont(new Font("Arial", Font.BOLD, 22));
		btn.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent evt) {
				JButton btn = (JButton) evt.getSource();
				operacionPulsado(btn.getText());
			}
		});

		panelOperaciones.add(btn);
	}
	/*Gestiona las pulsaciones de las teclas numericas*/
	private void numeroPulsado(String digito) {
		if (numerosPantalla.getText().equals("0") || nuevaOperacion) {
			numerosPantalla.setText(digito);
		} else {
			numerosPantalla.setText(numerosPantalla.getText() + digito);
		}
		nuevaOperacion = false;
	}
	/*Gestiona el gestiona las pulsaciones de teclas de operación*/
	private void operacionPulsado(String tecla) {
		if (tecla.equals("=")) {
			calcularResultado();
		} else if (tecla.equals("C")) {
			resultado = 0;
			numerosPantalla.setText("");
			nuevaOperacion = true;
		} else {
			operacion = tecla;
			if ((resultado > 0) && !nuevaOperacion) {
				calcularResultado();
			} else {
				resultado = Double.valueOf(numerosPantalla.getText());
			}
		}

		nuevaOperacion = true;
	}
	/*Calcular el resultado y sacarlo por pantalla*/
	private void calcularResultado() {
		/*Opcion suma*/
		if (operacion.equals("+")) {
			resultado +=  Float.valueOf(numerosPantalla.getText());
		}
		/*Opcion resta*/
		else if(operacion.equals("-")) {
			resultado -=  Float.valueOf(numerosPantalla.getText());
		}
		/*Opcion multiplicacion*/
		else if (operacion.equals("x")) {
			resultado *=  Float.valueOf(numerosPantalla.getText());
		}
		/*Opcion division*/
		else if (operacion.equals("/")) {
			resultado /=  Float.valueOf(numerosPantalla.getText());
			if((resultado == Double.NaN) || (resultado == Double.POSITIVE_INFINITY) || (resultado == Double.NEGATIVE_INFINITY)){
				numerosPantalla.setText("ERROR");
				JOptionPane.showMessageDialog(ventanaError, " Error, no se puede dividir entre 0\n Dale al botón C para borrar el resultado ", "Error division", JOptionPane.ERROR_MESSAGE);
			}
			
		}
	numerosPantalla.setText("" + resultado);
	operacion = "";
	}
}
