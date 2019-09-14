# igv
[![Build Status](https://travis-ci.org/igvteam/igv.svg?branch=master)](https://travis-ci.org/igvteam/igv)

Integrative Genomics Viewer - desktop genome visualization tool for Mac, Windows, and Linux.

### Building

These instructions are meant for developers interested in working on the IGV code.  For normal use,
we recommend the pre-built releases available at [http://software.broadinstitute.org/software/igv/download](http://software.broadinstitute.org/software/igv/download).

Builds are executed from the IGV project directory.  Files will be created in the 'build' subdirectory.

IGV has been tested on **Java 11**. Previous (versions =< 2.6.3) running on Java8 have been deprecated.

NOTE: If on a Windows platform use ```./gradlew.bat'``` in the instructions below

#### Folder structure and build targets

Both [OpenJDK](https://openjdk.java.net/) and [Amazon's Correto Java 11](https://aws.amazon.com/corretto/) distributions have been tested with IGV.

* Install Gradle for your platform.  See https://gradle.org/ for details.

* Use ```./gradlew createDist``` to build a distribution directory (found in ```build/distributions```) containing 
  the igv.jar and its required runtime third-party dependencies (batik-codec, goby, and log4j-core) as
  well as helper scripts for launching.

    * These four JARs will be identical to those available in the download bundles from our website, 
    with the exception that they will not be signed with our certificate (required for JNLP) and
    will have slightly different build properties (timestamp, etc) in about.properties.

    * All four JARs must be in the same location in order to run IGV.  It can be run directly from
    'build/IGV-<YOUR_PLATFORM>'.

    * Launch with `igv.sh` on UNIX, `igv.command` on Mac, and `igv.bat` (or 'igv_32.bat' for 32-bit Java) on Windows.
     These scripts can be edited to adjust JVM flags like maximum memory, etc.

* Use ```./gradlew createToolsDist``` to build an igvtools distribution directory (found in 
  'build/IGVTools-dist') containing the igvtools.jar and dependencies (same as for IGV, above)  
  JAR dependencies plus helper scripts for running and launching.
  As above, these JARs will be identical aside from signing, timestamps, etc. and all must be
  present together to run.  See igvtools_readme.txt for more info.
  
* Use ```./gradlew test``` to run the test suite.  See 'src/test/README.txt' for more information about running
  the tests.

Note that Gradle creates a number of other subdirectories in 'build'.  These can be safely ignored.

#### Amazon Web Services support

For more details on how to use IGV with AWS, please refer to `docs/AWS.md`.
