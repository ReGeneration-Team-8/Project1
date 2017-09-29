package utilities;

import java.util.HashSet;
import java.util.Set;

public class NameGeneration {               //generates random strings.
    final String lexicon = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    final java.util.Random rand = new java.util.Random();
    final Set<String> identifiers = new HashSet<String>();

    public String randomIdentifier() {
        StringBuilder builder = new StringBuilder();
        while(builder.toString().length() == 0) {
            int length = rand.nextInt(5)+5;
            for(int i = 0; i < length; i++) {
                builder.append(lexicon.charAt(rand.nextInt(lexicon.length())));
            }
            if(identifiers.contains(builder.toString())) {
                builder = new StringBuilder();
            }
        }
        return builder.toString();
    }
}
