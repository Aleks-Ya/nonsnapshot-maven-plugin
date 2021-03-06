package at.nonblocking.maven.nonsnapshot.version;

import at.nonblocking.maven.nonsnapshot.version.VersionParser;
import org.junit.Test;

import static at.nonblocking.maven.nonsnapshot.version.VersionFormatter.formatWithBranch;
import static at.nonblocking.maven.nonsnapshot.version.VersionFormatter.formatWithoutBranch;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * @author Yablokov Aleksey
 */
public class VersionFormatterTest {
    private static final VersionParser.Version noBranch = new VersionParser.Version(1, 2, 3, null, null);
    private static final VersionParser.Version easyBranch = new VersionParser.Version(1, 2, 3, "master", 4);
    private static final VersionParser.Version hardBranch = new VersionParser.Version(1, 2, 3, "pks-1234-bnu-integration-5", 4);

    @Test
    public void withBranch() {
        assertThat(formatWithBranch(noBranch), equalTo("1.2.3"));
        assertThat(formatWithBranch(easyBranch), equalTo("1.2.3-master-4"));
        assertThat(formatWithBranch(hardBranch), equalTo("1.2.3-pks-1234-bnu-integration-5-4"));
    }

    @Test
    public void withoutBranch() {
        assertThat(formatWithoutBranch(noBranch), equalTo("1.2.3"));
        assertThat(formatWithoutBranch(easyBranch), equalTo("1.2.3"));
        assertThat(formatWithoutBranch(hardBranch), equalTo("1.2.3"));
    }

}