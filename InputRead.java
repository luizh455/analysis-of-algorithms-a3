import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

class Artigo {
    String titulo;
    String resumo;
    String tags;

    public Artigo(String titulo, String resumo, String tags) {
        this.titulo = titulo;
        this.resumo = resumo;
        this.tags = tags;
    }
}

public class InputRead {

    static File absPath = new File(InputRead.class.getProtectionDomain().getCodeSource().getLocation().getPath());
    static File resumePath = new File(
            InputRead.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "resumos");

    public static void main(String[] args) throws IOException {
        for (String file : listFiles()) {
            if (file.toString().contains(".txt")) {
                String path = resumePath.toString() + "/" + file;
                try {
                    readFiles(path);
                    print("lido:" + file);
                } catch (Exception e) {
                    print("Arquivo não lido:" + file);

                    // TODO: handle exception
                }
            }

        }
    }

    public static String[] listFiles() {
        String[] listaDeArquivos = resumePath.list();
        System.out.println(resumePath.toString());
        System.out.println("List of files and directories in the specified directory:");
        for (int i = 0; i < listaDeArquivos.length; i++) {
            System.out.println(listaDeArquivos[i]);
        }
        return listaDeArquivos;
    }

    public static Artigo readFiles(String fileName) throws IOException {
        Path path = Paths.get(fileName);
        List<String> allLines = Files.readAllLines(path, StandardCharsets.UTF_8);
        for (String line : allLines) {
            System.out.println(line);
        }

        Artigo artigo = new Artigo(allLines.get(0), allLines.get(1), allLines.get(2));

        return artigo;
    }

    public static void print(String msg) {
        System.out.println(msg);
    }
}
