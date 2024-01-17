package fr.kap35.kapeasymenu.versioning;

public class Version {
    public String version;

    public Version(String version) {
        if (version.chars().filter(ch -> ch == '.').count() != 2)
            version = "0.0.0";
        this.version = version;
    }

    public int getMajors() {
        String[] data = version.split(".");
        return Integer.parseInt(data[0]);
    }
    public int getMinor() {
        String[] data = version.split(".");
        return Integer.parseInt(data[1]);
    }
    public int getFix() {
        String[] data = version.split(".");
        return Integer.parseInt(data[2]);
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
}
