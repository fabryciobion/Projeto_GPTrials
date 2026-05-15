import java.io.*;
import java.net.URISyntaxException;
import java.util.*;

public class QuestionLoader {

    public static List<Question> carregar(String nomeArquivo) {
        List<Question> lista = new ArrayList<>();
        File arquivo = resolverCaminho(nomeArquivo);

        if (arquivo == null || !arquivo.exists()) {
            System.err.println("[QuestionLoader] Arquivo nao encontrado: " + nomeArquivo);
            System.err.println("  Usando perguntas de fallback.");
            return carregarFallback();
        }

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(arquivo), "UTF-8"))) {
            String linha;
            boolean primeiraLinha = true;

            while ((linha = br.readLine()) != null) {
                linha = linha.trim();
                if (linha.isEmpty()) continue;

                if (primeiraLinha) {
                    primeiraLinha = false;
                    continue;
                }

                String[] cols = linha.split(",", -1);
                if (cols.length < 9) continue;

                String tipo     = cols[0].trim().toUpperCase();
                String tema     = cols[1].trim();
                String enunc    = cols[2].trim();
                String alt1     = cols[3].trim();
                String alt2     = cols[4].trim();
                String alt3     = cols[5].trim();
                String alt4     = cols[6].trim();
                String resposta = cols[7].trim();
                int    pontos;
                try { pontos = Integer.parseInt(cols[8].trim()); }
                catch (NumberFormatException e) { pontos = 10; }

                switch (tipo) {
                    case "MULTIPLA" -> {
                        String[] alts = { alt1, alt2, alt3, alt4 };
                        int idx;
                        try { idx = Integer.parseInt(resposta); }
                        catch (NumberFormatException e) { idx = 1; }
                        lista.add(new QuestionMultiplaEscolha(enunc, alts, idx, pontos, tema));
                    }
                    case "VERDADEIRO_FALSO" -> {
                        lista.add(new QuestionVerdadeiroFalso(enunc, resposta, pontos, tema));
                    }
                    case "COMPLETAR" -> {
                        lista.add(new QuestionFillBlank(enunc, resposta, pontos, tema));
                    }
                    default -> System.err.println("[QuestionLoader] Tipo desconhecido: " + tipo);
                }
            }
        } catch (IOException e) {
            System.err.println("[QuestionLoader] Erro ao ler CSV: " + e.getMessage());
            return carregarFallback();
        }

        if (lista.isEmpty()) {
            System.err.println("[QuestionLoader] CSV vazio. Usando fallback.");
            return carregarFallback();
        }

        System.out.println("[Sistema] " + lista.size() + " perguntas carregadas.");
        return lista;
    }

    private static File resolverCaminho(String nomeArquivo) {
        File direto = new File(nomeArquivo);
        if (direto.exists()) return direto;

        try {
            File jarDir = new File(
                QuestionLoader.class.getProtectionDomain()
                    .getCodeSource().getLocation().toURI()).getParentFile();
            File relativo = new File(jarDir, nomeArquivo);
            if (relativo.exists()) return relativo;
        } catch (URISyntaxException | NullPointerException ignored) {
        }

        String userDir = System.getProperty("user.dir");
        if (userDir != null) {
            File fromUserDir = new File(userDir, nomeArquivo);
            if (fromUserDir.exists()) return fromUserDir;
        }

        return null;
    }

    public static Map<String, List<Question>> agruparPorTema(List<Question> perguntas) {
        Map<String, List<Question>> mapa = new LinkedHashMap<>();
        for (Question q : perguntas) {
            mapa.computeIfAbsent(q.getTema(), k -> new ArrayList<>()).add(q);
        }
        return mapa;
    }

    private static List<Question> carregarFallback() {
        List<Question> lista = new ArrayList<>();
        lista.add(new QuestionMultiplaEscolha(
            "DEU ERRADO?",
            new String[]{"Sim", "NAO", "TALVEZ", "SEILA"}, 1, 10, "Ciencias"));
        lista.add(new QuestionVerdadeiroFalso(
            "DEU ERRADO MESMO?", "Verdadeiro", 10, "Ciencias"));
        lista.add(new QuestionFillBlank(
            "Complete: DEU ERRADO? ", "sim", 20, "Portugues"));
        return lista;
    }
}