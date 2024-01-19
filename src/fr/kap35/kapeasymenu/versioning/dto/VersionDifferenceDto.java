package fr.kap35.kapeasymenu.versioning.dto;

import fr.kap35.kapeasymenu.versioning.Version;

public class VersionDifferenceDto {
    public String description;
    public Version fromVersion;
    public Version sinceVersion;
    public Version toVersion;

    public String getVersionToDebug() {
        if (fromVersion != null) {
            return fromVersion.version;
        }
        return  sinceVersion.version + " - " + toVersion.version;
    }
}
