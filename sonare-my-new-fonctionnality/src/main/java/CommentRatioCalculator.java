package com.mycompany.sonarqube.plugins;

public class CommentRatioCalculator {

    /**
     * Calcule le ratio de lignes commentées.
     *
     * @param totalLines     le nombre total de lignes dans le fichier
     * @param commentedLines le nombre de lignes commentées
     * @return le ratio de lignes commentées (entre 0.0 et 1.0)
     */
    public static double calculateRatio(int totalLines, int commentedLines) {
        if (totalLines <= 0) {
            return 0.0;
        }
        return (double) commentedLines / totalLines;
    }
}
