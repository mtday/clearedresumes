# cr-build-tools

This project provides some infrastructure to support the Maven build process. Currently, this includes an XMl configuration file for the `maven-checkstyle-plugin` (used to verify source code style and formatting), along with a custom checkstyle check that verifies the use of null-checking annotations (`@Nonnull`, `@Nullable`, and `@CheckForNull`) on all non-primitive method return values and parameters. Additionally, this project includes a custom PMD rule-set used during source code validation. This project follows the recommendations in the checkstyle plugin [documentation](https://maven.apache.org/plugins/maven-checkstyle-plugin/examples/multi-module-config.html) for multi-module Maven projects.

The root of this project also includes some IntelliJ settings that can be imported to specify the code style used in this project, along with code inspections used to configured some of the warnings and errors checked by checkstyle.
