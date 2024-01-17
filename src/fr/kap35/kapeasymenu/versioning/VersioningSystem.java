package fr.kap35.kapeasymenu.versioning;

import fr.kap35.kapeasymenu.KapEasyMenu;
import fr.kap35.kapeasymenu.versioning.dto.VersionDifferenceDto;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class VersioningSystem implements IVersioningService {
    private static String URL_VERSION = "https://benji-35.github.io/KapEasyMenuPlugin/resources/version";
    VersionReader local;
    VersionReader latest;

    public VersioningSystem() {
        URL url = KapEasyMenu.class.getClassLoader().getResource("version");
        local = new VersionReader(url);
        latest = new VersionReader(URL_VERSION);
    }

    @Override
    public Version getCurrentVersion() {
        return local.getCurrentVersion();
    }
    @Override
    public boolean isLatestVersion() {
        return getLatestVersion().isEquals(getCurrentVersion());
    }
    @Override
    public Version getLatestVersion() {
        return latest.getCurrentVersion();
    }
    @Override
    public List<VersionDifferenceDto> getDangerous() {
        return getTargetedModifications(latest.getDangerous());
    }
    @Override
    public List<VersionDifferenceDto> getMinors() {
        return getTargetedModifications(latest.getMinors());
    }
    @Override
    public List<VersionDifferenceDto> getMajors() {
        return getTargetedModifications(latest.getMajors());
    }

    private List<VersionDifferenceDto> getTargetedModifications(List<VersionDifferenceDto> list) {
        List<VersionDifferenceDto> result = new ArrayList<>();

        for(VersionDifferenceDto dto : list) {
            if (dto.fromVersion != null) {
                if (dto.fromVersion.isEquals(local.getCurrentVersion())) {
                    result.add(dto);
                }
                continue;
            }
            if (dto.sinceVersion == null)
                continue;
            if (dto.toVersion == null)
                dto.toVersion = latest.getCurrentVersion();
            if (getCurrentVersion().isBetween(dto.sinceVersion, dto.toVersion))
                result.add(dto);
        }
        return result;
    }

    public class VersionReader {

        private final List<String> lines;

        public VersionReader(String url) {
            List<String> lines;
            try {
                lines = getFile(url);
            } catch (Exception e) {
                lines = new ArrayList<>();
            }
            this.lines = lines;
        }
        public VersionReader(URL url) {
            List<String> lines;
            try {
                lines = getFile(url);
            } catch (Exception e) {
                lines = new ArrayList<>();
            }
            this.lines = lines;
        }

        public Version getCurrentVersion() {
            boolean inVersion = false;
            if (lines == null)
                return null;
            for (String line : lines) {
                if (!inVersion) {
                    if (line.startsWith("version")) {
                        inVersion = true;
                    }
                    continue;
                }
                return new Version(line.substring(1));
            }
            return null;
        }
        public List<VersionDifferenceDto> getDangerous() {
            return getDtoDifferences("dangerous");
        }
        public List<VersionDifferenceDto> getMajors() {
            return getDtoDifferences("majors");
        }
        public List<VersionDifferenceDto> getMinors() {
            return getDtoDifferences("minors");
        }

        private List<VersionDifferenceDto> getDtoDifferences(String key) {
            List<VersionDifferenceDto> result = new ArrayList<>();
            boolean inKey = false;
            if (lines == null)
                return new ArrayList<>();
            for (String line : lines) {
                if (!inKey) {
                    if (line.startsWith(key)) {
                        inKey = true;
                    }
                    continue;
                }
                if (!line.startsWith(" "))
                    continue;
                String[] parts = line.split("&", 4);
                if (parts.length != 4)
                    continue;
                VersionDifferenceDto dto = new VersionDifferenceDto();
                dto.description = parts[3];
                if (parts[0].isEmpty()) {
                    dto.fromVersion = null;
                } else {
                    dto.fromVersion = new Version(parts[0]);
                }
                if (parts[1].isEmpty()) {
                    dto.sinceVersion = null;
                } else {
                    dto.sinceVersion = new Version(parts[1]);
                }
                if (parts[2].isEmpty()) {
                    dto.toVersion = null;
                } else {
                    dto.toVersion = new Version(parts[2]);
                }
                result.add(dto);
            }
            return result;
        }

        private List<String> getFile(String url) throws IOException {
            URL _url = new URL(url);
            return getFile(_url);
        }

        private List<String> getFile(URL url) throws IOException {
            InputStream stream = url.openStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "utf-8"));
            List<String> lines = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            return lines;
        }

    }
}
