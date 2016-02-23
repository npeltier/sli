package org.apache.sling.sli.subcommands;

import junit.framework.Assert;
import org.apache.sling.sli.Config;
import org.apache.sling.sli.SliCommand;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * @todo add license & javadoc :-)
 */
public class ChangeDirTest {

    String backupPath;

    @Before
    public void setup() throws IOException {
        Config config = new Config();
        backupPath = config.getCurrentPath();
    }

    @After
    public void tearDown() throws IOException {
        Config config = new Config();
        config.setCurrentPath(backupPath);
    }

    @Test
    public void testComputeNewDirectory() throws Exception {
        testCd("relative cd", "/blah", "foo/bar", "/blah/foo/bar");
        testCd("parent cd", "/blah/blah", "..", "/blah");
        testCd("absolute cd", "/blah", "/foo/bar", "/foo/bar");
        testCd("absolute and relative", "/parent/child", "../foo/bar", "/parent/foo/bar");
        testCd("relative with .", "/parent/child", "./foo/bar", "/parent/child/foo/bar");
    }

    protected void testCd(String message, String currentPath, String arg, String expected) throws IOException {
        ChangeDir changeDir = new ChangeDir();
        Config config = new Config();
        config.setCurrentPath(currentPath);
        changeDir.setTarget(Arrays.asList(new String[]{arg}));
        String changedPath = changeDir.computeNewDirectory(config);
        Assert.assertEquals(message, expected, changedPath);
    }
}