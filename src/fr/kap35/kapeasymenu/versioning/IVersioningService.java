package fr.kap35.kapeasymenu.versioning;

import fr.kap35.kapeasymenu.versioning.dto.VersionDifferenceDto;

import java.util.List;

public interface IVersioningService {

    Version getCurrentVersion();
    Version getLatestVersion();
    List<VersionDifferenceDto> getDangerous();
    List<VersionDifferenceDto> getMinors();
    List<VersionDifferenceDto> getMajors();
    boolean isLatestVersion();

}
