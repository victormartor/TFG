/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

/**
 *
 * @author victor
 */
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
 
@SuppressWarnings("serial")
public class Checs extends JFrame {
 
 private JCheckBox check1;
 private JCheckBox check2;
 private JRadioButton radio1;
 private JRadioButton radio2;
 
 public Checs() {
 add(getCheck1());
 add(getCheck2());
 add(getLabel1());
 add(getLabel2());
 add(getLabel3());
 add(getLabel4());
 add(getLabel5());
 add(getLabel6());
 getButtonGroup(getRadio1(), getRadio2());
 add(this.radio1);
 add(this.radio2);
 inicializador();
 }
 
 private void inicializador() {
 setSize(400, 300);
 setLayout(null);
 setTitle("Aprendiendo Checkbox");
 setLocationRelativeTo(null);
 setVisible(true);
 setResizable(false);
 setDefaultCloseOperation(EXIT_ON_CLOSE);
 }
 
 private JLabel getLabel1() {
 JLabel l = new JLabel("Seleccione que le gusta");
 l.setBounds(10, 5, 180, 30);
 return l;
 }
 
 private JLabel getLabel2() {
 JLabel l = new JLabel("Comer");
 l.setBounds(10, 30, 80, 30);
 return l;
 }
 
 private JLabel getLabel3() {
 JLabel l = new JLabel("Dormir");
 l.setBounds(10, 50, 80, 30);
 return l;
 }
 
 private JCheckBox getCheck1() {
 check1 = new JCheckBox();
 check1.setBounds(80, 30, 20, 20);
 check1.setSelected(true);
 return check1;
 }
 
 private JCheckBox getCheck2() {
 check2 = new JCheckBox();
 check2.setBounds(80, 53, 20, 20);
 return check2;
 }
 
 private JLabel getLabel4() {
 JLabel l = new JLabel("Genero");
 l.setBounds(10, 80, 80, 30);
 return l;
 }
 
 private JLabel getLabel5() {
 JLabel l = new JLabel("Masculino");
 l.setBounds(10, 100, 80, 30);
 return l;
 }
 
 private JLabel getLabel6() {
 JLabel l = new JLabel("Femenino");
 l.setBounds(10, 115, 80, 30);
 return l;
 }
 
 private JRadioButton getRadio1() {
 radio1 = new JRadioButton();
 radio1.setSelected(true);
 radio1.setBounds(80, 100, 20, 20);
 return radio1;
 }
 
 private JRadioButton getRadio2() {
 radio2 = new JRadioButton();
 radio2.setBounds(80, 120, 20, 20);
 return radio2;
 }
  
 //Creación del grupo de botones recibe nuestro radiobutton creados
 //anteriormente
 private void getButtonGroup (JRadioButton radio1, JRadioButton radio2){
 //creamos el objeto
 ButtonGroup bgroup = new ButtonGroup();
 //añadimos al grupo el primer radioButton
 bgroup.add(radio1);
 //añadimos al grupo el segundo radioButton
 bgroup.add(radio2); 
 }
 
 public static void main(String[] args) {
 new Checs();
 }
 
}
