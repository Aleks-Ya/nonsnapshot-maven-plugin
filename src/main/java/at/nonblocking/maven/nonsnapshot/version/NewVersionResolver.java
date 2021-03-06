package at.nonblocking.maven.nonsnapshot.version;

import static at.nonblocking.maven.nonsnapshot.version.VersionIncrementer.incrementBuildVersion;
import static at.nonblocking.maven.nonsnapshot.version.VersionIncrementer.incrementMinorVersion;
import static at.nonblocking.maven.nonsnapshot.version.VersionIncrementer.removeBranchName;

/**
 * Form string with new version of module.
 *
 * @author Yablokov Aleksey
 */
public class NewVersionResolver {
    private final boolean appendBranchNameToVersion;
    private final String replaceSpecialSymbolsInVersionBy;
    private final VersionParser parser;

    public NewVersionResolver(boolean appendBranchNameToVersion, String incrementVersionPattern, String replaceSpecialSymbolsInVersionBy) {
        this.appendBranchNameToVersion = appendBranchNameToVersion;
        this.parser = new VersionParser(incrementVersionPattern);
        this.replaceSpecialSymbolsInVersionBy = replaceSpecialSymbolsInVersionBy;
    }

    public String resolveNewVersion(String currVersion, String branchName) {
        if (currVersion == null) {
            throw new IllegalArgumentException("Current version is null");
        }

        String newVersion;
        VersionParser.Version currV = parser.parse(currVersion);
        if (appendBranchNameToVersion) {
            if (branchName == null || branchName.isEmpty()) {
                throw new IllegalArgumentException("Branch name is null");
            }
            VersionParser.Version newV = incrementBuildVersion(currV, branchName);
            newVersion = VersionFormatter.formatWithBranch(newV);
            newVersion = replaceSpecialSymbols(newVersion);
        } else {
            VersionParser.Version newV = incrementMinorVersion(removeBranchName(currV));
            newVersion = VersionFormatter.formatWithBranch(newV);
        }
        return newVersion;
    }

    private String replaceSpecialSymbols(String newVersion) {
        newVersion = newVersion.replaceAll("/", replaceSpecialSymbolsInVersionBy);
        return newVersion;
    }
}
