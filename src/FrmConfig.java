
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

@SuppressWarnings("serial")
public class FrmConfig extends JFrame {

    private JPanel contentPane;
    private JLabel jLQuadro1;
    private JComboBox jTQuadro1;
    private JLabel jLQuadro2;
    private JComboBox jTQuadro2;
    private JRadioButton jRmedianaN;
    private JRadioButton jRmedianaNLogN;
    private JLabel jLInfo;

    private JButton jBProcessar;
    private JButton jBAbrirImagem;
    private JButton jBFechar;

    private String nomeArquivoImagem;

    public FrmConfig() {
        nomeArquivoImagem = "";
        inicializar();
    }

    private void inicializar() {
        contentPane = (JPanel) this.getContentPane();
        jLQuadro1 = new JLabel();
        String[] valores = {"3", "7", "15"};
        jTQuadro1 = new JComboBox(valores);
        jLQuadro2 = new JLabel();
        jTQuadro2 = new JComboBox(valores);
        jRmedianaN = new JRadioButton();
        jRmedianaNLogN = new JRadioButton();
        jLInfo = new JLabel();

        jBProcessar = new JButton();
        jBAbrirImagem = new JButton();
        jBFechar = new JButton();

        contentPane.setLayout(null);
        this.setSize(new Dimension(250, 220));
        this.setTitle("Filtro de Mediana");
        this.setResizable(false);

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        jLQuadro1.setBounds(new Rectangle(11, 11, 61, 17));
        jLQuadro1.setText("Quadro(p):");
        jTQuadro1.setBounds(new Rectangle(11, 28, 63, 21));

        jLQuadro2.setBounds(new Rectangle(11, 51, 61, 17));
        jLQuadro2.setText("Quadro(q):");
        jTQuadro2.setBounds(new Rectangle(11, 68, 63, 21));

        jRmedianaN.setBounds(new Rectangle(11, 108, 115, 21));
        jRmedianaN.setText("Versão 1(nlogn)");
        jRmedianaN.setSelected(true);

        jRmedianaNLogN.setBounds(new Rectangle(11, 128, 115, 21));
        jRmedianaNLogN.setText("Versão 2(n)");

        jLInfo.setBounds(new Rectangle(11, 148, 220, 41));
        jLInfo.setText("");

        ButtonGroup grupo = new ButtonGroup();
        grupo.add(jRmedianaN);
        grupo.add(jRmedianaNLogN);

        jBProcessar.setBounds(new Rectangle(111, 9, 120, 27));
        jBProcessar.setText("Processar");
        jBProcessar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jBProcessar_actionPerformed(e);
            }
        });

        jBAbrirImagem.setBounds(new Rectangle(111, 39, 120, 27));
        jBAbrirImagem.setText("Abrir Imagem");
        jBAbrirImagem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jBAbrirImagem_actionPerformed(e);
            }
        });

        jBFechar.setBounds(new Rectangle(111, 69, 120, 27));
        jBFechar.setText("Fechar");
        jBFechar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jBFechar_actionPerformed(e);
            }
        });

        contentPane.add(jLQuadro1, null);
        contentPane.add(jTQuadro1, null);
        contentPane.add(jLQuadro2, null);
        contentPane.add(jTQuadro2, null);
        contentPane.add(jRmedianaN, null);
        contentPane.add(jRmedianaNLogN, null);
        contentPane.add(jLInfo, null);
        contentPane.add(jBProcessar, null);
        contentPane.add(jBAbrirImagem, null);
        contentPane.add(jBFechar, null);
    }

    //Aplica o filtro na imagem default
    void jBProcessar_actionPerformed(ActionEvent e) {
        //Recupera o valor de p da janela
        int p = Integer.parseInt(jTQuadro1.getSelectedItem().toString());
        //Recupera o valor de q da janela
        int q = Integer.parseInt(jTQuadro2.getSelectedItem().toString());
        int tipoFiltro = 0;
        //Verifica o tipo do filtro
        if (jRmedianaNLogN.isSelected() == true) {
            tipoFiltro = 1;
        }
        //Abre a janela e passa os parametros	
        FiltroMediana filtro = new FiltroMediana();
        filtro.executar(p, q, tipoFiltro, nomeArquivoImagem);
        jLInfo.setText("Estatísticas no console da aplicação.");
    }

    //Seleciona uma imagem a ser processada
    void jBAbrirImagem_actionPerformed(ActionEvent e) {
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("Imagens", "jpg", "JPG");
        JFileChooser escolha = new JFileChooser();

        escolha.setFileSelectionMode(JFileChooser.FILES_ONLY);
        escolha.setAcceptAllFileFilterUsed(false);
        escolha.addChoosableFileFilter(filtro);

        if (escolha.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            nomeArquivoImagem = escolha.getSelectedFile().getAbsolutePath();
        }     
    }

    //Sai do programa
    void jBFechar_actionPerformed(ActionEvent e) {
        System.exit(0);
    }
}
