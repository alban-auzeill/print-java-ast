# Print Sonar's Java analyzer AST

AST stands for Abstract Syntax Tree.

If for an obscure reason you wonder what the Sonar's Java analyzer AST looks like,
this tiny project converts the AST into a human readable text.

## Prerequisite

* Java >= 11

## Build

```shell
./gradlew build 
```
```text
BUILD SUCCESSFUL in 2s
```

## Run

```shell
cat src/test/resources/Sample.java
```
```java
package org.example;

class Sample {
    // This is a comment
}
```
___
```shell
./gradlew --quiet run --args src/test/resources/Sample.java
```
```text
=== src/test/resources/Sample.java ===
COMPILATION_UNIT (CompilationUnitTree)
    PACKAGE (PackageDeclarationTree)
        token@1:1 package
        MEMBER_SELECT (MemberSelectExpressionTree)
            IDENTIFIER (IdentifierTree)
                token@1:9 org
            token@1:12 .
            IDENTIFIER (IdentifierTree)
                token@1:13 example
        token@1:20 ;
    CLASS (ClassTree)
        MODIFIERS (ModifiersTree)
        token@3:1 class
        IDENTIFIER (IdentifierTree)
            token@3:7 Sample
        TYPE_PARAMETERS (TypeParameterListTree)
        LIST (QualifiedIdentifierListTree)
        LIST (QualifiedIdentifierListTree)
        token@3:14 {
        comment@4:5 // This is a comment
        token@5:1 }
    token@6:1 EOF

```
