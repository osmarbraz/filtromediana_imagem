
import java.io.IOException;
import java.io.File;
import javax.imageio.ImageIO;

import javax.swing.JLabel;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.ImageIcon;

import java.awt.image.BufferedImage;
import java.awt.GridLayout;

public class FrmImagem extends JFrame {

    private final JLabel jLimagemLabel;
    private static int posicao = 25;

    public FrmImagem() {
        getContentPane().setLayout(new GridLayout());
        jLimagemLabel = new JLabel();
        getContentPane().add(new JScrollPane(jLimagemLabel));
        //Posição da janela
        setLocation(posicao, posicao);
        posicao = posicao + 25;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    //Metodo para exibir a imagem
    public void exibir(String nomeArquivoImagem) {
        setTitle("Nome Arquivo: =>" + nomeArquivoImagem);
        //carrega a imagem					
        BufferedImage imagem = CarregaImagem(nomeArquivoImagem);
        //Define o tamanho da janela
        setSize(imagem.getWidth() + 20, imagem.getHeight() + 40);
        ImageIcon icon = new ImageIcon(imagem);
        jLimagemLabel.setIcon(icon);
        setVisible(true);
    }

    // Método para carregar uma imagem do disco para um objeto da classe BufferedImage
    public BufferedImage CarregaImagem(String nomeArquivoImagem) {
        // Associa objeto BufferedImage com <arquivo_imagem>
        BufferedImage imagemEntrada = null;
        // Carrega imagem
        File arquivo = new File(nomeArquivoImagem);
        try {
            imagemEntrada = ImageIO.read(arquivo);
        } catch (IOException e) {
            System.out.println("Imagem '" + nomeArquivoImagem + "' não existe.");
        }
        return imagemEntrada;
    }
}
