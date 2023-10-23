import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

class Artigo {
    String titulo;
    String resumo;
    String tags;
    String[] frases;

    public Artigo(String titulo, String resumo, String tags) {
        this.titulo = titulo;
        this.resumo = resumo;
        this.tags = tags;
        this.frases = resumo.split("[\\.]");
    }
}

public class InputRead {

    //ATENCAO: configuração de path de arquivos
    static File absPath = new File(InputRead.class.getProtectionDomain().getCodeSource().getLocation().getPath());
    static File resumePath = new File(
            InputRead.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "resumos");

    //caso o programa não pegue o path correto automaticamente (é para funcionar no windows), insira os paths nas variaveis a baixo
    public static void setupPath() {
        if (resumePath.list() == null) {
            //path do repositorio
            absPath = new File("/Users/llage/dev/GitHub/una/analysis-of-algorithms-a3");
            //path dos resumos
            resumePath = new File("/Users/llage/dev/GitHub/una/analysis-of-algorithms-a3/resumos");
        }
    }

    public static void main(String[] args) throws IOException {
        List<Artigo> artigos = new ArrayList<>();
        List<Artigo> artigosNormalizados = new ArrayList<>();
        setupPath();

        for (String file : listFiles()) {
            if (file.toString().contains(".txt")) {
                String path = resumePath.toString() + "/" + file;
                try {
                    Artigo artigoAtual = readFiles(path);
                    artigos.add(artigoAtual);
                    print("lido:" + file);
                } catch (Exception e) {
                    print("Arquivo não lido:" + file);
                    print(e.getLocalizedMessage());
                }
            }
        }

        for (Artigo artigo : artigos) {
            Artigo normalized = normalizeArtigo(artigo);
            artigosNormalizados.add(normalized);
        }
        
    }

    public static String[] listFiles() {
        String[] listaDeArquivos = resumePath.list();
        print("Path Absoluto: " + absPath.toString());
        print("Path dos resumos: " + resumePath.toString());
        print("Lista de arquivos no repositorio de resumos:");
        for (int i = 0; i < listaDeArquivos.length; i++) {
            print(listaDeArquivos[i]);
        }
        return listaDeArquivos;
    }

    public static Artigo readFiles(String fileName) throws IOException {
        Charset charset = Charset.forName("Cp1252");
        Path path = Paths.get(fileName);
        List<String> allLines = Files.readAllLines(path, charset);

        //imprime todos os textos
        // for (String line : allLines) {
            //System.out.println(line);
        //}

        Artigo artigo = new Artigo(allLines.get(0), allLines.get(1), allLines.get(2));

        return artigo;
    }

    public static Artigo normalizeArtigo(Artigo artigo) {

        Artigo artigoAux = artigo;
        artigoAux.resumo = artigoAux.resumo.toLowerCase();
        artigoAux.resumo = Normalizer.normalize(artigoAux.resumo, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", ""); // faz o replace de todos caracteres especiais como acentos
        artigoAux.resumo = artigoAux.resumo.replaceAll("[^\\w\\s.]", ""); // remove todas as pontuações

        //imprime frases
        // for(String frase : artigo.frases) {
        //     print(frase);
        // }

        return new Artigo(artigo.titulo, artigo.resumo, artigo.tags);
    }

    public static void print(String msg) {
        System.out.println(msg);
    }
}
