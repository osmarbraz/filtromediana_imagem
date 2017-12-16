
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class FiltroMediana {

    //Executa o processamento da imagem com o filtro selecionado
    public void executar(int p, int q, int tipoFiltro, String nomeArquivoImagem) {
        // Nomes default dos arquivos quando não for selecionado imagem
        String nomeArquivoEntrada = "ImagemCormenRuido.jpg";
        String nomeArquivoSaida = "ImagemCormenProcessada.jpg";

        //Foi especificado um arquivo diferente do padrão
        if (nomeArquivoImagem.equalsIgnoreCase("") == false) {
            nomeArquivoEntrada = nomeArquivoImagem;
            //Inclui a palavra "Processada" no final do nome do arquivoOriginal
            StringBuilder stringBuilder = new StringBuilder(nomeArquivoImagem);
            stringBuilder.insert(nomeArquivoImagem.length() - 4, "Processada");
            nomeArquivoSaida = stringBuilder.toString();
        }

        //Carrega a imagem de entrada
        BufferedImage imagemEntrada = CarregaImagem(nomeArquivoEntrada);

        // Mostra as estatisticas
        System.out.println("-----------------------------------------------");
        System.out.println("Estatísticas da execução do Filtro da Mediana");
        System.out.println("Nome da Imagem: " + nomeArquivoImagem + " Tipo da Imagem: " + imagemEntrada.getType());

        //Carrega e processa imagem original e mostra imagem processada
        BufferedImage imagemSaida = ProcessaImagem(imagemEntrada, p, q, tipoFiltro);

        //Salva a imagem processada
        SalvaImagem(imagemSaida, nomeArquivoSaida);

        //Mostra a imagem original
        FrmImagem janela1 = new FrmImagem();
        janela1.exibir(nomeArquivoEntrada);

        //Mostra a imagem processada
        FrmImagem janela2 = new FrmImagem();
        janela2.exibir(nomeArquivoSaida);
    }

    // Método para carregar uma imagem do disco para um objeto da classe BufferedImage
    public static BufferedImage CarregaImagem(String nomeImagem) {
        // Associa objeto BufferedImage com <arquivo_imagem>
        BufferedImage imagemEntrada = null;
        // Carrega imagem
        File arquivo = new File(nomeImagem);
        try {
            imagemEntrada = ImageIO.read(arquivo);
        } catch (IOException e) {
            System.out.println("Imagem '" + nomeImagem + "' não existe: "+e);
            System.exit(0);
        }
        return imagemEntrada;
    }

    // Método para Salvar uma imagem no formato JPEG
    public static void SalvaImagem(BufferedImage destino, String nomeImagem) {
        try {
            ImageIO.write(destino, "jpg", new File(nomeImagem));
        } catch (IOException e) {
            System.out.println("Problema gravando arquivo: "+e);
            System.exit(0);
        }
    }

    // Mediana heapsort
    private static void maxHeapify(int A[], int n, int i) {
        int maior = 0;
        int e = 2 * i;
        int d = 2 * i + 1;
        if ((e < n) && (A[e] > A[i])) {
            maior = e;
        } else {
            maior = i;
        }
        if ((d < n) && (A[d] > A[maior])) {
            maior = d;
        }
        if (maior != i) {
            troca(A, i, maior);
            maxHeapify(A, n, maior);
        }
    }

    private static void maxHeap(int A[], int n) {
        int x;
        if ((n % 2) == 0) {
            x = n / 2;
        } else {
            x = (n - 1) / 2;
        }
        for (int i = x; i >= 0; i--) {
            maxHeapify(A, n, i);
        }
    }

    private static void heapsort(int A[], int n) {
        maxHeap(A, n);
        int m = n;
        for (int i = n - 1; i >= 0; i--) {
            troca(A, 0, i);
            m = m - 1;
            maxHeapify(A, m, 0);
        }
    }

    private static int medianaNLogN(int A[], int p, int r) {
        int n = r;
        int m = n / 2;
        heapsort(A, r); //Ordena todo o vetor para encontrar a mediana
        if (n % 2 == 1) {
            return (p + m);
        } else {
            return ((p + m) + (p + m - 1)) / 2;
        }
    }

    // Mediana seleciona aleatório	
    public static int aleatorio(int inicio, int fim) {
        return (int) Math.ceil(Math.random() * (fim - inicio + 1)) - 1 + inicio;
    }

    private static int particione(int A[], int p, int r) {
        int pivot = A[r];
        int i = p - 1;
        for (int j = p; j <= r - 1; j++) {
            if (A[j] <= pivot) {
                i = i + 1;
                troca(A, i, j);
            }
        }
        troca(A, i + 1, r);
        return i + 1;
    }

    private static int particionaAleatorio(int A[], int p, int r) {
        int j = aleatorio(p, r);
        troca(A, j, r);
        return particione(A, p, r);
    }

    private static int selecionaAleatorio(int A[], int p, int r, int i) {
        if (p == r) {
            return A[p];
        }
        int q = particionaAleatorio(A, p, r);
        int k = q - p + 1;
        if (i == k) {
            return A[q];
        } else {
            if (i < k) {
                return selecionaAleatorio(A, p, q - 1, i);
            } else {
                return selecionaAleatorio(A, q + 1, r, i - k);
            }
        }
    }

    private static int medianaN(int A[], int p, int r) {
        int n = r;
        int m = n / 2;
        selecionaAleatorio(A, p, n - 1, m); //Particiona todo o vetor para encontrar a mediana
        if (n % 2 == 1) {
            return (p + m);
        } else {
            //Particiona todo o vetor para encontrar a mediana
            return ((p + m) + (p + m - 1)) / 2;
        }
    }

    //Realiza a troca de posição de elementos
    private static void troca(int A[], int i, int j) {
        int aux;
        aux = A[i];
        A[i] = A[j];
        A[j] = aux;
    }

    // Método para Processar uma Imagem
    public static BufferedImage ProcessaImagem(BufferedImage imagemEntrada, int p, int q, int tipoFiltro) {
        //Inicia o cronômetro
        Cronometro relogio = new Cronometro();
        relogio.inicio();

        // Cria imagem de saida com mesmo tamanho e tipo da imagem de entrada
        BufferedImage imagemSaida = new BufferedImage(imagemEntrada.getWidth(), imagemEntrada.getHeight(), imagemEntrada.getType());

        // Recupera matriz das imagens de entrada e saida
        Raster raster = imagemEntrada.getRaster(); // declara e instancia objeto raster soh para leitura
        WritableRaster wraster = imagemSaida.getRaster(); // declara e instancia objeto raster para escrita		

        int larguraImagem = imagemEntrada.getWidth() - 1;
        int alturaImagem = imagemEntrada.getHeight() - 1;

        // Processa valores da imagem de entrada e armazena na imagem de saida
        for (int i = (p / 2); i < (alturaImagem - (p / 2)); i++) {
            for (int j = (q / 2); j < (larguraImagem - (q / 2)); j++) {
                for (int canal = 0; canal <= 2; canal++) {
                    //Conta os elementos inseridos no vetor
                    int x = 0;
                    //Vetor base para achar a mediana
                    int V[] = new int[p * q];
                    //Recupera os elementos para o quadro
                    for (int k = i - (p / 2); k <= i + (p / 2); k++) {
                        //Calcula o inicio do intervalo
                        int inicio = j - (q / 2);
                        //Calcula o fim do intervalo
                        int fim = j + (q / 2) + 1;
                        for (int elemento = inicio; elemento < fim; elemento++) {
                            //Recupera o 
                            V[x] = raster.getSample(elemento, k, canal);
                            x = x + 1;
                        }
                    }
                    int t = 0;
                    //Seleciona o tipo do filtro da mediana
                    if (tipoFiltro == 0) {
                        t = medianaNLogN(V, 0, V.length);
                    } else {
                        t = medianaN(V, 0, V.length);
                    }
                    int mediana = V[t];

                    //Salva a mediana na imagem de saida
                    wraster.setSample(j, i, canal, mediana);
                }
            }
        }
        relogio.parada();
        System.out.println("Tamanho da Imagem: Colunas " + imagemEntrada.getWidth() + " Linhas " + imagemEntrada.getHeight());
        System.out.println("Quadro pxq: " + p + " x " + q);

        String filtro = "";
        if (tipoFiltro == 0) {
            filtro = "Versão 1(nlogn)";
        } else {
            filtro = "Versão 2(n)";
        }
        System.out.println("Processada com filtro de mediana(" + filtro + ") em " + relogio.tempoGasto() + " milisegundos");

        return imagemSaida;
    }
}
