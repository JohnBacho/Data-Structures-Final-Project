public class testtttting {
    public static int calculateDistance(String word1, String word2) {
        int len1 = word1.length();
        int len2 = word2.length();

        if (len1 == 0) {
            return len2;
        }

        if (len2 == 0) {
            return len1;
        }

        if (word1.charAt(0) == word2.charAt(0)) {
            return calculateDistance(word1.substring(1), word2.substring(1));
        }

        int insertion = calculateDistance(word1, word2.substring(1)) + 1;
        int deletion = calculateDistance(word1.substring(1), word2) + 1;
        int substitution = calculateDistance(word1.substring(1), word2.substring(1)) + 1;

        return Math.min(Math.min(insertion, substitution), deletion);

    }
}