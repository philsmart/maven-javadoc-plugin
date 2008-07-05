package org.apache.maven.plugin.javadoc;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.maven.artifact.resolver.ArtifactResolutionResult;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.util.StringUtils;

/**
 * Bundles the Javadoc documentation for test source in a jar.
 * <br/>
 * <b>Note</b>: the <code>aggregate</code> parameter is always set to <code>false</code>.
 *
 * @author <a href="mailto:vincent.siveton@gmail.com">Vincent Siveton</a>
 * @version $Id$
 * @since 2.5
 * @goal test-jar
 * @phase package
 * @requiresDependencyResolution test
 */
public class TestJavadocJar
    extends JavadocJar
{
    // ----------------------------------------------------------------------
    // Javadoc Options (should be inline with Javadoc options defined in TestJavadocReport)
    // ----------------------------------------------------------------------

    /**
     * Specifies the destination directory where javadoc saves the generated HTML files.
     * <br/>
     * See <a href="http://java.sun.com/j2se/1.4.2/docs/tooldocs/windows/javadoc.html#d">d</a>.
     *
     * @parameter default-value="${project.build.directory}/testapidocs"
     * @required
     */
    private File outputDirectory;

    /**
     * Specifies the title to be placed near the top of the overview summary file.
     * <br/>
     * See <a href="http://java.sun.com/j2se/1.4.2/docs/tooldocs/windows/javadoc.html#doctitle">doctitle</a>.
     *
     * @parameter expression="${doctitle}" default-value="${project.name} ${project.version} Test API"
     */
    private String doctitle;

    /**
     * Specifies that javadoc should retrieve the text for the overview documentation from the "source" file
     * specified by path/filename and place it on the Overview page (overview-summary.html).
     * <br/>
     * See <a href="http://java.sun.com/j2se/1.4.2/docs/tooldocs/windows/javadoc.html#overview">overview</a>.
     *
     * @parameter expression="${overview}" default-value="${basedir}/src/test/javadoc/overview.html"
     */
    private File overview;

    /**
     * Specifies the title to be placed in the HTML title tag.
     * <br/>
     * See <a href="http://java.sun.com/j2se/1.4.2/docs/tooldocs/windows/javadoc.html#windowtitle">windowtitle</a>.
     *
     * @parameter expression="${windowtitle}" default-value="${project.name} ${project.version} Test API"
     */
    private String windowtitle;

    // ----------------------------------------------------------------------
    // Mojo Parameters (should be inline with options defined in TestJavadocReport)
    // ----------------------------------------------------------------------

    /**
     * Specifies the test Javadoc ressources directory to be included in the Javadoc (i.e. package.html, images...).
     *
     * @parameter expression="${basedir}/src/test/javadoc"
     */
    private File javadocDirectory;

    // ----------------------------------------------------------------------
    // Protected methods
    // ----------------------------------------------------------------------

    /**
     * @see org.apache.maven.plugin.javadoc.JavadocJar#getClassifier()
     */
    protected String getClassifier()
    {
        return "test-javadoc";
    }

    // Next methods should be inline with methods defined in TestJavadocReport

    /**
     * @see org.apache.maven.plugin.javadoc.AbstractJavadocMojo#getOutputDirectory()
     */
    protected String getOutputDirectory()
    {
        return outputDirectory.getAbsoluteFile().toString();
    }

    /**
     * @see org.apache.maven.plugin.javadoc.AbstractJavadocMojo#getJavadocDirectory()
     */
    protected File getJavadocDirectory()
    {
        return javadocDirectory;
    }

    /**
     * @see org.apache.maven.plugin.javadoc.AbstractJavadocMojo#getDoctitle()
     */
    protected String getDoctitle()
    {
        return doctitle;
    }

    /**
     * @see org.apache.maven.plugin.javadoc.AbstractJavadocMojo#getOverview()
     */
    protected File getOverview()
    {
        return overview;
    }

    /**
     * @see org.apache.maven.plugin.javadoc.AbstractJavadocMojo#getWindowtitle()
     */
    protected String getWindowtitle()
    {
        return windowtitle;
    }

    /**
     * @see org.apache.maven.plugin.javadoc.AbstractJavadocMojo#getProjectBuildOutputDirs(org.apache.maven.project.MavenProject)
     */
    protected List getProjectBuildOutputDirs( MavenProject p )
    {
        List dirs = new ArrayList();
        if ( StringUtils.isNotEmpty( p.getBuild().getOutputDirectory() ) )
        {
            dirs.add( p.getBuild().getOutputDirectory() );
        }
        if ( StringUtils.isNotEmpty( p.getBuild().getTestOutputDirectory() ) )
        {
            dirs.add( p.getBuild().getTestOutputDirectory() );
        }

        return dirs;
    }

    /**
     * @see org.apache.maven.plugin.javadoc.AbstractJavadocMojo#getProjectSourceRoots(org.apache.maven.project.MavenProject)
     */
    protected List getProjectSourceRoots( MavenProject p )
    {
        if ( "pom".equals( p.getPackaging().toLowerCase() ) )
        {
            return Collections.EMPTY_LIST;
        }

        return p.getTestCompileSourceRoots();
    }

    /**
     * @see org.apache.maven.plugin.javadoc.AbstractJavadocMojo#getExecutionProjectSourceRoots(org.apache.maven.project.MavenProject)
     */
    protected List getExecutionProjectSourceRoots( MavenProject p )
    {
        if ( "pom".equals( p.getExecutionProject().getPackaging().toLowerCase() ) )
        {
            return Collections.EMPTY_LIST;
        }

        return p.getExecutionProject().getTestCompileSourceRoots();
    }

    /**
     * @see org.apache.maven.plugin.javadoc.AbstractJavadocMojo#getProjectArtifacts(org.apache.maven.project.MavenProject)
     */
    protected List getProjectArtifacts( MavenProject p )
    {
        return p.getTestArtifacts();
    }

    /**
     * @see org.apache.maven.plugin.javadoc.AbstractJavadocMojo#getCompileArtifacts(org.apache.maven.artifact.resolver.ArtifactResolutionResult)
     */
    protected List getCompileArtifacts( ArtifactResolutionResult result )
    {
        return JavadocUtil.getCompileArtifacts( result.getArtifacts(), true );
    }
}