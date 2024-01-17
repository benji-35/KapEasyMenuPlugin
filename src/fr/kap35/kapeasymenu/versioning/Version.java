package fr.kap35.kapeasymenu.versioning;

import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;

public class Version {
    public String version;

    public Version(String version) {
        if (version == null || version.chars().filter(ch -> ch == '.').count() != 2)
            version = "0.0.0";
        version = version.trim();
        this.version = version;
    }

    public int getMajors() {
        List<String> data = splitString(version, ".");
        return Integer.parseInt(data.get(0));
    }
    public int getMinor() {
        List<String> data = splitString(version, ".");
        return Integer.parseInt(data.get(1));
    }
    public int getFix() {
        List<String> data = splitString(version, ".");
        return Integer.parseInt(data.get(2));
    }
    public boolean isBetween(Version v1, Version v2) {
        if (!v1.isBefore(this))
            return false;
        return v2.isAfter(this);
    }
    public boolean isAfter(Version v1) {
        if (getMajors() > v1.getMajors())
            return true;
        if (getMinor() > v1.getMinor())
            return true;
        return getFix() > v1.getFix();
    }
    public boolean isBefore(Version v1) {
        if (getMajors() < v1.getMajors())
            return true;
        if (getMinor() < v1.getMinor())
            return true;
        return getFix() < v1.getFix();
    }
    public boolean isEquals(Version v1) {
        return !isBefore(v1) && !isAfter(v1);
    }

    private List<String> splitString(String str, String splitter) {
        List<String> result = new ArrayList<>();
        while (!str.isEmpty()) {
            int index = str.indexOf(splitter);
            if (index == -1) {
                result.add(str);
                break;
            }
            String between = str.substring(0, index);
            result.add(between);
            str = str.substring(index + splitter.length());
        }
        return result;
    }
}
