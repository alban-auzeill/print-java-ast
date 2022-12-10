package com.auzeill.java.ast;

import java.io.IOException;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.sonar.plugins.java.api.tree.CompilationUnitTree;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PrintASTTest {
  @Test
  void render() throws IOException {
    Path samplePath = Path.of("src", "test", "resources", "Sample.java");
    CompilationUnitTree tree = PrintAST.parseJava(samplePath);
    assertEquals("" +
      "COMPILATION_UNIT (CompilationUnitTree)\n" +
      "    PACKAGE (PackageDeclarationTree)\n" +
      "        token@1:1 package\n" +
      "        MEMBER_SELECT (MemberSelectExpressionTree)\n" +
      "            IDENTIFIER (IdentifierTree)\n" +
      "                token@1:9 org\n" +
      "            token@1:12 .\n" +
      "            IDENTIFIER (IdentifierTree)\n" +
      "                token@1:13 example\n" +
      "        token@1:20 ;\n" +
      "    CLASS (ClassTree)\n" +
      "        MODIFIERS (ModifiersTree)\n" +
      "        token@3:1 class\n" +
      "        IDENTIFIER (IdentifierTree)\n" +
      "            token@3:7 Sample\n" +
      "        TYPE_PARAMETERS (TypeParameterListTree)\n" +
      "        LIST (QualifiedIdentifierListTree)\n" +
      "        LIST (QualifiedIdentifierListTree)\n" +
      "        token@3:14 {\n" +
      "        comment@4:5 // This is a comment\n" +
      "        token@5:1 }\n" +
      "    token@6:1 EOF\n",
      PrintAST.renderAST(tree));
  }
}
