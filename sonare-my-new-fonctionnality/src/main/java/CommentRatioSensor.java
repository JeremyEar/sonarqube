package com.mycompany.sonarqube.plugins;

import org.sonar.api.batch.sensor.Sensor;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.SensorDescriptor;
import org.sonar.api.measures.Measure;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class CommentRatioSensor implements Sensor {

    @Override
    public void describe(SensorDescriptor descriptor) {
        descriptor.name("Comment Ratio Sensor");
    }

    @Override
    public void execute(SensorContext context) {
        // Pour simplifier, on itère sur chaque fichier source récupéré depuis le contexte
        context.fileSystem().inputFiles(context.fileSystem().predicates().hasLanguage("java")).forEach(inputFile -> {
            try {
                Path filePath = inputFile.file().toPath();
                List<String> lines = Files.readAllLines(filePath);

                int totalLines = lines.size();
                int commentedLines = (int) lines.stream().filter(this::isCommentLine).count();
                double ratio = totalLines > 0 ? (double) commentedLines / totalLines : 0.0;

                // Sauvegarder la métrique dans le contexte d'analyse
                context.<Double>newMeasure()
                        .forMetric(Metrics.COMMENT_RATIO) // Supposons que Metrics.COMMENT_RATIO est défini dans nos métriques personnalisées
                        .on(inputFile)
                        .withValue(ratio)
                        .save();

            } catch (Exception e) {
                // Gestion de l'exception pour ce fichier
                e.printStackTrace();
            }
        });
    }

    // Méthode simple pour déterminer si une ligne est un commentaire
    private boolean isCommentLine(String line) {
        String trimmed = line.trim();
        return trimmed.startsWith("//") || trimmed.startsWith("/*") || trimmed.startsWith("*");
    }
}
