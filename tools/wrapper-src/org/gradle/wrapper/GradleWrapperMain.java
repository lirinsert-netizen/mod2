package org.gradle.wrapper;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HexFormat;
import java.util.List;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Tiny self-contained Gradle bootstrap used because the standard binary wrapper JAR
 * could not be downloaded while this project was generated. It reads the same
 * gradle-wrapper.properties file, downloads the configured Gradle distribution,
 * caches it under GRADLE_USER_HOME, and executes Gradle with the original arguments.
 */
public final class GradleWrapperMain {
    public static void main(String[] args) throws Exception {
        Path jar = Path.of(GradleWrapperMain.class.getProtectionDomain().getCodeSource().getLocation().toURI());
        Path projectDir = jar.getParent().getParent().getParent();
        Path propertiesPath = projectDir.resolve("gradle/wrapper/gradle-wrapper.properties");

        Properties props = new Properties();
        try (InputStream in = Files.newInputStream(propertiesPath)) {
            props.load(in);
        }

        String distributionUrl = props.getProperty("distributionUrl").replace("\\:", ":");
        String gradleUserHome = System.getenv().getOrDefault("GRADLE_USER_HOME",
                Path.of(System.getProperty("user.home"), ".gradle").toString());
        String hash = sha256(distributionUrl).substring(0, 16);
        Path cacheDir = Path.of(gradleUserHome, "wrapper", "dists", "zombiehorde-bootstrap", hash);
        Path marker = cacheDir.resolve(".ready");

        if (!Files.exists(marker)) {
            Files.createDirectories(cacheDir);
            Path zip = cacheDir.resolve("gradle.zip");
            download(URI.create(distributionUrl), zip);
            unzip(zip, cacheDir);
            Files.deleteIfExists(zip);
            Files.writeString(marker, "ok");
        }

        Path gradleHome = findGradleHome(cacheDir);
        boolean windows = System.getProperty("os.name").toLowerCase().contains("win");
        Path executable = gradleHome.resolve(windows ? "bin/gradle.bat" : "bin/gradle");
        if (!windows) executable.toFile().setExecutable(true);

        List<String> command = new ArrayList<>();
        if (windows) {
            command.add("cmd.exe");
            command.add("/c");
        }
        command.add(executable.toAbsolutePath().toString());
        command.addAll(List.of(args));

        Process process = new ProcessBuilder(command)
                .directory(projectDir.toFile())
                .inheritIO()
                .start();
        System.exit(process.waitFor());
    }

    private static void download(URI uri, Path target) throws IOException, InterruptedException {
        System.out.println("Downloading Gradle from " + uri + " ...");
        HttpClient client = HttpClient.newBuilder().followRedirects(HttpClient.Redirect.NORMAL).build();
        HttpRequest request = HttpRequest.newBuilder(uri).GET().build();
        HttpResponse<Path> response = client.send(request, HttpResponse.BodyHandlers.ofFile(target));
        if (response.statusCode() < 200 || response.statusCode() >= 300) {
            throw new IOException("Gradle download failed with HTTP " + response.statusCode());
        }
    }

    private static void unzip(Path zip, Path targetDir) throws IOException {
        try (ZipInputStream in = new ZipInputStream(Files.newInputStream(zip))) {
            ZipEntry entry;
            while ((entry = in.getNextEntry()) != null) {
                Path out = targetDir.resolve(entry.getName()).normalize();
                if (!out.startsWith(targetDir)) throw new IOException("Unsafe ZIP entry: " + entry.getName());
                if (entry.isDirectory()) {
                    Files.createDirectories(out);
                } else {
                    Files.createDirectories(out.getParent());
                    Files.copy(in, out, StandardCopyOption.REPLACE_EXISTING);
                }
            }
        }
    }

    private static Path findGradleHome(Path cacheDir) throws IOException {
        try (var stream = Files.list(cacheDir)) {
            return stream
                    .filter(Files::isDirectory)
                    .filter(path -> Files.exists(path.resolve("bin/gradle")) || Files.exists(path.resolve("bin/gradle.bat")))
                    .findFirst()
                    .orElseThrow(() -> new IOException("Could not locate extracted Gradle home in " + cacheDir));
        }
    }

    private static String sha256(String value) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        return HexFormat.of().formatHex(digest.digest(value.getBytes(java.nio.charset.StandardCharsets.UTF_8)));
    }
}
