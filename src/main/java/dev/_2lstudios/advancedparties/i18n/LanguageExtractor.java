package dev._2lstudios.advancedparties.i18n;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import dev._2lstudios.advancedparties.utils.FileUtils;

public class LanguageExtractor {
    private static File getJarFile() {
        return new File(LanguageExtractor.class.getProtectionDomain().getCodeSource().getLocation().getPath());
    }

    private static boolean shouldExtractEntry(final JarEntry entry) {
        return entry.getName().startsWith("lang/") && entry.getName().endsWith(".yml");
    }

    public static void extractAll(final File target) {
        if (!target.exists()) {
            target.mkdirs();
        }

        try {
            JarFile jar = new JarFile(LanguageExtractor.getJarFile());
            for (Enumeration<JarEntry> enums = jar.entries(); enums.hasMoreElements();) {
                final JarEntry entry = enums.nextElement();
                if (shouldExtractEntry(entry)) {
                    FileUtils.extractFile(new File(target, new File(entry.getName()).getName()), entry.getName());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}