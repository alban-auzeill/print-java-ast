package com.auzeill.java.ast;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import org.sonar.java.model.InternalSyntaxToken;
import org.sonar.java.model.JParser;
import org.sonar.java.model.JParserConfig;
import org.sonar.java.model.JavaTree;
import org.sonar.java.model.JavaVersionImpl;
import org.sonar.plugins.java.api.JavaVersion;
import org.sonar.plugins.java.api.tree.CompilationUnitTree;
import org.sonar.plugins.java.api.tree.SyntaxTrivia;
import org.sonar.plugins.java.api.tree.Tree;

import static java.nio.charset.StandardCharsets.UTF_8;

public class PrintAST {

  public static void main(String[] args) throws IOException {
    for (String fileName : args) {
      System.out.println("=== " + fileName + " ===");
      CompilationUnitTree ast = parseJava(Path.of(fileName));
      System.out.println(renderAST(ast));
    }
  }

  static CompilationUnitTree parseJava(Path filePath) throws IOException {
    String source = Files.readString(filePath, UTF_8);
    JavaVersion javaVersion = new JavaVersionImpl(JavaVersionImpl.MAX_SUPPORTED);
    List<File> classpath = Collections.emptyList();
    JParserConfig parserConfig = JParserConfig.Mode.FILE_BY_FILE.create(javaVersion, classpath);
    return JParser.parse(parserConfig.astParser(), javaVersion.effectiveJavaVersionAsString(), filePath.toString(), source);
  }

  static String renderAST(Tree node) {
    StringBuilder out = new StringBuilder();
    renderAST(out, node, "");
    return out.toString();
  }

  private static void renderAST(StringBuilder out, Tree node, String indent) {
    if (node.is(Tree.Kind.TOKEN)) {
      InternalSyntaxToken token = (InternalSyntaxToken) node;
      renderComments(out, token.trivias(), indent);
      String position = token.range().start().toString();
      String text = token.isEOF() ? "EOF" : token.text().replaceAll("\\R", " ");
      out.append(indent).append("token@").append(position).append(" ").append(text).append('\n');
    } else {
      String kind = node.kind().name();
      String className = node.getClass().getSimpleName().replaceFirst("Impl$", "");
      out.append(indent).append(kind).append(" (").append(className).append(")\n");
      for (Tree child : ((JavaTree) node).getChildren()) {
        renderAST(out, child, indent + "    ");
      }
    }
  }

  private static void renderComments(StringBuilder out, List<SyntaxTrivia> comments, String indent) {
    for (SyntaxTrivia comment : comments) {
      String position = comment.range().start().toString();
      String text = comment.comment().replaceAll("\\R", " ");
      out.append(indent).append("comment@").append(position).append(" ").append(text).append('\n');
    }
  }
}
